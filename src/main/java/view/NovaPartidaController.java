package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
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

    /** O JavaFX chama este método sozinho, logo depois de carregar o FXML. */
    @FXML
    private void initialize() {
        colocarFundo();

        // O nome que aparece em cada opção vem do toString() do Preset.
        comboPreset.getItems().addAll(Preset.values());
        comboPreset.setValue(Preset.COMUM);
        atualizarDescricao();
    }

    /** Chamado pelo onAction da caixa de preset, sempre que o jogador troca a opção. */
    @FXML
    private void onPresetEscolhido() {
        atualizarDescricao();
    }

    @FXML
    private void onComecarClick() {
        String nome = campoNome.getText().trim();
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

    /** Mostra a descrição do preset escolhido, com os três atributos iniciais. */
    private void atualizarDescricao() {
        Preset preset = comboPreset.getValue();
        if (preset == null) {
            descricaoPreset.setText("");
            return;
        }
        descricaoPreset.setText(preset.getDescricao()
                + "\nFôlego " + preset.getFolego()
                + " | Coragem " + preset.getCoragem()
                + " | Lucidez " + preset.getLucidez());
    }

    private void colocarFundo() {
        URL recurso = TelaMenuFX.class.getResource(CENARIO);
        if (recurso != null) {
            cenario.setStyle("-fx-background-image: url('" + recurso.toExternalForm() + "');"
                    + "-fx-background-size: cover; -fx-background-position: center center;");
        }
    }

    private void trocarPara(String arquivoFxml) {
        try {
            TelaMenuFX.trocarTela(arquivoFxml);
        } catch (IOException e) {
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setContentText("Não foi possível abrir " + arquivoFxml + ": " + e.getMessage());
            erro.showAndWait();
        }
    }
}
