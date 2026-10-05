package model.persistencia;

import model.entidades.Save;
import model.excecoes.FalhaAoCarregarException;
import model.excecoes.FalhaAoSalvarException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Quem sabe mexer nos arquivos de save: grava, lê, exclui e descobre qual
 * é o mais recente.
 *
 * Existem 4 slots: os manuais, de 1 a 3, e o autosave, que é o número 0.
 * Cada um é um arquivo dentro da pasta de saves (slot1.dat, slot2.dat,
 * slot3.dat e autosave.dat).
 *
 * Qualquer falha vira uma exceção do jogo (FalhaAoSalvarException ou
 * FalhaAoCarregarException). Quem decide o que mostrar ao jogador é o
 * controller, aqui só se avisa que deu errado.
 */
public class GerenciadorDeSaves {

    public static final int AUTOSAVE = 0;
    public static final int TOTAL_DE_SLOTS_MANUAIS = 3;

    private String pasta;

    /** Usa a pasta "saves", na raiz de onde o programa roda. */
    public GerenciadorDeSaves() {
        this("saves");
    }

    /**
     * Usa outra pasta. Serve para os testes não mexerem nos saves de verdade.
     *
     * @param pasta o caminho da pasta onde os arquivos ficam
     */
    public GerenciadorDeSaves(String pasta) {
        this.pasta = pasta;
    }

    /**
     * Grava o save no slot, criando a pasta se ela ainda não existir.
     * Se já houver um save ali, ele é substituído.
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

        /* Cada recurso em uma linha do try: assim o Java fecha os dois no fim,
         * mesmo se der erro no meio. Se o FileOutputStream ficasse escondido
         * dentro do ObjectOutputStream e o segundo falhasse ao ser criado,
         * o arquivo ficaria aberto e o Windows não deixaria apagá-lo depois.
         */
        try (FileOutputStream arquivoAberto = new FileOutputStream(arquivoDoSlot(slot));
             ObjectOutputStream saida = new ObjectOutputStream(arquivoAberto)) {
            saida.writeObject(save);
        } catch (IOException e) {
            throw new FalhaAoSalvarException("Não foi possível salvar " + descricao(slot), e);
        }
    }

    /**
     * Lê o save de um slot.
     *
     * @param slot 0 para o autosave, ou de 1 a 3 para os manuais
     * @return o save lido
     * @throws FalhaAoCarregarException se o slot for inválido, estiver vazio,
     *         o arquivo estiver corrompido ou os dados não fizerem sentido
     */
    public Save carregar(int slot) throws FalhaAoCarregarException {
        if (!slotValido(slot)) {
            throw new FalhaAoCarregarException("Slot inválido: " + slot);
        }

        File arquivo = arquivoDoSlot(slot);
        if (!arquivo.exists()) {
            throw new FalhaAoCarregarException("Não existe save para " + descricao(slot));
        }

        // mesmo esquema do salvar: os dois recursos são fechados no fim
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
            /* Um arquivo estragado no meio também pode dar erros que não são
             * IOException, como ClassCastException. Eles viram a exceção do
             * jogo, para um slot ruim nunca derrubar o Continuar.
             */
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
     * Descobre o slot do save mais recente, olhando a data e hora de cada um.
     * Slot vazio ou corrompido é ignorado, para um arquivo ruim não impedir
     * o jogador de continuar.
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
                // vazio ou corrompido: não serve para o "Continuar", segue para o próximo
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

    /** O nome do slot para as mensagens: "o autosave" ou "o slot 2". */
    private String descricao(int slot) {
        return (slot == AUTOSAVE) ? "o autosave" : "o slot " + slot;
    }
}
