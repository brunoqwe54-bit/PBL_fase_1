package model.entidades;

import model.enums.Flag;
import model.enums.Personagens;

import java.util.HashSet;
import java.util.Set;

/**
 * O ESTADO da partida atual.
 *
 * A Historia guarda os capitulos (iguais em toda partida).
 * A Partida guarda onde o jogador esta e o que aconteceu com ele.
 *
 * Comecar uma partida nova = criar uma Partida nova = tudo zerado.
 */
public class Partida {

    // 1. O JOGADOR E SEUS ITENS
    private Protagonista protagonista;
    private Inventario inventario;

    // 2. ONDE O JOGADOR ESTA
    private Cena cenaAtual;

    // 3. DECISOES ANTERIORES
    // Um conjunto guarda as flags que ja foram ligadas. O enum Flag lista
    // as possiveis; este Set guarda as que aconteceram nesta partida.
    private Set<Flag> flags;

    // 4. O ELENCO desta rodada
    private Npc otavio;
    private Npc mae;
    private Npc davi;
    private Npc zulmira;
    private Npc antonio;
    private Npc homemDeTerno;
    private Npc manuela;

    public Partida(String nomeJogador) {
        this.protagonista = new Protagonista(nomeJogador);
        this.inventario = new Inventario();
        this.flags = new HashSet<>();

        // Confiancas iniciais diferentes: o irmao confia mais, o menino
        // ainda nao te conhece.
        this.otavio       = new Npc(Personagens.OTAVIO.getNomeExibicao(), 50);
        this.mae          = new Npc(Personagens.MAE.getNomeExibicao(), 50);
        this.davi         = new Npc(Personagens.DAVI.getNomeExibicao(), 30);
        this.zulmira      = new Npc(Personagens.ZULMIRA.getNomeExibicao(), 40);
        this.antonio      = new Npc(Personagens.ANTONIO.getNomeExibicao(), 50);
        this.homemDeTerno = new Npc(Personagens.HOMEM_DE_TERNO.getNomeExibicao(), 0);
        this.manuela      = new Npc(Personagens.MANUELA.getNomeExibicao(), 30);

        // Item inicial: o terco da mae, a unica coisa que ele leva de casa.
        this.inventario.adicionar(model.enums.Item.TERCO);
    }

    // ---------- flags ----------

    public void ligarFlag(Flag flag) {
        flags.add(flag);
    }

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
