package view;

import controller.JogoController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entidades.RegistroDeFinais;

/**
 * Menu inicial em janela.
 *
 * Monta a janela com os mesmos itens do menu do console. Só as opções que
 * já têm equivalente em janela recebem ação; as outras ficam sem handler,
 * então o clique não faz nada.
 */
public class TelaMenuFX extends Application {

    /* Preenchido por MainFX antes do launch: o JavaFX instancia esta
     * classe por reflexão, então o controller não vem pelo construtor. */
    private static JogoController controller;

    public static void setController(JogoController jogoController) {
        controller = jogoController;
    }

    @Override
    public void start(Stage palco) {

        Label titulo = new Label("A  N O I T E  L O N G A");

        Label subtitulo = new Label("Menu principal");

        VBox coluna = new VBox(8);
        coluna.setAlignment(Pos.CENTER);
        coluna.setPadding(new Insets(24));
        coluna.getChildren().addAll(
                titulo,
                subtitulo,
                botao("Nova partida", null),
                botao("Continuar", null),
                botao("Carregar jogo", null),
                botao("Excluir save", null),
                botao("Preferências", null),
                botao("Galeria de finais", this::abrirGaleria),
                botao("Instruções", this::mostrarInstrucoes),
                botao("Créditos", this::mostrarCreditos),
                botao("Sair", Platform::exit)
        );

        palco.setTitle("A Noite Longa");
        palco.setScene(new Scene(coluna, 420, 560));
        palco.show();
        palco.centerOnScreen();
    }

    /** Cria um botão de largura total; com ação nula, o clique não faz nada. */
    private Button botao(String texto, Runnable acao) {
        Button botao = new Button(texto);
        botao.setMaxWidth(Double.MAX_VALUE);
        if (acao != null) {
            botao.setOnAction(evento -> acao.run());
        }
        return botao;
    }

    /** Lista os finais desbloqueados, lidos do controller. */
    private void abrirGaleria() {
        RegistroDeFinais registro = controller.getRegistroDeFinais();

        StringBuilder texto = new StringBuilder();
        texto.append("Finais vistos: ")
             .append(registro.quantosViu())
             .append(" de ")
             .append(RegistroDeFinais.IDS.length)
             .append("\n\n");

        for (int i = 0; i < RegistroDeFinais.IDS.length; i++) {
            if (registro.jaViu(RegistroDeFinais.IDS[i])) {
                texto.append("[x] ").append(RegistroDeFinais.NOMES[i]);
            } else {
                texto.append("[ ] ???");
            }
            texto.append("\n");
        }

        mostrarTexto("Galeria de finais", texto.toString());
    }

    private void mostrarInstrucoes() {
        mostrarTexto("Como se joga", Textos.INSTRUCOES);
    }

    private void mostrarCreditos() {
        mostrarTexto("Créditos", Textos.CREDITOS);
    }

    /** Abre uma janela de aviso com o texto informado. */
    private void mostrarTexto(String titulo, String texto) {
        Alert janela = new Alert(Alert.AlertType.INFORMATION);
        janela.setTitle(titulo);
        janela.setHeaderText(titulo);
        janela.setContentText(texto);
        janela.getDialogPane().setPrefWidth(520);
        janela.showAndWait();
    }
}
