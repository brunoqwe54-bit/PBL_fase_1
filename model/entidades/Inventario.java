package model.entidades;

import model.enums.Item;

/**
 * A mochila do protagonista.
 *
 * Cada item e um booleano: tem ou nao tem. Os metodos possui() e adicionar()
 * traduzem o enum Item para o booleano certo, entao o resto do jogo nunca
 * precisa saber que por baixo sao campos separados.
 */
public class Inventario {

    private boolean temTercoDaMae = false;
    private boolean temMedalhaSaoJorge = false;
    private boolean temChaveCemiterio = false;
    private boolean temCasacoVinho = false;
    private boolean temVelaApagada = false;
    private boolean temCantil = false;

    // ================================================================
    // ACESSO PELO ENUM -- e o que a Escolha usa
    // ================================================================

    public boolean possui(Item item) {
        switch (item) {
            case TERCO:   return temTercoDaMae;
            case MEDALHA: return temMedalhaSaoJorge;
            case CHAVE:   return temChaveCemiterio;
            case CASACO:  return temCasacoVinho;
            case VELA:    return temVelaApagada;
            case CANTIL:  return temCantil;
            default:      return false;
        }
    }

    public void adicionar(Item item) {
        definir(item, true);
    }

    public void remover(Item item) {
        definir(item, false);
    }

    private void definir(Item item, boolean valor) {
        switch (item) {
            case TERCO:   temTercoDaMae = valor;      break;
            case MEDALHA: temMedalhaSaoJorge = valor; break;
            case CHAVE:   temChaveCemiterio = valor;  break;
            case CASACO:  temCasacoVinho = valor;     break;
            case VELA:    temVelaApagada = valor;     break;
            case CANTIL:  temCantil = valor;          break;
            default:                                  break;
        }
    }

    /** Lista o que o jogador carrega, pra View mostrar. */
    public String listar() {
        String lista = "";
        for (Item item : Item.values()) {
            if (possui(item)) {
                lista += item.getNomeExibicao() + "; ";
            }
        }
        return lista.isEmpty() ? "(vazia)" : lista.trim();
    }
}
