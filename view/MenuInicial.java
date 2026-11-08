package view;

import controller.JogoController;
import model.enums.Personagens;
import model.enums.Preset;

import java.util.Scanner;

/**
 * A tela de antes da partida: menu, sinopse, instruções e créditos.
 *
 * É aqui que o jogador escolhe uma opção do menu, digita o nome do
 * protagonista e escolhe um dos quatro presets. Como View, é esta classe
 * que inicia o laço do menu e envia a opção escolhida ao controller.
 */
public class MenuInicial {

    private Scanner teclado;

    public MenuInicial(Scanner teclado) {
        this.teclado = teclado;
    }

    /** A View conduz o laço do menu e delega cada opção ao Controller. */
    public void iniciar(JogoController jogoController) {
        boolean rodando = true;
        while (rodando) {
            rodando = jogoController.processarOpcao(exibir());
        }
    }

    public int exibir() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         A  N O I T E  L O N G A        ");
        System.out.println("========================================");
        System.out.println("1 - Nova partida");
        System.out.println("2 - Continuar");
        System.out.println("3 - Carregar jogo");
        System.out.println("4 - Excluir save");
        System.out.println("5 - Preferências");
        System.out.println("6 - Galeria de finais");
        System.out.println("7 - Instruções");
        System.out.println("8 - Créditos");
        System.out.println("9 - Sair do jogo");
        System.out.print("\nEscolha: ");
        return lerNumero();
    }

    public void exibirMensagem(String texto) {
        System.out.println(texto);
    }

    public String pedirNome() {
        System.out.println("\nComo você se chama?");
        System.out.print("(ENTER para usar \"" + Personagens.VICENTE.getNomeExibicao() + "\") > ");
        String nome = teclado.nextLine().trim();
        return nome.isEmpty() ? Personagens.VICENTE.getNomeExibicao() : nome;
    }

    /** Mostra os presets. ENTER escolhe o padrão (o das preferências). */
    public Preset pedirPreset(Preset padrao) {
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
            System.out.print("\n(ENTER para usar \"" + padrao.getNome() + "\") Escolha: ");
            String texto;
            try {
                texto = teclado.nextLine().trim();
            } catch (java.util.NoSuchElementException e) {
                return padrao; // entrada acabou
            }
            if (texto.isEmpty()) {
                return padrao;
            }
            try {
                int numero = Integer.parseInt(texto);
                if (numero >= 1 && numero <= opcoes.length) {
                    return opcoes[numero - 1];
                }
            } catch (NumberFormatException e) {
                // não era número: cai na mensagem de baixo
            }
            System.out.println("Escolha um número de 1 a " + opcoes.length + ".");
        }
    }

    /** A sinopse, mostrada depois da escolha do preset e antes do capítulo 1. */
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
        System.out.println("noite, elas vão lembrar depois.");
        System.out.println("Seu primeiro teste de sobrevivência começa agora.");
        aguardarEnter();
    }
    /* Texto compartilhado com a versão em janela. */
    public void exibirInstrucoes() {
        System.out.println();
        System.out.println(Textos.INSTRUCOES);
        aguardarEnter();
    }

    /* Texto compartilhado com a versão em janela. */
    public void exibirCreditos() {
        System.out.println();
        System.out.println(Textos.CREDITOS);
        aguardarEnter();
    }

    private int lerNumero() {
        while (true) {
            try {
                int valor = teclado.nextInt();
                teclado.nextLine(); // consome o \n que o nextInt deixou
                return valor;
            } catch (java.util.InputMismatchException e) {
                teclado.nextLine(); // descarta o que não era número
                System.out.print("Digite um número: ");
            } catch (java.util.NoSuchElementException e) {
                return 9; // entrada acabou: sai do jogo
            }
        }
    }

    private void aguardarEnter() {
        System.out.print("\n[ENTER] ");
        try {
            teclado.nextLine();
        } catch (java.util.NoSuchElementException e) {

        }
    }
}
