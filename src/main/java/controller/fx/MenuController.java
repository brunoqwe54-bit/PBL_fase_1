package controller.fx;

import view.TelaMenuFX;
import view.Textos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.excecoes.FalhaAoCarregarException;

/** Eventos do menu. O JogoController coordena carregamento e persistência. */
public class MenuController {
    @FXML private Button continuar;

    @FXML private void initialize() {
        continuar.setDisable(!TelaMenuFX.getJogoController().temSave());
    }

    @FXML private void onNovaPartidaClick() { TelaMenuFX.mostrar("nova-partida-view.fxml"); }
    @FXML private void onCarregarClick() { TelaMenuFX.abrirSlots(false); }
    @FXML private void onPreferenciasClick() { TelaMenuFX.abrirPainel("preferencias-view.fxml"); }
    @FXML private void onGaleriaClick() { TelaMenuFX.abrirPainel("galeria-view.fxml"); }
    @FXML private void onInstrucoesClick() { TelaMenuFX.informar("Como se joga", Textos.INSTRUCOES); }
    @FXML private void onCreditosClick() { TelaMenuFX.informar("Créditos", Textos.CREDITOS); }
    @FXML private void onSairClick() { TelaMenuFX.sair(); }

    @FXML private void onContinuarClick() {
        try {
            TelaMenuFX.getJogoController().continuar();
            TelaMenuFX.mostrar("jogo-view.fxml");
        } catch (FalhaAoCarregarException e) {
            TelaMenuFX.erro(e.getMessage());
        }
    }
}
