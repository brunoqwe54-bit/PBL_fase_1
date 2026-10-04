package model.excecoes;

/**
 * Falha ao gravar um save: disco cheio, sem permissão, pasta que não
 * pôde ser criada.
 *
 * É checada (herda de Exception): quem chama é obrigado a tratar o erro,
 * e é o controller que decide o que mostrar ao jogador.
 */
public class FalhaAoSalvarException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param mensagem o texto que explica a falha
     */
    public FalhaAoSalvarException(String mensagem) {
        super(mensagem);
    }

    /**
     * @param mensagem o texto que explica a falha
     * @param causa    o erro original, guardado para ajudar a depurar
     */
    public FalhaAoSalvarException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
