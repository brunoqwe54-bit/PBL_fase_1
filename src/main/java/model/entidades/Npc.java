package model.entidades;

/**
 * Representa um personagem secundário. Sua confiança no protagonista varia de 0 a 100 e
 * pode liberar escolhas.
 */
public class Npc extends PersonagemBase {

    private static final long serialVersionUID = 1L;

    public static final int MINIMO = 0;
    public static final int MAXIMO = 100;

    private int confianca = 50;

    /**
     * Cria um NPC com confiança neutra (50).
     *
     * @param nome o nome mostrado na tela
     */
    public Npc(String nome) {
        super(nome);
    }

    /**
     * Cria um NPC com uma confiança inicial escolhida.
     *
     * @param nome      o nome mostrado na tela
     * @param confianca o valor inicial.  se estiver fora de 0.100, é cortado
     */
    public Npc(String nome, int confianca) {
        super(nome);
        this.confianca = limitar(confianca);
    }

    public int getConfianca() {
        return this.confianca;
    }

    /**
     * Altera a confiança sem ultrapassar os limites de 0 a 100.
     *
     * @param variacao variação aplicada. Use um valor negativo para diminuir
     */
    public void alterarConfianca(int variacao) {
        this.confianca = limitar(this.confianca + variacao);
    }

    private int limitar(int valor) {
        if (valor < MINIMO) return MINIMO;
        if (valor > MAXIMO) return MAXIMO;
        return valor;
    }
}
