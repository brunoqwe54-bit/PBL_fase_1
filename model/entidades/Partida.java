package model.entidades;

import model.enums.Flag;
import model.enums.Personagens;
import model.enums.Preset;

import java.util.HashSet;
import java.util.Set;

/**
 * O estado de um jogo em andamento: tudo que muda enquanto se joga.
 *
 * A separação vale a pena entender: a classe Historia é o livro, igual
 * para todo mundo; a Partida é o marcador de página e a mochila de um
 * jogador.
 *
 * Ela guarda quatro coisas, e nenhuma outra parte do programa guarda
 * estado que mude: o Protagonista, com os três atributos, o Inventario,
 * com os itens, os sete NPCs, cada um com sua confiança, e o conjunto de
 * flags ligadas, as decisões que o jogo memoriza.
 *
 * Mais a cena atual, que é onde o jogador está.
 *
 * Começar de novo é criar uma Partida nova: o construtor fabrica tudo do
 * zero, então nada da partida anterior sobrevive e não é preciso nenhum
 * método de "resetar".
 */
public class Partida {

    // 1. O JOGADOR E SEUS ITENS
    private Protagonista protagonista;
    private Inventario inventario;

    // 2. ONDE O JOGADOR ESTÁ
    private Cena cenaAtual;

    // 3. DECISÕES ANTERIORES
    // Um conjunto guarda as flags que já foram ligadas. O enum Flag lista
    // as possíveis; este Set guarda as que aconteceram nesta partida.
    private Set<Flag> flags;

    // 4. O ELENCO desta rodada
    private Npc otavio;
    private Npc mae;
    private Npc davi;
    private Npc zulmira;
    private Npc antonio;
    private Npc homemDeTerno;
    private Npc manuela;

    public Partida(String nomeJogador, Preset preset) {
        this.protagonista = new Protagonista(nomeJogador, preset);
        this.inventario = new Inventario();
        this.flags = new HashSet<>();

        // Confianças iniciais diferentes: o irmão confia mais, o menino
        // ainda não te conhece.
        this.otavio       = new Npc(Personagens.OTAVIO.getNomeExibicao(), 50);
        this.mae          = new Npc(Personagens.MAE.getNomeExibicao(), 50);
        this.davi         = new Npc(Personagens.DAVI.getNomeExibicao(), 30);
        this.zulmira      = new Npc(Personagens.ZULMIRA.getNomeExibicao(), 40);
        this.antonio      = new Npc(Personagens.ANTONIO.getNomeExibicao(), 50);
        this.homemDeTerno = new Npc(Personagens.HOMEM_DE_TERNO.getNomeExibicao(), 0);
        this.manuela      = new Npc(Personagens.MANUELA.getNomeExibicao(), 30);

        // Item inicial: o terço da mãe, a única coisa que ele leva de casa.
        this.inventario.adicionar(model.enums.Item.TERCO);
    }

    // ---------- flags ----------

    /**
     * Marca que uma decisão aconteceu nesta partida.
     *
     * Flag não desliga: uma decisão tomada não volta atrás. Ligar a mesma
     * flag duas vezes é o mesmo que ligar uma — é o conjunto que garante isso.
     *
     * @param flag a decisão a memorizar
     */
    public void ligarFlag(Flag flag) {
        flags.add(flag);
    }

    /**
     * Pergunta se uma decisão já aconteceu nesta partida.
     *
     * É o que a classe Escolha consulta para decidir se aparece ou fica
     * bloqueada, muitas vezes capítulos depois de a flag ter sido ligada.
     *
     * @param flag a decisão a consultar
     * @return true se a flag já foi ligada
     */
    public boolean temFlag(Flag flag) {
        return flags.contains(flag);
    }

    // ---------- getters ----------

    public Protagonista getProtagonista() { return protagonista; }
    public Inventario getInventario() { return inventario; }

    public Cena getCenaAtual() { return cenaAtual; }
    public void setCenaAtual(Cena cenaAtual) { this.cenaAtual = cenaAtual; }

    public Npc getOtavio() { return otavio; }
    public Npc getMae() { return mae; }
    public Npc getDavi() { return davi; }
    public Npc getZulmira() { return zulmira; }
    public Npc getAntonio() { return antonio; }
    public Npc getHomemDeTerno() { return homemDeTerno; }
    public Npc getManuela() { return manuela; }
}
