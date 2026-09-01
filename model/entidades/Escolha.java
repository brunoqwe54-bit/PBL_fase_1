package model.entidades;

import model.enums.Personagens;package model.entidades.Npc;


public class Escolha {
    // identificação da escolha
    private String id;
    // opcões de texto
    private String textoExibido;
    // conectar com a proxima cena
    private Cena cenaDestino;
    // variavel para alterar atributo dos npcs
    private int consquencia;
    // Npc que vai sofrer a consequncia
    private Npc npc

    public Escolha(String id, String textoExibido, Cena cenaDestino, int consquencia, Personagens npc) {
        this.id = id;
        this.textoExibido = textoExibido;
        this.cenaDestino = cenaDestino;
        this.consquencia += consquencia;
        this.npc = npc;
    }
    // Getters para o Controller conseguir acessar esses dados depois
    public String getId() { return id; }
    public String getTextoExibido() { return textoExibido; }
    public Cena getCenaDestino() { return cenaDestino; }
}
