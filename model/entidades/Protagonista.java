package model.entidades;

import model.enums.Atributo;

public class Protagonista extends PersonagemBase {

    // Limites: nenhum atributo passa disso
    public static final int MINIMO = 0;
    public static final int MAXIMO = 100;

    private int folego = 50;
    private int nervo = 50;
    private int lucidez = 50;

    public Protagonista(String nome) {
        super(nome);
    }

    /**
     * Um unico caminho para alterar atributo, recebendo a VARIACAO.
     * O limite 0..100 mora aqui dentro, entao nao tem como esquecer dele.
     */
    public void alterarAtributo(Atributo atributo, int variacao) {
        switch (atributo) {
            case FOLEGO:  folego  = limitar(folego + variacao);  break;
            case NERVO:   nervo   = limitar(nervo + variacao);   break;
            case LUCIDEZ: lucidez = limitar(lucidez + variacao); break;
        }
    }

    public int getAtributo(Atributo atributo) {
        switch (atributo) {
            case FOLEGO:  return folego;
            case NERVO:   return nervo;
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
    public int getNervo()   { return nervo; }
    public int getLucidez() { return lucidez; }
}
