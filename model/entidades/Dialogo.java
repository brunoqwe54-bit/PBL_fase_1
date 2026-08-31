package model.entidades;

public class Dialogo {
    private PersonagemBase personagemBase;
    private String texto;

    public Dialogo(PersonagemBase personagemBase, String texto) {
        this.personagemBase = personagemBase;
        this.texto = texto;
    }

    public PersonagemBase getPersonagem() {
        return personagemBase;
    }

    public String getTexto() {
        return texto;
    }
}
