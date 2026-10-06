package view;

import java.util.Scanner;

public class MenuPausa {

    // O Scanner vem de fora (do controller). Assim existe um só no programa.
    private Scanner teclado;

    public MenuPausa(Scanner teclado) {
        this.teclado = teclado;
    }

    /** Mostra o menu de pausa e devolve o número escolhido (1, 2 ou 3). */
    public int exibir() {
        System.out.println();
        System.out.println("========== MENU ==========");
        System.out.println("1 - Voltar ao jogo");
        System.out.println("2 - Salvar jogo");
        System.out.println("3 - Ir para o menu inicial");

        while (true) {
            System.out.print("\nEscolha: ");
            try {
                int opcao = teclado.nextInt();
                teclado.nextLine();
                if (opcao >= 1 && opcao <= 3) {
                    return opcao;
                }
                System.out.println("Escolha 1, 2 ou 3.");
            } catch (java.util.InputMismatchException e) {
                teclado.nextLine();
                System.out.println("Isso não é um número.");
            } catch (java.util.NoSuchElementException e) {
                return 3; // entrada acabou: sai da partida
            }
        }
    }
}
