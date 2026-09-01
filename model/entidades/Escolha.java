package model.entidades;

public class Escolha {
    // identificação da escolha
    private String id;
    // opções de texto
    private String textoExibido;
    // conectar com a próxima cena
    private Cena cenaDestino;
    // variável para alterar atributo dos npcs
    private int consequencia;
    // Npc que vai sofrer a consequência
    private Npc npc;

    // O parâmetro deve ser do tipo Npc (o objeto), não Personagens (o Enum)
    public Escolha(String id, String textoExibido, Cena cenaDestino, int consequencia, Npc npc) {
        this.id = id;
        this.textoExibido = textoExibido;
        this.cenaDestino = cenaDestino;
        this.consequencia = consequencia;
        this.npc = npc;
    }

    public String getId() { return id; }
    public String getTextoExibido() { return textoExibido; }
    public Cena getCenaDestino() { return cenaDestino; }
    public int getConsequencia() { return consequencia; }
    public Npc getNpc() { return npc; }
}