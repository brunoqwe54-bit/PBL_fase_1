package model.entidades;

import model.enums.Atributo;
import model.enums.Preset;

/**
 * Representa o jogador e seus atributos de fôlego, coragem e lucidez.
 * O preset define os valores iniciais. alterarAtributo mantém os valores entre 0 e 100.
 */
public class Protagonista extends PersonagemBase {

    private static final long serialVersionUID = 1L;

    // Limites dos atributos.
    public static final int MINIMO = 0;
    public static final int MAXIMO = 100;

    private int folego;
    private int coragem;
    private int lucidez;

    /**
     * Cria o protagonista com os valores iniciais do preset escolhido.
     *
     * @param nome   o nome digitado pelo jogador no menu
     * @param preset define os atributos iniciais da partida
     */
    public Protagonista(String nome, Preset preset) {
        super(nome);
        this.folego  = preset.getFolego();
        this.coragem = preset.getCoragem();
        this.lucidez = preset.getLucidez();
    }

    /**
     * Soma uma variação a um dos três atributos, respeitando 0 e 100.
     *
     * @param atributo qual dos três mudar
     * @param variacao quanto alterar
     */
    public void alterarAtributo(Atributo atributo, int variacao) {
        switch (atributo) {
            case FOLEGO:  folego  = limitar(folego + variacao);  break;
            case CORAGEM: coragem = limitar(coragem + variacao); break;
            case LUCIDEZ: lucidez = limitar(lucidez + variacao); break;
        }
    }

    /**
     * Lê o valor atual de um dos três atributos.
     *
     * @param atributo qual dos três ler
     * @return o valor, entre 0 e 100
     */
    public int getAtributo(Atributo atributo) {
        switch (atributo) {
            case FOLEGO:  return folego;
            case CORAGEM: return coragem;
            case LUCIDEZ: return lucidez;
            default:      return 0;
        }
    }

    private int limitar(int valor) {
        if (valor < MINIMO) return MINIMO;
        if (valor > MAXIMO) return MAXIMO;
        return valor;
    }

}
