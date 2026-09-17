package view;

import model.entidades.Cena;
import model.entidades.Dialogo;
import model.entidades.Escolha;
import model.entidades.Partida;
import model.enums.Atributo;

import java.util.List;
import java.util.Scanner;

public class ExibirJogo {

    // O Scanner vem de fora (do controller), o mesmo do MenuInicial.
    private Scanner teclado;

    public ExibirJogo(Scanner teclado) {
        this.teclado = teclado;
    }

    /**
     * Mostra a cena inteira: titulo, narracao, falas, status e as opcoes.
     * Recebe as listas ja separadas pelo controller.
     */
    public void exibirCena(Cena cena, Partida partida,
                           List<Escolha> disponiveis, List<Escolha> bloqueadas) {

        limparTela();
        System.out.println("========================================");
        System.out.println("  " + cena.getTitulo());
        System.out.println("========================================");

        // A narracao ja vem escrita com uma linha em branco entre os
        // paragrafos. Aqui ela e quebrada nesse ponto e mostrada um
        // paragrafo por vez, pra nao despejar a tela inteira de uma vez.
        String[] paragrafos = cena.getTextoPrincipal().split("\n\n");
        for (int i = 0; i < paragrafos.length; i++) {
            System.out.println();
            System.out.println(paragrafos[i]);
            aguardarEnter();
        }

        // Cada fala tambem espera o ENTER, como numa conversa.
        for (Dialogo fala : cena.getDialogos()) {
            System.out.println();
            System.out.println(fala.getPersonagem().getNome() + ": " + fala.getTexto());
            aguardarEnter();
        }

        exibirStatus(partida);

        System.out.println();
        int numero = 1;
        for (Escolha opcao : disponiveis) {
            System.out.println("  [" + numero + "] " + opcao.getTextoExibido());
            numero++;
        }

        // Mostrar o que esta bloqueado e de proposito: o jogador precisa
        // perceber que existia outro caminho e que ele mesmo o fechou.
        for (Escolha opcao : bloqueadas) {
            System.out.println("  [-] " + opcao.getTextoExibido()
                    + "  (" + opcao.getMotivoDoBloqueio() + ")");
        }
    }

    public void exibirStatus(Partida partida) {
        System.out.println();
        System.out.println("  [ Fôlego "  + partida.getProtagonista().getAtributo(Atributo.FOLEGO)
                        + " | Coragem "   + partida.getProtagonista().getAtributo(Atributo.CORAGEM)
                        + " | Lucidez "   + partida.getProtagonista().getAtributo(Atributo.LUCIDEZ)
                        + " ]  Mochila: " + partida.getInventario().listar());
    }

    /** Mostra o que a escolha mudou. Se nao mudou nada, nao mostra nada. */
    public void exibirConsequencia(String aviso) {
        if (aviso != null) {
            System.out.println();
            // O aviso vem com uma linha por efeito.
            for (String linha : aviso.split("\n")) {
                System.out.println("  > " + linha);
            }
            aguardarEnter();
        }
    }

    /** Le a escolha e so aceita um numero valido. */
    public int pedirEscolhaJogador(int quantidadeDeOpcoes) {
        while (true) {
            System.out.print("\nO que você faz? ");
            try {
                int opcao = teclado.nextInt();
                teclado.nextLine();
                if (opcao >= 1 && opcao <= quantidadeDeOpcoes) {
                    return opcao;
                }
                System.out.println("Digite um número entre 1 e " + quantidadeDeOpcoes + ".");
            } catch (java.util.InputMismatchException e) {
                teclado.nextLine();
                System.out.println("Isso não é um número.");
            } catch (java.util.NoSuchElementException e) {
                return 1; // entrada acabou
            }
        }
    }

    public void exibirMensagemFimDeJogo() {
        System.out.println("\n--- FIM DE JOGO ---");
        aguardarEnter();
    }

    /** Empurra o texto antigo pra cima pra cada cena comecar com a tela limpa. */
    private void limparTela() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
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
