package model.persistencia;

import model.entidades.Save;
import model.excecoes.FalhaAoCarregarException;
import model.excecoes.FalhaAoSalvarException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Grava, lê e exclui os saves. O slot 0 é automático e os slots 1 a 3 são manuais.
 * Falhas são informadas por exceções próprias do jogo.
 */
public class GerenciadorDeSaves {

    public static final int AUTOSAVE = 0;
    public static final int TOTAL_DE_SLOTS_MANUAIS = 3;

    private String pasta;

    /** Usa a pasta saves no diretório de execução. */
    public GerenciadorDeSaves() {
        this("saves");
    }

    /**
     * Usa uma pasta indicada, permitindo isolar os arquivos dos testes.
     *
     * @param pasta o caminho da pasta onde os arquivos ficam
     */
    public GerenciadorDeSaves(String pasta) {
        this.pasta = pasta;
    }

    /**
     * Grava o save e cria a pasta se necessário. Um save existente é substituído.
     *
     * @param slot 0 para o autosave, ou de 1 a 3 para os manuais
     * @param save o que será gravado
     * @throws FalhaAoSalvarException se o slot for inválido ou a gravação falhar
     */
    public void salvar(int slot, Save save) throws FalhaAoSalvarException {
        if (!slotValido(slot)) {
            throw new FalhaAoSalvarException("Slot inválido: " + slot);
        }
        if (save == null) {
            throw new FalhaAoSalvarException("Não há nada para salvar");
        }

        File diretorio = new File(pasta);
        if (!diretorio.exists() && !diretorio.mkdirs()) {
            throw new FalhaAoSalvarException("Não foi possível criar a pasta de saves");
        }

        try {
            ArquivoSerializado.gravar(arquivoDoSlot(slot), save);
        } catch (IOException e) {
            throw new FalhaAoSalvarException("Não foi possível salvar " + descricao(slot), e);
        }
    }

    /**
     * Lê o save de um slot.
     *
     * @param slot 0 para o autosave, ou de 1 a 3 para os manuais
     * @return o save lido
     * @throws FalhaAoCarregarException se o slot for inválido, estiver vazio, o arquivo
     *     estiver corrompido ou os dados não fizerem sentido
     */
    public Save carregar(int slot) throws FalhaAoCarregarException {
        if (!slotValido(slot)) {
            throw new FalhaAoCarregarException("Slot inválido: " + slot);
        }

        File arquivo = arquivoDoSlot(slot);
        if (!arquivo.exists()) {
            throw new FalhaAoCarregarException("Não existe save para " + descricao(slot));
        }

        // Fecha os dois recursos ao terminar a leitura.
        try (FileInputStream arquivoAberto = new FileInputStream(arquivo);
             ObjectInputStream entrada = new ObjectInputStream(arquivoAberto)) {
            Object lido = entrada.readObject();

            if (!(lido instanceof Save)) {
                throw new FalhaAoCarregarException("O arquivo de " + descricao(slot)
                        + " não é um save");
            }
            Save save = (Save) lido;

            if (save.getPartida() == null || save.getIdCena() == null
                    || save.getDataHora() == null) {
                throw new FalhaAoCarregarException("O save de " + descricao(slot)
                        + " está incompleto");
            }
            return save;
        } catch (IOException e) {
            throw new FalhaAoCarregarException("Não foi possível carregar " + descricao(slot), e);
        } catch (ClassNotFoundException e) {
            throw new FalhaAoCarregarException("Não foi possível carregar " + descricao(slot), e);
        } catch (RuntimeException e) {
            /* Converte erros de dados inválidos em uma exceção do jogo. */
            throw new FalhaAoCarregarException("O save de " + descricao(slot) + " está corrompido", e);
        }
    }

    /**
     * Diz se existe um arquivo naquele slot. Não confere se ele abre.
     *
     * @param slot 0 para o autosave, ou de 1 a 3 para os manuais
     * @return true se o arquivo existe
     */
    public boolean existe(int slot) {
        return slotValido(slot) && arquivoDoSlot(slot).exists();
    }

    /**
     * Apaga o arquivo de um slot.
     *
     * @param slot 0 para o autosave, ou de 1 a 3 para os manuais
     * @return true se apagou, false se o slot era inválido ou já estava vazio
     */
    public boolean excluir(int slot) {
        return existe(slot) && arquivoDoSlot(slot).delete();
    }

    /**
     * Busca o save mais recente pela data e ignora slots vazios ou corrompidos.
     *
     * @return o número do slot, ou -1 se nenhum save pôde ser lido
     */
    public int maisRecente() {
        int melhorSlot = -1;
        Save melhorSave = null;

        for (int slot = AUTOSAVE; slot <= TOTAL_DE_SLOTS_MANUAIS; slot++) {
            try {
                Save save = carregar(slot);
                if (melhorSave == null || save.getDataHora().isAfter(melhorSave.getDataHora())) {
                    melhorSave = save;
                    melhorSlot = slot;
                }
            } catch (FalhaAoCarregarException e) {
                // Ignora este slot e continua a busca.
            }
        }
        return melhorSlot;
    }

    private boolean slotValido(int slot) {
        return slot >= AUTOSAVE && slot <= TOTAL_DE_SLOTS_MANUAIS;
    }

    private File arquivoDoSlot(int slot) {
        String nome = (slot == AUTOSAVE) ? "autosave.dat" : "slot" + slot + ".dat";
        return new File(pasta, nome);
    }

    /** Devolve o nome do slot usado nas mensagens. */
    private String descricao(int slot) {
        return (slot == AUTOSAVE) ? "o autosave" : "o slot " + slot;
    }
}
