package model.factory;

import model.entidades.Protagonista;
import model.enums.Personagens;
import model.enums.Preset;

/** Cria o personagem jogável com o preset escolhido. */
public class FabricaDeProtagonista extends FabricaDePersonagem {
    private final Preset preset;

    public FabricaDeProtagonista(Preset preset) {
        this.preset = preset == null ? Preset.COMUM : preset;
    }

    @Override
    public Protagonista criar(String nome) {
        if (nome == null || nome.isBlank()) nome = Personagens.VICENTE.getNomeExibicao();
        return new Protagonista(nome.trim(), preset);
    }
}
