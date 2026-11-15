package view;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.enums.Preset;

/** Lê o formulário e pede à sessão para começar a partida. */
public class NovaPartidaController {
    @FXML private TextField campoNome;
    @FXML private ComboBox<Preset> comboPreset;
    @FXML private Label descricaoPreset;

    @FXML private void initialize() {
        comboPreset.getItems().addAll(Preset.values());
        comboPreset.setValue(TelaMenuFX.getSessao().getPreferencias().getPresetPadrao());
        atualizarDescricao();
    }

    @FXML private void onPresetEscolhido() { atualizarDescricao(); }

    private void atualizarDescricao() {
        Preset preset = comboPreset.getValue();
        if (preset != null) {
            descricaoPreset.setText(preset.getDescricao() + "\n\nFôlego " + preset.getFolego()
                    + "  ·  Coragem " + preset.getCoragem() + "  ·  Lucidez " + preset.getLucidez());
        }
    }

    @FXML private void onComecarClick() {
        TelaMenuFX.getSessao().novaPartida(campoNome.getText(), comboPreset.getValue());
        TelaMenuFX.mostrar("jogo-view.fxml");
    }

    @FXML private void onVoltarClick() { TelaMenuFX.mostrar("menu-view.fxml"); }
}
