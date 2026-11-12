package view;

import controller.SessaoDeJogo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.entidades.Cena;
import model.entidades.Dialogo;
import model.entidades.Escolha;
import model.entidades.Partida;
import model.enums.Atributo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A tela do jogo.
 *
 * Funciona para qualquer cena da história. Mostra um trecho de texto por
 * vez: um para cada parágrafo da narração e um para cada fala. O jogador
 * avança com o botão Continuar (ou com ENTER, que aperta esse botão).
 * Terminados os textos, as escolhas aparecem por cima do cenário.
 *
 * Ao escolher, a SessaoDeJogo aplica os efeitos e devolve o resumo do que
 * mudou; esse resumo também espera o Continuar antes da próxima cena.
 */
public class JogoViewController {

    @FXML private Region cenario;
    @FXML private Label tituloCena;
    @FXML private Label status;
    @FXML private Label texto;
    @FXML private Button botaoContinuar;
    @FXML private VBox opcoes;

    private SessaoDeJogo sessao = TelaMenuFX.getSessao();

    // Trechos de texto da cena atual (parágrafos e falas) e qual está na tela.
    private List<String> trechos = new ArrayList<>();
    private int posicao;

    // true quando a tela está mostrando a consequência de uma escolha.
    private boolean esperandoProximaCena;

    /** O JavaFX chama este método sozinho, logo depois de carregar o FXML. */
    @FXML
    private void initialize() {
        exibirCena();
    }

    /** Monta os trechos da cena atual e mostra o primeiro. */
    private void exibirCena() {
        Cena cena = sessao.getCenaAtual();

        tituloCena.setText(cena.getTitulo());
        trocarFundo(cena.getId());
        atualizarStatus();
        esconderOpcoes();

        trechos.clear();
        String[] paragrafos = cena.getTextoPrincipal().split("\n\n");
        for (String paragrafo : paragrafos) {
            trechos.add(paragrafo);
        }
        for (Dialogo fala : cena.getDialogos()) {
            trechos.add(fala.getPersonagem().getNome() + ": " + fala.getTexto());
        }
        if (sessao.cenaFinal()) {
            trechos.add("FIM DE JOGO");
        }

        posicao = 0;
        esperandoProximaCena = false;
        mostrarTrecho();
    }

    private void mostrarTrecho() {
        texto.setText(trechos.get(posicao));
        mostrarBotaoContinuar(true);
    }

    /** Botão Continuar (ou ENTER): próximo trecho, escolhas ou volta ao menu. */
    @FXML
    private void onContinuarClick() {
        if (esperandoProximaCena) {
            exibirCena();
            return;
        }
        if (posicao < trechos.size() - 1) {
            posicao++;
            mostrarTrecho();
            return;
        }
        if (sessao.cenaFinal()) {
            voltarAoMenu();
            return;
        }
        mostrarOpcoes();
    }

    /** Escolhas por cima do cenário, com as bloqueadas desabilitadas. */
    private void mostrarOpcoes() {
        opcoes.getChildren().clear();
        mostrarBotaoContinuar(false);   // agora quem decide é o clique numa escolha

        for (Escolha escolha : sessao.disponiveis()) {
            Button botao = new Button(escolha.getTextoExibido());
            botao.setMaxWidth(Double.MAX_VALUE);
            // Os botões das escolhas mudam a cada cena, por isso são criados
            // aqui e não no FXML. Lendo a linha: "quando clicar neste botão,
            // chama escolher() com esta escolha".
            botao.setOnAction(evento -> escolher(escolha));
            opcoes.getChildren().add(botao);
        }
        for (Escolha escolha : sessao.bloqueadas()) {
            Button botao = new Button("[-] " + escolha.getTextoExibido());
            botao.setMaxWidth(Double.MAX_VALUE);
            botao.setDisable(true);
            // Passando o mouse por cima, aparece o motivo do bloqueio.
            botao.setTooltip(new Tooltip(escolha.getMotivoDoBloqueio()));
            opcoes.getChildren().add(botao);
        }

        opcoes.setVisible(true);
    }

    private void escolher(Escolha escolha) {
        String aviso = sessao.escolher(escolha);
        esconderOpcoes();

        if (aviso == null) {
            exibirCena();
            return;
        }
        // Mostra o que mudou e só segue para a próxima cena depois do Continuar.
        texto.setText(aviso);
        mostrarBotaoContinuar(true);
        esperandoProximaCena = true;
    }

    private void esconderOpcoes() {
        opcoes.setVisible(false);
        opcoes.getChildren().clear();
    }

    /**
     * Mostra ou esconde o botão Continuar. Quando escondido, ele também fica
     * desabilitado, para o ENTER não apertar o botão enquanto as escolhas
     * estão na tela.
     */
    private void mostrarBotaoContinuar(boolean mostrar) {
        botaoContinuar.setVisible(mostrar);
        botaoContinuar.setDisable(!mostrar);
    }

    private void atualizarStatus() {
        Partida partida = sessao.getPartida();
        status.setText("Fôlego " + partida.getProtagonista().getAtributo(Atributo.FOLEGO)
                + "  |  Coragem " + partida.getProtagonista().getAtributo(Atributo.CORAGEM)
                + "  |  Lucidez " + partida.getProtagonista().getAtributo(Atributo.LUCIDEZ)
                + "   Mochila: " + partida.getInventario().listar());
    }

    /** Troca o cenário quando a cena tem imagem; cena sem imagem mantém a anterior. */
    private void trocarFundo(String idCena) {
        String arquivo = arquivoDoCenario(idCena);
        if (arquivo == null) {
            return;
        }
        URL recurso = TelaMenuFX.class.getResource("img/cenario/" + arquivo);
        if (recurso != null) {
            cenario.setStyle("-fx-background-image: url('" + recurso.toExternalForm() + "');"
                    + "-fx-background-size: cover; -fx-background-position: center center;");
        }
    }

    /**
     * Qual imagem de fundo usar em cada cena. É decisão de aparência, por
     * isso fica aqui na view. Cena que não está na lista devolve null.
     */
    private String arquivoDoCenario(String idCena) {
        switch (idCena) {
            case "CAP01":
                return "casa/entrance-noite.jpg";
            case "CAP02":
            case "CAP02B":
                return "rua/street-noite.jpg";
            case "CAP03":
                return "praca/park-noite.jpg";
            case "CAP03B":
                return "praca/smallpark-noite.jpg";
            case "CAP04":
                return "armazem/warehouse_control-noite.jpg";
            case "CAP04B":
                return "armazem/warehouse_floor-noite.jpg";
            case "CAP05":
                return "rua/alley-noite.jpg";
            case "CAP06":
            case "CAP06B":
                return "diversos/cell-noite.jpg";
            case "CAP07":
                return "casa/lounge-noite.jpg";
            case "CAP07B":
                return "casa/hallway-noite.jpg";
            default:
                return null;
        }
    }

    private void voltarAoMenu() {
        try {
            TelaMenuFX.trocarTela("menu-view.fxml");
        } catch (IOException e) {
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setContentText("Não foi possível voltar ao menu: " + e.getMessage());
            erro.showAndWait();
        }
    }
}
