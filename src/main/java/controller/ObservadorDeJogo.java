package controller;

/** Recebe os avisos de mudança enviados pelo JogoController. */
public interface ObservadorDeJogo {
    /**
     * Atualiza a tela após uma mudança na partida.
     *
     * @param mensagem aviso enviado pelo controlador
     */
    void atualizar(String mensagem);
}
