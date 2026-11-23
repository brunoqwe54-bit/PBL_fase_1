package model.enums;

/** Centraliza os nomes dos personagens usados para criar o elenco. */
public enum Personagens {
    VICENTE("Vicente"),
    OTAVIO("Otávio"),
    MANUELA("Manuela"),
    MAE("Mãe"),
    DAVI("Davi"),
    ZULMIRA("Dona Zulmira"),
    ANTONIO("Seu Antônio"),
    HOMEM_DE_TERNO("Homem de Terno");


    private String nomeExibicao;


    Personagens(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }


    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
