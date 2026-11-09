package view;

import atlantafx.base.theme.Dracula;
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

    // A janela do jogo. Fica guardada para qualquer tela poder trocar o conteúdo.
    private static Stage palco;

    @Override
    public void start(Stage janela) throws IOException {
        // Aplica o tema Dracula em todas as telas do jogo.
        // Precisa vir antes de carregar qualquer tela.
        Dracula tema = new Dracula();
        Application.setUserAgentStylesheet(tema.getUserAgentStylesheet());

        palco = janela;
        palco.setTitle("A Noite Longa");
        trocarTela("menu-view.fxml");
        palco.show();
        palco.centerOnScreen();
    }

    /** Mostra na janela a tela do arquivo FXML informado (ex.: "menu-view.fxml"). */
    public static void trocarTela(String arquivoFxml) throws IOException {
        FXMLLoader carregador = new FXMLLoader(TelaMenuFX.class.getResource(arquivoFxml));
        Parent tela = carregador.load();
        palco.setScene(new Scene(tela));
    }
