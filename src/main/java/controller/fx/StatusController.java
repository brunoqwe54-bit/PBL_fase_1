package controller.fx;

import view.TelaMenuFX;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import model.entidades.Npc;
import model.entidades.Partida;
import model.enums.Atributo;
import model.enums.Flag;
import model.enums.Item;

/** Apenas lê o modelo para apresentar os detalhes da partida. */
public class StatusController {
    @FXML private Label titulo, atributos, inventario, objetivos, decisoes;
    @FXML private VBox relacionamentos;

    @FXML private void initialize() {
        Partida partida = TelaMenuFX.getJogoController().getPartida();
        titulo.setText("Status de " + partida.getProtagonista().getNome());
        StringBuilder texto = new StringBuilder();
        for (Atributo atributo : Atributo.values()) {
            texto.append(atributo.getNomeExibicao()).append(": ")
                    .append(partida.getProtagonista().getAtributo(atributo)).append(" / 100\n\n");
        }
        atributos.setText(texto.toString());
        texto.setLength(0);
        for (Item item : Item.values()) {
            if (partida.getInventario().possui(item)) texto.append("• ").append(item.getNomeExibicao()).append("\n\n");
        }
        inventario.setText(texto.isEmpty() ? "Sua mochila está vazia." : texto.toString());
        for (Npc npc : partida.getNpcs()) {
            Label nome = new Label(npc.getNome() + "  ·  Confiança " + npc.getConfianca() + "/100");
            ProgressBar barra = new ProgressBar(npc.getConfianca() / 100.0);
            barra.setMaxWidth(Double.MAX_VALUE);
            relacionamentos.getChildren().add(new VBox(5, nome, barra));
        }
        objetivos.setText(TelaMenuFX.getJogoController().getObjetivos());
        texto.setLength(0);
        for (Flag flag : Flag.values()) {
            if (partida.temFlag(flag)) texto.append("• ").append(flag.getDescricao()).append("\n\n");
        }
        decisoes.setText(texto.isEmpty() ? "Nenhuma decisão relevante registrada ainda." : texto.toString());
    }

    @FXML private void onVoltarClick() { TelaMenuFX.voltar(); }
}
