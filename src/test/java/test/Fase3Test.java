package test;

import controller.ObservadorDeJogo;
import controller.JogoController;
import model.entidades.*;
import model.enums.*;
import model.excecoes.FalhaAoCarregarException;
import model.factory.*;
import model.persistencia.GerenciadorDeSaves;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/** Integração dos três GoF, persistência e percursos completos da história. */
public class Fase3Test {
    @Rule public TemporaryFolder pasta = new TemporaryFolder();
    private JogoController sessao;
    private static final String[] CAMINHO = {
            "ESC0102", "ESC0202", "ESC0204", "ESC0301", "ESC0305", "ESC0401", "ESC0406",
            "ESC0501", "ESC0601", "ESC0604", "ESC0702", "ESC0706", "ESC0803", "ESC0805", "ESC0901", "ESC0903"
    };

    @Before public void preparar() {
        sessao = new JogoController(pasta.getRoot().getPath());
        sessao.novaPartida("Bruno", Preset.COMUM);
    }

    private void escolher(String id) {
        for (Escolha escolha : sessao.disponiveis()) {
            if (escolha.getId().equals(id)) { sessao.escolher(escolha); return; }
        }
        fail("Escolha indisponível: " + id + " em " + sessao.getCenaAtual().getId());
    }

    private void irAte(String cena) {
        for (String escolha : CAMINHO) {
            if (sessao.getCenaAtual().getId().equals(cena)) return;
            escolher(escolha);
        }
        assertEquals(cena, sessao.getCenaAtual().getId());
    }

    @Test public void mementoGuardaUmaCopiaIndependenteDeTodoOEstado() {
        Partida original = sessao.getPartida();
        original.getInventario().adicionar(Item.CHAVE);
        original.ligarFlag(Flag.PORTA_ABERTA);
        original.getDavi().alterarConfianca(40);
        Partida.Memento estado = original.criarMemento();
        original.getInventario().remover(Item.CHAVE);
        original.getDavi().alterarConfianca(-50);
        Partida restaurada = Partida.restaurar(estado);
        assertTrue(restaurada.getInventario().possui(Item.CHAVE));
        assertTrue(restaurada.temFlag(Flag.PORTA_ABERTA));
        assertEquals(70, restaurada.getDavi().getConfianca());
        restaurada.getProtagonista().alterarAtributo(Atributo.FOLEGO, -40);
        assertEquals(50, Partida.restaurar(estado).getProtagonista().getAtributo(Atributo.FOLEGO));
    }

    @Test public void saveNaoMudaQuandoAJogadaContinua() throws Exception {
        escolher("ESC0102");
        sessao.salvar(1);
        escolher("ESC0202");
        escolher("ESC0204");
        sessao.carregar(1);
        assertEquals("CAP02", sessao.getCenaAtual().getId());
        assertEquals(30, sessao.getPartida().getDavi().getConfianca());
        assertTrue(sessao.getPartida().getInventario().possui(Item.TERCO));
        assertTrue(sessao.getPartida().temFlag(Flag.PORTA_ABERTA));
        escolher("ESC0202");
        escolher("ESC0204");
        assertEquals(70, sessao.getPartida().getDavi().getConfianca());
    }

    @Test public void observerRecebeUmAvisoPorAcaoEPodeSerRemovido() {
        List<String> mensagens = new ArrayList<>();
        ObservadorDeJogo observador = mensagens::add;
        sessao.adicionarObservador(observador);
        sessao.adicionarObservador(observador);
        escolher("ESC0102"); // Inclui autosave, mas é uma única mudança para a view.
        assertEquals(1, mensagens.size());
        assertTrue(mensagens.getFirst().contains("confiança +10"));
        assertTrue(mensagens.getFirst().contains("automaticamente"));
        sessao.removerObservador(observador);
        escolher("ESC0202");
        assertEquals(1, mensagens.size());
    }

    @Test public void factoryMethodCriaOsDoisTiposDePersonagem() {
        FabricaDePersonagem fabrica = new FabricaDeProtagonista(Preset.ATLETA);
        PersonagemBase jogador = fabrica.criar("  Ana  ");
        assertTrue(jogador instanceof Protagonista);
        assertEquals("Ana", jogador.getNome());
        assertEquals(90, ((Protagonista) jogador).getAtributo(Atributo.FOLEGO));
        fabrica = new FabricaDeNpc(30);
        assertEquals(30, ((Npc) fabrica.criar("Davi")).getConfianca());
        assertEquals("Vicente", new FabricaDeProtagonista(null).criar(" ").getNome());
    }

