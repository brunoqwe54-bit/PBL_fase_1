package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.entidades.RegistroDeFinais;

/** Finais descobertos ficam visíveis; os demais preservam a surpresa. */
public class GaleriaController {
    @FXML private Label resumo;
    @FXML private VBox finais;

    @FXML private void initialize() {
        RegistroDeFinais registro = TelaMenuFX.getSessao().getRegistroDeFinais();
        resumo.setText(registro.quantosViu() + " de " + RegistroDeFinais.IDS.length + " finais descobertos");
        for (int i = 0; i < RegistroDeFinais.IDS.length; i++) {
            boolean visto = registro.jaViu(RegistroDeFinais.IDS[i]);
            Label nome = new Label(visto ? RegistroDeFinais.NOMES[i] : "???");
            nome.getStyleClass().add("subtitulo");
            Label estado = new Label(visto ? "DESBLOQUEADO" : "Continue explorando a noite para descobrir.");
            VBox cartao = new VBox(6, nome, estado);
            cartao.getStyleClass().add(visto ? "cartao" : "cartao-bloqueado");
            finais.getChildren().add(cartao);
        }
    }

    @FXML private void onVoltarClick() { TelaMenuFX.voltar(); }
}
