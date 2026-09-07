package view;

import java.util.Scanner;

public class MenuInicial {

    // O Scanner vem de fora (do controller). Assim existe um so no programa.
    private Scanner teclado;

    public MenuInicial(Scanner teclado) {
        this.teclado = teclado;
    }

    public int exibir() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         A  N O I T E  L O N G A        ");
        System.out.println("========================================");
        System.out.println("1 - Nova partida");
        System.out.println("2 - Instruções");
        System.out.println("3 - Créditos");
        System.out.println("4 - Sair do jogo");
        System.out.print("\nEscolha: ");
        return lerNumero();
    }

    public void exibirMensagem(String texto) {
        System.out.println(texto);
    }

    public String pedirNome() {
        System.out.println("\nComo você se chama?");
        System.out.print("(ENTER para usar \"Vicente\") > ");
        String nome = teclado.nextLine().trim();
        return nome.isEmpty() ? "Vicente" : nome;
    }

    public void exibirInstrucoes() {
        System.out.println();
        System.out.println("COMO SE JOGA");
        System.out.println();
        System.out.println("Você lê, você escolhe, você vive com isso.");
        System.out.println("Digite o número da alternativa e pressione ENTER.");
        System.out.println();
        System.out.println("Vicente tem três atributos: FÔLEGO, NERVO e LUCIDEZ.");
        System.out.println("Eles sobem e descem conforme o que você faz, e algumas");
        System.out.println("escolhas só aparecem se o atributo for alto o bastante.");
        System.out.println();
        System.out.println("As pessoas que você encontra confiam mais ou menos em você.");
        System.out.println("Quem confia, ajuda. Quem não confia, fecha a porta.");
        System.out.println();
        System.out.println("Escolhas marcadas com [-] existem, mas estão bloqueadas:");
        System.out.println("falta um item, um atributo ou uma decisão que você não tomou.");
        aguardarEnter();
    }

    public void exibirCreditos() {
        System.out.println();
        System.out.println("A NOITE LONGA");
        System.out.println();
        System.out.println("Projeto acadêmico - EXA863 MI Programação");
        System.out.println("Universidade Estadual de Feira de Santana");
        System.out.println();
        System.out.println("Jogo narrativo interativo em Java, arquitetura MVC.");
        System.out.println("Nenhuma engine de jogo foi utilizada.");
        aguardarEnter();
    }

    private int lerNumero() {
        while (true) {
            try {
                int valor = teclado.nextInt();
                teclado.nextLine(); // consome o \n que o nextInt deixou
                return valor;
            } catch (java.util.InputMismatchException e) {
                teclado.nextLine(); // descarta o que nao era numero
                System.out.print("Digite um número: ");
            } catch (java.util.NoSuchElementException e) {
                return 4; // entrada acabou: sai do jogo
            }
        }
    }

    private void aguardarEnter() {
        System.out.print("\n[ENTER] ");
        try {
            teclado.nextLine();
        } catch (java.util.NoSuchElementException e) {
            // entrada acabou, segue
        }
    }
}
