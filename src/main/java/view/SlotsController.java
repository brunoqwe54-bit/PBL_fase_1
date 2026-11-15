package view;

import controller.SessaoDeJogo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entidades.Save;
import model.excecoes.FalhaAoCarregarException;
import model.excecoes.FalhaAoSalvarException;

import java.time.format.DateTimeFormatter;

/** Lista os quatro slots e pede confirmação antes de sobrescrever ou apagar. */
public class SlotsController {
    @FXML private Label titulo, mensagem;
    @FXML private VBox slots;
    private final SessaoDeJogo sessao = TelaMenuFX.getSessao();

    @FXML private void initialize() {
        titulo.setText(TelaMenuFX.isModoSalvar() ? "Salvar partida" : "Carregar e gerenciar saves");
        listar();
    }

    private void listar() {
        slots.getChildren().clear();
        for (int slot = 0; slot <= 3; slot++) {
            if (TelaMenuFX.isModoSalvar() && slot == 0) continue;
            adicionarSlot(slot);
        }
    }

    private void adicionarSlot(int slot) {
        boolean existe = sessao.existeSlot(slot);
        Save save = null;
        String resumo = "Espaço vazio";
        if (existe) {
            try {
                save = sessao.lerSlot(slot);
                resumo = save.getNomeJogador() + "  ·  "
                        + save.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                        + "\n" + save.getTituloCena() + "  ·  " + save.getProgresso() + "%";
            } catch (FalhaAoCarregarException e) {
                resumo = "Save corrompido. Você pode excluí-lo ou sobrescrevê-lo.";
            }
        }
        Label nome = new Label(slot == 0 ? "AUTOSAVE" : "SLOT " + slot);
        nome.getStyleClass().add("subtitulo");
        Label detalhes = new Label(resumo);
        detalhes.setWrapText(true);
        Button acao = new Button(TelaMenuFX.isModoSalvar() ? "Salvar aqui" : "Carregar");
        acao.setDisable(!TelaMenuFX.isModoSalvar() && save == null);
        acao.setOnAction(evento -> usar(slot));
        Button excluir = new Button("Excluir");
        excluir.setDisable(!existe);
        excluir.setOnAction(evento -> excluir(slot));
        VBox cartao = new VBox(8, nome, detalhes, new HBox(10, acao, excluir));
        cartao.getStyleClass().add("cartao");
        slots.getChildren().add(cartao);
    }

    private void usar(int slot) {
        try {
            if (TelaMenuFX.isModoSalvar()) {
                if (sessao.existeSlot(slot) && !TelaMenuFX.confirmar("Sobrescrever o save do slot " + slot + "?")) return;
                sessao.salvar(slot);
                mensagem.setText("Partida salva no slot " + slot + ".");
                listar();
            } else if (TelaMenuFX.confirmarPerda()) {
                sessao.carregar(slot);
                TelaMenuFX.mostrar("jogo-view.fxml");
            }
        } catch (FalhaAoSalvarException | FalhaAoCarregarException e) {
            TelaMenuFX.erro(e.getMessage());
        }
    }

    private void excluir(int slot) {
        if (!TelaMenuFX.confirmar("Excluir " + (slot == 0 ? "o autosave" : "o slot " + slot)
                + "? Essa ação não pode ser desfeita.")) return;
        try {
            sessao.excluir(slot);
            mensagem.setText("Save excluído.");
            listar();
        } catch (FalhaAoSalvarException e) { TelaMenuFX.erro(e.getMessage()); }
    }

    @FXML private void onVoltarClick() { TelaMenuFX.voltar(); }
}
