package model.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Guarda o Memento da partida e os dados exibidos no slot.
 * O ID da cena permite recuperar o ponto da história ao carregar.
 */
public class Save implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomeJogador;
    private LocalDateTime dataHora;
    private String idCena;
    private String tituloCena;
    private int progresso;
    private Partida partida;
    private Partida.Memento estado;

    /**
     * Cria o save com uma cópia do estado e os metadados da partida.
     *
     * @param nomeJogador o nome do protagonista
     * @param dataHora    o momento em que o save foi feito
     * @param idCena      o id da cena atual, ex. "CAP03B"
     * @param tituloCena  o título da cena atual, mostrado no slot
     * @param progresso   porcentagem de progresso da partida
     * @param partida     a partida em andamento
     */
    public Save(String nomeJogador, LocalDateTime dataHora, String idCena,
                String tituloCena, int progresso, Partida partida) {
        this.nomeJogador = nomeJogador;
        this.dataHora = dataHora;
        this.idCena = idCena;
        this.tituloCena = tituloCena;
        this.progresso = progresso;
        this.estado = partida == null ? null : partida.criarMemento();
    }

    public String getNomeJogador() { return nomeJogador; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getIdCena() { return idCena; }
    public String getTituloCena() { return tituloCena; }
    public int getProgresso() { return progresso; }
    public Partida getPartida() {
        // O campo antigo permite abrir os saves feitos antes da fase 3.
        if (estado == null && partida != null) return Partida.restaurar(partida.criarMemento());
        return estado == null ? null : Partida.restaurar(estado);
    }
}
