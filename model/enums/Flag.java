package model.enums;

/**
 * Decisoes anteriores que o jogo precisa lembrar.
 *
 * Uma flag e LIGADA por uma escolha e LIDA por outra, capitulos depois.
 * E esse par que produz o requisito "consequencias posteriores".
 */
public enum Flag {
    PORTA_ABERTA,       // CAP01 -- libera voltar pra casa em CAP05 e CAP08
    DEU_TERCO,          // CAP02
    SALVOU_DAVI,        // CAP02 -- Davi passa a seguir voce
    ABANDONOU_DAVI,     // CAP02
    DAVI_JUNTO,         // CAP05 -- Davi segue ate o cemiterio
    DAVI_NA_PONTE,      // CAP05 -- Davi fica em seguranca perto da agua
    AJOELHOU,           // CAP03
    GRITOU_NOME,        // CAP03 -- quebrou a regra: derruba a confianca de todos
    OUVIU_RADIO,        // CAP04 -- aprendeu as regras 1 e 4
    AVISOU_ANTONIO,     // CAP04 -- Antonio nao abre a janela e continua no ar
    PEGOU_MEDALHA,      // CAP07
    PROMETEU_VOLTAR,    // CAP08
    FALOU_DA_VOZ,       // CAP10 -- ja disse que a voz nao e da mae
    FALOU_DA_PORTA      // CAP10 -- ja contou do pedido ao Otavio
}
