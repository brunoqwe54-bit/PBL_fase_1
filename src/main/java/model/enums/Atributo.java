package model.enums;

/**
 * Os três atributos do protagonista.
 *
 * Os três vão de 0 a 100 e são guardados na classe Protagonista.
 */
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
