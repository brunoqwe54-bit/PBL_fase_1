package model.factory;

import model.entidades.*;
import model.enums.Personagens;

public class Historia {
    private Npc otavio = new Npc(Personagens.OTAVIO.getNomeExibicao());
    private Npc mae = new Npc(Personagens.MAE.getNomeExibicao());
    private Protagonista vicente;

    public Cena montarHistoria(String nomeProtagonista) {
        this.vicente = new Protagonista(nomeProtagonista);

        // criação de próxima cena
        Cena cap02 = new Cena(
                "CAP02",
                "A Rua de Casa",
                "Vinte casas ate a esquina. Voce conhece todas. Todas escuras.\n\n" +
                        "O cachorro dos Pereira esta na calcada, deitado de lado,\n" +
                        "olhando pra rua sem latir. Cachorro que nao late na Noite\n" +
                        "Longa e cachorro que ta vendo.\n\n" +
                        "Na metade do quarteirao voce escuta a voz de mainha."
        );

        Dialogo falaMae = new Dialogo(mae, "Vicente.");
        cap02.getDialogos().add(falaMae);

        // criando cena 01
        Cena cap01 = new Cena(
                "CAP01",
                "A Porta da Frente",
                "A chave ainda está na fechadura. Otávio não tirou.\n\n" +
                        "Ele está bloqueando a saída. A expressão dele não é de raiva, é de pânico. " +
                        "O mesmo medo que assombra a família de vocês há anos.\n\n" +
                        "Lá fora, aquele canto estranho continua rasgando a rua escura."
        );

        // dialogos
        Dialogo fala1 = new Dialogo(otavio, "Você não pode sair. Ninguém sai na Noite Longa. A gente sabe da regra desde criança.");
        Dialogo fala2 = new Dialogo(vicente, "Ela só tem dezessete anos, Otávio. Eu tenho que buscar ela.");
        Dialogo fala3 = new Dialogo(otavio, "E você tem vinte e dois! Já devia saber o que acontece lá fora.");
        Dialogo fala4 = new Dialogo(otavio, "Presta atenção: se você cruzar essa porta, eu vou trancar. É o que a nossa mãe faria.");
        Dialogo fala5 = new Dialogo(otavio, "E se você se arrepender e bater querendo entrar... eu não vou abrir.");

        // guardando dialogos na lista do cap01
        cap01.getDialogos().add(fala1);
        cap01.getDialogos().add(fala2);
        cap01.getDialogos().add(fala3);
        cap01.getDialogos().add(fala4);
        cap01.getDialogos().add(fala5);

        // escolhas
        Escolha opcao1 = new Escolha("ESC0101", "Sair em silêncio. Não vale a pena discutir.", cap02, -10, otavio);
        Escolha opcao2 = new Escolha("ESC0102", "Pedir para ele deixar a porta destrancada.", cap02, 0, otavio);
        Escolha opcao3 = new Escolha("ESC0103", "Tentar convencer Otávio a ir junto com você.", cap02, 0, otavio);

        // guardando escolhas na lista do cap01
        cap01.getOpcoes().add(opcao1);
        cap01.getOpcoes().add(opcao2);
        cap01.getOpcoes().add(opcao3);

        return cap01;
    }
}