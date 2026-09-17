package model.factory;

import model.entidades.Cena;
import model.entidades.Dialogo;
import model.entidades.Escolha;
import model.entidades.Npc;
import model.entidades.Partida;
import model.entidades.PersonagemBase;
import model.entidades.Protagonista;
import model.enums.Atributo;
import model.enums.Flag;
import model.enums.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * A HISTORIA COMPLETA -- "A Noite Longa", dez capitulos.
 *
 * Esta classe e uma MONTADORA: ela cria todas as cenas, falas e escolhas e
 * devolve a primeira cena. Ela nao guarda estado de partida nenhum.
 *
 * COMO ELA E ORGANIZADA (montagem em duas passadas):
 *
 *   1a passada -- criarCenas(): cria todas as cenas VAZIAS (id, titulo e
 *      narracao). Nenhuma conhece nenhuma ainda.
 *
 *   2a passada -- montarCap01(), montarCap02()...: agora que todas as cenas
 *      ja existem no mapa, cada capitulo recebe suas falas e suas escolhas, e
 *      pode apontar pra qualquer outra cena -- inclusive pra si mesma.
 *
 * Sem as duas passadas seria preciso escrever a historia de tras pra frente
 * (o capitulo 10 primeiro), porque o construtor de Escolha exige a cena de
 * destino ja criada.
 *
 * Os textos longos ficam em constantes no topo pra criarCenas() ficar legivel.
 */
public class Historia {

    // ================================================================
    // NARRACAO
    // ================================================================

    private static final String T_CAP01 =
            "A chave ainda está na fechadura. Otávio não tirou.\n\n" +
            "Ele está com a mão nela há dois minutos, do jeito de quem espera você\n" +
            "dizer alguma coisa que resolva. Você não tem essa coisa.\n\n" +
            "Do lado de fora, um canto. Distante, sem letra reconhecível -- nunca teve.";

    private static final String T_CAP02 =
            "Vinte casas até a esquina. Você conhece todas, e todas estão escuras do\n" +
            "mesmo jeito premeditado: não a escuridão de quem dorme, mas a de quem\n" +
            "apagou a luz de propósito e está, atrás da cortina, acordado.\n\n" +
            "O cachorro dos Pereira está deitado na calçada, olhando pra rua sem latir.\n\n" +
            "Na metade do quarteirão, uma voz.";

    private static final String T_CAP02B =
            "Na esquina tem um menino. Onze anos, pijama, descalço, com um cachorro\n" +
            "pequeno demais pro colo em que está.\n\n" +
            "É o Davi, filho da Rosângela, do número 40. Ele não está chorando --\n" +
            "está parado do jeito de quem esqueceu como se anda.";

    private static final String T_CAP03 =
            "O canto deixa de ser distante.\n\n" +
            "Você chega na Praça da Matriz e ela já vem pela rua da igreja: uma fila\n" +
            "de gente, dois a dois, cada um com uma vela na mão. Cinquenta, talvez\n" +
            "cem -- a conta se perde onde a rua faz a curva.\n\n" +
            "No terceiro lugar da fila você reconhece Seu Nilton, do armazém, que\n" +
            "morreu no ano passado.\n\n" +
            "Do outro lado da praça, agachada atrás de um banco, uma mulher de uns\n" +
            "sessenta anos põe o dedo na frente da boca. Depois aponta pro chão.";

    private static final String T_CAP03B =
            "A fila leva onze minutos pra passar. Você conta.\n\n" +
            "Quando o último dobra a esquina, a mulher se levanta, bate a poeira do\n" +
            "vestido e vem até você como quem encontra um conhecido na feira.";

    private static final String T_CAP04 =
            "A Rádio Serra é uma casa de dois cômodos com uma antena no telhado, e a\n" +
            "luz vermelha do letreiro NO AR é a única acesa em quatro quarteirões.\n\n" +
            "Um farol teimoso, quase ridículo na insistência, numa cidade que\n" +
            "escolheu o escuro.\n\n" +
            "A porta está encostada.";

    private static final String T_CAP04B =
            "Tem uma coisa batendo na janela dos fundos faz uns vinte minutos.\n\n" +
            "Bate três vezes, para, bate três vezes.\n\n" +
            "Não é o vento, porque não tem vento nenhum nesta noite.";

    private static final String T_CAP05 =
            "O riacho é raso e barulhento. A ponte de concreto é o único lugar da\n" +
            "cidade onde você sente, pela primeira vez desde que saiu de casa, que\n" +
            "não tem ninguém atrás de você.\n\n" +
            "Do outro lado a estrada se divide: a leste, subindo, o cemitério.\n" +
            "A oeste, de volta pro bairro alto. Pra sua casa.";

    private static final String T_CAP06 =
            "O hospital fechou em 2011 e ninguém nunca decidiu o que fazer com o\n" +
            "prédio. As janelas do térreo estão tapadas com tijolo. As de cima, não.\n\n" +
            "E nas de cima tem gente. Dez, quinze figuras, cada uma na sua janela,\n" +
            "todas paradas, todas olhando pro pátio que você precisa atravessar.\n\n" +
            "Nenhuma delas segura vela.";

    private static final String T_CAP06B =
            "Do outro lado do pátio, sentada no muro com a sacola de feira no colo,\n" +
            "Dona Zulmira espera.";

    private static final String T_CAP07 =
            "O casarão dos Peixoto é a única construção entre o hospital e o\n" +
            "cemitério. Dois andares, portão de ferro, mato até a cintura.\n\n" +
            "Na frente tem um homem de terno, com uma vela acesa na mão esquerda e a\n" +
            "mão direita estendida, oferecendo outra vela. Apagada.";

    private static final String T_CAP07B =
            "Nos fundos do casarão tem um alpendre com a porta arrombada.\n\n" +
            "Dentro, escondida com pressa em cima de uma viga, tem uma caixinha de\n" +
            "metal. A viga está alta demais pras suas mãos alcançarem sem apoio.";

