package test;

import model.entidades.Partida;
import model.entidades.Save;
import model.enums.Atributo;
import model.enums.Preset;
import model.excecoes.FalhaAoCarregarException;
import model.excecoes.FalhaAoSalvarException;
import model.persistencia.GerenciadorDeSaves;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testes da classe GerenciadorDeSaves.
 *
 * O que precisa ficar provado: o que é gravado volta igual, slot vazio,
 * inválido ou corrompido vira a exceção do jogo (e não um erro técnico do
 * Java), e o "mais recente" escolhe pela data e ignora slot ruim.
 */
public class GerenciadorDeSavesTest {

    /* Uma pasta temporária que o JUnit cria antes de CADA teste e apaga
     * depois. Assim os testes nunca mexem na pasta saves de verdade.
     */
    @Rule
    public TemporaryFolder pasta = new TemporaryFolder();

    private GerenciadorDeSaves gerenciador;

    @Before
    public void criarGerenciador() {
        gerenciador = new GerenciadorDeSaves(pasta.getRoot().getPath());
    }

    // Monta um save pronto, só mudando o nome e a data.
    private Save criarSave(String nome, LocalDateTime dataHora) {
        Partida partida = new Partida(nome, Preset.COMUM);
        return new Save(nome, dataHora, "CAP03", "Capítulo 3 - A Praça", 20, partida);
    }

    // Grava lixo no lugar do arquivo de um slot, para simular corrupção.
    private void corromperSlot(String nomeDoArquivo) throws Exception {
        File arquivo = new File(pasta.getRoot(), nomeDoArquivo);
        Files.write(arquivo.toPath(), "isto nao e um save".getBytes());
    }

    @Test
    public void salvarECarregarDevolveOQueFoiGravado() throws Exception {
        Save original = criarSave("Vicente", LocalDateTime.of(2026, 10, 3, 21, 30));
        original.getPartida().getProtagonista().alterarAtributo(Atributo.FOLEGO, -20);

        gerenciador.salvar(2, original);
        Save lido = gerenciador.carregar(2);

        assertEquals("Vicente", lido.getNomeJogador());
        assertEquals("CAP03", lido.getIdCena());
        assertEquals(20, lido.getProgresso());
        assertEquals(original.getDataHora(), lido.getDataHora());
        assertEquals(30, lido.getPartida().getProtagonista().getAtributo(Atributo.FOLEGO));
    }

    @Test
    public void salvarCriaAPastaQuandoElaNaoExiste() throws Exception {
        File naoExiste = new File(pasta.getRoot(), "novaPasta");
        GerenciadorDeSaves outro = new GerenciadorDeSaves(naoExiste.getPath());

        outro.salvar(1, criarSave("Vicente", LocalDateTime.now()));

        assertTrue(outro.existe(1));
    }

    @Test
    public void salvarNoMesmoSlotSubstituiOSaveAntigo() throws Exception {
        gerenciador.salvar(1, criarSave("Primeiro", LocalDateTime.now()));
        gerenciador.salvar(1, criarSave("Segundo", LocalDateTime.now()));

        assertEquals("Segundo", gerenciador.carregar(1).getNomeJogador());
    }

    @Test
    public void autosaveTambemSalvaECarrega() throws Exception {
        gerenciador.salvar(GerenciadorDeSaves.AUTOSAVE, criarSave("Auto", LocalDateTime.now()));

        assertEquals("Auto", gerenciador.carregar(GerenciadorDeSaves.AUTOSAVE).getNomeJogador());
    }

    @Test
    public void existeDevolveFalseParaSlotVazioETrueDepoisDeSalvar() throws Exception {
        assertFalse(gerenciador.existe(1));

        gerenciador.salvar(1, criarSave("Vicente", LocalDateTime.now()));

        assertTrue(gerenciador.existe(1));
    }

    @Test(expected = FalhaAoCarregarException.class)
    public void carregarSlotVazioLancaFalhaAoCarregar() throws Exception {
        gerenciador.carregar(3);
    }

    @Test(expected = FalhaAoCarregarException.class)
    public void carregarArquivoCorrompidoLancaFalhaAoCarregar() throws Exception {
        corromperSlot("slot1.dat");

        gerenciador.carregar(1);
    }

    @Test(expected = FalhaAoCarregarException.class)
    public void carregarSlotInvalidoLancaFalhaAoCarregar() throws Exception {
        gerenciador.carregar(9);
    }

    @Test(expected = FalhaAoSalvarException.class)
    public void salvarSlotInvalidoLancaFalhaAoSalvar() throws Exception {
        gerenciador.salvar(9, criarSave("Vicente", LocalDateTime.now()));
    }

    @Test(expected = FalhaAoSalvarException.class)
    public void salvarNullLancaFalhaAoSalvar() throws Exception {
        gerenciador.salvar(1, null);
    }

    @Test
    public void excluirApagaOSlotEDevolveTrue() throws Exception {
        gerenciador.salvar(1, criarSave("Vicente", LocalDateTime.now()));

        assertTrue(gerenciador.excluir(1));
        assertFalse(gerenciador.existe(1));
    }

    @Test
    public void excluirSlotVazioDevolveFalse() {
        assertFalse(gerenciador.excluir(2));
    }

    @Test
    public void maisRecenteSemNenhumSaveDevolveMenosUm() {
        assertEquals(-1, gerenciador.maisRecente());
    }

    @Test
    public void maisRecenteEscolhePelaDataEHora() throws Exception {
        gerenciador.salvar(1, criarSave("Antigo", LocalDateTime.of(2026, 10, 1, 10, 0)));
        gerenciador.salvar(2, criarSave("Novo", LocalDateTime.of(2026, 10, 3, 10, 0)));
        gerenciador.salvar(3, criarSave("Meio", LocalDateTime.of(2026, 10, 2, 10, 0)));

        assertEquals(2, gerenciador.maisRecente());
    }

    @Test
    public void maisRecenteIgnoraSlotCorrompido() throws Exception {
        gerenciador.salvar(1, criarSave("Bom", LocalDateTime.of(2026, 10, 1, 10, 0)));
        corromperSlot("slot2.dat");

        assertEquals(1, gerenciador.maisRecente());
    }
}
