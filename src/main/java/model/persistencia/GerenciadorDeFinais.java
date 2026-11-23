package model.persistencia;

import model.entidades.RegistroDeFinais;
import model.excecoes.FalhaAoSalvarException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Grava e lê os finais descobertos. Se a leitura falhar, começa com um registro vazio.
 */
public class GerenciadorDeFinais {

    private String pasta;

    /** Usa a pasta saves no diretório de execução. */
    public GerenciadorDeFinais() {
        this("saves");
    }

    /** Usa outra pasta para isolar os testes. */
    public GerenciadorDeFinais(String pasta) {
        this.pasta = pasta;
    }

    /** Carrega os finais ou devolve um registro vazio. */
    public RegistroDeFinais carregar() {
        File arquivo = new File(pasta, "finais.dat");
        if (!arquivo.exists()) {
            return new RegistroDeFinais();
        }

        try (FileInputStream arquivoAberto = new FileInputStream(arquivo);
             ObjectInputStream entrada = new ObjectInputStream(arquivoAberto)) {
            RegistroDeFinais registro = (RegistroDeFinais) entrada.readObject();
            return registro == null ? new RegistroDeFinais() : registro;
        } catch (Exception e) {
            return new RegistroDeFinais(); // Começa com o registro vazio se o arquivo estiver inválido.
        }
    }

    /**
     * Grava o registro, criando a pasta se ela ainda não existir.
     *
     * @throws FalhaAoSalvarException se a pasta não puder ser criada ou a gravação
     *     falhar
     */
    public void salvar(RegistroDeFinais registro) throws FalhaAoSalvarException {
        File diretorio = new File(pasta);
        if (!diretorio.exists() && !diretorio.mkdirs()) {
            throw new FalhaAoSalvarException("Não foi possível criar a pasta de saves");
        }

        try {
            ArquivoSerializado.gravar(new File(pasta, "finais.dat"), registro);
        } catch (IOException e) {
            throw new FalhaAoSalvarException("Não foi possível salvar os finais", e);
        }
    }
}
