package controller;

import view.MenuInicial;
import view.ViewDialogo;
import model.Dialogo;
import model.entidades.Protagonista;
import java.util.Scanner;

public class JogoController {
    //Dando acesso ao view
    private MenuInicial menuInicial = new MenuInicial();
    private ViewDialogo viewDialogo = new ViewDialogo();
    private Scanner scanner = new Scanner(System.in);

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
        String nome = menuInicial.pedirNome();
        Protagonista protagonista = new Protagonista(nome);
        Dialogo fala1 = new Dialogo(protagonista, "Blablablabla");

        viewDialogo.mostrar(fala1);
    }


    //teste menu de escolhas

    public void cenaPraca(){

        Protagonista heroi = new Protagonista("Agnaldo");
        Dialogo d1 = new Dialogo(heroi,"vish, tem um cara estranho na praça");

        String[] opcoesPraca = {
            "Falar com estranho","Evitar estranho",

         };

        int decisao = viewDialogo.exibirMenuNarrativo(d1,opcoesPraca);

        if(decisao == 1){
            menuInicial.exibirMensagem("Resultado: o estranho está possuido e ataca você");
        }
        if(decisao == 2){
            menuInicial.exibirMensagem("Resultado: o estranho não interage e você sai ileso");
        }


}
}
