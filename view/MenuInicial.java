package view;

import java.util.Scanner;

public class MenuInicial {
    private Scanner teclado = new Scanner(System.in);

    public int exibir() {
        System.out.println("==================");
        System.out.println("    TITULO   ");
        System.out.println("==================");
        System.out.println("1 - inciar a partida");
        System.out.println("2 - Sair do jogo");

        // Captura a opção e manda para o controller
        return teclado.nextInt();}
    // Na sua View (MenuInicial):
    public void exibirMensagem(String texto) {
        System.out.println(texto);
    }

    }



