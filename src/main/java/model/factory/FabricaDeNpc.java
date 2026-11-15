package model.factory;

import model.entidades.Npc;

/** Cria os personagens secundários com sua confiança inicial. */
public class FabricaDeNpc extends FabricaDePersonagem {
    private final int confianca;

    public FabricaDeNpc(int confianca) { this.confianca = confianca; }

    @Override
    public Npc criar(String nome) { return new Npc(nome, confianca); }
}
