package model.enums;

/**
 * Os tipos de menino que o jogador pode escolher no comeco da partida.
 * Cada um comeca com valores diferentes nos tres atributos.
 *
 * Um atributo alto abre escolhas; um atributo baixo fecha. Como o FOLEGO
 * so desce durante o jogo, o valor inicial dele e o que o jogador tem pra
 * gastar a noite inteira.
 */
public enum Preset {

    ATLETA("Atleta",
           "Corre, carrega e sobe. O corpo aguenta; a cabeça às vezes não.",
           90, 45, 45),

    DESTEMIDO("Destemido",
              "Não amarela na frente de nada. Mas cansa rápido.",
              45, 90, 45),

    OBSERVADOR("Observador",
               "Repara em tudo. Às vezes repara demais.",
               45, 45, 90),

    COMUM("Comum",
          "Um menino comum. Nada de mais em nada.",
          50, 50, 50);

    private String nome;
    private String descricao;
    private int folego;
    private int coragem;
    private int lucidez;

    Preset(String nome, String descricao, int folego, int coragem, int lucidez) {
        this.nome = nome;
        this.descricao = descricao;
        this.folego = folego;
        this.coragem = coragem;
        this.lucidez = lucidez;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getFolego() {
        return folego;
    }

    public int getCoragem() {
        return coragem;
    }

    public int getLucidez() {
        return lucidez;
    }
}
