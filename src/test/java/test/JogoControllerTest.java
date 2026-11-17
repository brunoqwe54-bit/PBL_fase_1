package test;

import controller.JogoController;
import model.entidades.Escolha;
import model.enums.Atributo;
import model.enums.Preset;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Testes do fluxo da partida: começar, listar escolhas, escolher e terminar.
 *
 * Não precisam de JavaFX: o JogoController não conhece interface nenhuma.
 */
public class JogoControllerTest {

    @Rule public TemporaryFolder pasta = new TemporaryFolder();

    private JogoController jogoController;

    @Before
    public void comecarPartida() {
        jogoController = new JogoController(pasta.getRoot().getPath());
        jogoController.novaPartida("Vicente", Preset.COMUM);
    }

    @Test
    public void partidaNovaComecaNoCapitulo1() {
        assertEquals("CAP01", jogoController.getCenaAtual().getId());
    }

    @Test
    public void capitulo1TemDuasEscolhasDisponiveisEUmaBloqueada() {
        // ESC0103 exige coragem 55 e o preset COMUM começa com 50.
        assertEquals(2, jogoController.disponiveis().size());
        assertEquals(1, jogoController.bloqueadas().size());
        assertEquals("você não tem coragem de pedir", jogoController.bloqueadas().get(0).getMotivoDoBloqueio());
    }

    @Test
    public void escolherAvancaParaOCapitulo2() {
        jogoController.escolher(escolhaPorId("ESC0101"));

        assertEquals("CAP02", jogoController.getCenaAtual().getId());
    }

    @Test
    public void escolherAplicaOsEfeitosDaEscolha() {
        // ESC0101: Otávio -10 de confiança, protagonista +5 de coragem.
        jogoController.escolher(escolhaPorId("ESC0101"));

        assertEquals(40, jogoController.getPartida().getOtavio().getConfianca());
        assertEquals(55, jogoController.getPartida().getProtagonista().getAtributo(Atributo.CORAGEM));
    }

    @Test
    public void escolherDevolveOResumoDoQueMudou() {
        String aviso = jogoController.escolher(escolhaPorId("ESC0102"));

        assertNotNull(aviso);
        assertTrue(aviso.contains("confiança +10"));
    }

    @Test
    public void cenaDeMorteEhFinal() {
        jogoController.escolher(escolhaPorId("ESC0101"));
        jogoController.escolher(escolhaPorId("ESC0201"));   // "Olhar." leva para MORTE_NOME

        assertEquals("MORTE_NOME", jogoController.getCenaAtual().getId());
        assertTrue(jogoController.cenaFinal());
    }

    /** Procura uma escolha da cena atual pelo id, do jeito que a Historia a nomeia. */
    private Escolha escolhaPorId(String id) {
        for (Escolha escolha : jogoController.getCenaAtual().getOpcoes()) {
            if (escolha.getId().equals(id)) {
                return escolha;
            }
        }
        throw new IllegalArgumentException("Escolha inexistente nesta cena: " + id);
    }
}
