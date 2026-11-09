package model.enums;

/**
 * Decisões anteriores que o jogo precisa lembrar.
 *
 * Uma flag é LIGADA por uma escolha e LIDA por outra, capítulos depois.
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

    // ligadas e lidas no CAP10, pra não repetir a mesma fala duas vezes
    FALOU_DA_VOZ,
    FALOU_DA_PORTA
}
