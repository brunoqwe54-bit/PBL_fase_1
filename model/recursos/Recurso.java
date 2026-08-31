package model.recursos;

public class Recurso {
    private String nome;
    private String descricao;

    public Recurso(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;

    }
    public String getNome(){
        return this.nome;
    }
    public String getDescricao() {
        return this.descricao;
    }
}
