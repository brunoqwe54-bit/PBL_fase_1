package controller;

/** Observer: a sessão avisa as telas quando o estado do jogo muda. */
public interface ObservadorDeJogo {
    void atualizar(String mensagem);
}
