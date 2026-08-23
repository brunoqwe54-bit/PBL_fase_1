import controller.JogoController;
import view.MenuInicial;

public class Main {
    public static void main(String[] args){
        JogoController jogoController = new JogoController();

        jogoController.iniciarPartida();
    }
}
