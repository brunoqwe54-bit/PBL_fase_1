# A Noite Longa

Jogo narrativo interativo em Java (MVC), EXA863 MI Programação, UEFS, 2026.2.
Bruno Gabriel Santos Pinto e Caio Cerqueira Francisco Batista.

## Requisitos

- JDK 21
- Não precisa instalar o Maven nem o JavaFX: o Maven Wrapper (`mvnw`) baixa
  os dois na primeira execução.

## Rodar o jogo (janela JavaFX)

```
mvnw.cmd clean javafx:run      (Windows)
./mvnw clean javafx:run        (Linux/macOS)
```

## Rodar os testes de unidade

```
mvnw.cmd test
```

## Pelo IntelliJ

File > Open, selecione o `pom.xml` e escolha "Open as Project".
Para jogar, rode a classe `MainFX` (ou, na aba Maven, Plugins > javafx > javafx:run).
A versão antiga em terminal continua na classe `Main`.

## Estrutura

```
src/main/java   código do jogo (model, view, controller)
src/test/java   testes de unidade (JUnit 4)
```
