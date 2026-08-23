package controller;

import view.MenuInicial;



public class JogoController {
    //Dando acesso ao view
    private MenuInicial menuInicial = new MenuInicial();

    public void iniciarPartida() {
        //Chamando o MenuInicial e retornando o numero digitado
        int escolha = menuInicial.exibir();


        //Case para escolha
        switch (escolha) {
            case 1:
                menuInicial.iniciarJogo();
                break;
            case 2:
                menuInicial.terminarJogo();
                break;
            default:
                menuInicial.erro();
                break;

        }
    }
}
