package model.entidades;

import model.enums.Atributo;
import model.enums.Flag;
import model.enums.Item;

/**
 * Uma alternativa apresentada ao jogador.
 *
 * Alem do texto e do destino, ela guarda:
 *   - CONDICOES: o que precisa ser verdade pra ela aparecer
 *   - CONSEQUENCIAS: o que ela muda quando e escolhida
 *
 * Todos os campos de condicao e consequencia sao OPCIONAIS: quando ficam
 * nulos (ou zero), simplesmente nao sao verificados nem aplicados.
 */
public class Escolha {

    // ---------- identificacao ----------
    private String id;
    private String textoExibido;
    private Cena cenaDestino;

    // ---------- consequencias ----------
    private Npc npc;                 // quem tem a confianca alterada
    private int consequencia;        // quanto a confianca muda
    private Atributo atributoAfetado;
    private int deltaAtributo;
    private Item itemGanho;
    private Item itemPerdido;
    private Flag flagLigada;

    // ---------- condicoes ----------
    private Atributo atributoExigido;
    private int valorMinimoAtributo;
    private Item itemExigido;
    private Npc npcConfianca;
    private int confiancaMinima;
    private Flag flagExigida;
    private Flag flagProibida;       // so aparece se a flag NAO estiver ligada
    private String motivoDoBloqueio = "você não pode fazer isso agora";

    /** Construtor simples: escolha sem efeito nenhum. */
    public Escolha(String id, String textoExibido, Cena cenaDestino) {
        this.id = id;
        this.textoExibido = textoExibido;
        this.cenaDestino = cenaDestino;
    }

    /** Construtor antigo, mantido: escolha que so mexe na confianca de um NPC. */
    public Escolha(String id, String textoExibido, Cena cenaDestino, int consequencia, Npc npc) {
        this(id, textoExibido, cenaDestino);
        this.consequencia = consequencia;
        this.npc = npc;
    }

    // ================================================================
    // CONFIGURACAO
    // Cada metodo devolve a propria Escolha (return this), o que permite
    // encadear as chamadas e deixar a Historia legivel.
    // ================================================================

    public Escolha comConfianca(Npc npc, int variacao) {
        this.npc = npc;
        this.consequencia = variacao;
        return this;
    }

    public Escolha comAtributo(Atributo atributo, int variacao) {
        this.atributoAfetado = atributo;
        this.deltaAtributo = variacao;
        return this;
    }

    public Escolha ganhaItem(Item item) {
        this.itemGanho = item;
        return this;
    }

    public Escolha perdeItem(Item item) {
        this.itemPerdido = item;
        return this;
    }

    public Escolha ligaFlag(Flag flag) {
        this.flagLigada = flag;
        return this;
    }

    public Escolha exigeAtributo(Atributo atributo, int minimo, String motivo) {
        this.atributoExigido = atributo;
        this.valorMinimoAtributo = minimo;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    public Escolha exigeItem(Item item, String motivo) {
        this.itemExigido = item;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    public Escolha exigeConfianca(Npc npc, int minimo, String motivo) {
        this.npcConfianca = npc;
        this.confiancaMinima = minimo;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    public Escolha exigeFlag(Flag flag, String motivo) {
        this.flagExigida = flag;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    public Escolha proibeFlag(Flag flag, String motivo) {
        this.flagProibida = flag;
        this.motivoDoBloqueio = motivo;
        return this;
    }

    // ================================================================
    // CONDICOES DE ACESSO
    // ================================================================

    /** A escolha so aparece na tela se TODAS as condicoes forem satisfeitas. */
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
    // CONSEQUENCIAS
    // ================================================================

    /**
     * Aplica os efeitos na partida e devolve um texto curto do que mudou,
     * pra View poder mostrar. Devolve null quando nao houve efeito visivel.
     */
    public String aplicar(Partida partida) {
        // Cada efeito vira UMA LINHA. A view imprime uma embaixo da outra,
        // pra ninguem confundir o que mudou no NPC com o que mudou no jogador.
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
            // flag e silenciosa: o efeito dela aparece capitulos depois
        }

        return aviso.isEmpty() ? null : aviso.trim();
    }

    /** Escreve o numero com o sinal na frente: +10, -5. */
    private String comSinal(int valor) {
        return valor > 0 ? "+" + valor : "" + valor;
    }

    // ================================================================
    // GETTERS
    // ================================================================

    public String getId() { return id; }
    public String getTextoExibido() { return textoExibido; }
    public Cena getCenaDestino() { return cenaDestino; }
    public int getConsequencia() { return consequencia; }
    public Npc getNpc() { return npc; }
    public String getMotivoDoBloqueio() { return motivoDoBloqueio; }
}