    private static final String T_CAP08 =
            "A Rua da Saudade tem duzentos metros e liga a cidade ao portão do\n" +
            "cemitério. Dos dois lados, encostadas no meio-fio, centenas de velas\n" +
            "ardem sem vacilar, embora não tenha vento nenhum.\n\n" +
            "No meio da rua, a Procissão está parada. Não andando: parada, de frente\n" +
            "pro cemitério, cantando baixo, esperando alguma coisa que ainda não\n" +
            "chegou.\n\n" +
            "Você entende, olhando, que não é alguma coisa. É alguém.";

    private static final String T_CAP08B =
            "Do outro lado da rua, sentada no meio-fio entre duas velas como quem\n" +
            "espera ônibus, Dona Zulmira aguarda com a sacola no colo.";

    private static final String T_CAP09 =
            "O cemitério de Riacho do Fogo tem três fileiras de túmulos e um mausoléu\n" +
            "no fundo, da família Peixoto.\n\n" +
            "A Procissão inteira está lá dentro, em círculo, cantando a mesma melodia\n" +
            "desde as onze da noite, cercando alguma coisa que você não consegue ver\n" +
            "do portão.\n\n" +
            "No chão, na entrada, tem um casaco vinho.";

    private static final String T_CAP09B =
            "Três formas de entrar. Só uma delas não envolve trinta cabeças virando\n" +
            "ao mesmo tempo pra receber você.";

    private static final String T_CAP10 =
            "No centro do círculo, Manuela está de pé, sem vela na mão, ao lado de um\n" +
            "homem de terno que segura duas -- uma acesa, uma apagada.\n\n" +
            "Ela ainda não aceitou nenhuma das duas.\n\n" +
            "Cinco horas de espera, e a Procissão tem a noite toda.\n" +
            "Mas a noite está, agora, a quarenta minutos do fim.";

    // ---------------- desfechos ----------------

    private static final String T_FIM_A_TEMPO =
            "FINAL 1 - CHEGA A TEMPO\n\n" +
            "Ela solta a mão do homem de terno.\n\n" +
            "Não tem grito, não tem luta, não tem nada de bonito de contar. Ela só\n" +
            "solta, e dá três passos pra trás, e os três passos custam mais do que a\n" +
            "noite inteira custou até aqui. O homem não vai atrás. Eles nunca vão\n" +
            "atrás: o convite ou é aceito ou não é nada.\n\n" +
            "O céu do lado do posto fica cinza. Depois fica menos cinza.\n\n" +
            "Quando o primeiro galo canta em Riacho do Fogo, a Procissão já se\n" +
            "desfez, e vocês estão sentados no meio-fio da Rua da Saudade sem falar\n" +
            "nada.\n\n" +
            "MANUELA: Otávio deve ta acordado.\n" +
            "VICENTE: Otávio não dormiu.\n\n" +
            "De algum lugar, muito longe, uma voz de setenta anos diz que são cinco e\n" +
            "vinte, que a noite acabou, e que quem estiver ouvindo isso conseguiu.";

    private static final String T_FIM_TARDE =
            "FINAL 2 - CHEGA TARDE\n\n" +
            "O céu clareia atrás do mausoléu.\n\n" +
            "Manuela olha pro lado como quem ouve alguém chamar da cozinha.\n\n" +
            "MANUELA: Ela ta chamando.\n" +
            "VICENTE: Não ta.\n" +
            "MANUELA: Ta sim.\n\n" +
            "E ela pega a vela. Não tem nada dramático: ela pega do jeito que se pega\n" +
            "um copo que alguém passa na mesa. A chama não aumenta. Ela não muda de\n" +
            "cara.\n\n" +
            "Ela só entra na fila e começa a cantar baixo, e canta bem, porque ela\n" +
            "sempre cantou bem.\n\n" +
            "Quando o sol nasce, você fica sozinho num cemitério vazio.\n\n" +
            "No ano que vem tem outra Noite Longa. E você vai estar na rua.";

    private static final String T_FIM_DESISTE =
            "FINAL 3 - VOLTA PRA CASA\n\n" +
            "Você vira as costas.\n\n" +
            "Isso é mais difícil do que qualquer coisa que você fez essa noite, e\n" +
            "leva mais tempo, e não tem nenhum momento em que fica mais fácil.\n\n" +
            "A porta está destrancada, como ele prometeu.\n\n" +
            "Otávio está sentado na mesa da cozinha com a luz apagada. Ele olha pra\n" +
            "porta. Depois olha pra trás de você, procurando uma segunda pessoa que\n" +
            "não vem.\n\n" +
            "Ele não fala nada. Nem naquela noite, nem no dia seguinte, nem nunca.\n\n" +
            "Em junho, na véspera de São João, vocês dois fecham a janela cedo. E\n" +
            "toda vez que a Procissão passa, os dois ficam parados no meio da sala,\n" +
            "tentando escutar se tem uma voz de dezessete anos naquele canto.\n\n" +
            "Tem. Sempre tem.";

    // ---------------- mortes ----------------

    private static final String T_MORTE_NOME =
            "GAME OVER\n\n" +
            "Você vira.\n\n" +
            "Não tem nada. Tem a rua, tem o poste queimado, tem a casa dos Pereira\n" +
            "com a janela fechada.\n\n" +
            "E tem, de pé no meio da rua onde não tinha ninguém, uma pessoa segurando\n" +
            "uma vela. Ela sorri do jeito que sua mãe sorria.\n\n" +
            "Você da um passo na direção dela porque parece a coisa certa a fazer, e\n" +
            "é a última coisa que você faz.";

    private static final String T_MORTE_FILA =
            "GAME OVER\n\n" +
            "Você entra na fila achando que da pra passar entre duas pessoas.\n" +
            "Da. Você passa.\n\n" +
            "Do outro lado, você continua andando. Bem devagar. Cantando baixo -- e a\n" +
            "letra agora você entende perfeitamente, e ela é simples, e você já\n" +
            "sabia.\n\n" +
            "Alguém põe uma vela acesa na sua mão. Você agradece.";

