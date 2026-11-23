package model.entidades;

import java.util.List;
import java.util.ArrayList;

/**
 * Guarda o título, a narração, as falas e as escolhas de uma cena.
 * Historia preenche suas listas. Uma cena sem escolhas encerra a partida.
 */
public class Cena {
    // Identificador da cena.
    private String id;
    // Título da cena.
    private String titulo;
    // Narração da cena.
    private String textoPrincipal;
    // Escolhas da cena.
    private List<Escolha> opcoes;
    // Falas da cena.
    private List<Dialogo> dialogos;

    /**
     * Cria a cena com as listas de falas e escolhas vazias.
     *
     * @param id            identificador usado para achar a cena, ex. "CAP03B"
     * @param titulo        o cabeçalho mostrado na tela
     * @param textoPrincipal a narração, com parágrafos separados por linha em branco
     */
    public Cena(String id, String titulo, String textoPrincipal) {
        this.id = id;
        this.titulo = titulo;
        this.textoPrincipal = textoPrincipal;
        this.opcoes = new ArrayList<>();
        this.dialogos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTextoPrincipal() {
        return textoPrincipal;
    }

    /**
     * Devolve a lista usada por Historia para adicionar as escolhas.
     *
     * @return a lista onde a Historia acrescenta as escolhas da cena
     */
    public List<Escolha> getOpcoes() {
        return opcoes;
    }

    public List<Dialogo> getDialogos() {
        return dialogos;
    }
}