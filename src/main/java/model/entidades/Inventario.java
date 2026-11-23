package model.entidades;

import model.enums.Item;

import java.io.Serializable;

/** Guarda os itens do protagonista. Cada campo indica se ele possui um item. */
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean temTercoDaMae = false;
    private boolean temMedalhaSaoJorge = false;
    private boolean temChaveCemiterio = false;
    private boolean temCasacoVinho = false;
    private boolean temVelaApagada = false;
    private boolean temCantil = false;


    // Consulta e alteração dos itens.


    /**
     * Diz se o item está na mochila.
     *
     * @param item o item procurado
     * @return true se o jogador está com ele
     */
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

    /**
     * Adiciona um item. Adicionar o mesmo item novamente não muda o inventário.
     *
     * @param item o item a guardar
     */
    public void adicionar(Item item) {
        definir(item, true);
    }

    /**
     * Remove um item. Se ele já estiver ausente, nada muda.
     *
     * @param item o item a retirar
     */
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


    /**
     * Lista os nomes dos itens presentes no inventário.
     *
     * @return os nomes dos itens separados por ponto e vírgula, ou "(vazia)" quando o
     *     jogador não carrega nada
     */
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
