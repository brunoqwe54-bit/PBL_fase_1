package test;

import model.entidades.Escolha;
import model.entidades.Npc;
import model.entidades.Partida;
import model.enums.Atributo;
import model.enums.Flag;
import model.enums.Item;
import model.enums.Preset;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Testa as condições, os efeitos e a configuração das escolhas. A cena de destino não é
 * necessária nesses testes.
 */
public class EscolhaTest {

    private Partida partida;

    @Before
    public void criarPartida() {
        partida = new Partida("Vicente", Preset.COMUM);
    }


    // Escolha sem nenhuma condição: tem que aparecer sempre.
    @Test
    public void escolhaSemCondicaoEstaSempreDisponivel() {
        Escolha escolha = new Escolha("E1", "texto", null);

        assertTrue(escolha.estaDisponivel(partida));
    }

    // Condição de atributo mínimo.

    @Test
    public void atributoAbaixoDoMinimoBloqueiaAEscolha() {
        // O preset COMUM começa com coragem 50. A escolha exige 55.
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeAtributo(Atributo.CORAGEM, 55, "sua voz não sai");

        assertFalse(escolha.estaDisponivel(partida));
    }

    @Test
    public void atributoIgualAoMinimoLiberaAEscolha() {
        // O valor mínimo também deve liberar a escolha.
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeAtributo(Atributo.CORAGEM, 50, "sua voz não sai");

        assertTrue(escolha.estaDisponivel(partida));
    }

    // Condição de item exigido: precisa estar na mochila.

    @Test
    public void itemExigidoAusenteBloqueiaAEscolha() {
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeItem(Item.CHAVE, "falta a chave");

        assertFalse(escolha.estaDisponivel(partida));
    }

    @Test
    public void itemExigidoPresenteLiberaAEscolha() {
        partida.getInventario().adicionar(Item.CHAVE);
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeItem(Item.CHAVE, "falta a chave");

        assertTrue(escolha.estaDisponivel(partida));
    }

    // Condição de confiança mínima de um NPC.

    @Test
    public void confiancaAbaixoDoMinimoBloqueiaAEscolha() {
        // O homem de terno começa com 0 de confiança.
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeConfianca(partida.getHomemDeTerno(), 10, "ele não confia em você");

        assertFalse(escolha.estaDisponivel(partida));
    }

    @Test
    public void confiancaSuficienteLiberaAEscolha() {
        // Otávio começa com confiança 50, que já atende ao mínimo exigido.
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeConfianca(partida.getOtavio(), 50, "ele não confia em você");

        assertTrue(escolha.estaDisponivel(partida));
    }

    // Condição de flag exigida: só aparece se a decisão já tiver acontecido.

    @Test
    public void flagExigidaAusenteBloqueiaAEscolha() {
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeFlag(Flag.PORTA_ABERTA, "a porta está trancada");

        assertFalse(escolha.estaDisponivel(partida));
    }

    @Test
    public void flagExigidaPresenteLiberaAEscolha() {
        partida.ligarFlag(Flag.PORTA_ABERTA);
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeFlag(Flag.PORTA_ABERTA, "a porta está trancada");

        assertTrue(escolha.estaDisponivel(partida));
    }

    // A decisão registrada deve bloquear esta escolha.


    @Test
    public void flagProibidaPresenteBloqueiaAEscolha() {
        partida.ligarFlag(Flag.OUVIU_RADIO);
        Escolha escolha = new Escolha("E1", "texto", null)
                .proibeFlag(Flag.OUVIU_RADIO, "você já sabe demais");

        assertFalse(escolha.estaDisponivel(partida));
    }

    @Test
    public void flagProibidaAusenteLiberaAEscolha() {
        Escolha escolha = new Escolha("E1", "texto", null)
                .proibeFlag(Flag.OUVIU_RADIO, "você já sabe demais");

        assertTrue(escolha.estaDisponivel(partida));
    }

    // O motivo mostrado ao jogador quando a escolha aparece bloqueada.

    @Test
    public void motivoDoBloqueioEOTextoConfigurado() {
        Escolha escolha = new Escolha("E1", "texto", null)
                .exigeItem(Item.CHAVE, "falta a chave");

        assertEquals("falta a chave", escolha.getMotivoDoBloqueio());
    }

