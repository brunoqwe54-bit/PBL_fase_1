package model.entidades;

import model.enums.Atributo;
import model.enums.Flag;
import model.enums.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A classe onde mora a lógica de encadeamento do jogo.
 *
 * Além do texto e da cena de destino, cada escolha guarda dois grupos,
 * todos opcionais:
 *
 * Consequências (comConfianca, comAtributo, ganhaItem, perdeItem, ligaFlag):
 * o que ela muda quando é escolhida. Ficam em COLEÇÕES, porque uma escolha
 * pode mudar dois atributos ou a confiança de dois NPCs de uma vez. Quem
 * aplica é o método aplicar().
 *
 * Condições (os métodos exige... e proibeFlag): o que precisa ser
 * verdade para ela aparecer. Verificadas pelo método estaDisponivel().
 *
 * Item não preenchido simplesmente não é verificado nem aplicado. Uma
 * escolha sem nenhuma condição aparece sempre.
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

    // Consequências. LinkedHashMap e ArrayList guardam a ordem em que
    // foram escritas na Historia, então o aviso sai na mesma ordem.
    private final Map<Atributo, Integer> atributosAfetados = new LinkedHashMap<>();
    private final Map<Npc, Integer> confiancasAfetadas = new LinkedHashMap<>();
    private final List<Item> itensGanhos = new ArrayList<>();
    private final List<Item> itensPerdidos = new ArrayList<>();
    private final List<Flag> flagsLigadas = new ArrayList<>();

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
     * Pode ser chamado mais de uma vez: os valores são somados, não
     * substituídos.
     *
     * @param npc      o personagem afetado
     * @param variacao quanto somar; negativo diminui
     * @return a própria escolha, para encadear
     */
    public Escolha comConfianca(Npc npc, int variacao) {
        confiancasAfetadas.merge(npc, variacao, Integer::sum);
        return this;
    }

    /**
     * Consequência: muda um atributo do protagonista.
     *
     * Pode ser chamado mais de uma vez: a escolha que bebe o cantil, por
     * exemplo, devolve fôlego e lucidez ao mesmo tempo.
     *
     * @param atributo qual dos três
     * @param variacao quanto somar; negativo diminui
     * @return a própria escolha, para encadear
     */
    public Escolha comAtributo(Atributo atributo, int variacao) {
        atributosAfetados.merge(atributo, variacao, Integer::sum);
        return this;
    }

    /**
     * Consequência: põe um item na mochila.
     *
     * @param item o item ganho
     * @return a própria escolha, para encadear
     */
    public Escolha ganhaItem(Item item) {
        itensGanhos.add(item);
        return this;
    }

    /**
     * Consequência: tira um item da mochila.
     *
     * @param item o item consumido ou entregue
     * @return a própria escolha, para encadear
     */
    public Escolha perdeItem(Item item) {
        itensPerdidos.add(item);
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
        flagsLigadas.add(flag);
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
     * Cada efeito aplicado vira uma linha no texto devolvido, para a view
     * imprimir uma embaixo da outra e não confundir o que mudou no NPC
     * com o que mudou no jogador. A flag é o único efeito silencioso, de
     * propósito: ela só se manifesta quando for cobrada, capítulos depois.
     *
     * @param partida o estado do jogo, que será alterado
     * @return o resumo do que mudou, ou null se a escolha não teve
     *         nenhum efeito visível
     */
    public String aplicar(Partida partida) {
        StringBuilder aviso = new StringBuilder();

        for (Map.Entry<Npc, Integer> mudanca : confiancasAfetadas.entrySet()) {
            Npc npc = mudanca.getKey();
            int variacao = mudanca.getValue();
            if (variacao == 0) {
                continue;
            }
            npc.alterarConfianca(variacao);
            aviso.append(npc.getNome())
                 .append(variacao > 0 ? " confia mais em você" : " confia menos em você")
                 .append(" (confiança ").append(comSinal(variacao))
                 .append(", agora ").append(npc.getConfianca()).append(")\n");
        }
        for (Map.Entry<Atributo, Integer> mudanca : atributosAfetados.entrySet()) {
            Atributo atributo = mudanca.getKey();
            int variacao = mudanca.getValue();
            if (variacao == 0) {
                continue;
            }
            partida.getProtagonista().alterarAtributo(atributo, variacao);
            aviso.append(atributo.getNomeExibicao()).append(" ").append(comSinal(variacao))
                 .append(" (agora ").append(partida.getProtagonista().getAtributo(atributo))
                 .append(")\n");
        }
        for (Item item : itensGanhos) {
            partida.getInventario().adicionar(item);
            aviso.append("Você guardou: ").append(item.getNomeExibicao()).append("\n");
        }
        for (Item item : itensPerdidos) {
            partida.getInventario().remover(item);
            aviso.append("Você não tem mais: ").append(item.getNomeExibicao()).append("\n");
        }
        for (Flag flag : flagsLigadas) {
            partida.ligarFlag(flag);
            // flag é silenciosa: o efeito dela aparece capítulos depois
        }

        return aviso.length() == 0 ? null : aviso.toString().trim();
    }

    /** Escreve o número com o sinal na frente: +10, -5. */
    private String comSinal(int valor) {
        return valor > 0 ? "+" + valor : "" + valor;
    }

    // ================================================================
    // LEITURA
    // ================================================================

    public String getId() { return id; }
    public String getTextoExibido() { return textoExibido; }
    public Cena getCenaDestino() { return cenaDestino; }

    /**
     * A variação de confiança da primeira mudança de confiança, ou 0 se
     * a escolha não mexe com a confiança de ninguém.
     *
     * Serve para ler o caso comum, de uma mudança só. Numa escolha que
     * muda dois NPCs, o getter mostra apenas a primeira — aplicar()
     * aplica as duas.
     *
     * @return a variação, ou 0 quando não há esse efeito
     */
    public int getConsequencia() {
        return confiancasAfetadas.isEmpty() ? 0 : confiancasAfetadas.values().iterator().next();
    }

    /**
     * O NPC da primeira mudança de confiança, ou null se a escolha não
     * mexe com a confiança de ninguém.
     *
     * @return o personagem afetado, ou null quando não há esse efeito
     */
    public Npc getNpc() {
        return confiancasAfetadas.isEmpty() ? null : confiancasAfetadas.keySet().iterator().next();
    }

    public String getMotivoDoBloqueio() { return motivoDoBloqueio; }
}
