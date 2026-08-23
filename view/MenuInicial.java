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
    public void iniciarJogo () {
        System.out.println("o jogo está sendo iniciado");
    }
    public void terminarJogo () {
        System.out.println("Encerrando jogo");
    }
    public void erro () {
        System.out.println("Opção inválida! Tente novamente.");
    }


    }



