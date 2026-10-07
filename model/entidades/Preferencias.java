package model.entidades;

import model.enums.Preset;

import java.io.Serializable;

/**
 * Os ajustes do jogador, que valem para todas as partidas.
 *
 * São três: o preset que já vem escolhido ao começar uma partida nova, se
 * as pausas de ENTER nas cenas são puladas e se o autosave está ligado.
 * Ficam guardadas num arquivo, então continuam valendo quando o jogo é
 * fechado e aberto de novo.
 *
 * Os valores iniciais (de fábrica) são os do começo de cada campo.
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
