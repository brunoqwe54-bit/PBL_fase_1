package model.entidades;

import java.io.Serializable;

/** Classe base que guarda o nome do protagonista ou de um NPC. */
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
