package test;

import model.persistencia.Progresso;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testes da classe Progresso: o capítulo sai do id da cena, e o progresso
 * é a fração de capítulos já concluídos.
 */
public class ProgressoTest {

    @Test
    public void primeiroCapituloDaZeroPorCento() {
        assertEquals(0, Progresso.calcular("CAP01"));
    }

    @Test
    public void segundoCapituloDaDezPorCento() {
        assertEquals(10, Progresso.calcular("CAP02"));
    }

    @Test
    public void capituloDoMeioDaQuarentaPorCento() {
        assertEquals(40, Progresso.calcular("CAP05"));
    }

    @Test
    public void cenaComLetraContaComoOMesmoCapitulo() {
        assertEquals(40, Progresso.calcular("CAP05B"));
    }

    @Test
    public void ultimoCapituloNaoChegaACemPorCento() {
        assertEquals(90, Progresso.calcular("CAP10"));
    }

    @Test
    public void capituloAlemDoUltimoNaoPassaDeNoventa() {
        assertEquals(90, Progresso.calcular("CAP11"));
    }

    @Test
    public void capituloZeroDaZero() {
        assertEquals(0, Progresso.calcular("CAP00"));
    }

    @Test
    public void idForaDoFormatoDaZero() {
        assertEquals(0, Progresso.calcular("FIM_TARDE"));
        assertEquals(0, Progresso.calcular("MORTE_NOME"));
        assertEquals(0, Progresso.calcular("CAPXX"));
        assertEquals(0, Progresso.calcular("CAP1"));
        assertEquals(0, Progresso.calcular(""));
    }

    @Test
    public void idNuloDaZero() {
        assertEquals(0, Progresso.calcular(null));
    }
}
