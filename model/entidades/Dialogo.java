package model.entidades;

/**
 * Uma fala: quem falou e o que foi dito.
 *
 * É um par simples, sem lógica nenhuma. A Cena guarda uma lista deles e
 * a view imprime na ordem em que foram adicionados.
 *
 * O personagem é guardado como PersonagemBase, o tipo comum entre
 * Protagonista e Npc, então tanto um quanto o outro podem falar e ser
 * guardados na mesma lista.
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
