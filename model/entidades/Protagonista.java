package model.entidades;

public class Protagonista extends PersonagemBase {


    private int sanidade = 100;
    private int folego = 100;
    private int nervo = 0;

    // Construtor usando super() para mandar o nome pro PersonagemBase
    public Protagonista(String nome) {
        super(nome);
    }


    public int getSanidade() {
        return sanidade;
    }

    public void setSanidade(int sanidade) {
        this.sanidade = sanidade;
    }

    public int getFolego() {
        return folego;
    }

    public void setFolego(int folego) {
        this.folego = folego;
    }

    public int getNervo() {
        return nervo;
    }

    public void setNervo(int nervo) {
        this.nervo = nervo;
    }
}