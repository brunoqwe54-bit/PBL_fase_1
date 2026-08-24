package view;

import model.Dialogo;

public class ViewDialogo {
    public void mostrar(Dialogo dialogo) {

        System.out.println(dialogo.getPersonagem().getNome() + ":" + dialogo.getTexto());
    }
}