    private static final String T_MORTE_JANELA =
            "GAME OVER\n\n" +
            "A janela é de vidro canelado, daquelas que deixam ver vulto e não deixam\n" +
            "ver cara.\n\n" +
            "Tem um vulto. Da altura de uma pessoa de dezenove anos.\n\n" +
            "Você entende, tarde, que essa janela não é pra você -- e abre, porque\n" +
            "abrir é o que a mão faz.";

    private static final String T_MORTE_PATIO =
            "GAME OVER\n\n" +
            "Um. Dois. Três. Quatro.\n\n" +
            "Na quinta janela você percebe que eles não estão mais olhando pro pátio.\n" +
            "Estão olhando pra você.\n\n" +
            "Dezesseis. Dezessete.\n\n" +
            "Você percebe que parou de contar as janelas há um tempo e começou a\n" +
            "contar outra coisa -- e essa outra coisa está contando também, e chega\n" +
            "em você primeiro.";

    private static final String T_MORTE_CASARAO =
            "GAME OVER\n\n" +
            "A sala é grande e tem gente sentada em todas as cadeiras. Ninguém come.\n" +
            "Tem prato, tem talher, tem toalha de renda, mas ninguém come.\n\n" +
            "Eles abrem um lugar pra você na ponta da mesa. A cadeira já está puxada,\n" +
            "e tem um prato ali com o seu nome escrito na borda, escrito há muito\n" +
            "tempo, com a letra da sua mãe.\n\n" +
            "Você senta, porque seria falta de educação não sentar.";

    private static final String T_MORTE_VELA =
            "GAME OVER\n\n" +
            "Você acende. A chama é pequena, amarela e comum, e por um segundo você\n" +
            "se sente ridículo.\n\n" +
            "Depois o canto para. Duzentas pessoas viram a cabeça ao mesmo tempo, e\n" +
            "nenhuma delas está surpresa.\n\n" +
            "Alguém abre um espaço na fila. Você entra, porque você foi convidado --\n" +
            "e você aceitou o convite lá atrás, no portão do casarão, quando achou\n" +
            "que estava só sendo educado.";

    private static final String T_MORTE_CHAMOU =
            "GAME OVER\n\n" +
            "-- MANUELA!\n\n" +
            "O canto para. E então, muito devagar, todos eles respondem.\n\n" +
            "Duzentas vozes dizem o nome da sua irmã ao mesmo tempo, e a última voz a\n" +
            "dizer é uma que você conhece, e vem do meio do círculo.\n\n" +
            "Ela diz o próprio nome como quem responde a chamada.\n" +
            "E depois ela diz o seu.";

    private static final String T_MORTE_CIRCULO =
            "GAME OVER\n\n" +
            "Você entra. Alguém põe uma vela na sua mão e você agradece, porque foi\n" +
            "gentileza.\n\n" +
            "Manuela olha pra você e sorri, e é o sorriso mais aliviado que você já\n" +
            "viu na cara dela, e é agora que você entende.\n\n" +
            "O chamado era de verdade. Ela ouviu mesmo, e ela ia de qualquer jeito.\n\n" +
            "Ela só não queria ir sozinha.\n\n" +
            "O canto recomeça. Você sabe a letra.";

    // ================================================================
    // O MAPA DAS CENAS
    // ================================================================

    private Map<String, Cena> cenas = new HashMap<>();

    /**
     * Monta a historia inteira e devolve a primeira cena.
     *
     * Recebe a Partida porque as escolhas precisam apontar pros objetos Npc
     * daquela partida especifica -- sao eles que tem a confianca.
     */
    public Cena montarHistoria(Partida partida) {
        cenas.clear();

        criarCenas();                 // 1a passada

        montarCap01(partida);         // 2a passada
        montarCap02(partida);
        montarCap03(partida);
        montarCap04(partida);
        montarCap05(partida);
        montarCap06(partida);
        montarCap07(partida);
        montarCap08(partida);
        montarCap09(partida);
        montarCap10(partida);

        return getCena("CAP01");
    }

    // ================================================================
    // 1a PASSADA -- todas as cenas nascem vazias
    // ================================================================

    private void criarCenas() {
        criar("CAP01",  "Capítulo 1 - A Porta",               T_CAP01);
        criar("CAP02",  "Capítulo 2 - A Rua de Casa",         T_CAP02);
        criar("CAP02B", "Capítulo 2 - A Esquina",             T_CAP02B);
        criar("CAP03",  "Capítulo 3 - A Praça",               T_CAP03);
        criar("CAP03B", "Capítulo 3 - Dona Zulmira",          T_CAP03B);
        criar("CAP04",  "Capítulo 4 - A Rádio",               T_CAP04);
        criar("CAP04B", "Capítulo 4 - A Janela dos Fundos",   T_CAP04B);
        criar("CAP05",  "Capítulo 5 - A Ponte do Riacho",     T_CAP05);
        criar("CAP06",  "Capítulo 6 - O Hospital Velho",      T_CAP06);
        criar("CAP06B", "Capítulo 6 - O Muro",                T_CAP06B);
        criar("CAP07",  "Capítulo 7 - O Casarão dos Peixoto", T_CAP07);
        criar("CAP07B", "Capítulo 7 - O Alpendre",            T_CAP07B);
        criar("CAP08",  "Capítulo 8 - A Rua das Velas",       T_CAP08);
        criar("CAP08B", "Capítulo 8 - O Meio-Fio",            T_CAP08B);
        criar("CAP09",  "Capítulo 9 - O Cemitério",           T_CAP09);
        criar("CAP09B", "Capítulo 9 - As Três Entradas",      T_CAP09B);
        criar("CAP10",  "Capítulo 10 - O Amanhecer",          T_CAP10);

        // Desfechos e mortes: cenas SEM escolhas. O laco do controller ja
        // encerra o jogo quando a cena nao tem opcoes.
        criar("FIM_A_TEMPO",   "FIM",       T_FIM_A_TEMPO);
        criar("FIM_TARDE",     "FIM",       T_FIM_TARDE);
        criar("FIM_DESISTE",   "FIM",       T_FIM_DESISTE);
        criar("MORTE_NOME",    "GAME OVER", T_MORTE_NOME);
        criar("MORTE_FILA",    "GAME OVER", T_MORTE_FILA);
        criar("MORTE_JANELA",  "GAME OVER", T_MORTE_JANELA);
        criar("MORTE_PÁTIO",   "GAME OVER", T_MORTE_PATIO);
        criar("MORTE_CASARÃO", "GAME OVER", T_MORTE_CASARAO);
        criar("MORTE_VELA",    "GAME OVER", T_MORTE_VELA);
        criar("MORTE_CHAMOU",  "GAME OVER", T_MORTE_CHAMOU);
        criar("MORTE_CÍRCULO", "GAME OVER", T_MORTE_CIRCULO);
    }

