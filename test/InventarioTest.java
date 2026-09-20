package test;

import model.entidades.Inventario;
import model.enums.Item;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testes da classe Inventario.
 *
 * possui/adicionar/remover são a única porta de entrada e saída dos itens.
 * O que precisa ficar provado: cada item é independente dos outros, e
 * listar() sempre reflete o que realmente está guardado.
 */
public class InventarioTest {

    private Inventario mochila;

    @Before
    public void criarInventario() {
        mochila = new Inventario();
    }

    @Test
    public void comecaSemNenhumItem() {
        for (Item item : Item.values()) {
            assertFalse(mochila.possui(item));
        }
    }

    @Test
    public void adicionarFazPossuirDevolverTrue() {
        mochila.adicionar(Item.CANTIL);

        assertTrue(mochila.possui(Item.CANTIL));
    }

    @Test
    public void removerFazPossuirDevolverFalse() {
        mochila.adicionar(Item.CANTIL);
        mochila.remover(Item.CANTIL);

        assertFalse(mochila.possui(Item.CANTIL));
    }

    @Test
    public void adicionarUmItemNaoAfetaOsOutros() {
        mochila.adicionar(Item.CHAVE);

        assertFalse(mochila.possui(Item.VELA));
        assertFalse(mochila.possui(Item.CANTIL));
    }

    @Test
    public void removerUmItemQueNuncaFoiAdicionadoNaoQuebra() {
        // Não deveria dar erro nenhum, só continuar sem o item.
        mochila.remover(Item.MEDALHA);

        assertFalse(mochila.possui(Item.MEDALHA));
    }

    @Test
    public void listarVaziaMostraMensagemPadrao() {
        assertEquals("(vazia)", mochila.listar());
    }

    @Test
    public void listarComUmItemMostraSeuNomeDeExibicao() {
        mochila.adicionar(Item.TERCO);

        /* listar() junta os itens com "; " no final de cada um, e só tira o
         *espaço extra do fim, por isso o ";" continua na última posição.
         */
        assertEquals(Item.TERCO.getNomeExibicao() + ";", mochila.listar());
    }

    @Test
    public void listarComVariosItensMostraTodosOsNomes() {
        mochila.adicionar(Item.TERCO);
        mochila.adicionar(Item.CANTIL);

        String lista = mochila.listar();

        assertTrue(lista.contains(Item.TERCO.getNomeExibicao()));
        assertTrue(lista.contains(Item.CANTIL.getNomeExibicao()));
    }
}
