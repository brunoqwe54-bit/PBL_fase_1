package model.enums;

/**
 * Os nomes dos personagens da história, em um lugar só.
 *
 * Serve para a classe Partida criar os NPCs sem nome escrito à mão em
 * cada linha. Assim, corrigir um nome é mudar um ponto só, e um erro de
 * digitação vira erro de compilação em vez de aparecer na tela do
 * jogador.
 */
public enum Personagens {
    VICENTE("Vicente"),
    OTAVIO("Otávio"),
    MANUELA("Manuela"),
    MAE("Mãe"),
    DAVI("Davi"),
    ZULMIRA("Dona Zulmira"),
    ANTONIO("Seu Antônio"),
    HOMEM_DE_TERNO("Homem de Terno"),
    ROSANGELA("Rosângela"),
    NILTON("Seu Nilton"),
    NARRADOR("Narrador");


    private String nomeExibicao;


    Personagens(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }


    public String getNomeExibicao() {
        return nomeExibicao;
    }
}