    @Test public void todosOsOnzeFinaisSaoAlcancaveisEContinuamNaGaleria() {
        verificarFinal("CAP02", "MORTE_NOME", "ESC0201");
        verificarFinal("CAP03", "MORTE_FILA", "ESC0304");
        verificarFinal("CAP04B", "MORTE_JANELA", "ESC0404");
        verificarFinal("CAP05", "FIM_DESISTE", "ESC0505");
        verificarFinal("CAP06", "MORTE_PÁTIO", "ESC0603");
        verificarFinal("CAP07", "MORTE_CASARÃO", "ESC0704");
        verificarFinal("CAP07", "MORTE_VELA", "ESC0701", "ESC0706", "ESC0801");
        verificarFinal("CAP09B", "MORTE_CHAMOU", "ESC0906");
        verificarFinal("CAP09B", "FIM_TARDE", "ESC0907");
        verificarFinal("CAP10", "MORTE_CÍRCULO", "ESC1006");
        verificarFinal("CAP10", "FIM_A_TEMPO", "ESC1001", "ESC1002", "ESC1004");
        assertEquals(11, new JogoController(pasta.getRoot().getPath()).getRegistroDeFinais().quantosViu());
    }

    private void verificarFinal(String cena, String finalEsperado, String... escolhas) {
        sessao.novaPartida("Bruno", Preset.COMUM);
        irAte(cena);
        for (String id : escolhas) escolher(id);
        assertEquals(finalEsperado, sessao.getCenaAtual().getId());
        assertTrue(sessao.cenaFinal());
        assertTrue(sessao.getRegistroDeFinais().jaViu(finalEsperado));
        assertEquals(100, sessao.getProgresso());
    }

    @Test public void preferenciasPersistemEControlamOAutosave() throws Exception {
        sessao.alterarPreferencias(Preset.OBSERVADOR, true, false);
        sessao.excluir(0);
        sessao.novaPartida("Ana", Preset.OBSERVADOR);
        escolher("ESC0102");
        assertFalse(sessao.existeSlot(0));
        assertTrue(sessao.temAlteracoesNaoSalvas());
        JogoController outra = new JogoController(pasta.getRoot().getPath());
        assertEquals(Preset.OBSERVADOR, outra.getPreferencias().getPresetPadrao());
        assertTrue(outra.getPreferencias().isPularEnter());
        assertFalse(outra.getPreferencias().isAutosaveLigado());
        sessao.salvar(1);
        assertFalse(sessao.temAlteracoesNaoSalvas());
    }

    @Test public void carregarSaveInvalidoPreservaAPartidaAtual() throws Exception {
        new GerenciadorDeSaves(pasta.getRoot().getPath()).salvar(2,
                new Save("Ana", LocalDateTime.now().plusHours(1), "CENA_INVALIDA", "?", 0, sessao.getPartida()));
        try { sessao.carregar(2); fail("Deveria rejeitar a cena inválida"); }
        catch (FalhaAoCarregarException esperado) { assertEquals("CAP01", sessao.getCenaAtual().getId()); }
        sessao.continuar(); // Ignora o save inválido mais recente e usa o autosave.
        assertEquals("CAP01", sessao.getCenaAtual().getId());
    }

    @Test public void falhaNoAutosaveAvisaSemImpedirOJogo() throws Exception {
        sessao = new JogoController(pasta.newFile("nao-e-pasta").getPath());
        sessao.novaPartida("Ana", Preset.COMUM);
        assertTrue(sessao.getUltimaMensagem().contains("Atenção"));
        assertTrue(sessao.temAlteracoesNaoSalvas());
        escolher("ESC0102");
        assertEquals("CAP02", sessao.getCenaAtual().getId());
    }

    @Test public void escolhaBloqueadaOuDeOutraCenaNaoPodeSerExecutada() {
        Escolha bloqueada = sessao.bloqueadas().getFirst();
        try { sessao.escolher(bloqueada); fail("Deveria rejeitar a escolha bloqueada"); }
        catch (IllegalArgumentException esperado) { assertEquals("CAP01", sessao.getCenaAtual().getId()); }
        Escolha antiga = sessao.disponiveis().getFirst();
        sessao.escolher(antiga);
        try { sessao.escolher(antiga); fail("Deveria rejeitar uma escolha antiga"); }
        catch (IllegalArgumentException esperado) { assertEquals("CAP02", sessao.getCenaAtual().getId()); }
    }

    @Test public void motivoDoBloqueioCorrespondeACondicaoQueFalhou() {
        Escolha escolha = new Escolha("X", "texto", null)
                .exigeFlag(Flag.DAVI_JUNTO, "Davi não está com você")
                .exigeConfianca(sessao.getPartida().getDavi(), 60, "Davi não confia em você");
        sessao.getPartida().getDavi().alterarConfianca(40);
        assertEquals("Davi não está com você", escolha.getMotivoDoBloqueio(sessao.getPartida()));
    }

    @Test public void perguntaDeAntonioAgoraTemCaminhoDeDesbloqueio() {
        irAte("CAP04");
        escolher("ESC0402");
        assertEquals("CAP04B", sessao.getCenaAtual().getId());
    }
}
