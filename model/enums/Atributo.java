package model.enums;

public enum Atributo {
    FOLEGO("Fôlego"),
    NERVO("Nervo"),
    LUCIDEZ("Lucidez");

    private String nomeExibicao;

    Atributo(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
