package model.entidades;



public class Escolha {
    // identificação da escolha
    private String id;
    // opcões de texto
    private String textoExibido;
    // conectar com a proxima cena
    private Cena cenaDestino;

    public Escolha(String id, String textoExibido, Cena cenaDestino) {
        this.id = id;
        this.textoExibido = textoExibido;
        this.cenaDestino = cenaDestino;
    }
    // Getters para o Controller conseguir acessar esses dados depois
    public String getId() { return id; }
    public String getTextoExibido() { return textoExibido; }
    public Cena getCenaDestino() { return cenaDestino; }
}
