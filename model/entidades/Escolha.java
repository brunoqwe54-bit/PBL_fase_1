package model.entidades;

import model.enums.Atributo;
import model.enums.Flag;
import model.enums.Item;

/**
 * A classe onde mora a lógica de encadeamento do jogo.
 *
 * Além do texto e da cena de destino, cada escolha guarda dois grupos de
 * campos, todos opcionais:
 *
 * Condições (os métodos exige... e proibeFlag): o que precisa ser
 * verdade para ela aparecer. Verificadas pelo método estaDisponivel().
 *
 * Consequências (ganhaItem, perdeItem, ligaFlag): o que ela
 * muda quando é escolhida. Aplicadas pelo método aplicar().
 *
 * Campo não preenchido fica nulo e simplesmente não é verificado nem
 * aplicado. Uma escolha sem nenhuma condição aparece sempre.
 *
 * Os métodos de configuração devolvem a própria escolha (return this),
 * o que permite encadear as chamadas e montar uma escolha inteira numa
 * sequência só, do jeito que está feito na classe Historia.
 *
 * A escolha é escrita na Historia e lida no controller: os campos são
 * preenchidos em um lugar só e consultados em outro lugar só.
 */
public class Escolha {

    // Identificação
    private String id;
    private String textoExibido;
    private Cena cenaDestino;

    // Consequências
    private Npc npc;                 // quem tem a confiança alterada
    private int consequencia;        // quanto a confiança muda
    private Atributo atributoAfetado;
    private int deltaAtributo;
    private Item itemGanho;
    private Item itemPerdido;
    private Flag flagLigada;

    // Condições
    private Atributo atributoExigido;
    private int valorMinimoAtributo;
    private Item itemExigido;
    private Npc npcConfianca;
    private int confiancaMinima;
    private Flag flagExigida;
    private Flag flagProibida;       // só aparece se a flag NÃO estiver ligada
    private String motivoDoBloqueio = "você não pode fazer isso agora";

    /** Construtor simples: escolha sem efeito nenhum. */
    public Escolha(String id, String textoExibido, Cena cenaDestino) {
        this.id = id;
        this.textoExibido = textoExibido;
        this.cenaDestino = cenaDestino;
    }

    // ================================================================
    // CONFIGURAÇÃO
    // ================================================================

    /**
     * Consequência: muda a confiança de um NPC quando esta escolha for feita.
     *
     * @param npc      o personagem afetado
     * @param variacao quanto somar; negativo diminui
     * @return a própria escolha, para encadear
     */
    public Escolha comConfianca(Npc npc, int variacao) {
        this.npc = npc;
        this.consequencia = variacao;
        return this;
    }

    /**
     * Consequência: muda um atributo do protagonista.
     *
     * @param atributo qual dos três
     * @param variacao quanto somar; negativo diminui
     * @return a própria escolha, para encadear
     */
    public Escolha comAtributo(Atributo atributo, int variacao) {
        this.atributoAfetado = atributo;
        this.deltaAtributo = variacao;
        return this;
    }

    /**
     * Consequência: põe um item na mochila.
     *
     * @param item o item ganho
     * @return a própria escolha, para encadear
     */
    public Escolha ganhaItem(Item item) {
        this.itemGanho = item;
        return this;
    }

    /**
     * Consequência: tira um item da mochila.
     *
     * @param item o item consumido ou entregue
     * @return a própria escolha, para encadear
     */
    public Escolha perdeItem(Item item) {
        this.itemPerdido = item;
        return this;
    }

    /**
     * Consequência: memoriza esta decisão para ser cobrada depois.
     *
     * É o único efeito que não aparece na tela na hora, a flag fica
     * guardada e só se manifesta quando outra escolha, capítulos adiante,
     * exigir ou proibir ela. É o que produz consequência posterior.
     *
     * @param flag a decisão a memorizar
     * @return a própria escolha, para encadear
     */
    public Escolha ligaFlag(Flag flag) {
        this.flagLigada = flag;
        return this;
    }

