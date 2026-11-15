package model.enums;

/**
 * Decisões anteriores que o jogo precisa lembrar.
 *
 * Uma flag é LIGADA por uma escolha e LIDA por outra, capítulos depois.
 */
public enum Flag {

    // ligada no CAP01 -- lida no CAP05, no CAP08 e no CAP10
    PORTA_ABERTA("Pediu a Otávio para deixar a porta aberta"),

    // ligada no CAP02 -- lida no CAP05
    SALVOU_DAVI("Ajudou Davi na rua"),

    // ligada no CAP05 -- lida no CAP07
    DAVI_JUNTO("Levou Davi como companhia"),

    // ligada no CAP04 -- lida no CAP04
    OUVIU_RADIO("Ouviu a transmissão de Seu Antônio"),

    // ligadas e lidas no CAP10, pra não repetir a mesma fala duas vezes
    FALOU_DA_VOZ("Disse a Manuela que a voz não era da mãe"),
    FALOU_DA_PORTA("Lembrou Manuela de que a porta estava aberta");

    private final String descricao;

    Flag(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
