package controller;

import model.entidades.*;
import model.enums.Flag;
import model.enums.Preset;
import model.excecoes.FalhaAoCarregarException;
import model.excecoes.FalhaAoSalvarException;
import model.factory.Historia;
import model.persistencia.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Coordena a partida e a persistência sem conhecer JavaFX nem o terminal. */
public class SessaoDeJogo {
    private final Historia historia = new Historia();
    private final GerenciadorDeSaves saves;
    private final GerenciadorDePreferencias ajustes;
    private final GerenciadorDeFinais finais;
    private final List<ObservadorDeJogo> observadores = new ArrayList<>();
    private Preferencias preferencias;
    private RegistroDeFinais registro;
    private Partida partida;
    private boolean alteracoesNaoSalvas;
    private String ultimaMensagem = "";

    public SessaoDeJogo() { this(System.getProperty("noite.saves", "saves")); }

    /** Outra pasta permite testar sem tocar nos saves do jogador. */
    public SessaoDeJogo(String pasta) {
        saves = new GerenciadorDeSaves(pasta);
        ajustes = new GerenciadorDePreferencias(pasta);
        finais = new GerenciadorDeFinais(pasta);
        preferencias = ajustes.carregar();
        registro = finais.carregar();
    }

    public void adicionarObservador(ObservadorDeJogo observador) {
        if (!observadores.contains(observador)) observadores.add(observador);
    }

    public void removerObservador(ObservadorDeJogo observador) { observadores.remove(observador); }

    private void notificar(String mensagem) {
        ultimaMensagem = mensagem == null ? "" : mensagem;
        for (ObservadorDeJogo observador : List.copyOf(observadores)) {
            observador.atualizar(ultimaMensagem);
        }
    }

    public void novaPartida(String nome, Preset preset) {
        partida = new Partida(nome, preset);
        partida.setCenaAtual(historia.montarHistoria(partida));
        alteracoesNaoSalvas = true;
        notificar(juntar("A noite começou. Encontre Manuela.", autosalvar()));
    }

    public List<Escolha> disponiveis() { return escolhas(true); }
    public List<Escolha> bloqueadas() { return escolhas(false); }

    private List<Escolha> escolhas(boolean disponiveis) {
        List<Escolha> lista = new ArrayList<>();
        if (getCenaAtual() != null) {
            for (Escolha escolha : getCenaAtual().getOpcoes()) {
                if (escolha.estaDisponivel(partida) == disponiveis) lista.add(escolha);
            }
        }
        return lista;
    }

    public String escolher(Escolha escolha) {
        if (!disponiveis().contains(escolha) || escolha.getCenaDestino() == null) {
            throw new IllegalArgumentException("Essa escolha não está disponível nesta cena.");
        }
        String capituloAnterior = capitulo();
        String aviso = escolha.aplicar(partida);
        partida.setCenaAtual(escolha.getCenaDestino());
        alteracoesNaoSalvas = true;
        if (cenaFinal()) {
            aviso = juntar(aviso, registrarFinal());
        } else if (!capitulo().equals(capituloAnterior)) {
            aviso = juntar(aviso, autosalvar());
        }
        notificar(aviso);
        return aviso;
    }

    public boolean cenaFinal() {
        return getCenaAtual() != null && getCenaAtual().getOpcoes().isEmpty();
    }

    public void salvar(int slot) throws FalhaAoSalvarException {
        gravar(slot);
        notificar(slot == 0 ? "Jogo salvo automaticamente." : "Jogo salvo no slot " + slot + ".");
    }

    private void gravar(int slot) throws FalhaAoSalvarException {
        if (partida == null) throw new FalhaAoSalvarException("Não há partida para salvar.");
        try {
            Cena cena = getCenaAtual();
            Save save = new Save(partida.getProtagonista().getNome(), LocalDateTime.now(),
                    cena.getId(), cena.getTitulo(), getProgresso(), partida);
            saves.salvar(slot, save);
        } catch (IllegalStateException e) {
            throw new FalhaAoSalvarException("Não foi possível preparar o save.", e);
        }
        alteracoesNaoSalvas = false;
    }

    public void carregar(int slot) throws FalhaAoCarregarException {
        Save save = saves.carregar(slot);
        Partida restaurada = prepararPartida(save);
        partida = restaurada; // Só troca a partida depois de validar tudo.
        alteracoesNaoSalvas = false;
        String mensagem = "Partida carregada: " + getCenaAtual().getTitulo() + ".";
        if (cenaFinal()) mensagem = juntar(mensagem, registrarFinal());
        notificar(mensagem);
    }

    private Partida prepararPartida(Save save) throws FalhaAoCarregarException {
        try {
            Partida restaurada = save.getPartida();
            if (restaurada == null || restaurada.getProtagonista() == null
                    || restaurada.getProtagonista().getNome() == null || restaurada.getInventario() == null) {
                throw new IllegalArgumentException("Dados incompletos");
            }
            restaurada.getNpcs();
            restaurada.getFlags();
            // A história é reconstruída para usar os NPCs da partida restaurada.
            Historia reconstruida = new Historia();
            reconstruida.montarHistoria(restaurada);
            restaurada.setCenaAtual(reconstruida.getCena(save.getIdCena()));
            return restaurada;
        } catch (RuntimeException e) {
            throw new FalhaAoCarregarException("O save contém uma partida ou cena inválida.", e);
        }
    }

