import controller.JogoController;

/**
 * O ponto de partida do programa.
 *
 * Não faz nada além de criar o JogoController e mandar ele começar. Toda
 * a lógica está nas outras classes, então esta aqui não muda quando a
 * história cresce.
 */
public class Main {
    public static void main(String[] args){
        JogoController jogoController = new JogoController();

        jogoController.iniciarPartida();
    }
}
