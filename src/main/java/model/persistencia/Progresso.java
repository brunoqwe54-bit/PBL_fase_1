package model.persistencia;

/**
 * Calcula o progresso dos capítulos de 0 a 90. Por exemplo, o capítulo 5 indica 40%.
 * O JogoController define 100% para os finais.
 */
public class Progresso {

    public static final int TOTAL_DE_CAPITULOS = 10;

    /**
     * Calcula a porcentagem pelo ID da cena.
     *
     * @param idCena o id da cena atual, ex. "CAP05B"
     * @return a porcentagem de 0 a 90, em múltiplos de 10
     */
    public static int calcular(String idCena) {
        if (idCena == null || !idCena.startsWith("CAP") || idCena.length() < 5) {
            return 0;
        }

        int capitulo;
        try {
            capitulo = Integer.parseInt(idCena.substring(3, 5));
        } catch (NumberFormatException e) {
            return 0; // Retorna zero quando o número do capítulo é inválido.
        }

        int concluidos = capitulo - 1;
        if (concluidos < 0) {
            concluidos = 0;
        }
        if (concluidos > TOTAL_DE_CAPITULOS - 1) {
            concluidos = TOTAL_DE_CAPITULOS - 1;
        }
        return concluidos * 100 / TOTAL_DE_CAPITULOS;
    }
}
