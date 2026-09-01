package controller;

import model.entidades.Cena;
import model.factory.Historia;
import view.ExibirJogo;
import view.MenuInicial;
import model.entidades.*;




public class JogoController {
    //Dando acesso ao view
    private MenuInicial menuInicial = new MenuInicial();
    private ExibirJogo exibirJogo = new ExibirJogo();
    private Historia historia = new Historia();


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
    private void iniciarJogo(){
        //cria a cena 1
        Cena cenaAtual = historia.montarHistoria();

        //loop para rodar o jogo
        while(cenaAtual != null){
            //chama view para exibir os textos, dialogos e opções
            exibirJogo.exibirCena(cenaAtual);

            //verifica se é o final do jogo (uma cena sem escolhas)
            if (cenaAtual.getOpcoes().isEmpty()) {
                System.out.println("--- FIM DE JOGO ---");
                break; // Sai do loop e encerra
            }
            //numero digitado pelo jogador
            int escolhaJogador = exibirJogo.pedirEscolhaJogador();


            try {
                if (escolhaJogador.getNpc() != null && escolhaJogador.getConsequencia() != 0) {
                    escolhaJogador.getNpc().alterarConfianca(escolhaJogador.getConsequencia());
                }
                // A lista (ArrayList) começa em 0, por isso fazemos escolhaJogador - 1
                cenaAtual = cenaAtual.getOpcoes().get(escolhaJogador - 1).getCenaDestino();
            } catch (IndexOutOfBoundsException e) {
                //caso o jogador digite um número que não está na lista (
                System.out.println("\n[ERRO] Opção inválida. Digite o número correspondente à escolha.");

            }
        }


    }



}
