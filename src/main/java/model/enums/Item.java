package model.enums;

/**
 * Define os itens disponíveis e seus nomes na tela. Inventario guarda quais itens o
 * jogador possui.
 */
public enum Item {
    TERCO("Terço da mãe"),
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
