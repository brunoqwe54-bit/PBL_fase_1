package test;

import model.entidades.Cena;
import model.entidades.Partida;
import model.entidades.RegistroDeFinais;
import model.enums.Preset;
import model.factory.Historia;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testes da classe RegistroDeFinais.
 *
 * Também confere que cada id da lista existe na Historia e é mesmo uma
 * cena sem escolhas, para a lista nunca ficar fora do jogo.
 */
public class RegistroDeFinaisTest {

    private RegistroDeFinais registro;

    @Before
    public void criarRegistro() {
        registro = new RegistroDeFinais();
    }

    @Test
    public void comecaVazio() {
        assertEquals(0, registro.quantosViu());
        assertFalse(registro.jaViu("FIM_TARDE"));
    }

    @Test
    public void registrarFinalNovoDevolveTrue() {
        assertTrue(registro.registrar("FIM_TARDE"));
        assertTrue(registro.jaViu("FIM_TARDE"));
        assertEquals(1, registro.quantosViu());
    }

    @Test
    public void registrarOMesmoFinalDeNovoDevolveFalseENaoDuplica() {
        registro.registrar("MORTE_FILA");

        assertFalse(registro.registrar("MORTE_FILA"));
        assertEquals(1, registro.quantosViu());
    }

    @Test
    public void cenaQueNaoEFinalNaoEhRegistrada() {
        assertFalse(registro.registrar("CAP03"));
        assertFalse(registro.registrar(null));
        assertEquals(0, registro.quantosViu());
    }

    @Test
    public void todoIdDaListaEhUmaCenaSemEscolhasDaHistoria() {
        Partida partida = new Partida("Vicente", Preset.COMUM);
        Historia historia = new Historia();
        historia.montarHistoria(partida);

        assertEquals(RegistroDeFinais.IDS.length, RegistroDeFinais.NOMES.length);
        for (String id : RegistroDeFinais.IDS) {
            Cena cena = historia.getCena(id);
            assertTrue(id + " devia ser uma cena sem escolhas", cena.getOpcoes().isEmpty());
        }
    }
}
