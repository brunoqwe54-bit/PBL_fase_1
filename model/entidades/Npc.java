package model.entidades;

public class Npc extends PersonagemBase{
    public Npc(String nome) {
        super(nome);
    }
    private int confianca = 50;

    public Npc(String nome,int confianca){
        super(nome);

        this.confianca = confianca;

    }

    public int getConfianca(){
        return this.confianca;
    }

    public void alterarConfianca(int valor) {
        this.confianca += valor;

    }
    }
