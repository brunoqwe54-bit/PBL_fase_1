package model.entidades;

/**
 * O que todo personagem tem: um nome.
 * É abstrata porque "personagem genérico" não existe no jogo: ou é o
 * protagonista, ou é um NPC.
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
