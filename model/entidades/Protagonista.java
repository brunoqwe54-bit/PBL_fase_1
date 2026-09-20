package model.entidades;

import model.enums.Atributo;
import model.enums.Preset;

/**
 * O personagem que o jogador controla: Vicente, irmão da Manuela.
 *
 * Guarda os três atributos (do enum Atributo), que vão de 0 a 100. Os
 * valores iniciais não são fixos: vêm do Preset escolhido no menu, e é
 * isso que faz um Atleta e um Observador terem caminhos diferentes pela
 * mesma história.
 *
 * Os atributos são privados e só podem ser mudados pelo método
 * alterarAtributo. Ter um caminho só é o que garante que nenhum valor
 * fora de 0..100 entre no jogo.
 */
public class Protagonista extends PersonagemBase {

    // Limites: nenhum atributo passa disso
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
