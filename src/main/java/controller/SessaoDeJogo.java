package controller;

import model.entidades.Cena;
import model.entidades.Escolha;
import model.entidades.Partida;
import model.enums.Preset;
import model.factory.Historia;

import java.util.ArrayList;
import java.util.List;

/**
 * O fluxo de uma partida, sem interface nenhuma.
 *
 * Guarda a Partida (o estado do jogo) e a Cena atual (onde o jogador está).
 * Responde às perguntas que qualquer tela precisa fazer: quais escolhas
 * cabem agora, o que acontece ao escolher e se a cena é um final.
 *
 * Não conhece console nem JavaFX: por isso serve para as duas interfaces e
 * pode ser testada sozinha, sem abrir janela.
 */
public class SessaoDeJogo {

    private final Historia historia = new Historia();

    private Partida partida;
    private Cena cenaAtual;

    /**
     * Começa uma partida nova e para no capítulo 1.
     *
     * A história é remontada a cada partida porque as escolhas guardam os
     * Npc desta Partida, que são os que têm a confiança.
     */
    public void novaPartida(String nome, Preset preset) {
        partida = new Partida(nome, preset);
        cenaAtual = historia.montarHistoria(partida);
        partida.setCenaAtual(cenaAtual);
    }

    public Cena getCenaAtual() {
        return cenaAtual;
    }

    public Partida getPartida() {
        return partida;
    }

    /** Escolhas que podem ser oferecidas agora (todas as condições satisfeitas). */
    public List<Escolha> disponiveis() {
        List<Escolha> lista = new ArrayList<>();
        for (Escolha opcao : cenaAtual.getOpcoes()) {
            if (opcao.estaDisponivel(partida)) {
                lista.add(opcao);
            }
        }
        return lista;
    }

    /** Escolhas que a cena tem mas estão bloqueadas; o motivo vem de getMotivoDoBloqueio(). */
    public List<Escolha> bloqueadas() {
        List<Escolha> lista = new ArrayList<>();
        for (Escolha opcao : cenaAtual.getOpcoes()) {
            if (!opcao.estaDisponivel(partida)) {
                lista.add(opcao);
            }
        }
        return lista;
    }

    /**
     * Aplica os efeitos da escolha, anda para a cena de destino e devolve o
     * resumo do que mudou (ou null quando nada visível mudou).
     */
    public String escolher(Escolha escolha) {
        String aviso = escolha.aplicar(partida);
        cenaAtual = escolha.getCenaDestino();
        partida.setCenaAtual(cenaAtual);
        return aviso;
    }

    /** Cena sem nenhuma escolha disponível é fim de jogo (desfecho ou morte). */
    public boolean cenaFinal() {
        return disponiveis().isEmpty();
    }
}
