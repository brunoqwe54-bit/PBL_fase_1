package view;

import controller.SessaoDeJogo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A tela do jogo.
 *
 * Funciona para qualquer cena da história. Mostra um trecho de texto por
 * vez e espera ENTER, igual ao jogo em console: um trecho para cada
 * parágrafo da narração e um para cada fala. Terminados os textos, as
 * escolhas aparecem por cima do cenário.
 *
 * Ao escolher, a SessaoDeJogo aplica os efeitos e devolve o resumo do que
 * mudou; esse resumo também espera ENTER antes da próxima cena.
 *
 * A tabela CENARIOS liga o id da cena ao arquivo de fundo: é decisão de
 * aparência, por isso fica aqui na view.
 */
public class JogoViewController {

    private static final Map<String, String> CENARIOS = new LinkedHashMap<>();

    static {
        CENARIOS.put("CAP01", "casa/entrance-noite.jpg");
        CENARIOS.put("CAP02", "rua/street-noite.jpg");
        CENARIOS.put("CAP02B", "rua/street-noite.jpg");
        CENARIOS.put("CAP03", "praca/park-noite.jpg");
        CENARIOS.put("CAP03B", "praca/smallpark-noite.jpg");
        CENARIOS.put("CAP04", "armazem/warehouse_control-noite.jpg");
        CENARIOS.put("CAP04B", "armazem/warehouse_floor-noite.jpg");
        CENARIOS.put("CAP05", "rua/alley-noite.jpg");
        CENARIOS.put("CAP06", "diversos/cell-noite.jpg");
        CENARIOS.put("CAP06B", "diversos/cell-noite.jpg");
        CENARIOS.put("CAP07", "casa/lounge-noite.jpg");
        CENARIOS.put("CAP07B", "casa/hallway-noite.jpg");
    }

    @FXML private BorderPane raiz;
    @FXML private Region cenario;
    @FXML private Label tituloCena;
    @FXML private Label status;
    @FXML private Label texto;
    @FXML private Label dica;
    @FXML private VBox opcoes;

    private final SessaoDeJogo sessao = TelaMenuFX.getSessao();

    // Trechos de texto da cena atual (parágrafos e falas) e em qual está.
    private final List<String> trechos = new ArrayList<>();
    private int posicao;

    // true quando a tela está mostrando a consequência de uma escolha.
    private boolean esperandoProximaCena;

    @FXML
    private void initialize() {
        raiz.sceneProperty().addListener((observado, antes, cena) -> {
            if (cena != null) {
                cena.setOnKeyPressed(evento -> {
                    if (evento.getCode() == KeyCode.ENTER) {
                        avancar();
                    }
                });
            }
        });
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
        for (String paragrafo : cena.getTextoPrincipal().split("\n\n")) {
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
        dica.setVisible(true);
    }

    /** ENTER: próximo trecho, escolhas ou volta ao menu. */
    private void avancar() {
        if (esperandoProximaCena) {
            exibirCena();
            return;
        }
        if (opcoes.isVisible()) {
            return;   // com as escolhas na tela, quem decide é o clique
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
        dica.setVisible(false);

        for (Escolha escolha : sessao.disponiveis()) {
            Button botao = new Button(escolha.getTextoExibido());
            botao.setMaxWidth(Double.MAX_VALUE);
            botao.setOnAction(evento -> escolher(escolha));
            opcoes.getChildren().add(botao);
        }
        for (Escolha escolha : sessao.bloqueadas()) {
            Button botao = new Button("[-] " + escolha.getTextoExibido());
            botao.setMaxWidth(Double.MAX_VALUE);
            botao.setDisable(true);
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
        // O console mostra o que mudou e só segue depois do ENTER.
        texto.setText(aviso);
        dica.setVisible(true);
        esperandoProximaCena = true;
    }

    private void esconderOpcoes() {
        opcoes.setVisible(false);
        opcoes.getChildren().clear();
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
        String arquivo = CENARIOS.get(idCena);
        if (arquivo == null) {
            return;
        }
        URL recurso = TelaMenuFX.class.getResource("img/cenario/" + arquivo);
        if (recurso != null) {
            cenario.setStyle("-fx-background-image: url('" + recurso.toExternalForm() + "');"
                    + "-fx-background-size: cover; -fx-background-position: center center;");
        }
    }

    private void voltarAoMenu() {
        try {
            TelaMenuFX.trocarTela("menu-view.fxml");
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível voltar ao menu: " + e.getMessage()).showAndWait();
        }
    }
}
