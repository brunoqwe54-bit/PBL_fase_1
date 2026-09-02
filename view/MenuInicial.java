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
        int opcao = teclado.nextInt();
        teclado.nextLine(); // consome o \n deixado pelo nextInt()
        return opcao;}
    // Na sua View (MenuInicial):
    public void exibirMensagem(String texto) {
        System.out.println(texto);
    }
    public String pedirNome() {
        System.out.println("Como você se chama?");
        System.out.print("(ENTER para usar \"Vicente\") > ");
        String nome = teclado.nextLine().trim();
        return nome.isEmpty() ? "Vicente" : nome;
    }
    }



