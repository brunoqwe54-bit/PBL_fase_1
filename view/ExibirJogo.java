package view;

import model.entidades.Cena;
import model.entidades.Dialogo;
import model.entidades.Escolha;
import java.util.Scanner;

public class ExibirJogo {
    private Scanner scanner = new Scanner(System.in);

    public void exibirCena(Cena cena){
        System.out.println("\n==="+ cena.getTitulo()+"===");
        aguardarTecla();
        System.out.println(cena.getTextoPrincipal());
        aguardarTecla();
        System.out.println();

        //pecorre os dialogos e printa
        for(Dialogo fala: cena.getDialogos()){
            System.out.println("["+ fala.getPersonagem().getNome()+"]:" + fala.getTexto());
            aguardarTecla();

        }
        System.out.println();

        //percorre as opções e printa
        int index=1;
        for(Escolha opcao : cena.getOpcoes()){
            System.out.println(index + " - " + opcao.getTextoExibido());
            index++;
        }
    }

    // Pausa a exibição até o jogador apertar ENTER
    private void aguardarTecla() {
        System.out.print("\nv\n");
        scanner.nextLine();
    }

    public int pedirEscolhaJogador(){
        System.out.print("\nO que você faz? ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // limpa o \n pendente, evita bug no próximo nextLine()
        return opcao;
    }
}
