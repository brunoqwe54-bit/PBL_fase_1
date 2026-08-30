package model.entidades;

import java.util.List;

public class Cena {
    // identificação do capitulo
    private String id;
    // titulo do capitulo
    private String titulo;
    // narração
    private String textoPrincipal;
    //turnos de decisões
    private List<Escolha> opcoes;

    public Cena(String id, String titulo, String textoPrincipal, List<Escolha> opcoes){
        this.id = id;
        this.titulo = titulo;
        this.textoPrincipal = textoPrincipal;
        this.opcoes = opcoes;

    }

}
