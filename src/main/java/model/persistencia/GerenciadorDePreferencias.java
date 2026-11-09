package model.persistencia;

import model.entidades.Preferencias;
import model.excecoes.FalhaAoSalvarException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Quem grava e lê o arquivo das preferências (preferencias.dat), na mesma
 * pasta dos saves.
 *
 * Ler nunca dá erro: se o arquivo não existe ou está ruim, devolve as
 * preferências de fábrica, porque o jogo precisa abrir de qualquer jeito.
 * Gravar pode falhar, e aí lança FalhaAoSalvarException.
 */
public class GerenciadorDePreferencias {

    private String pasta;

    /** Usa a pasta "saves", a mesma dos saves. */
    public GerenciadorDePreferencias() {
        this("saves");
    }

    /** Usa outra pasta. Serve para os testes não mexerem nos arquivos de verdade. */
    public GerenciadorDePreferencias(String pasta) {
        this.pasta = pasta;
    }

    /** Lê as preferências. Sem arquivo, ou com arquivo ruim, devolve as de fábrica. */
    public Preferencias carregar() {
        File arquivo = new File(pasta, "preferencias.dat");
        if (!arquivo.exists()) {
            return new Preferencias();
        }

        try (FileInputStream arquivoAberto = new FileInputStream(arquivo);
             ObjectInputStream entrada = new ObjectInputStream(arquivoAberto)) {
            Preferencias lidas = (Preferencias) entrada.readObject();
            if (lidas.getPresetPadrao() != null) {
                return lidas;
            }
        } catch (Exception e) {
            // arquivo ruim: cai no return de baixo, com os valores de fábrica
        }
        return new Preferencias();
    }

    /**
     * Grava as preferências, criando a pasta se ela ainda não existir.
     *
     * @throws FalhaAoSalvarException se a pasta não puder ser criada ou a gravação falhar
     */
    public void salvar(Preferencias preferencias) throws FalhaAoSalvarException {
        File diretorio = new File(pasta);
        if (!diretorio.exists() && !diretorio.mkdirs()) {
            throw new FalhaAoSalvarException("Não foi possível criar a pasta de saves");
        }

        try (FileOutputStream arquivoAberto = new FileOutputStream(new File(pasta, "preferencias.dat"));
             ObjectOutputStream saida = new ObjectOutputStream(arquivoAberto)) {
            saida.writeObject(preferencias);
        } catch (IOException e) {
            throw new FalhaAoSalvarException("Não foi possível salvar as preferências", e);
        }
    }
}
