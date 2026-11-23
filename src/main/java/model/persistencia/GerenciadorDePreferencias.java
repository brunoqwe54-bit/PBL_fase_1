package model.persistencia;

import model.entidades.Preferencias;
import model.excecoes.FalhaAoSalvarException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/** Grava e lê as preferências. Se a leitura falhar, usa os valores padrão. */
public class GerenciadorDePreferencias {

    private String pasta;

    /** Usa a pasta saves no diretório de execução. */
    public GerenciadorDePreferencias() {
        this("saves");
    }

    /** Usa outra pasta para isolar os testes. */
    public GerenciadorDePreferencias(String pasta) {
        this.pasta = pasta;
    }

    /** Carrega as preferências ou devolve os valores padrão. */
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
            // Usa os valores padrão se o arquivo estiver inválido.
        }
        return new Preferencias();
    }

    /**
     * Grava as preferências, criando a pasta se ela ainda não existir.
     *
     * @throws FalhaAoSalvarException se a pasta não puder ser criada ou a gravação
     *     falhar
     */
    public void salvar(Preferencias preferencias) throws FalhaAoSalvarException {
        File diretorio = new File(pasta);
        if (!diretorio.exists() && !diretorio.mkdirs()) {
            throw new FalhaAoSalvarException("Não foi possível criar a pasta de saves");
        }

        try {
            ArquivoSerializado.gravar(new File(pasta, "preferencias.dat"), preferencias);
        } catch (IOException e) {
            throw new FalhaAoSalvarException("Não foi possível salvar as preferências", e);
        }
    }
}
