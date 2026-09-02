package controller;

import model.entidades.Cena;
import model.factory.Historia;
import view.ExibirJogo;
import view.MenuInicial;

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
                String nomeProtagonista = menuInicial.pedirNome();
                menuInicial.exibirMensagem("O jogo está sendo iniciado...");
                iniciarJogo(nomeProtagonista);
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

    private void iniciarJogo(String nomeProtagonista) {
        Cena cenaAtual = historia.montarHistoria(nomeProtagonista);

        //loop para rodar o jogo
        while(cenaAtual != null){
            //chama view para exibir os textos, dialogos e opções
            exibirJogo.exibirCena(cenaAtual);

            //verifica se é o final do jogo (uma cena sem escolhas)
            if (cenaAtual.getOpcoes().isEmpty()) {
                System.out.println("--- FIM DE JOGO ---");
                break;
            }
            //numero digitado pelo jogador
            int escolhaJogador = exibirJogo.pedirEscolhaJogador();

            try {
                model.entidades.Escolha opcaoEscolhida = cenaAtual.getOpcoes().get(escolhaJogador - 1);

                if (opcaoEscolhida.getNpc() != null && opcaoEscolhida.getConsequencia() != 0) {
                    opcaoEscolhida.getNpc().alterarConfianca(opcaoEscolhida.getConsequencia());
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