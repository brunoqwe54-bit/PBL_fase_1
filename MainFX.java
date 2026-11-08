import controller.JogoController;
import javafx.application.Application;
import view.TelaMenuFX;

/**
 * Abre o menu em janela.
 *
 * Este main não estende Application de propósito: com um main comum, o
 * JavaFX roda apenas com os jars no classpath, sem --module-path.
 */
public class MainFX {

    public static void main(String[] args) {
        TelaMenuFX.setController(new JogoController());
        Application.launch(TelaMenuFX.class, args);
    }
}
