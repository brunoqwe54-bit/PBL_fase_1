package model.entidades;

import model.enums.Flag;
import model.enums.Personagens;
import model.enums.Preset;
import model.factory.FabricaDeNpc;
import model.factory.FabricaDeProtagonista;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * O estado de um jogo em andamento: tudo que muda enquanto se joga.
 *
 * Ela guarda quatro coisas, e nenhuma outra parte do programa guarda:
 * o Protagonista, com os três atributos, o Inventario,
 * com os itens, os sete NPCs, cada um com sua confiança, e o conjunto de
 * flags ligadas, as decisões que o jogo memoriza.
 *
 * Mais a cena atual, que é onde o jogador está.
 *
 * Começar de novo é criar uma Partida nova: o construtor fabrica tudo do
 * zero, então nada da partida anterior sobrevive e não é preciso nenhum
 * método de "resetar".
 */
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;

    // 1. O JOGADOR E SEUS ITENS
    private Protagonista protagonista;
    private Inventario inventario;

    // 2. ONDE O JOGADOR ESTÁ
    /* transient: a Cena não vai pro arquivo de save, porque ela arrastaria
     * a história inteira junto. Depois de carregar, este campo volta null e
     * o controller coloca a cena de volta pelo id guardado no Save.
     */
    private transient Cena cenaAtual;

    /* 3. DECISÕES ANTERIORES
    * Um conjunto guarda as flags que já foram ligadas. O enum Flag lista
    * as possíveis; este Set guarda as que aconteceram nesta partida.
    */
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
        this.protagonista = new FabricaDeProtagonista(preset).criar(nomeJogador);
        this.inventario = new Inventario();
        this.flags = new HashSet<>();

        // Confianças iniciais diferentes
        FabricaDeNpc neutro = new FabricaDeNpc(50);
        this.otavio       = neutro.criar(Personagens.OTAVIO.getNomeExibicao());
        this.mae          = neutro.criar(Personagens.MAE.getNomeExibicao());
        this.antonio      = neutro.criar(Personagens.ANTONIO.getNomeExibicao());
        this.davi         = new FabricaDeNpc(30).criar(Personagens.DAVI.getNomeExibicao());
        this.zulmira      = new FabricaDeNpc(40).criar(Personagens.ZULMIRA.getNomeExibicao());
        this.homemDeTerno = new FabricaDeNpc(0).criar(Personagens.HOMEM_DE_TERNO.getNomeExibicao());
        this.manuela      = new FabricaDeNpc(30).criar(Personagens.MANUELA.getNomeExibicao());

        // Item inicial: o terço da mãe, a única coisa que ele leva de casa.
        this.inventario.adicionar(model.enums.Item.TERCO);
    }

    // flags

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


    public Set<Flag> getFlags() { return Set.copyOf(flags); }

    public List<Npc> getNpcs() {
        return List.of(otavio, mae, davi, zulmira, antonio, homemDeTerno, manuela);
    }

    /** Memento: uma fotografia independente, sem expor os dados internos. */
    public Memento criarMemento() {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream saida = new ObjectOutputStream(bytes)) {
            saida.writeObject(this);
            return new Memento(bytes.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível copiar a partida", e);
        }
    }

    /** Só a própria Partida sabe reconstruir o estado guardado no Memento. */
    public static Partida restaurar(Memento memento) {
        if (memento == null) throw new IllegalArgumentException("O estado da partida está vazio");
        try (ObjectInputStream entrada = new ObjectInputStream(
                new ByteArrayInputStream(memento.dados))) {
            return (Partida) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Não foi possível restaurar a partida", e);
        }
    }

    public static final class Memento implements Serializable {
        private static final long serialVersionUID = 1L;
        private final byte[] dados;

        private Memento(byte[] dados) { this.dados = dados; }
    }

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
