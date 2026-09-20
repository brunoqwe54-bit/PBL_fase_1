package controller;

import model.entidades.Cena;
import model.entidades.Escolha;
import model.entidades.Partida;
import model.enums.Preset;
import model.factory.Historia;
import view.ExibirJogo;
import view.MenuInicial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoController {

    // Um único Scanner no programa inteiro.
    private Scanner teclado = new Scanner(System.in);

    private MenuInicial menuInicial = new MenuInicial(teclado);
    private ExibirJogo exibirJogo = new ExibirJogo(teclado);
    private Historia historia = new Historia();

    public void iniciarPartida() {
        boolean rodando = true;

        // O menu roda em laço: quando a partida acaba, volta pra cá.
        while (rodando) {
            int escolha = menuInicial.exibir();

            switch (escolha) {
                case 1:
                    String nomeProtagonista = menuInicial.pedirNome();
                    Preset preset = menuInicial.pedirPreset();
                    menuInicial.exibirSinopse();
                    jogar(nomeProtagonista, preset);
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

    private void jogar(String nomeProtagonista, Preset preset) {

        // Partida nova = estado novo. Nada da partida anterior sobra.
        Partida partida = new Partida(nomeProtagonista, preset);
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

            // A escolha aplica os próprios efeitos e devolve o que contar.
            exibirJogo.exibirConsequencia(escolhida.aplicar(partida));

            partida.setCenaAtual(escolhida.getCenaDestino());
        }
    }
}