    // Efeitos aplicados quando a escolha é escolhida.

    @Test
    public void aplicarAlteraAConfiancaDoNpc() {
        Npc otavio = partida.getOtavio();
        Escolha escolha = new Escolha("E1", "texto", null)
                .comConfianca(otavio, -10);

        escolha.aplicar(partida);

        assertEquals(40, otavio.getConfianca());
    }

    @Test
    public void aplicarAlteraOAtributoDoProtagonista() {
        Escolha escolha = new Escolha("E1", "texto", null)
                .comAtributo(Atributo.CORAGEM, 10);

        escolha.aplicar(partida);

        assertEquals(60, partida.getProtagonista().getAtributo(Atributo.CORAGEM));
    }

    @Test
    public void aplicarDaOItemGanho() {
        Escolha escolha = new Escolha("E1", "texto", null)
                .ganhaItem(Item.CANTIL);

        escolha.aplicar(partida);

        assertTrue(partida.getInventario().possui(Item.CANTIL));
    }

    @Test
    public void aplicarTiraOItemPerdido() {
        partida.getInventario().adicionar(Item.VELA);
        Escolha escolha = new Escolha("E1", "texto", null)
                .perdeItem(Item.VELA);

        escolha.aplicar(partida);

        assertFalse(partida.getInventario().possui(Item.VELA));
    }

    @Test
    public void aplicarLigaAFlag() {
        Escolha escolha = new Escolha("E1", "texto", null)
                .ligaFlag(Flag.SALVOU_DAVI);

        escolha.aplicar(partida);

        assertTrue(partida.temFlag(Flag.SALVOU_DAVI));
    }

    @Test
    public void aplicarSemNenhumEfeitoDevolveNull() {
        Escolha escolha = new Escolha("E1", "texto", null);

        assertNull(escolha.aplicar(partida));
    }

    @Test
    public void aplicarComVariacaoZeroNaoContaComoEfeito() {
        // Uma variação zero não deve alterar a confiança nem gerar aviso.
        Npc otavio = partida.getOtavio();
        Escolha escolha = new Escolha("E1", "texto", null)
                .comConfianca(otavio, 0);

        String aviso = escolha.aplicar(partida);

        assertNull(aviso);
        assertEquals(50, otavio.getConfianca());
    }

    // Verifica vários efeitos na mesma escolha.


    @Test
    public void aplicarDuasConfiancasAlteraOsDoisNpcs() {
        // Caso real da Historia (ESC0306).
        Npc zulmira = partida.getZulmira();
        Npc manuela = partida.getManuela();

        Escolha escolha = new Escolha("E1", "texto", null)
                .comConfianca(zulmira, 10)
                .comConfianca(manuela, 5);

        escolha.aplicar(partida);

        assertEquals(50, zulmira.getConfianca());  // 40 + 10
        assertEquals(35, manuela.getConfianca());  // 30 + 5
    }

    @Test
    public void aplicarItemEDoisAtributosJuntos() {
        // Beber o cantil consome o item e recupera fôlego e lucidez.

        partida.getInventario().adicionar(Item.CANTIL);

        Escolha escolha = new Escolha("E1", "texto", null)
                .perdeItem(Item.CANTIL)
                .comAtributo(Atributo.FOLEGO, 20)
                .comAtributo(Atributo.LUCIDEZ, 5);

        escolha.aplicar(partida);

        assertFalse(partida.getInventario().possui(Item.CANTIL));
        assertEquals(70, partida.getProtagonista().getAtributo(Atributo.FOLEGO));
        assertEquals(55, partida.getProtagonista().getAtributo(Atributo.LUCIDEZ));
    }

    // Encadeamento: cada método de configuração devolve a própria escolha.

    @Test
    public void metodosDeConfiguracaoDevolvemAPropriaEscolha() {
        Escolha escolha = new Escolha("E1", "texto", null);

        // assertSame verifica se a referência aponta para o mesmo objeto.
        assertSame(escolha, escolha.comAtributo(Atributo.LUCIDEZ, 5));
    }
}
