package model.persistencia;

import model.entidades.RegistroDeFinais;
import model.excecoes.FalhaAoSalvarException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Quem grava e lê o arquivo dos finais já vistos (finais.dat), na mesma
 * pasta dos saves.
 *
 * Funciona como o GerenciadorDePreferencias: ler nunca dá erro (sem
 * arquivo ou com arquivo ruim, devolve um registro vazio) e gravar pode
 * lançar FalhaAoSalvarException.
 */
public class GerenciadorDeFinais {

    private String pasta;

    /** Usa a pasta "saves", a mesma dos saves. */
    public GerenciadorDeFinais() {
        this("saves");
    }

    /** Usa outra pasta. Serve para os testes não mexerem nos arquivos de verdade. */
    public GerenciadorDeFinais(String pasta) {
        this.pasta = pasta;
    }

    /** Lê o registro. Sem arquivo, ou com arquivo ruim, devolve um registro vazio. */
    public RegistroDeFinais carregar() {
        File arquivo = new File(pasta, "finais.dat");
        if (!arquivo.exists()) {
            return new RegistroDeFinais();
        }

        try (FileInputStream arquivoAberto = new FileInputStream(arquivo);
             ObjectInputStream entrada = new ObjectInputStream(arquivoAberto)) {
            return (RegistroDeFinais) entrada.readObject();
        } catch (Exception e) {
            return new RegistroDeFinais(); // arquivo ruim: começa vazio
        }
    }

    /**
     * Grava o registro, criando a pasta se ela ainda não existir.
     *
     * @throws FalhaAoSalvarException se a pasta não puder ser criada ou a gravação falhar
     */
    public void salvar(RegistroDeFinais registro) throws FalhaAoSalvarException {
        File diretorio = new File(pasta);
        if (!diretorio.exists() && !diretorio.mkdirs()) {
            throw new FalhaAoSalvarException("Não foi possível criar a pasta de saves");
        }

        try (FileOutputStream arquivoAberto = new FileOutputStream(new File(pasta, "finais.dat"));
             ObjectOutputStream saida = new ObjectOutputStream(arquivoAberto)) {
            saida.writeObject(registro);
        } catch (IOException e) {
            throw new FalhaAoSalvarException("Não foi possível salvar os finais", e);
        }
    }
}
