package test;

import model.entidades.Preferencias;
import model.enums.Preset;
import model.excecoes.FalhaAoSalvarException;
import model.persistencia.GerenciadorDePreferencias;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testes do GerenciadorDePreferencias. Usam uma pasta temporária, que o
 * JUnit apaga no fim, para não mexer nas preferências de verdade.
 */
public class GerenciadorDePreferenciasTest {

    @Rule
    public TemporaryFolder pastaTemporaria = new TemporaryFolder();

    private String pasta;
    private GerenciadorDePreferencias gerenciador;

    @Before
    public void preparar() {
        pasta = pastaTemporaria.getRoot().getAbsolutePath();
        gerenciador = new GerenciadorDePreferencias(pasta);
    }

    @Test
    public void semArquivoDevolveOsValoresDeFabrica() {
        Preferencias preferencias = gerenciador.carregar();

        assertEquals(Preset.COMUM, preferencias.getPresetPadrao());
        assertFalse(preferencias.isPularEnter());
        assertTrue(preferencias.isAutosaveLigado());
    }

    @Test
    public void salvarELerDevolveOsMesmosValores() throws FalhaAoSalvarException {
        Preferencias preferencias = new Preferencias();
        preferencias.setPresetPadrao(Preset.ATLETA);
        preferencias.setPularEnter(true);
        preferencias.setAutosaveLigado(false);

        gerenciador.salvar(preferencias);
        Preferencias lidas = gerenciador.carregar();

        assertEquals(Preset.ATLETA, lidas.getPresetPadrao());
        assertTrue(lidas.isPularEnter());
        assertFalse(lidas.isAutosaveLigado());
    }

    @Test
    public void salvarCriaAPastaSeNaoExistir() throws FalhaAoSalvarException {
        String pastaNova = pasta + File.separator + "nova";
        GerenciadorDePreferencias outro = new GerenciadorDePreferencias(pastaNova);

        outro.salvar(new Preferencias());

        assertTrue(new File(pastaNova, "preferencias.dat").exists());
    }

    @Test
    public void arquivoCorrompidoDevolveOsValoresDeFabrica() throws IOException {
        FileWriter escritor = new FileWriter(new File(pasta, "preferencias.dat"));
        escritor.write("isso nao e uma preferencia");
        escritor.close();

        Preferencias preferencias = gerenciador.carregar();

        assertEquals(Preset.COMUM, preferencias.getPresetPadrao());
        assertTrue(preferencias.isAutosaveLigado());
    }

    @Test(expected = FalhaAoSalvarException.class)
    public void salvarFalhaQuandoAPastaNaoPodeSerCriada() throws IOException, FalhaAoSalvarException {
        // a "pasta" é na verdade um arquivo, então não dá para criar nada dentro dela
        File arquivoComum = pastaTemporaria.newFile("arquivo.txt");
        GerenciadorDePreferencias outro = new GerenciadorDePreferencias(arquivoComum.getAbsolutePath());

        outro.salvar(new Preferencias());
    }
}
