package view;

import controller.JogoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Abre a janela do jogo e mostra o menu inicial.
 *
 * A tela do menu em si está no menu-view.fxml, e o que os botões fazem
 * está no MenuController. Esta classe só cria a janela e carrega o FXML.
 */
public class TelaMenuFX extends Application {

    /* Preenchido por MainFX antes do launch: o JavaFX cria esta classe
     * sozinho, então o controller não pode vir pelo construtor. Fica
     * guardado aqui para as telas (como o MenuController) pegarem. */
    private static JogoController controller;

    public static void setController(JogoController jogoController) {
        controller = jogoController;
    }

    public static JogoController getController() {
        return controller;
    }

    @Override
    public void start(Stage palco) throws IOException {
        FXMLLoader carregador = new FXMLLoader(TelaMenuFX.class.getResource("menu-view.fxml"));
        Parent menu = carregador.load();

        palco.setTitle("A Noite Longa");
        palco.setScene(new Scene(menu));
        palco.show();
        palco.centerOnScreen();
    }
}
