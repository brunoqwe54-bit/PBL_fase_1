package model.factory;

import model.entidades.PersonagemBase;

/** Factory Method: cada fábrica concreta decide qual personagem criar. */
public abstract class FabricaDePersonagem {
    /**
     * Cria o tipo de personagem definido pela fábrica concreta.
     *
     * @param nome nome do personagem
     * @return personagem criado
     */
    public abstract PersonagemBase criar(String nome);
}
