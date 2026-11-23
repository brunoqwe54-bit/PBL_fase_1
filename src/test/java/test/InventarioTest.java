package test;

import model.entidades.Inventario;
import model.enums.Item;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Testa a posse, a adição, a remoção e a listagem de itens. */
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
        // Remover um item ausente não deve causar erro.
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

        /* A listagem mantém o ponto e vírgula depois do último item. */
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
