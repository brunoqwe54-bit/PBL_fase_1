package controller.fx;

import view.TelaMenuFX;

import controller.ObservadorDeJogo;
import controller.JogoController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.entidades.*;
import model.enums.Atributo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/** Apresentação da partida. O JogoController coordena as decisões e os saves. */
public class JogoViewController implements ObservadorDeJogo {
    @FXML private Region cenario;
    @FXML private Label tituloCena, status, texto, avisos, nomePersonagem, progressoTexto;
    @FXML private ProgressBar progresso;
    @FXML private Button botaoContinuar;
    @FXML private VBox opcoes, personagemVisual;
    @FXML private ScrollPane leitura, listaOpcoes;

    private final JogoController jogoController = TelaMenuFX.getJogoController();
    private final List<Trecho> trechos = new ArrayList<>();
    private int posicao;
    private boolean esperandoProximaCena;
    private boolean leituraRapida;

    /** Um trecho de narração tem personagem null; uma fala tem seu autor. */
    private record Trecho(String texto, PersonagemBase personagem) { }

    @FXML private void initialize() { exibirCena(); }

    private void exibirCena() {
        leituraRapida = jogoController.getPreferencias().isPularEnter();
        Cena cena = jogoController.getCenaAtual();
        tituloCena.setText(cena.getTitulo());
        trocarFundo(cena.getId());
        atualizar(jogoController.getUltimaMensagem());
        esconderOpcoes();
        trechos.clear();
        for (String paragrafo : cena.getTextoPrincipal().split("\n\n")) {
            String narrativa = paragrafo.replace('\n', ' ').replace("VICENTE:",
                    jogoController.getPartida().getProtagonista().getNome() + ":");
            trechos.add(new Trecho(narrativa, null));
        }
        for (Dialogo fala : cena.getDialogos()) {
            trechos.add(new Trecho(fala.getTexto(), fala.getPersonagem()));
        }
        if (jogoController.cenaFinal()) trechos.add(new Trecho("FIM DE JOGO · Outras escolhas revelam novos desfechos.", null));
        posicao = 0;
        esperandoProximaCena = false;
        if (leituraRapida) {
            StringBuilder tudo = new StringBuilder();
            for (Trecho trecho : trechos) {
                if (trecho.personagem() != null) tudo.append(trecho.personagem().getNome()).append(": ");
                tudo.append(trecho.texto()).append("\n\n");
            }
            texto.setText(tudo.toString().trim());
            personagemVisual.setVisible(false);
            leitura.setVvalue(0);
            mostrarOpcoes();
            mostrarBotaoContinuar(jogoController.cenaFinal());
            botaoContinuar.setText("Voltar ao menu");
        } else {
            mostrarTrecho();
        }
    }

    private void mostrarTrecho() {
        Trecho trecho = trechos.get(posicao);
        texto.setText(trecho.texto());
        leitura.setVvalue(0);
        PersonagemBase pessoa = trecho.personagem();
        personagemVisual.setVisible(pessoa != null);
        if (pessoa != null) nomePersonagem.setText(pessoa.getNome());
        mostrarBotaoContinuar(true);
        botaoContinuar.setText(jogoController.cenaFinal() && posicao == trechos.size() - 1
                ? "Voltar ao menu" : "Continuar  [ENTER]");
    }

    @FXML private void onContinuarClick() {
        if (esperandoProximaCena) exibirCena();
        else if (jogoController.getPreferencias().isPularEnter() && jogoController.cenaFinal()) onMenuInicialClick();
        else if (posicao < trechos.size() - 1) { posicao++; mostrarTrecho(); }
        else if (jogoController.cenaFinal()) onMenuInicialClick();
        else mostrarOpcoes();
    }

    private void mostrarOpcoes() {
        opcoes.getChildren().clear();
        mostrarBotaoContinuar(false);
        for (Escolha escolha : jogoController.getCenaAtual().getOpcoes()) {
            boolean disponivel = escolha.estaDisponivel(jogoController.getPartida());
            Button botao = new Button(escolha.getTextoExibido());
            botao.setMaxWidth(Double.MAX_VALUE);
            botao.setWrapText(true);
            botao.setDisable(!disponivel);
            botao.setOnAction(evento -> escolher(escolha));
            opcoes.getChildren().add(botao);
            if (!disponivel) {
                Label motivo = new Label("Bloqueada: " + escolha.getMotivoDoBloqueio(jogoController.getPartida()));
                motivo.getStyleClass().add("motivo");
                motivo.setWrapText(true);
                opcoes.getChildren().add(motivo);
            }
        }
        listaOpcoes.setVisible(!jogoController.cenaFinal());
        listaOpcoes.setVvalue(0);
    }

