package view;

import model.entidades.Save;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Tela que lista os slots de save e deixa o jogador escolher um.
 *
 * Serve para Salvar, Carregar e Excluir: só muda o título e se o autosave
 * aparece. Ela só mostra e lê a escolha. Quem abre os arquivos é o
 * controller, que entrega aqui os saves já lidos.
 */
public class TelaSlots {

    private Scanner teclado;

    // O Scanner vem de fora (do controller). Assim existe um só no programa.
    public TelaSlots(Scanner teclado) {
        this.teclado = teclado;
    }

    /**
     * Mostra os slots e devolve o que o jogador escolheu.
     *
     * Os arrays têm 4 posições, e a posição é o número do slot: 0 é o
     * autosave e 1, 2 e 3 são os manuais. Para cada slot vale uma destas:
     * saves[slot] tem o save, ou saves[slot] é null (vazio), ou
     * corrompidos[slot] é true (o arquivo existe mas não abriu).
     *
     * @param titulo         o título da tela, ex. "SALVAR"
     * @param saves          o save de cada slot, null quando não há
     * @param corrompidos    true no slot cujo arquivo não pôde ser lido
     * @param mostrarAutosave true para listar o autosave como opção 4
     * @return o número do slot (0 é o autosave), ou -1 se o jogador voltou
     */
    public int exibir(String titulo, Save[] saves, boolean[] corrompidos,
                      boolean mostrarAutosave) {
        System.out.println();
        System.out.println("========== " + titulo + " ==========");

        for (int slot = 1; slot <= 3; slot++) {
            System.out.println("  [" + slot + "] " + descrever(slot, saves, corrompidos));
        }
        int ultimaOpcao = 3;
        if (mostrarAutosave) {
            System.out.println("  [4] Autosave: " + descrever(0, saves, corrompidos));
            ultimaOpcao = 4;
        }
        System.out.println("  [0] Voltar");

        while (true) {
            System.out.print("\nEscolha: ");
            try {
                int opcao = teclado.nextInt();
                teclado.nextLine();
                if (opcao == 0) {
                    return -1;
                }
                if (opcao >= 1 && opcao <= ultimaOpcao) {
                    // a opção 4 da tela é o slot 0, o autosave
                    return (opcao == 4) ? 0 : opcao;
                }
                System.out.println("Digite um número entre 0 e " + ultimaOpcao + ".");
            } catch (java.util.InputMismatchException e) {
                teclado.nextLine();
                System.out.println("Isso não é um número.");
            } catch (java.util.NoSuchElementException e) {
                return -1; // entrada acabou: volta
            }
        }
    }

    // O texto de uma linha: o resumo do save, ou "vazio", ou "corrompido".
    private String descrever(int slot, Save[] saves, boolean[] corrompidos) {
        if (corrompidos[slot]) {
            return "(save corrompido)";
        }
        Save save = saves[slot];
        if (save == null) {
            return "(vazio)";
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return save.getNomeJogador() + " - " + save.getDataHora().format(formato)
                + " - " + save.getTituloCena() + " (" + save.getProgresso() + "%)";
    }
}
