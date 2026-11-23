package model.entidades;

/**
 * Guarda uma fala e o personagem que a diz. O autor pode ser o protagonista ou um NPC.
 */
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
