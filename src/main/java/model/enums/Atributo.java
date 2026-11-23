package model.enums;

/** Define os três atributos do protagonista, com valores entre 0 e 100. */
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
