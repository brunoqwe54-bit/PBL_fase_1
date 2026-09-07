package model.enums;

public enum Item {
    LANTERNA("Lanterna de mão"),
    FACA("Faca de cozinha"),
    TERCO("Terço da mãe"),
    RADIO("Rádio de pilha"),
    CANTIL("Cantil com água do riacho"),
    MEDALHA("Medalha de São Jorge"),
    VELA("Vela apagada"),
    CHAVE("Chave da capela"),
    CASACO("Casaco vinho");

    private String nomeExibicao;

    Item(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
