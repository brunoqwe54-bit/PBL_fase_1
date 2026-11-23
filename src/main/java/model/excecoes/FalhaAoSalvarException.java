package model.excecoes;

/**
 * Indica uma falha ao salvar dados. O controlador deve tratar o erro e informar o
 * jogador.
 */
public class FalhaAoSalvarException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Cria a exceção com uma mensagem.
     *
     * @param mensagem o texto que explica a falha
     */
    public FalhaAoSalvarException(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria a exceção com uma mensagem e a causa original.
     *
     * @param mensagem o texto que explica a falha
     * @param causa    o erro original, guardado para ajudar a depurar
     */
    public FalhaAoSalvarException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
