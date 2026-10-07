package controller;

import model.entidades.Cena;
import model.entidades.Escolha;
import model.entidades.Partida;
import model.entidades.Preferencias;
import model.entidades.RegistroDeFinais;
import model.entidades.Save;
import model.enums.Preset;
import model.excecoes.FalhaAoCarregarException;
import model.excecoes.FalhaAoSalvarException;
import model.factory.Historia;
import model.persistencia.GerenciadorDeFinais;
import model.persistencia.GerenciadorDePreferencias;
import model.persistencia.GerenciadorDeSaves;
import model.persistencia.Progresso;
import view.ExibirJogo;
import view.MenuInicial;
import view.MenuPausa;
import view.TelaGaleria;
import view.TelaPreferencias;
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
    private TelaPreferencias telaPreferencias = new TelaPreferencias(teclado);
    private TelaGaleria telaGaleria = new TelaGaleria(teclado);
    private GerenciadorDeSaves gerenciadorDeSaves = new GerenciadorDeSaves();
    private GerenciadorDePreferencias gerenciadorDePreferencias = new GerenciadorDePreferencias();
    private Preferencias preferencias = gerenciadorDePreferencias.carregar();
    private GerenciadorDeFinais gerenciadorDeFinais = new GerenciadorDeFinais();
    private RegistroDeFinais registroDeFinais = gerenciadorDeFinais.carregar();
    private Historia historia = new Historia();

    public void iniciarPartida() {
        boolean rodando = true;

        // O menu roda em laço: quando a partida acaba, volta pra cá.
        while (rodando) {
            int escolha = menuInicial.exibir();

            switch (escolha) {
                case 1:
                    String nomeProtagonista = menuInicial.pedirNome();
                    Preset preset = menuInicial.pedirPreset(preferencias.getPresetPadrao());
                    menuInicial.exibirSinopse();
                    Partida partidaNova = new Partida(nomeProtagonista, preset);
                    partidaNova.setCenaAtual(historia.montarHistoria(partidaNova));
                    jogar(partidaNova);
                    break;
                case 2:
                    continuarPartida();
                    break;
                case 3:
                    carregarPartida();
                    break;
                case 4:
                    excluirSave();
                    break;
                case 5:
                    editarPreferencias();
                    break;
                case 6:
                    telaGaleria.exibir(registroDeFinais);
                    break;
                case 7:
                    menuInicial.exibirInstrucoes();
                    break;
                case 8:
                    menuInicial.exibirCreditos();
                    break;
                case 9:
                    menuInicial.exibirMensagem("\nAté a próxima Noite Longa.");
                    rodando = false;
                    break;
                default:
                    menuInicial.exibirMensagem("Opção inválida! Tente novamente.");
                    break;
            }
        }
    }

    /* Recebe a partida pronta, nova ou carregada, já com a cena atual. */
    private void jogar(Partida partida) {

        exibirJogo.setPularEnter(preferencias.isPularEnter());

        // Capítulo em que o jogador está. Quando muda, grava o autosave.
        String capituloAtual = capituloDe(partida.getCenaAtual().getId());

        while (partida.getCenaAtual() != null) {

            Cena cena = partida.getCenaAtual();

            // Entrou num capítulo novo? Salva sozinho no slot 0.
            String capituloDaCena = capituloDe(cena.getId());
            if (!capituloDaCena.isEmpty() && !capituloDaCena.equals(capituloAtual)) {
                capituloAtual = capituloDaCena;
                if (preferencias.isAutosaveLigado()) {
                    autosalvar(partida);
                }
            }

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
                registrarFinal(cena.getId());
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

        try {
            gerenciadorDeSaves.salvar(slot, criarSave(partida));
            telaSlots.exibirMensagem("Jogo salvo no slot " + slot + ".");
        } catch (FalhaAoSalvarException e) {
            telaSlots.exibirMensagem(e.getMessage()); // a mensagem da exceção já é uma frase completa
        }
    }

    /** Anota o final no registro. Se for um final novo, grava o arquivo e avisa. */
    private void registrarFinal(String idCena) {
        if (!registroDeFinais.registrar(idCena)) {
            return;
        }
        try {
            gerenciadorDeFinais.salvar(registroDeFinais);
            telaGaleria.exibirMensagem("\n[Final novo na Galeria de finais!]");
        } catch (FalhaAoSalvarException e) {
            telaGaleria.exibirMensagem(e.getMessage());
        }
    }

    /** Tela de preferências: cada mudança já é gravada no arquivo. */
    private void editarPreferencias() {
        while (true) {
            int opcao = telaPreferencias.exibir(preferencias);
            if (opcao == 0) {
                return;
            } else if (opcao == 1) {
                preferencias.setPresetPadrao(menuInicial.pedirPreset(preferencias.getPresetPadrao()));
                salvarPreferencias();
            } else if (opcao == 2) {
                preferencias.setPularEnter(!preferencias.isPularEnter());
                salvarPreferencias();
            } else if (opcao == 3) {
                preferencias.setAutosaveLigado(!preferencias.isAutosaveLigado());
                salvarPreferencias();
            } else {
                telaPreferencias.exibirMensagem("Opção inválida.");
            }
        }
    }

    private void salvarPreferencias() {
        try {
            gerenciadorDePreferencias.salvar(preferencias);
        } catch (FalhaAoSalvarException e) {
            telaPreferencias.exibirMensagem(e.getMessage());
        }
    }

    /** "Continuar": carrega o save mais recente, sem perguntar o slot. */
    private void continuarPartida() {
        int slot = gerenciadorDeSaves.maisRecente();
        if (slot == -1) {
            menuInicial.exibirMensagem("\nNão há nenhum save para continuar.");
            return;
        }
        carregarSlot(slot);
    }

    /** "Carregar jogo": mostra os slots e carrega o que o jogador escolher. */
    private void carregarPartida() {
        Save[] saves = new Save[4];
        boolean[] corrompidos = new boolean[4];
        lerSlots(saves, corrompidos);

        // true: aqui o autosave aparece, o jogador pode carregar dele
        int slot = telaSlots.exibir("CARREGAR", saves, corrompidos, true);
        if (slot == -1) {
            return;
        }
        if (saves[slot] == null && !corrompidos[slot]) {
            telaSlots.exibirMensagem("O slot " + slot + " está vazio.");
            return;
        }
        carregarSlot(slot);
    }

    /** "Excluir save": o jogador escolhe o slot, confirma, e o arquivo é apagado. */
    private void excluirSave() {
        Save[] saves = new Save[4];
        boolean[] corrompidos = new boolean[4];
        lerSlots(saves, corrompidos);

        int slot = telaSlots.exibir("EXCLUIR", saves, corrompidos, true);
        if (slot == -1) {
            return;
        }
        if (saves[slot] == null && !corrompidos[slot]) {
            telaSlots.exibirMensagem("O slot " + slot + " está vazio.");
            return;
        }
        if (!telaSlots.confirmar("Excluir o slot " + slot + "? Isso não pode ser desfeito.")) {
            return;
        }
        if (gerenciadorDeSaves.excluir(slot)) {
            telaSlots.exibirMensagem("Slot " + slot + " excluído.");
        } else {
            telaSlots.exibirMensagem("Não foi possível excluir o slot " + slot + ".");
        }
    }

    /**
     * Lê o save do slot, remonta a história com a partida lida e joga.
     *
     * A cena atual não vai no arquivo (é transient), então ela é
     * recolocada aqui pelo id guardado no Save.
     */
    private void carregarSlot(int slot) {
        Partida partida;
        try {
            Save save = gerenciadorDeSaves.carregar(slot);
            partida = save.getPartida();
            historia.montarHistoria(partida);
            partida.setCenaAtual(historia.getCena(save.getIdCena()));
        } catch (FalhaAoCarregarException e) {
            telaSlots.exibirMensagem(e.getMessage());
            return;
        } catch (IllegalArgumentException e) {
            telaSlots.exibirMensagem("O save aponta para uma cena que não existe.");
            return;
        }
        jogar(partida);
    }

    /** Grava no slot 0 sem perguntar nada. Se falhar, avisa e o jogo continua. */
    private void autosalvar(Partida partida) {
        try {
            gerenciadorDeSaves.salvar(GerenciadorDeSaves.AUTOSAVE, criarSave(partida));
            telaSlots.exibirMensagem("[Jogo salvo automaticamente]");
        } catch (FalhaAoSalvarException e) {
            telaSlots.exibirMensagem(e.getMessage());
        }
    }

    /** Monta o Save com a situação atual da partida (usado pelo salvar manual e pelo autosave). */
    private Save criarSave(Partida partida) {
        Cena cena = partida.getCenaAtual();
        return new Save(partida.getProtagonista().getNome(), LocalDateTime.now(),
                cena.getId(), cena.getTitulo(), Progresso.calcular(cena.getId()), partida);
    }

    /**
     * Devolve o capítulo de uma cena: "CAP05B" vira "CAP05". Cenas de
     * final (que não começam com CAP) devolvem "", e nelas não há autosave.
     */
    private String capituloDe(String idCena) {
        if (idCena.startsWith("CAP") && idCena.length() >= 5) {
            return idCena.substring(0, 5);
        }
        return "";
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
