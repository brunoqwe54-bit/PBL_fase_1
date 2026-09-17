package model.enums;

/**
 * Decisoes anteriores que o jogo precisa lembrar.
 *
 * Uma flag e LIGADA por uma escolha e LIDA por outra, capitulos depois.
 * E esse par que produz o requisito "consequencias posteriores".
 *
 * Toda flag desta lista tem os dois lados. Se voce criar uma nova,
 * escreva quem liga e quem le na mesma sentada: flag ligada e nunca lida
 * e uma decisao que o jogo prometeu lembrar e nunca cobrou.
 */
public enum Flag {

    // ligada no CAP01 -- lida no CAP05, no CAP08 e no CAP10
    PORTA_ABERTA,

    // ligada no CAP02 -- lida no CAP05
    SALVOU_DAVI,

    // ligada no CAP05 -- lida no CAP07
    DAVI_JUNTO,

    // ligada no CAP04 -- lida no CAP04
    OUVIU_RADIO,

    // ligadas e lidas no CAP10, pra nao repetir a mesma fala duas vezes
    FALOU_DA_VOZ,
    FALOU_DA_PORTA
}
