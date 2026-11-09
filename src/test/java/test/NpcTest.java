package test;

import model.entidades.Npc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testes da classe Npc.
 *
 * A lógica é quase igual à do Protagonista: um único caminho de escrita
 * (alterarConfianca) que corta em 0 e 100. O que muda aqui é que existem
 * DOIS construtores, e os dois precisam respeitar esse limite.
 */
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
        // Muito mais do que caberia: tem que parar no teto.
        davi.alterarConfianca(200);

        assertEquals(Npc.MAXIMO, davi.getConfianca());
    }

    @Test
    public void confiancaNaoFicaAbaixoDeZero() {
        // Muito mais do que existe: tem que parar no piso.
        davi.alterarConfianca(-200);

        assertEquals(Npc.MINIMO, davi.getConfianca());
    }

    @Test
    public void construtorComConfiancaTambemRespeitaOTeto() {
        /* Se alguém tentar CRIAR um Npc já fora do limite, o construtor
         *precisa cortar também, não só o alterarConfianca depois.
         */
        Npc npc = new Npc("Teste", 500);

        assertEquals(Npc.MAXIMO, npc.getConfianca());
    }

    @Test
    public void guardaONomeQueRecebeu() {
        assertEquals("Davi", davi.getNome());
    }
}
