package model.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Um slot de salvamento: a Partida mais as informações que o menu mostra
 * na lista de slots.
 *
 * É só um pacote de dados, sem lógica nenhuma. Quem monta o Save (com o
 * progresso já calculado) é o controller, e quem grava e lê o arquivo é
 * a classe de arquivo.
 *
 * A cena atual da Partida não vai pro arquivo (é transient). Por isso o
 * Save guarda o id e o título da cena: ao carregar, o controller usa o id
 * para colocar o jogador de volta no lugar.
 */
public class Save implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomeJogador;
    private LocalDateTime dataHora;
    private String idCena;
    private String tituloCena;
    private int progresso;
    private Partida partida;

    /**
     * Cria o save com tudo já pronto.
     *
     * @param nomeJogador o nome do protagonista
     * @param dataHora    o momento em que o save foi feito
     * @param idCena      o id da cena atual, ex. "CAP03B"
     * @param tituloCena  o título da cena atual, mostrado no slot
     * @param progresso   porcentagem de capítulos concluídos, de 0 a 100
     * @param partida     a partida em andamento
     */
    public Save(String nomeJogador, LocalDateTime dataHora, String idCena,
                String tituloCena, int progresso, Partida partida) {
        this.nomeJogador = nomeJogador;
        this.dataHora = dataHora;
        this.idCena = idCena;
        this.tituloCena = tituloCena;
        this.progresso = progresso;
        this.partida = partida;
    }

    public String getNomeJogador() { return nomeJogador; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getIdCena() { return idCena; }
    public String getTituloCena() { return tituloCena; }
    public int getProgresso() { return progresso; }
    public Partida getPartida() { return partida; }
}
