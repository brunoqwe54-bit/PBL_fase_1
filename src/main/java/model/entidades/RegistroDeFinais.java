package model.entidades;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Guarda os IDs dos finais já alcançados pelo jogador.
 * IDS e NOMES mantêm os identificadores e os nomes na mesma ordem.
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

    // O conjunto evita registrar o mesmo final mais de uma vez.
    private HashSet<String> vistos = new HashSet<>();

    /**
     * Registra a cena se ela for um final válido.
     *
     * @return true se era um final ainda não visto, false se já estava anotado ou se a
     *     cena nem é um final
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
