package test;

import model.entidades.Npc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Testa os construtores e os limites da confiança. */
public class NpcTest {


    private Npc davi;

    @Before
    public void criarNpc() {
        // Davi começa com 30 de confiança.
        davi = new Npc("Davi", 30);
    }

    @Test
    public void comecaComAConfiancaRecebida() {
        assertEquals(30, davi.getConfianca());
    }

    @Test
    public void construtorSemConfiancaComecaEmCinquenta() {
        Npc otavio = new Npc("Otávio");

        assertEquals(50, otavio.getConfianca());
    }

    @Test
    public void variacaoPositivaSomaNaConfianca() {
        davi.alterarConfianca(10);

        assertEquals(40, davi.getConfianca());
    }

    @Test
    public void variacaoNegativaSubtraiDaConfianca() {
        davi.alterarConfianca(-10);

        assertEquals(20, davi.getConfianca());
    }

    @Test
    public void confiancaNaoPassaDeCem() {
        // Limita a confiança máxima a 100.
        davi.alterarConfianca(200);

        assertEquals(Npc.MAXIMO, davi.getConfianca());
    }

    @Test
    public void confiancaNaoFicaAbaixoDeZero() {
        // Limita a confiança mínima a zero.
        davi.alterarConfianca(-200);

        assertEquals(Npc.MINIMO, davi.getConfianca());
    }

    @Test
    public void construtorComConfiancaTambemRespeitaOTeto() {
        /* O construtor também deve respeitar os limites da confiança. */
        Npc npc = new Npc("Teste", 500);

        assertEquals(Npc.MAXIMO, npc.getConfianca());
    }

    @Test
    public void guardaONomeQueRecebeu() {
        assertEquals("Davi", davi.getNome());
    }
}
