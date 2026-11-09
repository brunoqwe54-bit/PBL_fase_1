package model.persistencia;

/**
 * Calcula a porcentagem de progresso mostrada na lista de slots.
 *
 * O progresso é a fração de capítulos já concluídos. Estar no capítulo 5
 * quer dizer que 4 de 10 foram concluídos, então 40%. O capítulo 1 dá 0%,
 * e nenhum save chega a 100%: no capítulo 10 o jogador ainda não terminou.
 *
 * O capítulo vem do id da cena: "CAP05" e "CAP05B" são o capítulo 5. Id
 * fora desse formato (como "FIM_TARDE") dá 0.
 */
public class Progresso {

    public static final int TOTAL_DE_CAPITULOS = 10;

    /**
     * Calcula o progresso de quem está na cena com esse id.
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
            return 0; // "CAPXX", por exemplo
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
