package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import model.enums.Personagens;
import model.enums.Preset;

import java.io.IOException;
import java.net.URL;

/**
 * O que acontece na tela de criação do protagonista.
 *
 * Lê o nome digitado e o preset escolhido, manda a SessaoDeJogo começar a
 * partida e troca a janela para a tela do jogo. O fundo é o cenário do
 * capítulo 1, para a tela já ter a cara do jogo.
 */
public class NovaPartidaController {

    private static final String CENARIO = "img/cenario/casa/entrance-noite.jpg";

    @FXML private Region cenario;
    @FXML private TextField campoNome;
    @FXML private ComboBox<Preset> comboPreset;
    @FXML private Label descricaoPreset;

    @FXML
    private void initialize() {
        URL recurso = TelaMenuFX.class.getResource(CENARIO);
        if (recurso != null) {
            cenario.setStyle("-fx-background-image: url('" + recurso.toExternalForm() + "');"
                    + "-fx-background-size: cover; -fx-background-position: center center;");
        }

        comboPreset.getItems().setAll(Preset.values());
        comboPreset.setConverter(new StringConverter<Preset>() {
            @Override
            public String toString(Preset preset) {
                return preset == null ? "" : preset.getNome();
            }

            @Override
            public Preset fromString(String texto) {
                return null;   // o combo só é usado para escolher, nunca para digitar
            }
        });
        comboPreset.getSelectionModel().selectedItemProperty().addListener(
                (observado, antes, agora) -> descricaoPreset.setText(descricaoDe(agora)));
        comboPreset.getSelectionModel().select(Preset.COMUM);
    }

    @FXML
    private void onComecarClick() {
        String nome = campoNome.getText() == null ? "" : campoNome.getText().trim();
        if (nome.isEmpty()) {
            nome = Personagens.VICENTE.getNomeExibicao();
        }

        TelaMenuFX.getSessao().novaPartida(nome, comboPreset.getValue());
        trocarPara("jogo-view.fxml");
    }

    @FXML
    private void onVoltarClick() {
        trocarPara("menu-view.fxml");
    }

    /** Descrição do preset com os três atributos iniciais. */
    private String descricaoDe(Preset preset) {
        if (preset == null) {
            return "";
        }
        return preset.getDescricao()
                + "\nFôlego " + preset.getFolego()
                + " | Coragem " + preset.getCoragem()
                + " | Lucidez " + preset.getLucidez();
    }

    private void trocarPara(String arquivoFxml) {
        try {
            TelaMenuFX.trocarTela(arquivoFxml);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível abrir " + arquivoFxml + ": " + e.getMessage())
                    .showAndWait();
        }
    }
}
