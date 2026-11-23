package model.enums;

/** Define as decisões que podem ser registradas e consultadas pelas escolhas. */
public enum Flag {

    // Registrada no CAP01 e consultada no CAP05, CAP08 e CAP10.
    PORTA_ABERTA("Pediu a Otávio para deixar a porta aberta"),

    // Registrada no CAP02 e consultada no CAP05.
    SALVOU_DAVI("Ajudou Davi na rua"),

    // Registrada no CAP05 e consultada no CAP07.
    DAVI_JUNTO("Levou Davi como companhia"),

    // Registrada e consultada no CAP04.
    OUVIU_RADIO("Ouviu a transmissão de Seu Antônio"),

    // Evitam repetir a mesma fala no CAP10.
    FALOU_DA_VOZ("Disse a Manuela que a voz não era da mãe"),
    FALOU_DA_PORTA("Lembrou Manuela de que a porta estava aberta");

    private final String descricao;

    Flag(String descricao) { this.descricao = descricao; }
    public String getDescricao() { return descricao; }
}
