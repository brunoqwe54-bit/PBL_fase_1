package view;

/** Reúne as instruções e os créditos mostrados nas telas. */
public class Textos {

    /** Impede criar objetos desta classe de constantes. */
    private Textos() {
    }

    public static final String INSTRUCOES = """
            COMO SE JOGA

            Você lê, você escolhe, você vive com isso.
            Na janela, clique em Continuar ou pressione ENTER para ler.
            Depois, clique na alternativa desejada.

            Use Status para ver atributos, itens, relacionamentos,
            objetivos e decisões. No Menu da partida você pode salvar,
            carregar, configurar, reiniciar ou voltar ao início.
            Há três slots manuais e um autosave por capítulo.

            O protagonista tem três atributos: FÔLEGO, CORAGEM e LUCIDEZ.
            O FÔLEGO só desce, e a água do cantil é o único jeito de
            recuperar um pouco. Coragem e Lucidez sobem e descem
            conforme o que você faz, e algumas
            escolhas só ficam disponíveis se o atributo for alto o bastante.

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
              https://spiralatlas.itch.io/contemporary-vn-backgrounds

            Personagens:
              "Village Elders Vol. 1 e Vol. 2" e "Faces of HOME",
              por WonderWeave Studio
              https://wonderweave.itch.io/

            Retratos de Vicente, Otávio, Davi, Manuela e Homem de Terno:
              Gerados com apoio de IA (ChatGPT).""";
}
