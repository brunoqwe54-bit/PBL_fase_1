package model.factory;

import model.entidades.PersonagemBase;

/** Factory Method: cada fábrica concreta decide qual personagem criar. */
public abstract class FabricaDePersonagem {
    public abstract PersonagemBase criar(String nome);
}
