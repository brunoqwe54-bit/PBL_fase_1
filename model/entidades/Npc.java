package model.entidades;

/**
 * Um personagem que não é o jogador, com um valor de confiança.
 *
 * A confiança vai de 0 a 100 e mede o quanto aquele personagem confia
 * no protagonista. Cada NPC começa com um valor diferente, definido na
 * classe Partida: Otávio 50, Davi 30, Zulmira 40.
 *
 * A confiança não é enfeite: várias escolhas do jogo só aparecem se ela
 * estiver acima de um mínimo.
 */
public class Npc extends PersonagemBase {

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
     * @param confianca o valor inicial; se estiver fora de 0..100, é cortado
     */
    public Npc(String nome, int confianca) {
        super(nome);
        this.confianca = limitar(confianca);
    }

    public int getConfianca() {
        return this.confianca;
    }

    /**
     * Soma uma variação à confiança, respeitando os limites 0 e 100.
     *
     * Este é o único caminho para mudar a confiança de um NPC, e é por
     * isso que o limite mora aqui dentro: não existe como um valor
     * inválido entrar.
     *
     * @param variacao quanto somar; use negativo para diminuir
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
