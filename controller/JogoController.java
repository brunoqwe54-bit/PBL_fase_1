package controller;

import view.MenuInicial;
import view.ViewDialogo;
import model.Dialogo;
import model.entidades.Protagonista;


public class JogoController {
    //Dando acesso ao view
    private MenuInicial menuInicial = new MenuInicial();
    private ViewDialogo viewDialogo = new ViewDialogo();

    public void iniciarPartida() {
        //Chamando o menuInicial e retornando o numero digitado
        int escolha = menuInicial.exibir();


        //Case para escolha

        switch (escolha) {
            case 1:
                menuInicial.exibirMensagem("O jogo está sendo iniciado...");
                iniciarJogo();
                break;
            case 2:
                menuInicial.exibirMensagem("Encerrando jogo.");
                System.exit(0);
                break;
            default:
                menuInicial.exibirMensagem("Opção inválida! Tente novamente.");
                break;
        }

    }
    public void iniciarJogo() {
        Protagonista protagonista = new Protagonista("Andre");
        Dialogo fala1 = new Dialogo(protagonista, "Blablablabla");

        viewDialogo.mostrar(fala1);
    }
}
