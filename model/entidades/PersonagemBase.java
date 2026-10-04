package model.entidades;

import java.io.Serializable;

/**
 * O que todo personagem tem: um nome.
 * É abstrata porque "personagem genérico" não existe no jogo: ou é o
 * protagonista, ou é um NPC.
 */
public abstract class PersonagemBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;

    public PersonagemBase(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
