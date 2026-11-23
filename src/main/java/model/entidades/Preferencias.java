package model.entidades;

import model.enums.Preset;

import java.io.Serializable;

/**
 * Guarda o preset padrão, a leitura rápida e a opção de autosave.
 * As preferências são salvas em arquivo e valem para todas as partidas.
 */
public class Preferencias implements Serializable {

    private static final long serialVersionUID = 1L;

    private Preset presetPadrao = Preset.COMUM;
    private boolean pularEnter = false;
    private boolean autosaveLigado = true;

    public Preset getPresetPadrao() { return presetPadrao; }
    public boolean isPularEnter() { return pularEnter; }
    public boolean isAutosaveLigado() { return autosaveLigado; }

    public void setPresetPadrao(Preset presetPadrao) { this.presetPadrao = presetPadrao; }
    public void setPularEnter(boolean pularEnter) { this.pularEnter = pularEnter; }
    public void setAutosaveLigado(boolean autosaveLigado) { this.autosaveLigado = autosaveLigado; }
}
