package model.entidades;

import java.util.List;
import java.util.ArrayList;

public class Cena {
    // identificação do capitulo
    private String id;
    // titulo do capitulo
    private String titulo;
    // narração
    private String textoPrincipal;
    // turnos de decisões
    private List<Escolha> opcoes;
    // lista de dialogos
    private List<Dialogo> dialogos;

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

    public List<Escolha> getOpcoes() {
        return opcoes;
    }

    public List<Dialogo> getDialogos() {
        return dialogos;
    }
}