package view;

import model.entidades.Cena;
import model.entidades.Dialogo;
import model.entidades.Escolha;
import java.util.Scanner;

public class ExibirJogo {
    private Scanner scanner = new Scanner(System.in);

    public void exibirCena(Cena cena){
        System.out.println("\n==="+ cena.getTitulo()+"===");
        System.out.println(cena.getTextoPrincipal());
        System.out.println();

        //pecorre os dialogos e printa
        for(Dialogo fala: cena.getDialogos()){
            System.out.println("["+ fala.getPersonagem().getNome()+"]:" + fala.getTexto());

        }
        System.out.println();

        //percorre as opções e printa
        int index=1;
        for(Escolha opcao : cena.getOpcoes()){
            System.out.println(index + " - " + opcao.getTextoExibido());
            index++;
        }
    }
    public int pedirEscolhaJogador(){
        System.out.print("\nO que você faz? ");
        return scanner.nextInt();
    }
}
