package model.entidades;

import model.enums.Atributo;
import model.enums.Flag;
import model.enums.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Guarda o texto, o destino, as condições e os efeitos de uma escolha.
 * estaDisponivel verifica as condições. aplicar altera o estado da partida.
 * Os métodos de configuração devolvem a própria escolha para permitir chamadas em
 * sequência.
 */
public class Escolha {

    // Identificação
    private String id;
    private String textoExibido;
    private Cena cenaDestino;

    // Guarda os efeitos na ordem em que foram definidos.

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
    private Flag flagProibida;       // Bloqueia a escolha se esta flag estiver ligada.
    private String motivoDoBloqueio = "você não pode fazer isso agora";
    private String motivoAtributo, motivoItem, motivoConfianca, motivoFlag, motivoProibida;

    /** Cria uma escolha sem condições ou efeitos. */
    public Escolha(String id, String textoExibido, Cena cenaDestino) {
        this.id = id;
        this.textoExibido = textoExibido;
        this.cenaDestino = cenaDestino;
    }


    // Configuração da escolha.


    /**
     * Define uma mudança na confiança do NPC. Chamadas repetidas somam os valores.
     *
     * @param npc      o personagem afetado
     * @param variacao variação aplicada. Valores negativos diminuem
     * @return a própria escolha, para encadear
     */
    public Escolha comConfianca(Npc npc, int variacao) {
        confiancasAfetadas.merge(npc, variacao, Integer::sum);
        return this;
    }

    /**
     * Define uma mudança em um atributo. A mesma escolha pode alterar vários atributos.
     *
     * @param atributo qual dos três
     * @param variacao variação aplicada. Valores negativos diminuem
     * @return a própria escolha, para encadear
     */
    public Escolha comAtributo(Atributo atributo, int variacao) {
        atributosAfetados.merge(atributo, variacao, Integer::sum);
        return this;
    }

    /**
     * Define um item que será adicionado ao inventário.
     *
     * @param item o item ganho
     * @return a própria escolha, para encadear
     */
    public Escolha ganhaItem(Item item) {
        itensGanhos.add(item);
        return this;
    }

    /**
     * Define um item que será removido do inventário.
     *
     * @param item o item consumido ou entregue
     * @return a própria escolha, para encadear
     */
    public Escolha perdeItem(Item item) {
        itensPerdidos.add(item);
        return this;
    }

    /**
     * Registra uma decisão para ser consultada em outras escolhas.
     *
     * @param flag a decisão a memorizar
     * @return a própria escolha, para encadear
     */
    public Escolha ligaFlag(Flag flag) {
        flagsLigadas.add(flag);
        return this;
    }

    /**
     * Exige um valor mínimo para o atributo.
     *
     * @param atributo qual dos três é cobrado
     * @param minimo   o valor mínimo, inclusive
     * @param motivo   a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha exigeAtributo(Atributo atributo, int minimo, String motivo) {
        this.atributoExigido = atributo;
        this.valorMinimoAtributo = minimo;
        this.motivoAtributo = motivo;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    /**
     * Exige a posse do item sem consumi-lo. Use perdeItem para removê-lo.
     *
     * @param item   o item necessário
     * @param motivo a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha exigeItem(Item item, String motivo) {
        this.itemExigido = item;
        this.motivoItem = motivo;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    /**
     * Exige um valor mínimo de confiança do NPC.
     *
     * @param npc    o personagem consultado
     * @param minimo a confiança mínima, inclusive
     * @param motivo a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha exigeConfianca(Npc npc, int minimo, String motivo) {
        this.npcConfianca = npc;
        this.confiancaMinima = minimo;
        this.motivoConfianca = motivo;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    /**
     * Exige que a decisão já tenha sido registrada.
     *
     * @param flag   a decisão necessária
     * @param motivo a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha exigeFlag(Flag flag, String motivo) {
        this.flagExigida = flag;
        this.motivoFlag = motivo;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    /**
     * Bloqueia a escolha se a decisão já foi registrada.
     *
     * @param flag   a decisão que bloqueia esta escolha
     * @param motivo a frase mostrada quando a escolha aparece bloqueada
     * @return a própria escolha, para encadear
     */
    public Escolha proibeFlag(Flag flag, String motivo) {
        this.flagProibida = flag;
        this.motivoProibida = motivo;
        this.motivoDoBloqueio = motivo;
        return this;
    }


    // Condições de acesso.


    /**
     * Verifica as condições da escolha. Se alguma falhar, a opção fica bloqueada.
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


    // Aplicação dos efeitos.


    /**
     * Aplica os efeitos e devolve um resumo das mudanças. As flags são registradas sem
     * aviso próprio.
     *
     * @param partida o estado do jogo, que será alterado
     * @return o resumo do que mudou, ou null se a escolha não teve nenhum efeito
     *     visível
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
            // Guarda a decisão para outras escolhas.
        }

        return aviso.length() == 0 ? null : aviso.toString().trim();
    }

    /** Formata a variação com sinal positivo ou negativo. */
    private String comSinal(int valor) {
        return valor > 0 ? "+" + valor : "" + valor;
    }


    // Consulta dos dados.


    public String getId() { return id; }
    public String getTextoExibido() { return textoExibido; }
    public Cena getCenaDestino() { return cenaDestino; }

    /**
     * Devolve a primeira variação de confiança. Os demais efeitos são aplicados por
     * aplicar.
     *
     * @return a variação, ou 0 quando não há esse efeito
     */
    public int getConsequencia() {
        return confiancasAfetadas.isEmpty() ? 0 : confiancasAfetadas.values().iterator().next();
    }

    /**
     * Devolve o NPC da primeira mudança de confiança.
     *
     * @return o personagem afetado, ou null quando não há esse efeito
     */
    public Npc getNpc() {
        return confiancasAfetadas.isEmpty() ? null : confiancasAfetadas.keySet().iterator().next();
    }

    public String getMotivoDoBloqueio() { return motivoDoBloqueio; }

    /** Devolve o motivo da primeira condição que falhou. */
    public String getMotivoDoBloqueio(Partida partida) {
        if (atributoExigido != null
                && partida.getProtagonista().getAtributo(atributoExigido) < valorMinimoAtributo) {
            return motivoAtributo;
        }
        if (itemExigido != null && !partida.getInventario().possui(itemExigido)) return motivoItem;
        if (npcConfianca != null && npcConfianca.getConfianca() < confiancaMinima) return motivoConfianca;
        if (flagExigida != null && !partida.temFlag(flagExigida)) return motivoFlag;
        if (flagProibida != null && partida.temFlag(flagProibida)) return motivoProibida;
        return "";
    }
}
