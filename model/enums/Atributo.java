package model.enums;

public enum Atributo {
    FOLEGO("Fôlego"),
    CORAGEM("Coragem"),
    LUCIDEZ("Lucidez");

    private String nomeExibicao;

    Atributo(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
