package model.enums;

public enum Personagens {
    VICENTE("Vicente"),
    OTAVIO("Otávio"),
    MAE("Mãe"),
    IRMA("Irmã"),
    NARRADOR("Narrador");


    private String nomeExibicao;


    Personagens(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }


    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
