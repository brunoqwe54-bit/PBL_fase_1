package view;

import atlantafx.base.theme.Dracula;
import controller.ObservadorDeJogo;
import controller.fx.JogoViewController;
import controller.JogoController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/** Cria a janela e organiza a navegação. Não contém regras de jogo. */
public class TelaMenuFX extends Application {
    private static final JogoController sessao = new JogoController();
    private static Stage palco;
    private static Object controllerAtual;
    private static String telaAtual = "menu-view.fxml";
    private static String retorno = "menu-view.fxml";
    private static boolean modoSalvar;
    private static Parent jogoEmPausa;
    private static Object controllerEmPausa;

    public static JogoController getSessao() { return sessao; }
    public static boolean isModoSalvar() { return modoSalvar; }

    @Override
    public void start(Stage janela) throws IOException {
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        palco = janela;
        palco.setTitle("A Noite Longa");
        palco.setMinWidth(900);
        palco.setMinHeight(680);
        palco.setOnCloseRequest(evento -> {
            evento.consume();
            sair();
        });
        trocarTela("menu-view.fxml");
        palco.setWidth(1100);
        palco.setHeight(780);
        palco.show();
        palco.centerOnScreen();
    }

    public static void trocarTela(String arquivo) throws IOException {
        FXMLLoader carregador = new FXMLLoader(TelaMenuFX.class.getResource(arquivo));
        Parent raiz = carregador.load();
        if (controllerAtual instanceof ObservadorDeJogo observador) {
            sessao.removerObservador(observador);
        }
        controllerAtual = carregador.getController();
        if (controllerAtual instanceof ObservadorDeJogo observador) {
            sessao.adicionarObservador(observador);
        }
        if (palco.getScene() == null) {
            Scene cena = new Scene(raiz);
            cena.getStylesheets().add(TelaMenuFX.class.getResource("jogo.css").toExternalForm());
            palco.setScene(cena);
        } else {
            palco.getScene().setRoot(raiz);
        }
        telaAtual = arquivo;
        if (arquivo.equals("jogo-view.fxml") || arquivo.equals("menu-view.fxml")
                || arquivo.equals("nova-partida-view.fxml")) {
            jogoEmPausa = null;
            controllerEmPausa = null;
        }
    }

    public static void mostrar(String arquivo) {
        try {
            trocarTela(arquivo);
        } catch (IOException | RuntimeException e) {
            erro("Não foi possível abrir a tela. " + e.getMessage());
        }
    }

    public static void abrirPainel(String arquivo) {
        retorno = telaAtual;
        if (telaAtual.equals("jogo-view.fxml")) {
            jogoEmPausa = palco.getScene().getRoot();
            controllerEmPausa = controllerAtual;
        }
        mostrar(arquivo);
    }

    public static void abrirSlots(boolean salvar) {
        modoSalvar = salvar;
        abrirPainel("slots-view.fxml");
    }

    public static void voltar() {
        if (jogoEmPausa == null) {
            mostrar(retorno);
            return;
        }
        palco.getScene().setRoot(jogoEmPausa);
        controllerAtual = controllerEmPausa;
        telaAtual = "jogo-view.fxml";
        jogoEmPausa = null;
        controllerEmPausa = null;
        JogoViewController jogo = (JogoViewController) controllerAtual;
        sessao.adicionarObservador(jogo);
        jogo.retomar();
    }

    public static boolean confirmar(String mensagem) {
        ButtonType confirmar = new ButtonType("Confirmar", ButtonType.YES.getButtonData());
        ButtonType cancelar = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, mensagem, confirmar, cancelar);
        alerta.initOwner(palco);
        alerta.setTitle("A Noite Longa");
        alerta.setHeaderText("Confirmar ação");
        ((Button) alerta.getDialogPane().lookupButton(confirmar)).setDefaultButton(false);
        ((Button) alerta.getDialogPane().lookupButton(cancelar)).setDefaultButton(true);
        return alerta.showAndWait().orElse(cancelar) == confirmar;
    }

    public static boolean confirmarPerda() {
        return !sessao.temAlteracoesNaoSalvas()
                || confirmar("Há progresso que ainda não foi salvo. Deseja sair desta partida mesmo assim?");
    }

    public static void sair() {
        if (confirmarPerda()) Platform.exit();
    }

    public static void erro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensagem);
        alerta.initOwner(palco);
        alerta.setHeaderText("Não foi possível concluir");
        alerta.showAndWait();
    }

    public static void informar(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensagem);
        alerta.initOwner(palco);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.getDialogPane().setPrefWidth(620);
        alerta.showAndWait();
    }
}
