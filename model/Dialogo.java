package model;

import model.entidades.Personagem;

public class Dialogo {
    private Personagem personagem;
    private String texto;

    public Dialogo(Personagem personagem, String texto) {
        this.personagem = personagem;
        this.texto = texto;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public String getTexto() {
        return texto;
    }
}
