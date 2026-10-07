package view;

import model.entidades.RegistroDeFinais;

import java.util.Scanner;

/**
 * A Galeria de finais: lista os 11 finais do jogo. Os que o jogador já
 * viu aparecem com o nome, os outros aparecem como "???".
 *
 * Só mostra. Quem guarda quais foram vistos é o RegistroDeFinais.
 */
public class TelaGaleria {

    // O Scanner vem de fora (do controller). Assim existe um só no programa.
    private Scanner teclado;

    public TelaGaleria(Scanner teclado) {
        this.teclado = teclado;
    }

    public void exibir(RegistroDeFinais registro) {
        System.out.println();
        System.out.println("========== GALERIA DE FINAIS ==========");
        System.out.println("Finais vistos: " + registro.quantosViu()
                + " de " + RegistroDeFinais.IDS.length);
        System.out.println();

        for (int i = 0; i < RegistroDeFinais.IDS.length; i++) {
            if (registro.jaViu(RegistroDeFinais.IDS[i])) {
                System.out.println("  [x] " + RegistroDeFinais.NOMES[i]);
            } else {
                System.out.println("  [ ] ???");
            }
        }

        System.out.print("\n[ENTER] ");
        try {
            teclado.nextLine();
        } catch (java.util.NoSuchElementException e) {

        }
    }

    public void exibirMensagem(String texto) {
        System.out.println(texto);
    }
}
