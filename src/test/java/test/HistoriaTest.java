package test;

import model.entidades.Cena;
import model.entidades.Partida;
import model.enums.Preset;
import model.factory.Historia;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Testa a montagem das cenas, a cena inicial e a busca por ID. */
public class HistoriaTest {

    private Historia historia;
    private Partida partida;

    @Before
    public void criarHistoriaEPartida() {
        historia = new Historia();
        partida = new Partida("Vicente", Preset.COMUM);
    }

    @Test
    public void montarHistoriaDevolveACenaCap01() {
        Cena primeiraCena = historia.montarHistoria(partida);

        assertEquals("CAP01", primeiraCena.getId());
    }

    @Test
    public void montaAsVinteEOitoCenas() {
        historia.montarHistoria(partida);

        assertEquals(28, historia.totalDeCenas());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCenaComIdInexistenteQuebraComMensagemClara() {
        historia.montarHistoria(partida);

        /* Uma cena inexistente deve gerar uma exceção. */
        historia.getCena("CENA_FANTASMA");
    }

    @Test
    public void getCenaDevolveACenaCertaPeloId() {
        historia.montarHistoria(partida);

        Cena cap01 = historia.getCena("CAP01");

        assertEquals("Capítulo 1 - A Porta", cap01.getTitulo());
    }

    @Test
    public void montarDuasVezesNaoDuplicaAsCenas() {
        /* Montar a história novamente não deve duplicar as cenas. */
        historia.montarHistoria(partida);
        historia.montarHistoria(partida);

        assertEquals(28, historia.totalDeCenas());
    }
}
