package controller;

import model.entidades.*;
import model.enums.Preset;
import model.excecoes.FalhaAoCarregarException;
import model.excecoes.FalhaAoSalvarException;
import view.*;

import java.util.Scanner;

/** Adaptador do terminal. Usa a mesma sessão que a interface JavaFX. */
public class JogoController {
    private final Scanner teclado = new Scanner(System.in);
    private final MenuInicial menu = new MenuInicial(teclado);
    private final ExibirJogo jogo = new ExibirJogo(teclado);
    private final MenuPausa pausa = new MenuPausa(teclado);
    private final TelaSlots slots = new TelaSlots(teclado);
    private final TelaPreferencias preferencias = new TelaPreferencias(teclado);
    private final TelaGaleria galeria = new TelaGaleria(teclado);
    private final SessaoDeJogo sessao = new SessaoDeJogo();

    public void iniciarPartida() { menu.iniciar(this); }
    public RegistroDeFinais getRegistroDeFinais() { return sessao.getRegistroDeFinais(); }

    public boolean processarOpcao(int escolha) {
        try {
            switch (escolha) {
                case 1 -> {
                    String nome = menu.pedirNome();
                    Preset preset = menu.pedirPreset(sessao.getPreferencias().getPresetPadrao());
                    menu.exibirSinopse();
                    sessao.novaPartida(nome, preset);
                    jogar();
                }
                case 2 -> { sessao.continuar(); jogar(); }
                case 3 -> {
                    int slot = escolherSlot("CARREGAR", true);
                    if (slot != -1) { sessao.carregar(slot); jogar(); }
                }
                case 4 -> excluir();
                case 5 -> editarPreferencias();
                case 6 -> galeria.exibir(sessao.getRegistroDeFinais());
                case 7 -> menu.exibirInstrucoes();
                case 8 -> menu.exibirCreditos();
                case 9 -> { return false; }
                default -> menu.exibirMensagem("Opção inválida.");
            }
        } catch (FalhaAoCarregarException | FalhaAoSalvarException e) {
            menu.exibirMensagem(e.getMessage());
        }
        return true;
    }

    private void jogar() {
        jogo.setPularEnter(sessao.getPreferencias().isPularEnter());
        while (sessao.getPartida() != null) {
            jogo.exibirCena(sessao.getCenaAtual(), sessao.getPartida(), sessao.disponiveis(), sessao.bloqueadas());
            if (sessao.cenaFinal()) {
                jogo.exibirMensagemFimDeJogo();
                sessao.encerrarPartida();
                return;
            }
            int numero = jogo.pedirEscolhaJogador(sessao.disponiveis().size());
            if (numero == 0) {
                int opcao = pausa.exibir();
                if (opcao == 2) salvar();
                if (opcao == 3 && (!sessao.temAlteracoesNaoSalvas()
                        || slots.confirmar("Sair da partida sem salvar as últimas escolhas?"))) {
                    sessao.encerrarPartida();
                    return;
                }
            } else {
                jogo.exibirConsequencia(sessao.escolher(sessao.disponiveis().get(numero - 1)));
            }
        }
    }

    private int escolherSlot(String titulo, boolean autosave) {
        Save[] saves = new Save[4];
        boolean[] corrompidos = new boolean[4];
        for (int i = 0; i <= 3; i++) {
            if (sessao.existeSlot(i)) {
                try { saves[i] = sessao.lerSlot(i); }
                catch (FalhaAoCarregarException e) { corrompidos[i] = true; }
            }
        }
        return slots.exibir(titulo, saves, corrompidos, autosave);
    }

    private void salvar() {
        int slot = escolherSlot("SALVAR", false);
        if (slot == -1 || sessao.existeSlot(slot) && !slots.confirmar("Sobrescrever o slot " + slot + "?")) return;
        try {
            sessao.salvar(slot);
            slots.exibirMensagem(sessao.getUltimaMensagem());
        } catch (FalhaAoSalvarException e) { slots.exibirMensagem(e.getMessage()); }
    }

    private void excluir() throws FalhaAoSalvarException {
        int slot = escolherSlot("EXCLUIR", true);
        if (slot != -1 && slots.confirmar("Excluir esse save definitivamente?")) {
            sessao.excluir(slot);
            slots.exibirMensagem(sessao.getUltimaMensagem());
        }
    }

    private void editarPreferencias() throws FalhaAoSalvarException {
        while (true) {
            Preferencias atuais = sessao.getPreferencias();
            int opcao = preferencias.exibir(atuais);
            if (opcao == 0) return;
            Preset preset = atuais.getPresetPadrao();
            boolean pular = atuais.isPularEnter();
            boolean autosave = atuais.isAutosaveLigado();
            if (opcao == 1) preset = menu.pedirPreset(preset);
            else if (opcao == 2) pular = !pular;
            else if (opcao == 3) autosave = !autosave;
            else { preferencias.exibirMensagem("Opção inválida."); continue; }
            sessao.alterarPreferencias(preset, pular, autosave);
        }
    }
}
