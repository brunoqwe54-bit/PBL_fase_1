package test;

import model.entidades.RegistroDeFinais;
import model.excecoes.FalhaAoSalvarException;
import model.persistencia.GerenciadorDeFinais;

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
 * Testes do GerenciadorDeFinais. Usam uma pasta temporária, que o JUnit
 * apaga no fim, para não mexer nos finais de verdade.
 */
public class GerenciadorDeFinaisTest {

    @Rule
    public TemporaryFolder pastaTemporaria = new TemporaryFolder();

    private String pasta;
    private GerenciadorDeFinais gerenciador;

    @Before
    public void preparar() {
        pasta = pastaTemporaria.getRoot().getAbsolutePath();
        gerenciador = new GerenciadorDeFinais(pasta);
    }

    @Test
    public void semArquivoDevolveRegistroVazio() {
        assertEquals(0, gerenciador.carregar().quantosViu());
    }

    @Test
    public void salvarELerMantemOsFinaisVistos() throws FalhaAoSalvarException {
        RegistroDeFinais registro = new RegistroDeFinais();
        registro.registrar("FIM_A_TEMPO");
        registro.registrar("MORTE_PÁTIO");

        gerenciador.salvar(registro);
        RegistroDeFinais lido = gerenciador.carregar();

        assertEquals(2, lido.quantosViu());
        assertTrue(lido.jaViu("FIM_A_TEMPO"));
        assertTrue(lido.jaViu("MORTE_PÁTIO"));
        assertFalse(lido.jaViu("FIM_TARDE"));
    }

    @Test
    public void salvarCriaAPastaSeNaoExistir() throws FalhaAoSalvarException {
        String pastaNova = pasta + File.separator + "nova";

        new GerenciadorDeFinais(pastaNova).salvar(new RegistroDeFinais());

        assertTrue(new File(pastaNova, "finais.dat").exists());
    }

    @Test
    public void arquivoCorrompidoDevolveRegistroVazio() throws IOException {
        FileWriter escritor = new FileWriter(new File(pasta, "finais.dat"));
        escritor.write("isso nao e um registro");
        escritor.close();

        assertEquals(0, gerenciador.carregar().quantosViu());
    }

    @Test(expected = FalhaAoSalvarException.class)
    public void salvarFalhaQuandoAPastaNaoPodeSerCriada() throws IOException, FalhaAoSalvarException {
        File arquivoComum = pastaTemporaria.newFile("arquivo.txt");

        new GerenciadorDeFinais(arquivoComum.getAbsolutePath()).salvar(new RegistroDeFinais());
    }
}