    public void continuar() throws FalhaAoCarregarException {
        int slot = maisRecenteValido();
        if (slot == -1) throw new FalhaAoCarregarException("Não há nenhum save válido para continuar.");
        carregar(slot);
    }

    public Save lerSlot(int slot) throws FalhaAoCarregarException { return saves.carregar(slot); }
    public boolean existeSlot(int slot) { return saves.existe(slot); }
    public boolean temSave() { return maisRecenteValido() != -1; }

    private int maisRecenteValido() {
        int escolhido = -1;
        LocalDateTime data = LocalDateTime.MIN;
        for (int slot = 0; slot <= 3; slot++) {
            if (!saves.existe(slot)) continue;
            try {
                Save save = saves.carregar(slot);
                prepararPartida(save);
                if (save.getDataHora().isAfter(data)) {
                    data = save.getDataHora();
                    escolhido = slot;
                }
            } catch (FalhaAoCarregarException e) {
                // Um slot ruim não impede continuar pelos outros.
            }
        }
        return escolhido;
    }

    public void excluir(int slot) throws FalhaAoSalvarException {
        if (!saves.excluir(slot)) throw new FalhaAoSalvarException("Não foi possível excluir esse save.");
        notificar("Save excluído.");
    }

    public void alterarPreferencias(Preset preset, boolean pularEnter, boolean autosave)
            throws FalhaAoSalvarException {
        Preferencias novas = new Preferencias();
        novas.setPresetPadrao(preset == null ? Preset.COMUM : preset);
        novas.setPularEnter(pularEnter);
        novas.setAutosaveLigado(autosave);
        ajustes.salvar(novas);
        preferencias = novas;
        notificar("Preferências salvas.");
    }

    private String autosalvar() {
        if (!preferencias.isAutosaveLigado()) return null;
        try {
            gravar(GerenciadorDeSaves.AUTOSAVE);
            return "Jogo salvo automaticamente.";
        } catch (FalhaAoSalvarException e) {
            return "Atenção: " + e.getMessage();
        }
    }

    private String registrarFinal() {
        String id = getCenaAtual().getId();
        if (registro.jaViu(id)) return null;
        // Grava primeiro: se falhar, uma tentativa posterior ainda pode registrar.
        RegistroDeFinais atualizado = new RegistroDeFinais();
        for (String finalId : RegistroDeFinais.IDS) {
            if (registro.jaViu(finalId)) atualizado.registrar(finalId);
        }
        if (!atualizado.registrar(id)) return null;
        try {
            finais.salvar(atualizado);
            registro = atualizado;
            return "Novo final desbloqueado na galeria!";
        } catch (FalhaAoSalvarException e) {
            return "Atenção: " + e.getMessage();
        }
    }

    private String capitulo() {
        String id = getCenaAtual().getId();
        return id.startsWith("CAP") ? id.substring(0, 5) : id;
    }

    private String juntar(String primeira, String segunda) {
        if (primeira == null || primeira.isBlank()) return segunda;
        if (segunda == null || segunda.isBlank()) return primeira;
        return primeira + "\n" + segunda;
    }

    public void encerrarPartida() {
        partida = null;
        alteracoesNaoSalvas = false;
        notificar("");
    }

    public boolean temAlteracoesNaoSalvas() { return partida != null && !cenaFinal() && alteracoesNaoSalvas; }
    public Cena getCenaAtual() { return partida == null ? null : partida.getCenaAtual(); }
    public Partida getPartida() { return partida; }
    public Preferencias getPreferencias() { return preferencias; }
    public RegistroDeFinais getRegistroDeFinais() { return registro; }
    public String getUltimaMensagem() { return ultimaMensagem; }
    public int getProgresso() { return cenaFinal() ? 100 : Progresso.calcular(getCenaAtual() == null ? null : getCenaAtual().getId()); }

    public String getObjetivos() {
        if (partida == null) return "Nenhuma partida em andamento.";
        String id = getCenaAtual().getId();
        String principal;
        if (id.equals("FIM_A_TEMPO")) principal = "Concluído: você trouxe Manuela de volta.";
        else if (cenaFinal()) principal = "Encerrado: Manuela não voltou para casa com você.";
        else if (id.startsWith("CAP10")) principal = "Convença Manuela a voltar antes do amanhecer.";
        else if (id.startsWith("CAP09")) principal = "Encontre uma entrada para o cemitério.";
        else principal = "Encontre Manuela. Siga as pistas até o cemitério.";
        String davi = partida.temFlag(Flag.SALVOU_DAVI) ? "Concluído: ajudar Davi."
                : getProgresso() >= 20 ? "Não concluído: ajudar Davi." : "Opcional: ajudar Davi na rua.";
        return "OBJETIVO PRINCIPAL\n" + principal + "\n\nOBJETIVO OPCIONAL\n" + davi;
    }
}
