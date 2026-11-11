package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.entidades.RegistroDeFinais;

import java.io.IOException;

/**
 * O que acontece quando o jogador clica nos botões do menu inicial.
 *
 * A aparência do menu está no arquivo menu-view.fxml. Cada botão de lá tem
 * um onAction="#nomeDoMetodo" que chama um dos métodos com @FXML daqui.
 */
public class MenuController {

    @FXML
    private void onNovaPartidaClick() {
        try {
            TelaMenuFX.trocarTela("nova-partida-view.fxml");
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível abrir a criação de partida: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    private void onGaleriaClick() {
        RegistroDeFinais registro = TelaMenuFX.getController().getRegistroDeFinais();

        String texto = "Finais vistos: " + registro.quantosViu()
                + " de " + RegistroDeFinais.IDS.length + "\n\n";

        // Final já visto mostra o nome; final ainda não visto fica escondido.
        for (int i = 0; i < RegistroDeFinais.IDS.length; i++) {
            if (registro.jaViu(RegistroDeFinais.IDS[i])) {
                texto = texto + "[x] " + RegistroDeFinais.NOMES[i] + "\n";
            } else {
                texto = texto + "[ ] ???\n";
            }
        }

        mostrarTexto("Galeria de finais", texto);
    }

    @FXML
    private void onInstrucoesClick() {
        mostrarTexto("Como se joga", Textos.INSTRUCOES);
    }

    @FXML
    private void onCreditosClick() {
        mostrarTexto("Créditos", Textos.CREDITOS);
    }

    @FXML
    private void onSairClick() {
        Platform.exit();
    }

    /** Abre uma janelinha de aviso com o título e o texto informados. */
    private void mostrarTexto(String titulo, String texto) {
        Alert janela = new Alert(Alert.AlertType.INFORMATION);
        janela.setTitle(titulo);
        janela.setHeaderText(titulo);
        janela.setContentText(texto);
        janela.getDialogPane().setPrefWidth(520);
        janela.showAndWait();
    }
}
