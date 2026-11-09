package model.excecoes;

/**
 * Falha ao carregar um save: arquivo inexistente, corrompido, de versão
 * incompatível, ou com dado inválido (por exemplo, id de cena que não
 * existe).
 *
 * É checada (herda de Exception): quem chama é obrigado a tratar o erro,
 * e é o controller que decide o que mostrar ao jogador.
 */
public class FalhaAoCarregarException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param mensagem o texto que explica a falha
     */
    public FalhaAoCarregarException(String mensagem) {
        super(mensagem);
    }

    /**
     * @param mensagem o texto que explica a falha
     * @param causa    o erro original, guardado para ajudar a depurar
     */
    public FalhaAoCarregarException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
