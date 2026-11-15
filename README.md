# A Noite Longa — Fase 3

Jogo narrativo em JavaFX, com MVC, dez capítulos e onze finais.
EXA863 MI Programação, UEFS, 2026.2.
Bruno Gabriel Santos Pinto e Caio Cerqueira Francisco Batista.

## Executar

Instale um **JDK 21** e configure `JAVA_HOME` para a pasta desse JDK.
O Maven Wrapper baixa o Maven e as dependências JavaFX na primeira execução;
nessa primeira vez é necessário acesso à internet. O JDK não vem no projeto.

Abra o terminal na pasta `PBL_fase_2`, onde está o `pom.xml`:

```powershell
# Windows
.\mvnw.cmd javafx:run
```

```sh
# Linux / macOS
sh mvnw javafx:run
```

No IntelliJ, abra o `pom.xml` como projeto Maven, selecione o JDK 21 e execute
`MainFX` ou a tarefa Maven `javafx:run`. A versão de terminal usa `Main`.

## Testar e compilar

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

Há 117 testes em 13 classes. Os testes usam pastas temporárias e não alteram os
saves do jogador. Incluem os onze finais, restauração do estado, preferências,
falhas de autosave, condições de escolhas e os três padrões GoF.

`package` gera `target/a-noite-longa-3.0.jar`. Esse JAR contém o jogo e seus
recursos; as dependências JavaFX são fornecidas pelo Maven ao usar `javafx:run`.
Ele não é um executável independente com JavaFX embutido.

## Como jogar

- Informe um nome e escolha um dos quatro presets.
- Clique em **Continuar** ou pressione ENTER para avançar a leitura.
- Clique em uma alternativa. As bloqueadas mostram o motivo logo abaixo.
- Use **Status** para consultar atributos, inventário, confiança, objetivos e decisões.
- Em **Menu**, salve, carregue, configure, reinicie ou volte à tela inicial.
- Abrir um painel e voltar mantém o trecho que estava sendo lido.
- **Continuar**, no menu inicial, abre o save válido mais recente.
- A leitura rápida mostra todo o texto da cena de uma vez, com rolagem.

O autosave ocorre ao começar a partida e ao entrar em outro capítulo. Os três
slots manuais podem ser usados entre as escolhas. Sair de uma partida com
mudanças posteriores ao último save pede confirmação. Reiniciar, excluir e
sobrescrever também pedem confirmação; cancelar preserva os dados.

Os finais alcançados são registrados automaticamente na galeria. Uma falha de
gravação é informada sem interromper a partida. Saves corrompidos podem ser
excluídos ou sobrescritos e são ignorados pelo botão Continuar.

## Os três padrões GoF

### Memento

- **Originator:** `Partida`.
- **Memento:** `Partida.Memento`, com o estado serializado e encapsulado.
- **Caretaker:** `Save` e `GerenciadorDeSaves`, coordenados por `SessaoDeJogo`.

`Partida.criarMemento()` captura uma cópia independente. `Partida.restaurar()`
reconstrói essa cópia. Continuar jogando não altera um snapshot já criado.
A cena é transient: seu ID fica nos metadados do Save, e a história é remontada
ao carregar para que as escolhas apontem para os NPCs restaurados.
Isso resolve salvar e recuperar atributos, confiança, itens e decisões juntos.

### Observer

- **Subject:** `SessaoDeJogo`, que mantém a lista de observadores.
- **Observer:** `ObservadorDeJogo`.
- **Concrete Observer:** `JogoViewController`.

A sessão notifica mudanças após escolhas, salvamentos e carregamentos. A tela
atualiza atributos, progresso e avisos sem que a sessão conheça JavaFX.
O observador é removido ao sair da tela e registrado novamente ao voltar.

### Factory Method

- **Creator:** `FabricaDePersonagem`, com o método abstrato `criar`.
- **Concrete Creators:** `FabricaDeProtagonista` e `FabricaDeNpc`.
- **Product:** `PersonagemBase`.
- **Concrete Products:** `Protagonista` e `Npc`.

Cada fábrica sobrescreve o método e cria um tipo de personagem. A fábrica do
protagonista aplica o preset e o nome padrão; a do NPC aplica a confiança
inicial. `Partida` usa essas fábricas para montar o elenco. `Historia` continua
responsável por montar cenas e escolhas; não é apresentada como um GoF só por
estar no pacote factory.

## Organização MVC

```text
MainFX -> TelaMenuFX -> controllers FXML -> SessaoDeJogo -> Model
Main   -> JogoController (terminal) -----> SessaoDeJogo -> Model
                                          |
                                          +-> ObservadorDeJogo -> HUD
```

- `model/entidades`: estado, Memento, cenas, diálogos e regras das escolhas.
- `model/factory`: criação dos personagens e montagem da história.
- `model/persistencia`: arquivos, slots, preferências e registro de finais.
- `controller`: coordenação da sessão e adaptador do terminal.
- `view`: apresentação JavaFX e terminal, sem regras de persistência no FXML.
- `src/main/resources/view`: sete telas FXML, CSS e cenários.
- `src/test/java/test`: testes JUnit 4.

## Arquivos do jogador

A pasta `saves`, relativa ao diretório de execução, guarda `slot1.dat`,
`slot2.dat`, `slot3.dat`, `autosave.dat`, `preferencias.dat` e `finais.dat`.
O formato anterior de Save continua sendo lido. A gravação usa um arquivo
temporário antes de substituir o destino.

Para testes visuais isolados, é possível definir a propriedade Java
`-Dnoite.saves=target/ui-saves`. Essa opção não é necessária para jogar.

Os cenários são de Spiral Atlas, "Contemporary Visual Novel Backgrounds".
Os personagens são representados por silhuetas vetoriais simples em JavaFX,
identificadas pelo nome de quem está falando. Nenhuma engine de jogo é usada.
