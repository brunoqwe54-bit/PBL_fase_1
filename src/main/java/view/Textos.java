package view;

/**
 * Textos longos mostrados pelas telas.
 *
 * Ficam aqui para que o menu em console e o menu em janela exibam
 * exatamente o mesmo conteúdo.
 */
public class Textos {

    /** Classe só de constantes. */
    private Textos() {
    }

    public static final String INSTRUCOES = """
            COMO SE JOGA

            Você lê, você escolhe, você vive com isso.
            Digite o número da alternativa e pressione ENTER.

            O protagonista tem três atributos: FÔLEGO, CORAGEM e LUCIDEZ.
            O FÔLEGO só desce, e a água do cantil é o único jeito de
            recuperar um pouco. Coragem e Lucidez sobem e descem
            conforme o que você faz, e algumas
            escolhas só aparecem se o atributo for alto o bastante.

            As pessoas que você encontra confiam mais ou menos em você.
            Quem confia, ajuda. Quem não confia, fecha a porta.

            Escolhas marcadas com [-] existem, mas estão bloqueadas:
            falta um item, um atributo ou uma decisão que você não tomou.""";
    public static final String CREDITOS = """
            A NOITE LONGA

            Projeto acadêmico - EXA863 MI Programação
            Universidade Estadual de Feira de Santana

            Docente:
              Roberto Almeida Bittencourt

            Discentes:
              Bruno Gabriel Santos Pinto
              Caio Cerqueira Francisco Batista

            Jogo narrativo interativo em Java, arquitetura MVC.
            Nenhuma engine de jogo foi utilizada.

            Cenários:
              "Contemporary Visual Novel Backgrounds", por Spiral Atlas
              https://spiralatlas.itch.io/contemporary-vn-backgrounds""";
}
