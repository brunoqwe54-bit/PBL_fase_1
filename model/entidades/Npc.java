package model.entidades;

public class Npc extends PersonagemBase {

    public static final int MINIMO = 0;
    public static final int MAXIMO = 100;

    private int confianca = 50;

    public Npc(String nome) {
        super(nome);
    }

    public Npc(String nome, int confianca) {
        super(nome);
        this.confianca = limitar(confianca);
    }

    public int getConfianca() {
        return this.confianca;
    }

    /** Recebe a variacao e aplica o limite aqui, num lugar so. */
    public void alterarConfianca(int variacao) {
        this.confianca = limitar(this.confianca + variacao);
    }

    private int limitar(int valor) {
        if (valor < MINIMO) return MINIMO;
        if (valor > MAXIMO) return MAXIMO;
        return valor;
    }
}
