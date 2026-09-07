package model.entidades;

/**
 * O que todo personagem tem: um nome.
 * E abstrata porque "personagem generico" nao existe no jogo: ou e o
 * protagonista, ou e um NPC.
 */
public abstract class PersonagemBase {

    private String nome;

    public PersonagemBase(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
