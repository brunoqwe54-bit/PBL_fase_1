package model.entidades;

import model.enums.Item;

/**
 * A mochila do protagonista.
 *
 * Cada item é um booleano: tem ou não tem. Os métodos públicos traduzem
 * o enum Item para o campo certo, então o resto do jogo nunca precisa
 * saber que por baixo são variáveis separadas.
 */
public class Inventario {

    private boolean temTercoDaMae = false;
    private boolean temMedalhaSaoJorge = false;
    private boolean temChaveCemiterio = false;
    private boolean temCasacoVinho = false;
    private boolean temVelaApagada = false;
    private boolean temCantil = false;

    // ================================================================
    // ACESSO PELO ENUM, é o que a Escolha usa
    // ================================================================

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
     * Põe um item na mochila. Adicionar duas vezes é o mesmo que uma.
     *
     * @param item o item a guardar
     */
    public void adicionar(Item item) {
        definir(item, true);
    }

    /**
     * Tira um item da mochila. Remover o que não está lá não faz nada.
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

    /** Lista o que o jogador carrega, pra View mostrar. */
    /**
     * Monta o texto da mochila para a view mostrar na barra de status.
     *
     * @return os nomes dos itens separados por ponto e vírgula,
     *         ou "(vazia)" quando o jogador não carrega nada
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
