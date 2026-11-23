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
 * Guarda o estado da partida, incluindo protagonista, itens, NPCs, decisões e cena
 * atual.
 * Uma nova partida começa com seus próprios objetos e valores iniciais.
 */
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;

    // Protagonista e inventário.
    private Protagonista protagonista;
    private Inventario inventario;

    // Cena atual.
    /* A cena não é serializada. Ao carregar, o controlador a recupera pelo ID do Save. */
    private transient Cena cenaAtual;

    /* Decisões registradas nesta partida. */
    private Set<Flag> flags;

    // NPCs da partida.
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

        // Confiança inicial dos NPCs.
        FabricaDeNpc neutro = new FabricaDeNpc(50);
        this.otavio       = neutro.criar(Personagens.OTAVIO.getNomeExibicao());
        this.mae          = neutro.criar(Personagens.MAE.getNomeExibicao());
        this.antonio      = neutro.criar(Personagens.ANTONIO.getNomeExibicao());
        this.davi         = new FabricaDeNpc(30).criar(Personagens.DAVI.getNomeExibicao());
        this.zulmira      = new FabricaDeNpc(40).criar(Personagens.ZULMIRA.getNomeExibicao());
        this.homemDeTerno = new FabricaDeNpc(0).criar(Personagens.HOMEM_DE_TERNO.getNomeExibicao());
        this.manuela      = new FabricaDeNpc(30).criar(Personagens.MANUELA.getNomeExibicao());

        // O protagonista começa com o terço da mãe.
        this.inventario.adicionar(model.enums.Item.TERCO);
    }

    // Decisões da partida.

    /**
     * Registra uma decisão. Repetir a mesma flag não altera o conjunto.
     *
     * @param flag a decisão a memorizar
     */
    public void ligarFlag(Flag flag) {
        flags.add(flag);
    }

    /**
     * Verifica se a decisão já foi registrada.
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

    /**
     * Cria uma cópia independente do estado no Memento.
     *
     * @return estado capturado da partida
     * @throws IllegalStateException se não conseguir copiar o estado
     */
    public Memento criarMemento() {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream saida = new ObjectOutputStream(bytes)) {
            saida.writeObject(this);
            return new Memento(bytes.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível copiar a partida", e);
        }
    }

    /**
     * Restaura uma partida a partir do Memento.
     *
     * @param memento estado que será restaurado
     * @return uma nova partida com o estado salvo
     * @throws IllegalArgumentException se o Memento estiver vazio
     * @throws IllegalStateException se não conseguir restaurar o estado
     */
    public static Partida restaurar(Memento memento) {
        if (memento == null) throw new IllegalArgumentException("O estado da partida está vazio");
        try (ObjectInputStream entrada = new ObjectInputStream(
                new ByteArrayInputStream(memento.dados))) {
            return (Partida) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Não foi possível restaurar a partida", e);
        }
    }

    /** Guarda os dados da cópia sem permitir acesso direto ao conteúdo. */
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
