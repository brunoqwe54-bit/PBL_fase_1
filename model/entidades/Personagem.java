package model.entidades;

public abstract class Personagem {
    public String nome;

    public Personagem(String nome) {
        this.nome = nome;
    }
    protected int atrituto_1;
    protected int atributo_2;
    protected int atributo_3;

    public String getNome() {
        return nome;
    }
}
