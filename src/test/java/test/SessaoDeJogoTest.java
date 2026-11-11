package test;

import controller.SessaoDeJogo;
import model.entidades.Escolha;
import model.enums.Atributo;
import model.enums.Preset;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Testes do fluxo da partida: começar, listar escolhas, escolher e terminar.
 *
 * Não precisam de JavaFX: a SessaoDeJogo não conhece interface nenhuma.
 */
public class SessaoDeJogoTest {

    private SessaoDeJogo sessao;

    @Before
    public void comecarPartida() {
        sessao = new SessaoDeJogo();
        sessao.novaPartida("Vicente", Preset.COMUM);
    }

    @Test
    public void partidaNovaComecaNoCapitulo1() {
        assertEquals("CAP01", sessao.getCenaAtual().getId());
    }

    @Test
    public void capitulo1TemDuasEscolhasDisponiveisEUmaBloqueada() {
        // ESC0103 exige coragem 55 e o preset COMUM começa com 50.
        assertEquals(2, sessao.disponiveis().size());
        assertEquals(1, sessao.bloqueadas().size());
        assertEquals("você não tem coragem de pedir", sessao.bloqueadas().get(0).getMotivoDoBloqueio());
    }

    @Test
    public void escolherAvancaParaOCapitulo2() {
        sessao.escolher(escolhaPorId("ESC0101"));

        assertEquals("CAP02", sessao.getCenaAtual().getId());
    }

    @Test
    public void escolherAplicaOsEfeitosDaEscolha() {
        // ESC0101: Otávio -10 de confiança, protagonista +5 de coragem.
        sessao.escolher(escolhaPorId("ESC0101"));

        assertEquals(40, sessao.getPartida().getOtavio().getConfianca());
        assertEquals(55, sessao.getPartida().getProtagonista().getAtributo(Atributo.CORAGEM));
    }

    @Test
    public void escolherDevolveOResumoDoQueMudou() {
        String aviso = sessao.escolher(escolhaPorId("ESC0102"));

        assertNotNull(aviso);
        assertTrue(aviso.contains("confiança +10"));
    }

    @Test
    public void cenaDeMorteEhFinal() {
        sessao.escolher(escolhaPorId("ESC0101"));
        sessao.escolher(escolhaPorId("ESC0201"));   // "Olhar." leva para MORTE_NOME

        assertEquals("MORTE_NOME", sessao.getCenaAtual().getId());
        assertTrue(sessao.cenaFinal());
    }

    /** Procura uma escolha da cena atual pelo id, do jeito que a Historia a nomeia. */
    private Escolha escolhaPorId(String id) {
        for (Escolha escolha : sessao.getCenaAtual().getOpcoes()) {
            if (escolha.getId().equals(id)) {
                return escolha;
            }
        }
        throw new IllegalArgumentException("Escolha inexistente nesta cena: " + id);
    }
}
