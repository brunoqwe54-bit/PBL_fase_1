package view;

import model.entidades.Dialogo;

import java.util.Scanner;

public class ViewDialogo {
    private Scanner teclado = new Scanner(System.in);
    public void mostrar(Dialogo dialogo) {

        System.out.println(dialogo.getPersonagem().getNome() + ": " + dialogo.getTexto());
    }
    public int exibirMenuNarrativo(Dialogo dialogo, String[] opcoesDeResposta){
        //imprime a fala do personagem
        System.out.println(dialogo.getPersonagem().getNome() + ": " + dialogo.getTexto());
        System.out.println("--------------------------------");

        //numero de opções de escolha

        for( int i =0; i< opcoesDeResposta.length; i++ ){
            System.out.println((i + 1) + " - " + opcoesDeResposta[i]);
        }



        //captura o que o jogador digitou e devolve para o Controller
        System.out.print("Sua escolha: ");
        return teclado.nextInt();

    }
}