    // ================================================================
    // 2a PASSADA -- um metodo por capitulo
    // ================================================================

    /**
     * CAPITULO 1 -- A Porta.
     * Aqui nasce a decisao mais importante do jogo, e o jogador nao tem como
     * saber disso: pedir a porta destrancada e o que permite desistir depois,
     * nos capitulos 5 e 8, e e uma das falas do capitulo 10.
     */
    private void montarCap01(Partida partida) {
        Cena cena = getCena("CAP01");
        Protagonista vicente = partida.getProtagonista();
        Npc otavio = partida.getOtavio();

        falar(cena, otavio,  "Não olha pra mim assim.");
        falar(cena, otavio,  "Ela sabia. Sabe desde os seis anos, igual a gente. Ninguém sai na Noite Longa.");
        falar(cena, vicente, "Ela tem dezessete.");
        falar(cena, otavio,  "E você tem vinte e dois e tá com a mão na maçaneta.");
        falar(cena, otavio,  "Todo ano ela pede pra ficar acordada nessa noite. Todo ano eu mando ela dormir.");
        falar(cena, otavio,  "Hoje ela não pediu. E ela não saiu correndo, não. Saiu andando.");
        falar(cena, otavio,  "Se você sair, eu tranco. Não é ameaça, é o que se faz. A mãe fazia.");
        falar(cena, otavio,  "E se você bater, eu não abro. Você sabe que eu não abro.");

        cena.getOpcoes().add(
                new Escolha("ESC0101", "Sair. Não discutir.", getCena("CAP02"))
                        .comConfianca(otavio, -10)
                        .comAtributo(Atributo.CORAGEM, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0102", "Pedir que ele deixe a porta destrancada.", getCena("CAP02"))
                        .ligaFlag(Flag.PORTA_ABERTA)
                        .comConfianca(otavio, 10)
                        .comAtributo(Atributo.LUCIDEZ, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0103", "Chamar ele pra ir junto.", getCena("CAP02"))
                        .exigeAtributo(Atributo.CORAGEM, 55, "você não tem coragem de pedir")
                        .comConfianca(otavio, -20)
                        .comAtributo(Atributo.CORAGEM, -5));
    }

    /**
     * CAPITULO 2 -- A voz da mae (primeira regra) e o encontro com Davi.
     * Dar o terco troca protecao propria por confianca -- e essa confianca e
     * o que abre a medalha la no capitulo 7.
     */
    private void montarCap02(Partida partida) {
        Cena cena = getCena("CAP02");
        Npc mae = partida.getMae();
        Npc davi = partida.getDavi();

        // O nome vem do que o jogador digitou no menu.
        String nome = partida.getProtagonista().getNome();

        falar(cena, mae, nome + ".");
        falar(cena, mae, nome + ". Olha pra mim, meu filho.");

        cena.getOpcoes().add(
                new Escolha("ESC0201", "Olhar.", getCena("MORTE_NOME")));

        cena.getOpcoes().add(
                new Escolha("ESC0202", "Não olhar. Continuar andando.", getCena("CAP02B"))
                        .comAtributo(Atributo.LUCIDEZ, 10));

        cena.getOpcoes().add(
                new Escolha("ESC0203", "Responder sem virar: a senhora não ta aí.", getCena("CAP02B"))
                        .exigeAtributo(Atributo.CORAGEM, 55, "sua voz não sai")
                        .comAtributo(Atributo.CORAGEM, 10)
                        .comAtributo(Atributo.LUCIDEZ, -5));

        // ---- a esquina, com o Davi ----
        Cena esquina = getCena("CAP02B");

        falar(esquina, davi, "O Tico fugiu. Eu só vim pegar o Tico.");
        falar(esquina, davi, "Aí eu virei e a minha casa ficou longe.");

        esquina.getOpcoes().add(
                new Escolha("ESC0204", "Dar o terço da sua mãe pra ele.", getCena("CAP03"))
                        .exigeItem(Item.TERCO, "você não tem mais o terço")
                        .perdeItem(Item.TERCO)
                        .ligaFlag(Flag.SALVOU_DAVI)
                        .comConfianca(davi, 40)
                        .comAtributo(Atributo.CORAGEM, 5));

        esquina.getOpcoes().add(
                new Escolha("ESC0205", "Levar ele até a casa da Rosângela.", getCena("CAP03"))
                        .exigeAtributo(Atributo.FOLEGO, 40, "você não tem fôlego pra isso")
                        .ligaFlag(Flag.SALVOU_DAVI)
                        .comConfianca(davi, 20)
                        .comAtributo(Atributo.FOLEGO, -15));

        esquina.getOpcoes().add(
                new Escolha("ESC0206", "Mandar ele correr pra casa sozinho.", getCena("CAP03"))
                        .ligaFlag(Flag.ABANDONOU_DAVI)
                        .comConfianca(davi, -20)
                        .comAtributo(Atributo.CORAGEM, -10));
    }

    /**
     * CAPITULO 3 -- A Procissao passa (segunda regra) e Zulmira ensina o resto.
     * Gritar o nome nao mata agora: derruba a lucidez e a confianca da
     * Zulmira, e fecha as melhores escolhas do capitulo 4.
     */
    private void montarCap03(Partida partida) {
        Cena cena = getCena("CAP03");
        Npc zulmira = partida.getZulmira();
        Npc manuela = partida.getManuela();
        Protagonista vicente = partida.getProtagonista();

        cena.getOpcoes().add(
                new Escolha("ESC0301", "Ajoelhar e baixar os olhos.", getCena("CAP03B"))
                        .ligaFlag(Flag.AJOELHOU)
                        .comAtributo(Atributo.LUCIDEZ, 10)
                        .comConfianca(zulmira, 20));

        cena.getOpcoes().add(
                new Escolha("ESC0302", "Correr pela lateral da praça.", getCena("CAP03B"))
                        .exigeAtributo(Atributo.FOLEGO, 55, "você não aguenta correr agora")
                        .comAtributo(Atributo.FOLEGO, -15)
                        .comConfianca(zulmira, -10));

        cena.getOpcoes().add(
                new Escolha("ESC0303", "Gritar o nome da Manuela.", getCena("CAP03B"))
                        .ligaFlag(Flag.GRITOU_NOME)
                        .comAtributo(Atributo.LUCIDEZ, -15)
                        .comConfianca(zulmira, -25));

        cena.getOpcoes().add(
                new Escolha("ESC0304", "Atravessar a fila.", getCena("MORTE_FILA")));

        // ---- a conversa com Zulmira ----
        Cena conversa = getCena("CAP03B");

        falar(conversa, zulmira, "Zulmira. Você é o do meio dos Nogueira. O da oficina.");
        falar(conversa, vicente, "A senhora ta na rua.");
        falar(conversa, zulmira, "Todo ano, meu filho. Faz trinta e um.");
        falar(conversa, vicente, "A senhora não tem medo deles?");
        falar(conversa, zulmira, "Eles não pegam ninguém. Nunca pegaram. Eles convidam.");
        falar(conversa, zulmira, "E convidam com a voz de quem faz falta. É por isso que funciona.");
        falar(conversa, zulmira, "Ninguém diz sim de primeira. Ninguém quer ir sozinho.");

        conversa.getOpcoes().add(
                new Escolha("ESC0305", "Perguntar as regras.", getCena("CAP04"))
                        .exigeConfianca(zulmira, 55, "ela ainda não confia em você")
                        .comAtributo(Atributo.LUCIDEZ, 15)
                        .comConfianca(zulmira, 10));

        conversa.getOpcoes().add(
                new Escolha("ESC0306", "Perguntar baixo se ela viu a Manuela.", getCena("CAP04"))
                        .exigeAtributo(Atributo.LUCIDEZ, 50, "do jeito que você está, ia acabar gritando")
                        .comConfianca(zulmira, 10)
                        .comConfianca(manuela, 5));

        conversa.getOpcoes().add(
                new Escolha("ESC0307", "Seguir sozinho.", getCena("CAP04"))
                        .comConfianca(zulmira, -10));
    }

    /**
     * CAPITULO 4 -- Seu Antonio.
     * Quem nao ajoelhou na praca chega agitado demais pra sentar e ouvir, e
     * sem ouvir a transmissao nao da pra avisar Antonio -- e sem o aviso nao
     * tem radio, que faz falta nos capitulos 5 e 8.
     */
    private void montarCap04(Partida partida) {
        Cena cena = getCena("CAP04");
        Npc antonio = partida.getAntonio();

        falar(cena, antonio, "Fecha a porta. Fecha e senta.");
        falar(cena, antonio, "Nogueira. Sua mãe trabalhou aqui. Dois anos. Lia o boletim das seis.");
        falar(cena, antonio, "Você tem a voz dela.");
        falar(cena, antonio, "Escuta uma coisa antes de sair daqui.");
        falar(cena, antonio, "Se te oferecerem qualquer coisa hoje -- vela, carona, lugar na fila -- não aceita.");
        falar(cena, antonio, "Aceitar é responder. E responder é dizer sim, mesmo que você ache que tá só sendo educado.");

        cena.getOpcoes().add(
                new Escolha("ESC0401", "Ouvir a transmissão inteira.", getCena("CAP04B"))
                        .exigeAtributo(Atributo.LUCIDEZ, 55, "você está agitado demais pra sentar e ouvir")
                        .ligaFlag(Flag.OUVIU_RADIO)
                        .comAtributo(Atributo.LUCIDEZ, 15)
                        .comConfianca(antonio, 15));

        cena.getOpcoes().add(
                new Escolha("ESC0402", "Perguntar por que ele fica.", getCena("CAP04B"))
                        .exigeConfianca(antonio, 55, "ele não vai te contar isso ainda")
                        .comConfianca(antonio, 20)
                        .comAtributo(Atributo.CORAGEM, 10));

        cena.getOpcoes().add(
                new Escolha("ESC0403", "Sair rápido, sem conversa.", getCena("CAP04B"))
                        .comConfianca(antonio, -15));

        // ---- a janela dos fundos ----
        Cena janela = getCena("CAP04B");

        falar(janela, antonio, "Em 1994 eu tinha um filho de dezenove anos.");
        falar(janela, antonio, "Ele saiu numa Noite Longa e não teve ninguém dizendo pra ele não olhar pra trás.");
        falar(janela, antonio, "Toda Noite Longa ele bate ali atrás. Três vezes, para, três vezes.");
        falar(janela, antonio, "Eu não abro.");

        janela.getOpcoes().add(
                new Escolha("ESC0404", "Ir olhar a janela dos fundos.", getCena("MORTE_JANELA")));

        janela.getOpcoes().add(
                new Escolha("ESC0405", "Ignorar e sair pela frente.", getCena("CAP05"))
                        .comAtributo(Atributo.LUCIDEZ, 5));

        janela.getOpcoes().add(
                new Escolha("ESC0406", "Avisar Seu Antônio pra não abrir de jeito nenhum.", getCena("CAP05"))
                        .exigeFlag(Flag.OUVIU_RADIO, "você não sabe o que tem naquele corredor")
                        .ligaFlag(Flag.AVISOU_ANTONIO)
                        .ganhaItem(Item.RADIO)
                        .comConfianca(antonio, 30)
                        .comAtributo(Atributo.LUCIDEZ, 10));
    }

    /**
     * CAPITULO 5 -- A ponte.
     * Primeiro ponto de desistencia (so pra quem pediu a porta no capitulo 1)
     * e a decisao sobre o Davi.
     */
    private void montarCap05(Partida partida) {
        Cena cena = getCena("CAP05");
        Npc davi = partida.getDavi();
        Npc antonio = partida.getAntonio();

        falar(cena, davi, "A minha mãe não abriu. Eu bati bastante.");
        falar(cena, antonio, "Pra quem estiver perto de água corrente: fiquem. E o único lugar que eles não pisam.");

        cena.getOpcoes().add(
                new Escolha("ESC0501", "Levar Davi junto.", getCena("CAP06"))
                        .exigeFlag(Flag.SALVOU_DAVI, "não tem ninguém com você")
                        .ligaFlag(Flag.DAVI_JUNTO)
                        .comConfianca(davi, 20)
                        .comAtributo(Atributo.FOLEGO, -10));

        // A escolha CERTA segundo a regra da agua corrente -- e a que mais
        // parece abandono. O jogo nao avisa qual e qual.
        cena.getOpcoes().add(
                new Escolha("ESC0502", "Deixar Davi na ponte, perto da água.", getCena("CAP06"))
                        .exigeFlag(Flag.SALVOU_DAVI, "não tem ninguém com você")
                        .ligaFlag(Flag.DAVI_NA_PONTE)
                        .comConfianca(davi, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0503", "Encher o cantil com água do riacho.", getCena("CAP06"))
                        .ganhaItem(Item.CANTIL)
                        .comAtributo(Atributo.FOLEGO, -5)
                        .comAtributo(Atributo.LUCIDEZ, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0504", "Subir a estrada do cemitério.", getCena("CAP06"))
                        .comAtributo(Atributo.CORAGEM, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0505", "Pegar a estrada da esquerda. Voltar pra casa.", getCena("FIM_DESISTE"))
                        .exigeFlag(Flag.PORTA_ABERTA, "a porta está trancada"));
    }

    /**
     * CAPITULO 6 -- O patio do hospital.
     * A escolha que mata aqui e bloqueada por lucidez BAIXA: quem esta lucido
     * demais acha que consegue contar as figuras, e e justamente quem morre.
     */
    private void montarCap06(Partida partida) {
        Cena cena = getCena("CAP06");
        Npc zulmira = partida.getZulmira();
        Npc manuela = partida.getManuela();
        Protagonista vicente = partida.getProtagonista();

        cena.getOpcoes().add(
                new Escolha("ESC0601", "Atravessar o pátio reto, olhando pro chão.", getCena("CAP06B"))
                        .comAtributo(Atributo.CORAGEM, -5)
                        .comAtributo(Atributo.LUCIDEZ, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0602", "Levantar os olhos e procurar a cara dela nas janelas.", getCena("CAP06B"))
                        .exigeAtributo(Atributo.CORAGEM, 55, "você não consegue levantar a cabeça")
                        .comAtributo(Atributo.CORAGEM, -20)
                        .comAtributo(Atributo.LUCIDEZ, 10)
                        .comConfianca(manuela, 10));

        cena.getOpcoes().add(
                new Escolha("ESC0603", "Parar no meio do pátio e contar quantos são.", getCena("MORTE_PÁTIO"))
                        .exigeAtributo(Atributo.LUCIDEZ, 70, "você nem pensaria nisso"));

        // ---- o muro, com Zulmira ----
        Cena muro = getCena("CAP06B");

        falar(muro, zulmira, "Demorou.");
        falar(muro, vicente, "A senhora ta me esperando?");
        falar(muro, zulmira, "Eu to indo pro mesmo lugar que você, menino. Faz trinta e um ano.");

        muro.getOpcoes().add(
                new Escolha("ESC0604", "Perguntar o que ela vai fazer no cemitério.", getCena("CAP07"))
                        .exigeConfianca(zulmira, 60, "ela não vai te contar isso")
                        .comConfianca(zulmira, 20)
                        .comAtributo(Atributo.CORAGEM, 10));

        muro.getOpcoes().add(
                new Escolha("ESC0605", "Seguir sem falar com ela.", getCena("CAP07"))
                        .comConfianca(zulmira, -15));
    }

    /**
     * CAPITULO 7 -- A vela (terceira regra) e a medalha.
     * Aceitar a vela NAO mata aqui. Ela fica no bolso e cobra no capitulo 8.
     */
    private void montarCap07(Partida partida) {
        Cena cena = getCena("CAP07");
        Npc homem = partida.getHomemDeTerno();
        Npc davi = partida.getDavi();
        Protagonista vicente = partida.getProtagonista();

        falar(cena, homem,   "Boa noite. A casa está aberta. Faz frio.");
        falar(cena, homem,   "A gente estava esperando.");
        falar(cena, vicente, "A gente quem?");
        falar(cena, homem,   "A gente.");

        cena.getOpcoes().add(
                new Escolha("ESC0701", "Aceitar a vela apagada.", getCena("CAP07B"))
                        .ganhaItem(Item.VELA)
                        .comAtributo(Atributo.LUCIDEZ, -10));

        cena.getOpcoes().add(
                new Escolha("ESC0702", "Recusar e passar direto pelo portão.", getCena("CAP07B"))
                        .exigeAtributo(Atributo.LUCIDEZ, 45, "você está confuso demais pra recusar")
                        .comAtributo(Atributo.LUCIDEZ, 5)
                        .comAtributo(Atributo.CORAGEM, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0703", "Recusar e mandar ele sair da sua frente.", getCena("CAP07B"))
                        .exigeAtributo(Atributo.CORAGEM, 60, "você não tem coragem de enfrentar ele")
                        .comAtributo(Atributo.CORAGEM, 15));

        cena.getOpcoes().add(
                new Escolha("ESC0704", "Entrar na casa com ele.", getCena("MORTE_CASARÃO")));

        // ---- o alpendre e a medalha ----
        Cena alpendre = getCena("CAP07B");

        alpendre.getOpcoes().add(
                new Escolha("ESC0705", "Subir no caixote e pegar a caixinha.", getCena("CAP08"))
                        .exigeAtributo(Atributo.FOLEGO, 55, "você não alcança sozinho")
                        .ganhaItem(Item.MEDALHA)
                        .ligaFlag(Flag.PEGOU_MEDALHA)
                        .comAtributo(Atributo.FOLEGO, -10));

        // Unico caminho pra medalha com folego baixo -- e so existe pra quem
        // tratou bem o menino cinco capitulos atras.
        alpendre.getOpcoes().add(
                new Escolha("ESC0706", "Pedir pro Davi subir nos seus ombros.", getCena("CAP08"))
                        .exigeFlag(Flag.DAVI_JUNTO, "Davi não está com você")
                        .exigeConfianca(davi, 60, "ele não confia em você o bastante")
                        .ganhaItem(Item.MEDALHA)
                        .ligaFlag(Flag.PEGOU_MEDALHA)
                        .comConfianca(davi, 15));

        alpendre.getOpcoes().add(
                new Escolha("ESC0707", "Deixar. Não tem tempo.", getCena("CAP08"))
                        .comAtributo(Atributo.CORAGEM, 5));
    }

    /**
     * CAPITULO 8 -- A armadilha da vela fecha aqui.
     * Segundo ponto de desistencia, e onde Zulmira entrega a chave.
     */
    private void montarCap08(Partida partida) {
        Cena cena = getCena("CAP08");
        Npc antonio = partida.getAntonio();
        Npc zulmira = partida.getZulmira();
        Protagonista vicente = partida.getProtagonista();

        falar(cena, antonio, "São três e vinte. Falta uma hora e quarenta.");
        falar(cena, antonio, "Se você ta com uma vela no bolso que você não acendeu, joga fora. Joga longe.");
        falar(cena, antonio, "Meu filho guardou a dele no bolso do paletó. Eu achei depois, e ela ainda tava quente.");

        cena.getOpcoes().add(
                new Escolha("ESC0801", "Acender a vela.", getCena("MORTE_VELA"))
                        .exigeItem(Item.VELA, "você não tem vela nenhuma"));

        cena.getOpcoes().add(
                new Escolha("ESC0802", "Jogar a vela fora, longe.", getCena("CAP08B"))
                        .exigeItem(Item.VELA, "você não tem vela nenhuma")
                        .perdeItem(Item.VELA)
                        .comAtributo(Atributo.LUCIDEZ, 15)
                        .comAtributo(Atributo.CORAGEM, 10));

        cena.getOpcoes().add(
                new Escolha("ESC0803", "Andar rente ao muro, sem pisar entre as velas.", getCena("CAP08B"))
                        .comAtributo(Atributo.LUCIDEZ, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0808", "Parar e beber a água do cantil.", getCena("CAP08B"))
                        .exigeItem(Item.CANTIL, "você não encheu o cantil no riacho")
                        .perdeItem(Item.CANTIL)
                        .comAtributo(Atributo.FOLEGO, 20)
                        .comAtributo(Atributo.LUCIDEZ, 5));

        cena.getOpcoes().add(
                new Escolha("ESC0804", "Voltar. Ir embora. Não vale.", getCena("FIM_DESISTE"))
                        .exigeFlag(Flag.PORTA_ABERTA, "a porta está trancada"));

        // ---- o meio-fio, com Zulmira ----
        Cena meioFio = getCena("CAP08B");

        falar(meioFio, zulmira, "Senta um pouco.");
        falar(meioFio, vicente, "A senhora ta sentada no meio delas.");
        falar(meioFio, zulmira, "Elas não são o problema, menino. Elas são aviso. O problema é quem acende.");

        meioFio.getOpcoes().add(
                new Escolha("ESC0805", "Perguntar como entrar no cemitério.", getCena("CAP09"))
                        .exigeConfianca(zulmira, 65, "ela não te entrega a chave")
                        .ganhaItem(Item.CHAVE)
                        .comConfianca(zulmira, 10));

        meioFio.getOpcoes().add(
                new Escolha("ESC0806", "Prometer que volta pra buscar ela.", getCena("CAP09"))
                        .exigeConfianca(zulmira, 70, "ela não ia acreditar")
                        .ligaFlag(Flag.PROMETEU_VOLTAR)
                        .comConfianca(zulmira, 15)
                        .comAtributo(Atributo.CORAGEM, 10));

        meioFio.getOpcoes().add(
                new Escolha("ESC0807", "Ir sem falar nada.", getCena("CAP09"))
                        .comConfianca(zulmira, -10));
    }

    /**
     * CAPITULO 9 -- O casaco e as tres entradas.
     * Este capitulo cobra quase tudo que veio antes: a chave, a medalha, o
     * folego. A escolha ESC0907 existe pra ninguem ficar preso sem saida.
     */
    private void montarCap09(Partida partida) {
        Cena cena = getCena("CAP09");
        Npc manuela = partida.getManuela();

        cena.getOpcoes().add(
                new Escolha("ESC0901", "Pegar o casaco.", getCena("CAP09B"))
                        .ganhaItem(Item.CASACO)
                        .comAtributo(Atributo.CORAGEM, 10)
                        .comConfianca(manuela, 15));

        cena.getOpcoes().add(
                new Escolha("ESC0902", "Deixar. Deve ser armadilha.", getCena("CAP09B"))
                        .exigeAtributo(Atributo.LUCIDEZ, 70, "você não consegue desconfiar disso agora")
                        .comAtributo(Atributo.LUCIDEZ, 5));

        // ---- as tres entradas ----
        Cena entradas = getCena("CAP09B");

        entradas.getOpcoes().add(
                new Escolha("ESC0903", "Entrar pela porta lateral da capela.", getCena("CAP10"))
                        .exigeItem(Item.CHAVE, "você não tem a chave")
                        .comAtributo(Atributo.LUCIDEZ, 10));

        entradas.getOpcoes().add(
                new Escolha("ESC0904", "Entrar pelo portão da frente.", getCena("CAP10"))
                        .exigeItem(Item.MEDALHA, "eles reconheceriam você na hora")
                        .comAtributo(Atributo.CORAGEM, -10));

        entradas.getOpcoes().add(
                new Escolha("ESC0905", "Pular o muro dos fundos.", getCena("CAP10"))
                        .exigeAtributo(Atributo.FOLEGO, 65, "você não tem fôlego pra escalar")
                        .comAtributo(Atributo.FOLEGO, -25)
                        .comAtributo(Atributo.CORAGEM, -5));

        // SAIDA DE SEGURANCA: sem ela, um jogador sem chave, sem medalha e com
        // folego baixo so teria a escolha que mata. Toda cena precisa de pelo
        // menos uma saida que nao seja a morte.
        entradas.getOpcoes().add(
                new Escolha("ESC0907", "Ficar no portão e esperar o sol nascer.", getCena("FIM_TARDE"))
                        .comAtributo(Atributo.CORAGEM, -15));

        entradas.getOpcoes().add(
                new Escolha("ESC0906", "Chamar o nome dela.", getCena("MORTE_CHAMOU")));
    }

    /**
     * CAPITULO 10 -- O amanhecer.
     * As tres primeiras escolhas sao FALAS: apontam pra propria cena e
     * aumentam a confianca da Manuela. So com a confianca em 75 a escolha do
     * final bom aparece -- e chegar la depende do casaco (capitulo 9), da
     * lucidez acumulada e do pedido feito no capitulo 1.
     */
    private void montarCap10(Partida partida) {
        Cena cena = getCena("CAP10");
        Npc manuela = partida.getManuela();
        Protagonista vicente = partida.getProtagonista();

        falar(cena, manuela, "Eu sabia que era você. Eu falei pra eles que ia ser você.");
        falar(cena, manuela, "Eu não peguei a vela ainda. Eu tava esperando você chegar.");
        falar(cena, vicente, "Por que você saiu?");
        falar(cena, manuela, "Porque a mãe ta aqui.");
        falar(cena, manuela, "Todo ano ela passa na nossa rua e todo ano vocês dois fecham a janela.");
        falar(cena, manuela, "Eu só queria ver. Aí eu vi.");

        cena.getOpcoes().add(
                new Escolha("ESC1001", "Estender o casaco pra ela.", cena)
                        .exigeItem(Item.CASACO, "você não pegou o casaco")
                        .perdeItem(Item.CASACO)
                        .comConfianca(manuela, 20));

        cena.getOpcoes().add(
                new Escolha("ESC1002", "Dizer que a mãe não está ali.", cena)
                        .exigeAtributo(Atributo.LUCIDEZ, 55, "você mesmo não tem certeza disso")
                        .proibeFlag(Flag.FALOU_DA_VOZ, "você já disse isso")
                        .ligaFlag(Flag.FALOU_DA_VOZ)
                        .comConfianca(manuela, 25)
                        .comAtributo(Atributo.LUCIDEZ, 5));

        cena.getOpcoes().add(
                new Escolha("ESC1003", "Contar que Otávio deixou a porta destrancada.", cena)
                        .exigeFlag(Flag.PORTA_ABERTA, "a porta está trancada, e ela sabe")
                        .proibeFlag(Flag.FALOU_DA_PORTA, "você já disse isso")
                        .ligaFlag(Flag.FALOU_DA_PORTA)
                        .comConfianca(manuela, 25));

        cena.getOpcoes().add(
                new Escolha("ESC1004", "Segurar a mão dela e não soltar.", getCena("FIM_A_TEMPO"))
                        .exigeConfianca(manuela, 75, "ela ainda não está pronta pra ir com você"));

        cena.getOpcoes().add(
                new Escolha("ESC1005", "Insistir que ela venha agora.", getCena("FIM_TARDE")));

        cena.getOpcoes().add(
                new Escolha("ESC1006", "Entrar no círculo com ela.", getCena("MORTE_CÍRCULO")));
    }

    // ================================================================
    // AUXILIARES
    // ================================================================

    private void criar(String id, String titulo, String texto) {
        cenas.put(id, new Cena(id, titulo, texto));
    }

    private void falar(Cena cena, PersonagemBase quem, String texto) {
        cena.getDialogos().add(new Dialogo(quem, texto));
    }

    /**
     * Busca a cena pelo id. Se o id nao existir, quebra AQUI com uma mensagem
     * clara, em vez de dar NullPointerException la na frente.
     */
    public Cena getCena(String id) {
        Cena cena = cenas.get(id);
        if (cena == null) {
            throw new IllegalArgumentException("Cena inexistente: " + id);
        }
        return cena;
    }

    /** Quantidade de cenas montadas -- util pra provar os dez capitulos. */
    public int totalDeCenas() {
        return cenas.size();
    }
}