    /**
     * Condição: só aparece se o atributo estiver no mínimo exigido.
     *
     * @param atributo qual dos três é cobrado
     * @param minimo   o valor mínimo, inclusive
     * @param motivo   a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha exigeAtributo(Atributo atributo, int minimo, String motivo) {
        this.atributoExigido = atributo;
        this.valorMinimoAtributo = minimo;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    /**
     * Condição: só aparece se o item estiver na mochila.
     *
     * Exigir não consome. Se a escolha também deve gastar o item, use
     * o método perdeItem junto.
     *
     * @param item   o item necessário
     * @param motivo a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha exigeItem(Item item, String motivo) {
        this.itemExigido = item;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    /**
     * Condição: só aparece se aquele NPC confiar o bastante no protagonista.
     *
     * @param npc    o personagem consultado
     * @param minimo a confiança mínima, inclusive
     * @param motivo a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha exigeConfianca(Npc npc, int minimo, String motivo) {
        this.npcConfianca = npc;
        this.confiancaMinima = minimo;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    /**
     * Condição: só aparece se aquela decisão já tiver sido tomada.
     *
     * @param flag   a decisão necessária
     * @param motivo a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha exigeFlag(Flag flag, String motivo) {
        this.flagExigida = flag;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    /**
     * Condição ao contrário: some se aquela decisão já tiver sido tomada.
     *
     * Como flag nunca desliga, é a única forma de escrever "isto vale
     * enquanto tal coisa não aconteceu".
     *
     * @param flag   a decisão que bloqueia esta escolha
     * @param motivo a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha proibeFlag(Flag flag, String motivo) {
        this.flagProibida = flag;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    // ================================================================
    // CONDIÇÕES DE ACESSO
    // ================================================================

    /**
     * Diz se esta escolha pode ser feita no estado atual da partida.
     *
     * Verifica, em sequência, as cinco condições possíveis: atributo
     * mínimo, item na mochila, confiança de um NPC, flag exigida e flag
     * proibida. Condição não preenchida não é verificada. Basta uma
     * falhar para a escolha ficar indisponível, é um E lógico.
     *
     * Quem devolve false não some da tela: o controller a coloca na lista
     * de bloqueadas, e a view a mostra riscada com o motivo.
     *
     * @param partida o estado atual do jogo
     * @return true se a escolha pode ser oferecida ao jogador
     */
    public boolean estaDisponivel(Partida partida) {

        if (atributoExigido != null
                && partida.getProtagonista().getAtributo(atributoExigido) < valorMinimoAtributo) {
            return false;
        }
        if (itemExigido != null && !partida.getInventario().possui(itemExigido)) {
            return false;
        }
        if (npcConfianca != null && npcConfianca.getConfianca() < confiancaMinima) {
            return false;
        }
        if (flagExigida != null && !partida.temFlag(flagExigida)) {
            return false;
        }
        if (flagProibida != null && partida.temFlag(flagProibida)) {
            return false;
        }
        return true;
    }

    // ================================================================
    // CONSEQUÊNCIAS
    // ================================================================

    /**
     * Aplica na partida todos os efeitos desta escolha.
     *
     * Efeito não preenchido é ignorado. Cada efeito aplicado vira uma
     * linha no texto devolvido, para a view imprimir uma embaixo da outra
     * e não confundir o que mudou no NPC com o que mudou no jogador. A
     * flag é o único efeito silencioso, de propósito: ela só se manifesta
     * quando for cobrada, capítulos depois.
     *
     * @param partida o estado do jogo, que será alterado
     * @return o resumo do que mudou, ou null se a escolha não teve
     *         nenhum efeito visível
     */
    public String aplicar(Partida partida) {
        // Cada efeito vira UMA LINHA. A view imprime uma embaixo da outra,
        // pra ninguém confundir o que mudou no NPC com o que mudou no jogador.
        String aviso = "";

        if (npc != null && consequencia != 0) {
            npc.alterarConfianca(consequencia);
            aviso += npc.getNome()
                   + (consequencia > 0 ? " confia mais em você" : " confia menos em você")
                   + " (confiança " + comSinal(consequencia) + ", agora " + npc.getConfianca() + ")\n";
        }
        if (atributoAfetado != null && deltaAtributo != 0) {
            partida.getProtagonista().alterarAtributo(atributoAfetado, deltaAtributo);
            aviso += atributoAfetado.getNomeExibicao() + " " + comSinal(deltaAtributo)
                   + " (agora " + partida.getProtagonista().getAtributo(atributoAfetado) + ")\n";
        }
        if (itemGanho != null) {
            partida.getInventario().adicionar(itemGanho);
            aviso += "Você guardou: " + itemGanho.getNomeExibicao() + "\n";
        }
        if (itemPerdido != null) {
            partida.getInventario().remover(itemPerdido);
            aviso += "Você não tem mais: " + itemPerdido.getNomeExibicao() + "\n";
        }
        if (flagLigada != null) {
            partida.ligarFlag(flagLigada);
            // flag é silenciosa: o efeito dela aparece capítulos depois
        }

        return aviso.isEmpty() ? null : aviso.trim();
    }

    /** Escreve o número com o sinal na frente: +10, -5. */
    private String comSinal(int valor) {
        return valor > 0 ? "+" + valor : "" + valor;
    }



    public String getId() { return id; }
    public String getTextoExibido() { return textoExibido; }
    public Cena getCenaDestino() { return cenaDestino; }
    public int getConsequencia() { return consequencia; }
    public Npc getNpc() { return npc; }
    public String getMotivoDoBloqueio() { return motivoDoBloqueio; }
}
