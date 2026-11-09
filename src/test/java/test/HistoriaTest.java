package test;

import model.entidades.Cena;
import model.entidades.Partida;
import model.enums.Preset;
import model.factory.Historia;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testes da montagem da história.
 *
 * Não testam o TEXTO de cada capítulo, só a
 * estrutura: quantas cenas existem, qual é a primeira, e o que acontece
 * quando alguém pede uma cena que não existe.
 */
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

        /* "CENA_FANTASMA" nunca foi criada, getCena tem que reclamar, e
        * não devolver null silenciosamente.
        */
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
        /* montarHistoria começa com cenas.clear(): montar de novo tem que
        * dar a mesma quantidade, nunca o dobro.
         */
        historia.montarHistoria(partida);
        historia.montarHistoria(partida);

        assertEquals(28, historia.totalDeCenas());
    }
}
