package test;

import model.entidades.Protagonista;
import model.enums.Atributo;
import model.enums.Preset;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testes da classe Protagonista.
 *
 * O metodo alterarAtributo e o unico caminho para mudar um atributo, e e
 * dentro dele que mora o limite de 0 a 100. Se esse limite falhar, o jogo
 * inteiro fica errado -- por isso ele e o que mais precisa de teste.
 */
public class ProtagonistaTest {

    // Este objeto e recriado antes de CADA teste, pelo metodo com @Before.
    // Assim um teste nunca comeca com o estrago que o anterior deixou.
    private Protagonista vicente;

    @Before
    public void criarProtagonista() {
        // O preset COMUM comeca com 50 em tudo.
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
        // Muito mais do que caberia: tem que parar no teto.
        vicente.alterarAtributo(Atributo.LUCIDEZ, 200);

        assertEquals(Protagonista.MAXIMO, vicente.getAtributo(Atributo.LUCIDEZ));
    }

    @Test
    public void atributoNaoFicaAbaixoDeZero() {
        // Muito mais do que existe: tem que parar no piso.
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
