package model.entidades;

import java.util.List;
import java.util.ArrayList;

/**
 * Um trecho da história: o que o jogador lê e o que ele pode fazer ali.
 *
 * Uma cena guarda o título, a narração, a lista de falas e a lista de
 * escolhas. Ela não decide nada e não sabe para onde o jogador vai,
 * quem sabe isso é cada Escolha da lista de opções.
 *
 * As cenas são criadas vazias pela classe Historia e preenchidas depois.
 * Por isso os métodos getOpcoes() e getDialogos() devolvem as listas de
 * verdade, e não cópias: é assim que a Historia consegue ir acrescentando.
 *
 * Cena sem nenhuma opção é fim de jogo, o laço do controller para
 * quando não tem mais para onde ir.
 */
public class Cena {
    // identificação do capítulo
    private String id;
    // título do capítulo
    private String titulo;
    // narração
    private String textoPrincipal;
    // turnos de decisões
    private List<Escolha> opcoes;
    // lista de diálogos
    private List<Dialogo> dialogos;

    /**
     * Cria a cena já com as listas de falas e de opções vazias.
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
     * Devolve a lista real de opções, não uma cópia.
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