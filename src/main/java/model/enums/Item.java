package model.enums;

/**
 * Os objetos que o jogador pode carregar na mochila.
 *
 * Todo item desta lista faz trabalho: é dado por alguma escolha e
 * exigido por outra. O TERCO é o único com que o jogador já começa.
 *
 * Quem guarda o que o jogador tem é a classe Inventario; este enum só
 * define quais itens existem e como cada um aparece na tela.
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
