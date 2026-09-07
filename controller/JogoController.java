package controller;

import model.entidades.Cena;
import model.entidades.Escolha;
import model.entidades.Partida;
import model.factory.Historia;
import view.ExibirJogo;
import view.MenuInicial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoController {

    // Um unico Scanner no programa inteiro, criado aqui e emprestado pras
    // duas views. Com dois Scanners lendo System.in, um "rouba" a entrada do
    // outro quando o texto chega rapido -- e e assim que um teste alimenta.
    private Scanner teclado = new Scanner(System.in);

    private MenuInicial menuInicial = new MenuInicial(teclado);
    private ExibirJogo exibirJogo = new ExibirJogo(teclado);
    private Historia historia = new Historia();

    public void iniciarPartida() {
        boolean rodando = true;

        // O menu roda em laco: quando a partida acaba, volta pra ca.
        while (rodando) {
            int escolha = menuInicial.exibir();

            switch (escolha) {
                case 1:
                    String nomeProtagonista = menuInicial.pedirNome();
                    menuInicial.exibirMensagem("O jogo está sendo iniciado...");
                    jogar(nomeProtagonista);
                    break;
                case 2:
                    menuInicial.exibirInstrucoes();
                    break;
                case 3:
                    menuInicial.exibirCreditos();
                    break;
                case 4:
                    menuInicial.exibirMensagem("\nAté a próxima Noite Longa.");
                    rodando = false;
                    break;
                default:
                    menuInicial.exibirMensagem("Opção inválida! Tente novamente.");
                    break;
            }
        }
    }

    private void jogar(String nomeProtagonista) {

        // Partida nova = estado novo. Nada da partida anterior sobra.
        Partida partida = new Partida(nomeProtagonista);
        partida.setCenaAtual(historia.montarHistoria(partida));

        while (partida.getCenaAtual() != null) {

            Cena cena = partida.getCenaAtual();

            // Separa as escolhas que o jogador pode ver das bloqueadas.
            List<Escolha> disponiveis = new ArrayList<>();
            List<Escolha> bloqueadas = new ArrayList<>();
            for (Escolha opcao : cena.getOpcoes()) {
                if (opcao.estaDisponivel(partida)) {
                    disponiveis.add(opcao);
                } else {
                    bloqueadas.add(opcao);
                }
            }

            exibirJogo.exibirCena(cena, partida, disponiveis, bloqueadas);

            // Cena sem escolhas = fim (desfecho ou game over).
            if (disponiveis.isEmpty()) {
                exibirJogo.exibirMensagemFimDeJogo();
                break;
            }

            int numero = exibirJogo.pedirEscolhaJogador(disponiveis.size());
            Escolha escolhida = disponiveis.get(numero - 1);

            // A escolha aplica os proprios efeitos e devolve o que contar.
            exibirJogo.exibirConsequencia(escolhida.aplicar(partida));

            partida.setCenaAtual(escolhida.getCenaDestino());
        }
    }
}
