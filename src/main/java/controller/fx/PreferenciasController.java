package controller.fx;

import view.TelaMenuFX;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import model.entidades.Preferencias;
import model.enums.Preset;
import model.excecoes.FalhaAoSalvarException;

/** Três preferências persistentes, editadas sem alterar a partida. */
public class PreferenciasController {
    @FXML private ComboBox<Preset> preset;
    @FXML private CheckBox leituraRapida, autosave;

    @FXML private void initialize() {
        Preferencias preferencias = TelaMenuFX.getSessao().getPreferencias();
        preset.getItems().addAll(Preset.values());
        preset.setValue(preferencias.getPresetPadrao());
        leituraRapida.setSelected(preferencias.isPularEnter());
        autosave.setSelected(preferencias.isAutosaveLigado());
    }

    @FXML private void onSalvarClick() {
        try {
            TelaMenuFX.getSessao().alterarPreferencias(preset.getValue(), leituraRapida.isSelected(), autosave.isSelected());
            TelaMenuFX.voltar();
        } catch (FalhaAoSalvarException e) { TelaMenuFX.erro(e.getMessage()); }
    }

    @FXML private void onVoltarClick() { TelaMenuFX.voltar(); }
}
