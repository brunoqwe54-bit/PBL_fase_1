package test;

import model.entidades.Partida;
import model.enums.Atributo;
import model.enums.Flag;
import model.enums.Item;
import model.enums.Preset;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/** Testa o estado inicial da partida e o registro de decisões. */
public class PartidaTest {

    private Partida partida;

    @Before
    public void criarPartida() {
        partida = new Partida("Vicente", Preset.COMUM);
    }

    @Test
    public void criaOProtagonistaComONomeRecebido() {
        assertEquals("Vicente", partida.getProtagonista().getNome());
    }

    @Test
    public void protagonistaComecaComOsValoresDoPresetRecebido() {
        assertEquals(50, partida.getProtagonista().getAtributo(Atributo.CORAGEM));
    }

    @Test
    public void comecaComOTercoDaMaeNaMochila() {
        // O terço é o item inicial da partida.
        assertTrue(partida.getInventario().possui(Item.TERCO));
    }

    @Test
    public void comecaSemNenhumOutroItem() {
        assertFalse(partida.getInventario().possui(Item.CANTIL));
    }

    @Test
    public void comecaSemCenaAtualDefinida() {
        // Historia define a primeira cena após a criação da partida.
        assertNull(partida.getCenaAtual());
    }

    @Test
    public void osNpcsComecamComAsConfiancasDaHistoria() {
        assertEquals(50, partida.getOtavio().getConfianca());
        assertEquals(50, partida.getMae().getConfianca());
        assertEquals(30, partida.getDavi().getConfianca());
        assertEquals(40, partida.getZulmira().getConfianca());
        assertEquals(50, partida.getAntonio().getConfianca());
        assertEquals(0, partida.getHomemDeTerno().getConfianca());
        assertEquals(30, partida.getManuela().getConfianca());
    }

    @Test
    public void naoTemNenhumaFlagLigadaNoComeco() {
        assertFalse(partida.temFlag(Flag.PORTA_ABERTA));
    }

    @Test
    public void ligarFlagFazTemFlagDevolverTrue() {
        partida.ligarFlag(Flag.SALVOU_DAVI);

        assertTrue(partida.temFlag(Flag.SALVOU_DAVI));
    }

    @Test
    public void ligarUmaFlagNaoLigaAsOutras() {
        partida.ligarFlag(Flag.OUVIU_RADIO);

        assertFalse(partida.temFlag(Flag.FALOU_DA_VOZ));
    }

    @Test
    public void ligarAMesmaFlagDuasVezesNaoDaErro() {
        // Registrar a mesma flag novamente não deve mudar o conjunto.
        partida.ligarFlag(Flag.DAVI_JUNTO);
        partida.ligarFlag(Flag.DAVI_JUNTO);

        assertTrue(partida.temFlag(Flag.DAVI_JUNTO));
    }
}