    private void escolher(Escolha escolha) {
        String aviso = jogoController.escolher(escolha);
        esconderOpcoes();
        personagemVisual.setVisible(false);
        if (aviso == null) { exibirCena(); return; }
        texto.setText(aviso);
        leitura.setVvalue(0);
        mostrarBotaoContinuar(true);
        botaoContinuar.setText("Continuar  [ENTER]");
        esperandoProximaCena = true;
    }

    private void esconderOpcoes() {
        listaOpcoes.setVisible(false);
        opcoes.getChildren().clear();
    }

    private void mostrarBotaoContinuar(boolean mostrar) {
        botaoContinuar.setVisible(mostrar);
        botaoContinuar.setManaged(mostrar);
        botaoContinuar.setDisable(!mostrar);
        if (mostrar) Platform.runLater(botaoContinuar::requestFocus);
    }

    /** Volta de um painel mantendo o trecho lido e as escolhas na tela. */
    public void retomar() {
        if (leituraRapida != jogoController.getPreferencias().isPularEnter()) exibirCena();
        else {
            atualizar(jogoController.getUltimaMensagem());
            if (botaoContinuar.isVisible()) Platform.runLater(botaoContinuar::requestFocus);
        }
    }

    /** Observer: o HUD muda ao escolher ou salvar, sem repetir a narração. */
    @Override public void atualizar(String mensagem) {
        if (jogoController.getPartida() == null) return;
        Partida partida = jogoController.getPartida();
        status.setText("Fôlego " + partida.getProtagonista().getAtributo(Atributo.FOLEGO)
                + "   ·   Coragem " + partida.getProtagonista().getAtributo(Atributo.CORAGEM)
                + "   ·   Lucidez " + partida.getProtagonista().getAtributo(Atributo.LUCIDEZ));
        progresso.setProgress(jogoController.getProgresso() / 100.0);
        progressoTexto.setText(jogoController.getProgresso() + "%");
        avisos.setText(mensagem == null ? "" : mensagem);
    }

    private void trocarFundo(String id) {
        String arquivo = switch (id) {
            case "CAP01" -> "casa/entrance-noite.jpg";
            case "CAP02", "CAP02B", "MORTE_NOME" -> "rua/street-noite.jpg";
            case "CAP03", "MORTE_FILA" -> "praca/park-noite.jpg";
            case "CAP03B" -> "praca/smallpark-noite.jpg";
            case "CAP04" -> "armazem/warehouse_control-noite.jpg";
            case "CAP04B", "MORTE_JANELA" -> "armazem/warehouse_floor-noite.jpg";
            case "CAP05" -> "rua/alley-noite.jpg";
            case "CAP06", "CAP06B", "MORTE_PÁTIO" -> "diversos/cell-noite.jpg";
            case "CAP07", "MORTE_CASARÃO" -> "casa/lounge-noite.jpg";
            case "CAP07B" -> "casa/hallway-noite.jpg";
            case "CAP08", "CAP08B", "MORTE_VELA" -> "rua/alley-noite.jpg";
            case "FIM_A_TEMPO", "FIM_TARDE" -> "praca/park.jpg";
            case "FIM_DESISTE" -> "casa/kitchen.jpg";
            default -> "praca/park-noite.jpg";
        };
        URL recurso = TelaMenuFX.class.getResource("img/cenario/" + arquivo);
        cenario.setStyle("-fx-background-image: url('" + recurso.toExternalForm()
                + "'); -fx-background-size: cover; -fx-background-position: center;");
    }

    @FXML private void onSalvarClick() { TelaMenuFX.abrirSlots(true); }
    @FXML private void onCarregarClick() { TelaMenuFX.abrirSlots(false); }
    @FXML private void onStatusClick() { TelaMenuFX.abrirPainel("status-view.fxml"); }
    @FXML private void onPreferenciasClick() { TelaMenuFX.abrirPainel("preferencias-view.fxml"); }
    @FXML private void onSairClick() { TelaMenuFX.sair(); }

    @FXML private void onReiniciarClick() {
        if (TelaMenuFX.confirmar("Reiniciar a partida? Você voltará à criação do protagonista.")) {
            jogoController.encerrarPartida();
            TelaMenuFX.mostrar("nova-partida-view.fxml");
        }
    }

    @FXML private void onMenuInicialClick() {
        if (TelaMenuFX.confirmarPerda()) {
            jogoController.encerrarPartida();
            TelaMenuFX.mostrar("menu-view.fxml");
        }
    }
}
