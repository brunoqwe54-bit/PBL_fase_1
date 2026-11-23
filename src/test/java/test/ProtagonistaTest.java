package test;

import model.entidades.Protagonista;
import model.enums.Atributo;
import model.enums.Preset;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Testa os presets e os limites dos atributos. */
public class ProtagonistaTest {

    /* Cria um protagonista novo antes de cada teste. */
    private Protagonista vicente;

    @Before
    public void criarProtagonista() {
        // O preset COMUM começa com 50 em tudo.
        vicente = new Protagonista("Vicente", Preset.COMUM);
    }

    @Test
    public void comecaComOsValoresDoPreset() {
        assertEquals(50, vicente.getAtributo(Atributo.FOLEGO));
        assertEquals(50, vicente.getAtributo(Atributo.CORAGEM));
        assertEquals(50, vicente.getAtributo(Atributo.LUCIDEZ));
    }

    @Test
    public void presetAtletaComecaComNoventaDeFolego() {
        Protagonista atleta = new Protagonista("Vicente", Preset.ATLETA);

        assertEquals(90, atleta.getAtributo(Atributo.FOLEGO));
        assertEquals(45, atleta.getAtributo(Atributo.CORAGEM));
        assertEquals(45, atleta.getAtributo(Atributo.LUCIDEZ));
    }

    @Test
    public void variacaoPositivaSomaNoAtributo() {
        vicente.alterarAtributo(Atributo.CORAGEM, 10);

        assertEquals(60, vicente.getAtributo(Atributo.CORAGEM));
    }

    @Test
    public void variacaoNegativaSubtraiDoAtributo() {
        vicente.alterarAtributo(Atributo.FOLEGO, -15);

        assertEquals(35, vicente.getAtributo(Atributo.FOLEGO));
    }

    @Test
    public void atributoNaoPassaDeCem() {
        // Limita o atributo máximo a 100.
        vicente.alterarAtributo(Atributo.LUCIDEZ, 200);

        assertEquals(Protagonista.MAXIMO, vicente.getAtributo(Atributo.LUCIDEZ));
    }

    @Test
    public void atributoNaoFicaAbaixoDeZero() {
        // Limita o atributo mínimo a zero.
        vicente.alterarAtributo(Atributo.FOLEGO, -200);

        assertEquals(Protagonista.MINIMO, vicente.getAtributo(Atributo.FOLEGO));
    }

    @Test
    public void alterarUmAtributoNaoMexeNosOutros() {
        vicente.alterarAtributo(Atributo.CORAGEM, 20);

        assertEquals(50, vicente.getAtributo(Atributo.FOLEGO));
        assertEquals(50, vicente.getAtributo(Atributo.LUCIDEZ));
    }

    @Test
    public void guardaONomeQueRecebeu() {
        assertEquals("Vicente", vicente.getNome());
    }
}
