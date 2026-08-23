package controller;

import view.MenuInicial;



public class JogoController {
    //Dando acesso ao view
    private MenuInicial menuInicial = new MenuInicial();

    public void iniciarPartida() {
        //Chamando o menuInicial e retornando o numero digitado
        int escolha = menuInicial.exibir();


        //Case para escolha

        switch (escolha) {
            case 1:
                menuInicial.exibirMensagem("O jogo está sendo iniciado...");
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
}
