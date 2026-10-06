package controller;

import model.entidades.Cena;
import model.entidades.Escolha;
import model.entidades.Partida;
import model.entidades.Save;
import model.enums.Preset;
import model.excecoes.FalhaAoCarregarException;
import model.excecoes.FalhaAoSalvarException;
import model.factory.Historia;
import model.persistencia.GerenciadorDeSaves;
import model.persistencia.Progresso;
import view.ExibirJogo;
import view.MenuInicial;
import view.MenuPausa;
import view.TelaSlots;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Quem coordena o jogo: liga o modelo com a visão.
 *
 * Cria o único Scanner do programa e empresta para as duas classes de
 * visão, roda o laço do menu e, quando começa uma partida, roda o laço
 * principal do jogo.
 *
 * Esse laço faz sempre os mesmos sete passos: pega a cena atual, separa
 * as escolhas disponíveis das bloqueadas perguntando estaDisponivel(),
 * manda exibir, encerra se não sobrou nenhuma opção, lê o número
 * escolhido, manda a escolha aplicar os próprios efeitos e vai para a
 * cena de destino.
 *
 * O controller não sabe o que cada escolha exige nem o que ela provoca:
 * só pergunta e manda aplicar. É por isso que capítulo novo não mexe
 * aqui.
 */
public class JogoController {

    // Um único Scanner no programa inteiro.
    private Scanner teclado = new Scanner(System.in);

    private MenuInicial menuInicial = new MenuInicial(teclado);
    private ExibirJogo exibirJogo = new ExibirJogo(teclado);
    private MenuPausa menuPausa = new MenuPausa(teclado);
    private TelaSlots telaSlots = new TelaSlots(teclado);
    private GerenciadorDeSaves gerenciadorDeSaves = new GerenciadorDeSaves();
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

            /* 0 abre o menu de pausa. Depois dele, pergunta de novo,
             * sem mostrar a cena outra vez.
             */
            while (numero == 0) {
                int opcaoMenu = menuPausa.exibir();
                if (opcaoMenu == 2) {
                    salvarPartida(partida);
                } else if (opcaoMenu == 3) {
                    return; // encerra o jogar() e volta pro menu inicial
                }
                numero = exibirJogo.pedirEscolhaJogador(disponiveis.size());
            }

            Escolha escolhida = disponiveis.get(numero - 1);

            // A escolha aplica os próprios efeitos e devolve o que contar.
            exibirJogo.exibirConsequencia(escolhida.aplicar(partida));

            partida.setCenaAtual(escolhida.getCenaDestino());
        }
    }

    /**
     * Deixa o jogador escolher um slot e grava a partida nele.
     *
     * Se o slot já tem save (ou um arquivo corrompido), pergunta antes de
     * sobrescrever. Se a gravação falhar, avisa e o jogo continua.
     */
    private void salvarPartida(Partida partida) {
        Save[] saves = new Save[4];
        boolean[] corrompidos = new boolean[4];
        lerSlots(saves, corrompidos);

        // false: o autosave não aparece, o jogador só grava nos slots 1 a 3
        int slot = telaSlots.exibir("SALVAR", saves, corrompidos, false);
        if (slot == -1) {
            return;
        }

        if (saves[slot] != null || corrompidos[slot]) {
            if (!telaSlots.confirmar("O slot " + slot + " já tem um save. Sobrescrever?")) {
                return;
            }
        }

        Cena cena = partida.getCenaAtual();
        Save save = new Save(partida.getProtagonista().getNome(), LocalDateTime.now(),
                cena.getId(), cena.getTitulo(), Progresso.calcular(cena.getId()), partida);

        try {
            gerenciadorDeSaves.salvar(slot, save);
            telaSlots.exibirMensagem("Jogo salvo no slot " + slot + ".");
        } catch (FalhaAoSalvarException e) {
            telaSlots.exibirMensagem(e.getMessage()); // a mensagem da exceção já é uma frase completa
        }
    }

    /**
     * Lê os 4 slots para montar a lista da tela. Slot vazio fica null em
     * saves; slot com arquivo que não abriu fica true em corrompidos.
     */
    private void lerSlots(Save[] saves, boolean[] corrompidos) {
        for (int slot = GerenciadorDeSaves.AUTOSAVE;
             slot <= GerenciadorDeSaves.TOTAL_DE_SLOTS_MANUAIS; slot++) {
            if (!gerenciadorDeSaves.existe(slot)) {
                continue;
            }
            try {
                saves[slot] = gerenciadorDeSaves.carregar(slot);
            } catch (FalhaAoCarregarException e) {
                corrompidos[slot] = true;
            }
        }
    }
}
