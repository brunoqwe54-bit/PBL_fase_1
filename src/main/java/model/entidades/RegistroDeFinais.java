package model.entidades;

import java.io.Serializable;
import java.util.HashSet;

/**
 * A lista dos finais que o jogador já alcançou, em todas as partidas.
 *
 * O jogo tem 11 finais: 3 finais de verdade e 8 mortes. Cada um é uma
 * cena sem escolhas, identificada pelo id (IDS). NOMES guarda o nome de
 * cada um para a Galeria, na mesma posição. O registro só guarda os ids
 * dos que já foram vistos.
 */
public class RegistroDeFinais implements Serializable {

    private static final long serialVersionUID = 1L;

    // Os ids dos finais, iguais aos da Historia.
    public static final String[] IDS = {
        "FIM_A_TEMPO", "FIM_TARDE", "FIM_DESISTE",
        "MORTE_NOME", "MORTE_FILA", "MORTE_JANELA", "MORTE_PÁTIO",
        "MORTE_CASARÃO", "MORTE_VELA", "MORTE_CHAMOU", "MORTE_CÍRCULO"
    };

    // O nome de cada final, na mesma ordem de IDS.
    public static final String[] NOMES = {
        "Chega a tempo", "Chega tarde", "Volta pra casa",
        "O Sorriso", "A Procissão", "Do Outro Lado", "A Contagem",
        "Lugar à Mesa", "O Convite", "A Chamada", "Companhia"
    };

    // O HashSet não guarda repetidos, então ver o mesmo final de novo não muda nada.
    private HashSet<String> vistos = new HashSet<>();

    /**
     * Anota que o jogador chegou nessa cena.
     *
     * @return true se era um final ainda não visto, false se já estava
     *         anotado ou se a cena nem é um final
     */
    public boolean registrar(String idCena) {
        for (int i = 0; i < IDS.length; i++) {
            if (IDS[i].equals(idCena)) {
                return vistos.add(idCena);
            }
        }
        return false;
    }

    public boolean jaViu(String idCena) {
        return vistos.contains(idCena);
    }

    public int quantosViu() {
        return vistos.size();
    }
}
