package model;

public abstract class Personagem {
    protected String nome;

    protected Personagem(String nome) {
        this.nome = nome;
    }
    protected int atrituto_1;
    protected int atributo_2;
    protected int atributo_3;

    protected void dialogo(String mensagem) {
        System.out.println(nome + ":" + mensagem);
    }
}
