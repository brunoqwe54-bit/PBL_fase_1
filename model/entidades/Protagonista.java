package model.entidades;

import model.enums.Atributo;
import model.enums.Preset;

public class Protagonista extends PersonagemBase {

    // Limites: nenhum atributo passa disso
    public static final int MINIMO = 0;
    public static final int MAXIMO = 100;

    private int folego;
    private int coragem;
    private int lucidez;

    // Os valores iniciais vem do Preset que o jogador escolheu no menu.
    public Protagonista(String nome, Preset preset) {
        super(nome);
        this.folego  = preset.getFolego();
        this.coragem = preset.getCoragem();
        this.lucidez = preset.getLucidez();
    }

    /**
     * Um unico caminho para alterar atributo, recebendo a VARIACAO.
     * O limite 0..100 mora aqui dentro, entao nao tem como esquecer dele.
     */
    public void alterarAtributo(Atributo atributo, int variacao) {
        switch (atributo) {
            case FOLEGO:  folego  = limitar(folego + variacao);  break;
            case CORAGEM: coragem = limitar(coragem + variacao); break;
            case LUCIDEZ: lucidez = limitar(lucidez + variacao); break;
        }
    }

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

    public int getFolego()  { return folego; }
    public int getCoragem() { return coragem; }
    public int getLucidez() { return lucidez; }
}
