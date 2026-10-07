package view;

import model.entidades.Preferencias;

import java.util.Scanner;

/**
 * A tela das preferências: mostra os três ajustes com o valor atual e lê
 * qual deles o jogador quer mudar.
 *
 * Só mostra e lê. Quem muda o valor e grava no arquivo é o JogoController.
 */
public class TelaPreferencias {

    // O Scanner vem de fora (do controller). Assim existe um só no programa.
    private Scanner teclado;

    public TelaPreferencias(Scanner teclado) {
        this.teclado = teclado;
    }

    /**
     * Mostra as preferências e lê a opção.
     *
     * @return 0 para voltar, de 1 a 3 para mudar um ajuste, ou -1 se não digitou um número
     */
    public int exibir(Preferencias preferencias) {
        System.out.println();
        System.out.println("========== PREFERÊNCIAS ==========");
        System.out.println("  [1] Preset padrão: " + preferencias.getPresetPadrao().getNome());
        System.out.println("  [2] Pular as pausas de ENTER nas cenas: "
                + (preferencias.isPularEnter() ? "sim" : "não"));
        System.out.println("  [3] Autosave: "
                + (preferencias.isAutosaveLigado() ? "ligado" : "desligado"));
        System.out.println("  [0] Voltar");
        System.out.print("\nEscolha: ");

        try {
            int numero = teclado.nextInt();
            teclado.nextLine();
            return numero;
        } catch (java.util.InputMismatchException e) {
            teclado.nextLine();
            return -1;
        } catch (java.util.NoSuchElementException e) {
            return 0; // entrada acabou: volta
        }
    }

    public void exibirMensagem(String texto) {
        System.out.println(texto);
    }
}
