package model.enums;

public enum Personagens {
    VICENTE("Vicente"),
    OTAVIO("Otávio"),
    MANUELA("Manuela"),
    MAE("Mãe"),
    DAVI("Davi"),
    ZULMIRA("Dona Zulmira"),
    ANTONIO("Seu Antônio"),
    HOMEM_DE_TERNO("Homem de Terno"),
    ROSANGELA("Rosângela"),
    NILTON("Seu Nilton"),
    NARRADOR("Narrador");


    private String nomeExibicao;


    Personagens(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }


    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
