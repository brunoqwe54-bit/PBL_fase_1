package view;

import model.enums.Preset;

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

    public Preset pedirPreset() {
        Preset[] opcoes = Preset.values();

        System.out.println("\nEscolha seu preset:");
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println();
            System.out.println((i + 1) + " - " + opcoes[i].getNome());
            System.out.println("    " + opcoes[i].getDescricao());
            System.out.println("    Fôlego " + opcoes[i].getFolego()
                             + " | Coragem " + opcoes[i].getCoragem()
                             + " | Lucidez " + opcoes[i].getLucidez());
        }

        while (true) {
            System.out.print("\nEscolha: ");
            int numero = lerNumero();
            if (numero >= 1 && numero <= opcoes.length) {
                return opcoes[numero - 1];
            }
            System.out.println("Escolha um número de 1 a " + opcoes.length + ".");
        }
    }

    /** A sinopse, mostrada depois da escolha do preset e antes do capitulo 1. */
    public void exibirSinopse() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         A  N O I T E  L O N G A        ");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Em Riacho do Fogo, toda criança aprende a mesma regra antes dos sete");
        System.out.println("anos: uma noite por ano, a Procissão passa, e ninguém sai de casa.");
        System.out.println("Não é superstição, não é castigo, nem história de assustar menino. É");
        System.out.println("apenas a ordem das coisas. Você tranca a porta, não olha pela");
        System.out.println("janela, não responde se chamarem seu nome, e espera amanhecer.");
        System.out.println();
        System.out.println("A sua irmã tem dezessete anos. E ela saiu.");
        System.out.println();
        System.out.println("Já passa da meia-noite. O rádio velho do Seu Antônio só vai anunciar");
        System.out.println("o fim do pesadelo às cinco e vinte da manhã — e o que estiver na rua");
        System.out.println("até lá, se torna parte dela. Entre você e o amanhecer, existem oito");
        System.out.println("quarteirões, um riacho, um hospital desativado, o cemitério e");
        System.out.println("pessoas que saíram em outros anos... e nunca mais voltaram direito.");
        System.out.println();
        System.out.println("Você vai atrás dela.");
        System.out.println();
        System.out.println("Mas lá fora, ninguém te ajuda de graça. Cada passo consome um fôlego");
        System.out.println("que não se recupera. O que você escolher dizer para as pessoas nesta");
        System.out.println("noite, elas vão lembrar depois. E o seu primeiro teste de");
        System.out.println("sobrevivência começa agora: se a porta da sua casa estará trancada");
        System.out.println("ou destrancada na sua volta, depende de uma única escolha que você");
        System.out.println("fará nos próximos dois minutos.");
        aguardarEnter();
    }
    public void exibirInstrucoes() {
        System.out.println();
        System.out.println("COMO SE JOGA");
        System.out.println();
        System.out.println("Você lê, você escolhe, você vive com isso.");
        System.out.println("Digite o número da alternativa e pressione ENTER.");
        System.out.println();
        System.out.println("O protagonista tem três atributos: FÔLEGO, CORAGEM e LUCIDEZ.");
        System.out.println("O FÔLEGO só desce, e a água do cantil é o único jeito de");
        System.out.println("recuperar um pouco. Coragem e Lucidez sobem e descem");
        System.out.println("conforme o que você faz, e algumas");
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
        System.out.println("Docente:");
        System.out.println("  Roberto Almeida Bittencourt");
        System.out.println();
        System.out.println("Discentes:");
        System.out.println("  Bruno Gabriel Santos Pinto");
        System.out.println("  Caio Cerqueira Francisco Batista");
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
