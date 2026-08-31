# A NOITE LONGA
## Documento de design narrativo — PBL Fase 1
### EXA863 — MI Programação — UEFS

> Este documento contém a história completa: proposta, sistemas, personagens, itens,
> os 10 capítulos com todos os diálogos e escolhas, as consequências de cada uma,
> os finais e os textos de menu. Está escrito para ser transcrito diretamente para
> estruturas de dados em Java — todos os identificadores estão em MAIÚSCULAS e são únicos.

---

# PARTE I — A PROPOSTA

## 1. Título, gênero e ambientação

**Título:** A Noite Longa

**Gênero:** Drama sobrenatural de sobrevivência com investigação leve. Aventura narrativa
baseada em escolhas, com estrutura de visual novel em terminal.

**Ambientação:** Riacho do Fogo, cidade fictícia do interior da Bahia, cerca de quatro mil
habitantes. Tempo presente. Toda a história acontece em uma única noite — a madrugada de
23 de junho para 24 de junho, entre 23h e o nascer do sol.

Uma vez por ano, na véspera de São João, a cidade inteira se tranca. As casas ficam com as
luzes apagadas e as janelas fechadas. Ninguém sai. Ninguém explica direito por quê — é uma
coisa que se aprende sem perguntar, como não pisar em cova.

Nessa noite, a **Procissão** passa pelas ruas. Uma fila de gente que caminha devagar, cada
um segurando uma vela acesa, cantando baixo. Não corre atrás de ninguém. Não força porta.
Só passa. Mas quem estiver na rua quando ela passar, e não souber o que fazer, entra na fila.
E quem entra na fila não sai mais.

Ao amanhecer, a Procissão se desfaz. A cidade abre as janelas e ninguém comenta.

## 2. Protagonista e personagens secundários

**Protagonista: Vicente** (nome padrão — o jogador pode informar outro)

Vinte e dois anos. Nasceu numa Noite Longa, o que na cidade é considerado azar e sorte ao
mesmo tempo: azar por ter nascido na noite errada, sorte porque *a noite reconhece os seus*.
Quem nasce nela não é levado de primeira. É devolvido. Sete minutos antes.

Vicente nunca acreditou nisso. Vai acreditar hoje.

Ele trabalha na oficina do tio, mora com o irmão mais velho e a irmã caçula. É o irmão do
meio: nunca foi o responsável, nunca foi o cuidado. Hoje precisa ser os dois.

**Os cinco personagens secundários com participação significativa:**

| ID | Nome | Papel | Aparece nos capítulos |
|---|---|---|---|
| `OTAVIO` | Otávio | Irmão mais velho. Trancou a porta com Vicente do lado de fora — ou do lado de dentro, dependendo de como se conta. | 1, 10 |
| `DAVI` | Davi | Menino de 11 anos, vizinho, que saiu de casa atrás do cachorro e não sabe voltar. | 2, 5, 7, 9 |
| `ANTONIO` | Seu Antônio | 70 anos, operador da Rádio Serra. Fica no ar a noite inteira transmitindo avisos para quem estiver na rua. Nunca sai do estúdio. | 4, 6, 9 |
| `ZULMIRA` | Dona Zulmira | Atravessa a Noite Longa todos os anos, há trinta e um. Conhece as regras. Não explica por que faz isso. | 3, 6, 8, 10 |
| `MANUELA` | Manuela | Irmã caçula, 17 anos. Saiu de casa às 22h40 e não voltou. É o objetivo. | 10 (e mencionada em todos) |

## 3. Conflito central e objetivo do jogador

**Conflito:** Manuela saiu de casa na Noite Longa. Otávio trancou a porta e disse que quem
sai não volta — regra da casa, regra da cidade. Vicente saiu mesmo assim.

**Objetivo:** atravessar Riacho do Fogo de ponta a ponta, do bairro alto até a capela do
cemitério, e alcançar Manuela antes do nascer do sol. São dez trechos. A Procissão está
na rua. E a cada erro, a noite cobra uma das devoluções de Vicente.

**Objetivo secundário implícito:** decidir quem mais vale ser salvo no caminho — e descobrir
que salvar alguém custa tempo, e tempo é a única coisa que não volta.

## 4. Mecânicas

### 4.1 Atributos do protagonista (requisito: mínimo 3, variáveis)

Todos vão de **0 a 10**. Começam distribuídos pelo jogador na criação do personagemBase.

| ID | Atributo | O que representa | Sobe quando | Desce quando |
|---|---|---|---|---|
| `FOLEGO` | Fôlego | Resistência física: correr, carregar, aguentar frio | Descansa, come, é ajudado | Corre, carrega alguém, se fere |
| `NERVO` | Nervo | Coragem: encarar o que se vê sem congelar | Enfrenta e sobrevive, é acolhido | Vê algo demais, é abandonado, morre |
| `LUCIDEZ` | Lucidez | Clareza: perceber detalhes, distinguir o real do que a noite sussurra | Segue as regras, ouve o rádio, bebe água corrente | Olha para trás, ouve o próprio nome, usa vela |

**Regra geral:** atributo em **0** não mata sozinho, mas fecha escolhas e abre mortes.
Nenhum atributo passa de 10.

### 4.2 Voltas (recurso gerenciável principal)

Vicente nasceu na Noite Longa. Quando morre, acorda **sete minutos antes**, no começo da
mesma cena. O mundo volta ao que era. Ele é a única coisa que continua lembrando.

- Começa com **5 voltas**.
- Cada morte consome **1 volta**.
- Morrer com **0 voltas** = fim de jogo (`GAME_OVER`).
- O jogador **não vê o número** no começo. A partir da 2ª volta gasta, o jogo passa a exibir
  uma sensação, não um número: *"restam poucas"*. O número exato só aparece na última.
- **Custo social:** quando Vicente volta, os outros **não lembram**. Toda confiança ganha
  naquela cena é perdida junto. Voltar é barato em vida e caro em gente.

### 4.3 Confiança (requisito: relacionamento com pelo menos 3 personagens)

Valor de **0 a 10** que cada NPC mantém em relação a Vicente. Começa em **3** para todos,
exceto `OTAVIO` e `MANUELA`, que começam em **5**.

| Faixa | Nome da faixa | Comportamento |
|---|---|---|
| 0–2 | Hostil | Nega ajuda, esconde informação, some da cena |
| 3–5 | Reservado | Ajuda no mínimo, não se arrisca |
| 6–8 | Aliado | Dá item, dá aviso, abre caminho |
| 9–10 | Devoto | Se arrisca por Vicente, inclusive morre por ele |

`DAVI`, `ANTONIO` e `ZULMIRA` são os três com vínculo obrigatório e vivo ao longo do jogo.
`OTAVIO` e `MANUELA` também têm valor, com menos oportunidades de variação.

#### 4.3.1 Efeitos concretos de cada faixa

Cada faixa precisa ter efeito mecânico em algum lugar do jogo, senão a tabela acima é
decoração. Estes são os efeitos implementados:

| NPC | Hostil (0–2) | Aliado (6–8) | Devoto (9–10) |
|---|---|---|---|
| `DAVI` | Recusa acompanhar em `CAP05`; o bloco dos fundos em `CAP07` não acontece | Sobe nos ombros e pega a `MEDALHA` (`ESC0706`) | Sacrifica-se em `CAP09` e abre caminho |
| `ANTONIO` | Não abre a porta da rádio em `CAP04` | Entrega o `RADIO`; conta sobre o filho | Fica no ar até o amanhecer (texto extra em `FIM_A_TEMPO`) |
| `ZULMIRA` | Não está no muro em `CAP06` nem no meio-fio em `CAP08` | Entrega a `CHAVE` (`ESC0806`) | Espera na capela em `CAP10` |
| `OTAVIO` | `ESC0102` bloqueada — não existe porta destrancada | Deixa destrancada | — |
| `MANUELA` | — | +1 no cálculo de desfecho | +2 no cálculo de desfecho |

#### 4.3.2 Confiança e as voltas no tempo

Quando Vicente morre e volta sete minutos, os NPCs **não lembram** do que aconteceu naquela
cena. Mecanicamente: a `Partida` guarda um retrato dos valores de confiança no **início de
cada capítulo**, e a rotina de morte restaura esse retrato junto com o resto do estado.

Consequência prática: confiança conquistada dentro do capítulo em que você morreu é perdida;
a de capítulos anteriores permanece. É o custo social do poder, e precisa aparecer no
diagrama de classes — é a única parte do sistema que exige guardar estado duplicado.

### 4.4 Inventário (recurso gerenciável secundário)

Lista simples de itens. Sem limite de peso. Alguns itens são **consumidos** ao usar.

| ID do item | Nome | Origem | Efeito |
|---|---|---|---|
| `LANTERNA` | Lanterna de mão | Escolha inicial | Abre escolhas em lugares escuros (CAP06, CAP07) |
| `FACA` | Faca de cozinha | Escolha inicial | Abre escolhas de coragem; +1 `NERVO` ao equipar |
| `TERCO` | Terço da mãe | Escolha inicial | Protege contra ouvir o próprio nome (CAP02, CAP09) |
| `RADIO` | Rádio de pilha | `ANTONIO`, CAP04 | Avisa antes de duas mortes (CAP05, CAP08) |
| `CANTIL` | Cantil com água do riacho | CAP05 | Consumível: +2 `LUCIDEZ` uma única vez |
| `MEDALHA` | Medalha de São Jorge | CAP07 (item raro) | A Procissão não olha para quem a carrega — sobrevive a CAP09 sem checagem |
| `VELA` | Vela apagada | CAP08 (armadilha) | **Item perigoso.** Se for acesa, entra na Procissão |
| `CHAVE` | Chave da capela | `ZULMIRA`, CAP08, se confiança ≥ 6 | Única forma de entrar na capela sem quebrar nada em CAP10 |
| `CASACO` | Casaco de Manuela | CAP09 | Prova de que ela passou ali; +1 `NERVO`, abre diálogo final |

### 4.5 As cinco regras da noite

São o conhecimento que o jogo ensina aos poucos. `ZULMIRA` conhece todas. `ANTONIO` transmite
algumas pelo rádio. O jogador aprende morrendo, se não prestar atenção.

1. **Ouviu seu nome? Não olhe para trás.**
2. **A Procissão passando? Ajoelhe e baixe os olhos. Não atravesse, não corra.**
3. **Não aceite vela acesa de ninguém.** Nem apagada, se puder evitar.
4. **Água corrente não é atravessada por eles.** Riacho é abrigo.
5. **Não diga o nome de quem você procura em voz alta na rua.** Eles anotam.

### 4.6 Condições de acesso (requisito)

Escolhas e cenas são bloqueadas ou liberadas por quatro tipos de condição, que aparecem no
documento sempre no mesmo formato:

- `ATRIBUTO >= n` — ex.: `NERVO >= 5`
- `ITEM(X)` — ex.: `ITEM(LANTERNA)`
- `CONFIANCA(NPC) >= n` — ex.: `CONFIANCA(DAVI) >= 6`
- `FLAG(X)` — decisão anterior registrada, ex.: `FLAG(SALVOU_DAVI)`

### 4.7 Flags de decisão (consequências posteriores)

| Flag | Definida em | Consequência posterior |
|---|---|---|
| `FLAG_PORTA_ABERTA` | CAP01 | Otávio deixou a porta destrancada — habilita o final `DESISTE` |
| `FLAG_SALVOU_DAVI` | CAP02 | Davi acompanha; abre CAP07 por dentro; morre por Vicente em CAP09 se confiança ≥ 9 |
| `FLAG_ABANDONOU_DAVI` | CAP02 | Davi aparece na Procissão em CAP09 — cena de culpa |
| `FLAG_AJOELHOU` | CAP03 | Zulmira testemunha; +2 confiança na primeira conversa |
| `FLAG_GRITOU_NOME` | CAP03 | Quebrou a regra 5: a Procissão procura Manuela mais rápido; final `CHEGA_TARDE` fica mais provável |
| `FLAG_OUVIU_RADIO` | CAP04 | Conhece as regras 1 e 4 sem precisar morrer |
| `FLAG_AVISOU_ANTONIO` | CAP04 (`ESC0407`) | Antônio não abre a janela dos fundos; continua no ar em CAP06 e CAP08 |
| `FLAG_PEGOU_MEDALHA` | CAP07 | Item raro no inventário |
| `FLAG_ACENDEU_VELA` | CAP08 | Morte garantida em CAP09 — a única morte que não pode ser evitada com atributo |
| `FLAG_PROMETEU_VOLTAR` | CAP08 | Zulmira espera na capela em CAP10 |

## 5. Estrutura

Dez capítulos, um por trecho da travessia, em tronco único: todo jogador que sobrevive passa
pelos dez, na mesma ordem. A ramificação está **no estado que se carrega**, não em capítulos
exclusivos. Cada capítulo tem pelo menos uma morte possível.

| # | ID | Capítulo | Horário | Morte possível |
|---|---|---|---|---|
| 1 | `CAP01` | A Porta | 23h10 | Não |
| 2 | `CAP02` | A Rua de Casa | 23h40 | Sim |
| 3 | `CAP03` | A Praça | 00h15 | Sim |
| 4 | `CAP04` | A Rádio | 00h50 | Sim |
| 5 | `CAP05` | A Ponte do Riacho | 01h20 | Sim |
| 6 | `CAP06` | O Hospital Velho | 02h00 | Sim |
| 7 | `CAP07` | O Casarão dos Peixoto | 02h40 | Sim |
| 8 | `CAP08` | A Rua das Velas | 03h20 | Sim |
| 9 | `CAP09` | O Cemitério | 04h10 | Sim |
| 10 | `CAP10` | O Amanhecer | 04h50 | Sim |

## 6. Rotas narrativas

Três rotas atravessam os mesmos dez capítulos. A rota não é escolhida num menu — ela se
revela pelo estado acumulado, e é avaliada em `CAP10`.

**`ROTA_BANDO`** — Vicente salva e carrega gente. Confiança alta com pelo menos dois NPCs.
Mais lento, mais aliados no fim. Condição aproximada: soma de confiança de `DAVI`, `ANTONIO`
e `ZULMIRA` ≥ 20.

**`ROTA_REGRA`** — Vicente obedece a noite. Segue as cinco regras, não improvisa. Lucidez alta,
poucas voltas gastas. Condição: `LUCIDEZ >= 7` e no máximo 2 voltas gastas.

**`ROTA_SOZINHO`** — Vicente vai reto, abandona quem atrasa, quebra regra quando convém.
Rápido e caro. É a rota padrão quando nenhuma das outras se qualifica.

## 7. Tipos de finais

| ID | Nome | Condição de acesso | Tom |
|---|---|---|---|
| `FIM_A_TEMPO` | Chega a tempo | Sobrevive a CAP10 e tira Manuela da fila | Vitória com preço |
| `FIM_TARDE` | Chega tarde | Sobrevive a CAP10 mas falha o resgate | Derrota amarga |
| `FIM_DESISTE` | Volta para casa | Escolhe voltar em CAP05 ou CAP08, com `FLAG_PORTA_ABERTA` | Sobrevivência com culpa |
| `GAME_OVER` | Fim de jogo | Morre com 0 voltas, em qualquer capítulo | Interrupção |

**Distinção importante para o relatório:** `GAME_OVER` **não** é um dos três finais exigidos
pelo enunciado. Ele é um encerramento prematuro. Os três finais são `FIM_A_TEMPO`,
`FIM_TARDE` e `FIM_DESISTE`.

## 8. Estilo visual e sonoro

**Visual (terminal):**
- Largura fixa de 72 colunas para todo o texto. Nada ultrapassa isso.
- Moldura de capítulo em caracteres simples:
  `============================================================`
- Falas no formato `NOME: texto`, com o nome sempre em maiúsculas.
- Narração em texto corrido, sem prefixo.
- Pensamento de Vicente entre parênteses e em linha isolada.
- Escolhas numeradas `[1]`, `[2]`, `[3]`, sempre no fim do bloco.
- Escolha bloqueada aparece como `[—] (texto da opção) — bloqueado`. Mostrar o bloqueio é
  proposital: o jogador precisa saber que havia outro caminho.
- Cores ANSI, se disponíveis: cinza para memória e narração distante, branco para o presente,
  vermelho apenas nas mortes. Nunca mais de três cores na tela.
- O painel de status aparece só quando pedido, nunca automático — o jogo não deve parecer
  planilha.

**Sonoro:**
- Sem áudio real. O som é descrito e é um recurso narrativo: a Procissão é sempre anunciada
  pelo canto antes de ser vista.
- O `beep` do terminal (`\007`) é usado **uma única vez no jogo inteiro**, no momento da
  primeira morte. Depois disso, nunca mais.
- Regra de escrita: toda cena de perigo começa por um som, não por uma imagem.

## 9. Público-alvo

Jovens e adultos a partir de 16 anos, com familiaridade com visual novels, jogos de escolha
e horror folclórico brasileiro. Leitores — o jogo é texto puro e pede atenção.

A classificação se justifica pelo tema (morte, luto, culpa) e pelo tom de horror psicológico.
Não há violência gráfica, conteúdo sexual, nem linguagem ofensiva. As mortes são descritas
pelo efeito, não pelo detalhe.

---

# PARTE II — TEXTOS DE SISTEMA

## Menu inicial

```
============================================================
                    A  N O I T E  L O N G A
              Riacho do Fogo - 23 de junho, 23h
============================================================

  [1] Nova partida
  [2] Instrucoes
  [3] Creditos
  [4] Sair

  Escolha:
```

## Instruções

```
COMO SE JOGA

Voce le, voce escolhe, voce vive com isso.

A cada momento da historia o jogo apresenta alternativas
numeradas. Digite o numero e pressione ENTER.

Vicente tem tres atributos: FOLEGO, NERVO e LUCIDEZ. Eles
sobem e descem conforme o que voce faz, e algumas escolhas
so aparecem se o atributo for alto o bastante.

Vicente carrega itens. Alguns abrem caminhos. Um deles nao
deveria ser carregado.

As pessoas que voce encontra confiam mais ou menos em voce.
Quem confia, ajuda. Quem nao confia, fecha a porta.

Sobre morrer: Vicente nasceu nesta noite, e a noite nao leva
os seus de primeira. Ele volta sete minutos. Nao e infinito.

Quando acabar, acaba.

ENTER para voltar
```

## Créditos

```
A NOITE LONGA

Projeto academico - EXA863 MI Programacao
Universidade Estadual de Feira de Santana

Desenvolvimento: [nome da dupla]
Fase 1 - 2026.2

Jogo narrativo interativo em Java, arquitetura MVC.
Nenhuma engine de jogo foi utilizada.

Riacho do Fogo e uma cidade que nao existe.
A Procissao e uma historia que se conta no interior
da Bahia de varias maneiras diferentes. Esta e uma delas.

ENTER para voltar
```

## Criação do protagonista

```
============================================================
                   ANTES DE COMECAR
============================================================

Como voce se chama?
(ENTER para usar "Vicente")
> _
```

Em seguida, distribuição de atributos:

```
Voce tem 15 pontos para dividir entre tres coisas.
Nenhuma pode ficar abaixo de 2 nem acima de 8.

  FOLEGO  - o quanto voce aguenta correr e carregar
  NERVO   - o quanto voce encara sem congelar
  LUCIDEZ - o quanto voce percebe antes de ser tarde

FOLEGO  (2-8): _
NERVO   (2-8): _
LUCIDEZ (2-8): _
```

Validação: soma exata de 15, cada valor entre 2 e 8. Se não fechar, repete a pergunta.

Em seguida, item inicial:

```
Voce tem dez segundos antes de Otavio trancar a porta.
Da pra pegar uma coisa.

  [1] A lanterna de mao, em cima da geladeira
  [2] A faca de cozinha, na pia
  [3] O terco da sua mae, pendurado no prego da parede

Escolha:
```

| Escolha | Item recebido | Efeito imediato |
|---|---|---|
| 1 | `LANTERNA` | nenhum |
| 2 | `FACA` | `NERVO +1` |
| 3 | `TERCO` | `LUCIDEZ +1` |

## Introdução narrativa

```
Toda cidade tem uma noite que nao se comenta.

Em Riacho do Fogo e a vespera de Sao Joao. As fogueiras sao
acesas cedo e apagadas antes das dez. As janelas se fecham.
As luzes se apagam. Ninguem sai.

Voce cresceu ouvindo que quem sai nao volta, do mesmo jeito
que ouviu que nao se assobia dentro de casa. Nunca perguntou
por que. Ninguem pergunta.

Sua mae morreu ha tres anos. Sobrou voce, Otavio e Manuela.

As dez e quarenta, Manuela saiu.

Nao teve briga, nao teve bilhete. Ela pegou o casaco, abriu a
porta e foi, do jeito de quem vai ali e volta. Otavio correu
atras ate a calcada, parou na calcada, e voltou.

E entao ele fez o que a cidade inteira faria.

Ele trancou.

============================================================

Sao 23h10. Voce esta do lado de dentro, olhando a porta.

Do lado de fora, muito longe ainda, alguem esta cantando.
```
---

# PARTE III — OS DEZ CAPÍTULOS

> Formato de cada escolha:
> **`ID` — "texto exibido"**
> Condição / Consequência / Destino
>
> `CONFIANCA(X) +n` altera relacionamento. `FOLEGO +n` altera atributo.
> `SET(FLAG)` registra decisão. `DAR(ITEM)` / `TIRAR(ITEM)` mexe no inventário.
> `MORTE(ID)` dispara a rotina de morte: consome uma volta e reinicia o capítulo,
> ou encerra em `GAME_OVER` se não houver volta.

---

## CAP01 — A Porta
**23h10 · Sala da casa dos Nogueira · Nenhuma morte possível**

Capítulo de abertura. Estabelece o conflito, apresenta Otávio, e a primeira escolha define
se existe caminho de volta. Sem morte: o jogador precisa aprender o sistema antes de poder
perdê-lo.

### Texto de abertura

```
A chave ainda esta na fechadura. Otavio nao tirou.

Ele esta parado com a mao nela, do jeito de quem espera voce
dizer alguma coisa que resolva. Voce nao tem essa coisa.

Do lado de fora o canto continua. Nao da pra entender a letra.
Nunca deu.
```

### Diálogos

```
OTAVIO: Nao olha pra mim assim.

OTAVIO: Ela sabia. Ela sabe desde os seis anos igual a gente.
        Ninguem sai na Noite Longa.

VICENTE: Ela tem dezessete.

OTAVIO: E voce tem vinte e dois e ta com a mao na macaneta.

(Ele nao esta com raiva. Ele esta com medo, que na sua familia
 e a mesma cara.)

OTAVIO: Se voce sair, eu tranco. Nao e ameaca, e o que se faz.
        Mainha fazia. O pai fazia.

OTAVIO: E se voce bater, eu nao abro. Voce sabe que eu nao abro.
```

### Escolhas

**`ESC0101` — "Sair. Não discutir."**
- Condição: nenhuma
- Consequência: `CONFIANCA(OTAVIO) -2` · `NERVO +1` · `SET(FLAG_PORTA_TRANCADA)`
- Destino: `CAP02`

**`ESC0102` — "Pedir que ele deixe destrancada."**
- Condição: `CONFIANCA(OTAVIO) >= 5`
- Consequência: `CONFIANCA(OTAVIO) +1` · `SET(FLAG_PORTA_ABERTA)` · `LUCIDEZ +1`
- Destino: `CAP02`
- Nota: esta é a única fonte de `FLAG_PORTA_ABERTA`, que habilita o final `FIM_DESISTE`.
  Quem não pedir aqui não terá para onde voltar depois. É a primeira consequência posterior
  do jogo e o jogador não tem como saber disso — de propósito.

**`ESC0103` — "Chamar ele pra ir junto."**
- Condição: `NERVO >= 6`
- Consequência: `CONFIANCA(OTAVIO) -3` · `NERVO -1`
- Destino: `CAP02`
- Resposta: Otávio recusa. Sempre. A cena existe para o jogador entender que ele não vai
  ser salvo por ninguém, e para cobrar caro por ter pedido.

```
OTAVIO: Vai voce, entao. Vai voce que e o corajoso.

OTAVIO: Eu fico com a casa. Alguem tem que ficar com a casa.

(Ele nao olha mais pra voce. Isso vai doer amanha, se houver
 amanha.)
```

### Encerramento do capítulo

```
Voce abre a porta.

O ar de junho entra frio e cheirando a fogueira apagada.
A rua esta vazia do jeito que rua nenhuma fica.

Atras de voce, a chave gira.
```

*(Se `FLAG_PORTA_ABERTA`: a última linha vira `Atras de voce, a chave nao gira.`)*

### Ecos
Nenhum — este é o capítulo que gera ecos, não o que os recebe.

---

## CAP02 — A Rua de Casa
**23h40 · Rua de terra, bairro alto · Morte possível**

Primeiro contato com a noite. Ensina a regra 1 (não olhar para trás) e apresenta Davi.
A decisão sobre Davi é a que mais reverbera no jogo inteiro.

### Texto de abertura

```
Vinte casas ate a esquina. Voce conhece todas. Todas escuras.

O cachorro dos Pereira esta na calcada, deitado de lado,
olhando pra rua sem latir. Cachorro que nao late na Noite
Longa e cachorro que ta vendo.

Na metade do quarteirao voce escuta:

- Vicente.

E a voz de mainha.
```

### Diálogos

```
(Ela morreu ha tres anos.)

(Voce sabe disso do jeito que se sabe as coisas quando ja e
 tarde: sabendo, e virando a cabeca do mesmo jeito.)

- Vicente. Olha pra mim, meu filho.
```

### Escolhas

**`ESC0201` — "Olhar."**
- Condição: nenhuma
- Consequência: `MORTE(MORTE_NOME)`
- Nota: é a primeira morte do jogo e ela é quase certa numa primeira partida. Proposital:
  o sistema de voltas precisa ser ensinado, e ensinado da forma mais barata possível.

**`ESC0202` — "Não olhar. Continuar andando."**
- Condição: `LUCIDEZ >= 4` **ou** `ITEM(TERCO)` **ou** `FLAG_OUVIU_RADIO` **ou** já morreu aqui
- Consequência: `LUCIDEZ +1` · `NERVO +1`
- Destino: continua no capítulo, segue para o bloco do Davi

**`ESC0203` — "Responder sem virar."**
- Condição: `NERVO >= 6`
- Consequência: `LUCIDEZ -1` · `NERVO +2` · a voz para
- Destino: continua no capítulo

```
VICENTE: A senhora nao ta ai.

(O canto para. Por dois segundos a rua fica sem som nenhum,
 e o silencio e pior.)

(Depois recomeca, mais longe.)
```

### Texto de morte `MORTE_NOME`

```
Voce vira.

Nao tem nada. Tem a rua, tem o poste queimado, tem a casa dos
Pereira com a janela fechada.

E tem, de pe no meio da rua onde nao tinha ninguem, uma pessoa
segurando uma vela.

Ela sorri do jeito que sua mae sorria.

Voce nao sente frio. Voce nao sente nada. Voce da um passo na
direcao dela porque parece a coisa certa a fazer, e e a ultima
coisa que voce faz.
```

### Bloco do Davi

```
Na esquina da rua tem um menino.

Onze anos, pijama, descalco, com um cachorro no colo que e
metade do tamanho dele. Voce conhece: e o Davi, filho da
Rosangela, do numero 40.

Ele nao ta chorando. Ele ta parado do jeito de quem esqueceu
como se anda.

DAVI: O Tico fugiu.

DAVI: Eu so vim pegar o Tico.

DAVI: Ai eu virei e a minha casa ficou longe.
```

### Escolhas do bloco Davi

**`ESC0204` — "Levar ele de volta pra casa da Rosângela."**
- Condição: `FOLEGO >= 4`
- Consequência: `FOLEGO -2` · `CONFIANCA(DAVI) +3` · `SET(FLAG_SALVOU_DAVI)`
- Destino: `CAP03`
- Nota: custa fôlego e tempo. Davi passa a acompanhar Vicente a partir de `CAP05` — a
  Rosângela não abre a porta, e o menino sai atrás dele.

**`ESC0205` — "Mandar ele correr pra casa sozinho."**
- Condição: nenhuma
- Consequência: `CONFIANCA(DAVI) -2` · `SET(FLAG_ABANDONOU_DAVI)` · `NERVO -1`
- Destino: `CAP03`

**`ESC0206` — "Dar o terço pra ele."**
- Condição: `ITEM(TERCO)`
- Consequência: `TIRAR(TERCO)` · `CONFIANCA(DAVI) +5` · `SET(FLAG_SALVOU_DAVI)` · `NERVO +1`
- Destino: `CAP03`
- Nota: a maior fonte de confiança do jogo inteiro, ao custo do item que protege Vicente
  em `CAP09`. Troca real, sem resposta certa.

### Ecos
- `FLAG_OUVIU_RADIO` ainda não pode existir aqui (rádio é `CAP04`). Fica disponível apenas
  em partidas posteriores à primeira morte, pela memória do jogador — não pelo sistema.

---

## CAP03 — A Praça
**00h15 · Praça da Matriz · Morte possível**

A Procissão aparece pela primeira vez. Ensina a regra 2. Dona Zulmira entra e observa o
comportamento de Vicente antes de decidir se fala com ele.

### Texto de abertura

```
O canto para de ser longe.

Voce chega na praca e ela ja esta vindo pela rua da igreja: uma
fila de gente, dois a dois, cada um com uma vela na mao. Devem
ser cinquenta. Talvez cem. A fila nao termina onde a rua vira.

Eles andam devagar. Cantam baixo. Nenhum deles olha pros lados.

Voce reconhece o terceiro da fila. E o Seu Nilton, do armazem,
que morreu no ano passado de derrame.

Do outro lado da praca, agachada atras do banco de concreto,
tem uma mulher de uns sessenta anos com uma sacola de feira.

Ela poe o dedo na frente da boca.

Depois ela aponta pro chao.
```

### Escolhas

**`ESC0301` — "Ajoelhar e baixar os olhos."**
- Condição: nenhuma
- Consequência: `SET(FLAG_AJOELHOU)` · `LUCIDEZ +2` · `CONFIANCA(ZULMIRA) +2`
- Destino: bloco da Zulmira

**`ESC0302` — "Correr pela lateral da praça."**
- Condição: `FOLEGO >= 6`
- Consequência: `FOLEGO -2` · `NERVO -1` · `CONFIANCA(ZULMIRA) -1`
- Destino: bloco da Zulmira (ela fala menos)

**`ESC0303` — "Gritar o nome da Manuela."**
- Condição: nenhuma
- Consequência: `SET(FLAG_GRITOU_NOME)` · `LUCIDEZ -2` · `NERVO +1` · `CONFIANCA(ZULMIRA) -3`
- Destino: bloco da Zulmira
- Nota: quebra a regra 5. A Procissão não reage na hora — e é isso que torna a consequência
  cruel. O efeito aparece em `CAP09` e `CAP10`.

**`ESC0304` — "Atravessar a fila."**
- Condição: nenhuma
- Consequência: `MORTE(MORTE_FILA)`

### Texto de morte `MORTE_FILA`

```
Voce entra na fila achando que da pra passar entre duas pessoas.

Da. Voce passa.

Do outro lado, voce continua andando. Continua andando bem
devagar. Continua cantando baixo, e a letra agora voce entende
perfeitamente, e ela e simples, e voce ja sabia.

Alguem poe uma vela acesa na sua mao. Voce agradece.
```

### Bloco da Zulmira

```
A fila leva onze minutos pra passar. Voce conta.

Quando o ultimo dobra a esquina, a mulher se levanta, bate a
poeira do vestido e vem ate voce como quem encontra um conhecido
na feira.

ZULMIRA: Zulmira.

ZULMIRA: Voce e o do meio dos Nogueira. O da oficina.

VICENTE: A senhora ta na rua.

ZULMIRA: Todo ano, meu filho. Faz trinta e um.
```

*(Se `FLAG_AJOELHOU`:)*
```
ZULMIRA: E voce ajoelhou. Bom. A maioria corre.

ZULMIRA: Quem corre chama atencao. Quem ajoelha e chao.
```

*(Se `FLAG_GRITOU_NOME`:)*
```
ZULMIRA: Voce gritou um nome.

(Ela para de andar.)

ZULMIRA: Voce gritou o nome dela na rua, na frente deles.

ZULMIRA: Menino, eles nao precisavam saber quem procurar.
         Agora precisam nao: agora eles sabem.
```

### Escolhas do bloco Zulmira

**`ESC0305` — "Perguntar as regras."**
- Condição: `CONFIANCA(ZULMIRA) >= 4`
- Consequência: `LUCIDEZ +1` · `CONFIANCA(ZULMIRA) +1` · jogador aprende regras 1, 2 e 3
- Destino: `CAP04`

```
ZULMIRA: Tres coisas, e presta atencao porque eu nao repito.

ZULMIRA: Um: chamaram seu nome, nao olha. Nao importa a voz.
         Principalmente se for uma voz que voce ama.

ZULMIRA: Dois: passou a fila, ajoelha e baixa o olho.

ZULMIRA: Tres: nao aceita vela de ninguem. Nem apagada.
         Vela apagada e convite. Vela acesa e assinatura.
```

**`ESC0306` — "Perguntar se ela viu a Manuela."**
- Condição: `LUCIDEZ >= 5` (não gritar o nome de novo, perguntar direito)
- Consequência: `CONFIANCA(ZULMIRA) +1`
- Destino: `CAP04`

```
ZULMIRA: Menina de seda? Casaco vinho?

ZULMIRA: Passou por aqui faz uma hora. Indo pro lado do
         cemiterio.

VICENTE: Por que o cemiterio?

ZULMIRA: (Ela nao responde.)

ZULMIRA: Anda logo.
```

**`ESC0307` — "Seguir sozinho."**
- Condição: nenhuma
- Consequência: `CONFIANCA(ZULMIRA) -1`
- Destino: `CAP04`

### Ecos
- `FLAG_AJOELHOU` (deste capítulo) muda o tom da primeira fala da Zulmira.
- `FLAG_GRITOU_NOME` (deste capítulo) tem efeito em `CAP09` e `CAP10`.

---

## CAP04 — A Rádio
**00h50 · Rádio Serra, sala pequena com antena no telhado · Morte possível**

Seu Antônio. Fonte de informação e do item `RADIO`. A morte deste capítulo é a primeira que
o jogador pode evitar totalmente com atenção, não com atributo.

### Texto de abertura

```
A Radio Serra e uma casa de dois comodos com uma antena no
telhado. A luz vermelha do "NO AR" e a unica luz acesa em
quatro quarteiroes.

Da rua da pra ouvir o alto-falante que o Seu Antonio botou
na fachada em 1998:

ANTONIO: ... repetindo, para quem estiver na rua: nao atenda
         quando chamarem pelo nome. Nao olhe. Ande.

ANTONIO: Sao zero hora e cinquenta. Faltam quatro horas e dez
         para o sol.

ANTONIO: Se voce estiver ouvindo isso, voce ainda esta vivo.
         Isso ja e alguma coisa.

A porta esta encostada.
```

### Diálogos

```
Seu Antonio tem setenta anos e uma camisa social abotoada ate
em cima, como quem trabalha.

ANTONIO: Fecha a porta. Fecha e senta.

ANTONIO: (Ele olha voce de cima a baixo.) Nogueira.

ANTONIO: Sua mae trabalhou aqui. Dois anos. Lia o boletim das
         seis.

ANTONIO: Voce tem a voz dela.
```

*(Se `FLAG_GRITOU_NOME`:)*
```
ANTONIO: E voce ta procurando alguem, ne.

ANTONIO: Eu sei porque eu ouvi. Todo mundo ouviu.
         Inclusive eles.
```

*(**Faixa Hostil** — se `CONFIANCA(ANTONIO) <= 2`, a porta não abre e o capítulo inteiro é
substituído por este bloco. `ESC0401` a `ESC0407` ficam indisponíveis; o jogador segue direto
para `CAP05` sem `RADIO`, sem as regras 1 e 4, e sem `FLAG_AVISOU_ANTONIO`.)*

```
Voce empurra a porta e ela nao cede.

Do outro lado, muito perto, tem alguem respirando.

ANTONIO: Vai embora.

VICENTE: Seu Antonio, e o Vicente, filho da -

ANTONIO: Eu sei quem e.

ANTONIO: Voce gritou um nome na praca. Voce ficou marcado.

ANTONIO: E quem abre porta pra marcado fica marcado junto.

(O alto-falante da fachada continua falando, com a voz calma de
 quem esta lendo boletim, sobre nao olhar pra tras.)

(A voz nao e pra voce.)
```

- Nota de sistema: esta é a única forma de `ANTONIO` chegar à faixa Hostil, e ela vem de
  `FLAG_GRITOU_NOME` (que aplica `CONFIANCA(ANTONIO) -1` sobre o valor inicial 3). É de
  propósito: a punição por quebrar a regra 5 no `CAP03` é perder um capítulo inteiro de
  recursos três capítulos depois.

### Escolhas

**`ESC0401` — "Ouvir a transmissão inteira."**
- Condição: nenhuma
- Consequência: `SET(FLAG_OUVIU_RADIO)` · `LUCIDEZ +2` · `FOLEGO +1` · `CONFIANCA(ANTONIO) +2`
- Destino: continua no capítulo
- Nota: custa "tempo" narrativamente, mas o jogo não tem relógio real. O custo é psicológico.
  Aprende as regras 1 e 4.

**`ESC0402` — "Pedir o rádio de pilha."**
- Condição: `CONFIANCA(ANTONIO) >= 5`
- Consequência: `DAR(RADIO)` · `CONFIANCA(ANTONIO) +1`
- Destino: continua no capítulo

```
ANTONIO: Leva. E de 1994, pega so a minha frequencia, e a pilha
         ta pela metade.

ANTONIO: Mas enquanto eu tiver no ar, voce me escuta.

ANTONIO: E eu vou ficar no ar.
```

**`ESC0403` — "Perguntar por que ele fica."**
- Condição: `CONFIANCA(ANTONIO) >= 6`
- Consequência: `CONFIANCA(ANTONIO) +2` · `NERVO +1`
- Destino: continua no capítulo

```
ANTONIO: Em 1994 eu tinha um filho de dezenove anos.

ANTONIO: Ele saiu numa Noite Longa e nao teve ninguem dizendo
         pra ele nao olhar pra tras.

(Ele ajeita o microfone que nao precisa de ajeitar.)

ANTONIO: Entao agora tem.
```

**`ESC0404` — "Sair rápido, sem conversa."**
- Condição: nenhuma
- Consequência: `CONFIANCA(ANTONIO) -2`
- Destino: `CAP05`

### Bloco de saída

```
ANTONIO: Antes de voce ir.

ANTONIO: Tem uma coisa batendo na janela dos fundos faz uns
         vinte minutos.

ANTONIO: Bate tres vezes, para, bate tres vezes.

ANTONIO: Nao e o vento porque nao tem vento.
```

**`ESC0405` — "Ir olhar a janela dos fundos."**
- Condição: nenhuma
- Consequência: `MORTE(MORTE_JANELA)`

**`ESC0406` — "Ignorar e sair pela frente."**
- Condição: nenhuma
- Consequência: `LUCIDEZ +1`
- Destino: `CAP05`

**`ESC0407` — "Avisar Seu Antônio pra não abrir de jeito nenhum."**
- Condição: `FLAG_OUVIU_RADIO`
- Consequência: `SET(FLAG_AVISOU_ANTONIO)` · `CONFIANCA(ANTONIO) +3` · `LUCIDEZ +1`
- Destino: `CAP05`
- Nota: consequência posterior forte, e a única forma de garantir que Antônio continue vivo
  e no ar em `CAP06` e `CAP08`.

```
VICENTE: Seu Antonio.

VICENTE: Nao abre aquela janela. Nao hoje, nao por nada.

ANTONIO: (Ele olha pro corredor dos fundos por um tempo grande.)

ANTONIO: Eu ia abrir.

ANTONIO: Todo ano eu penso em abrir.

(Ele encosta a cadeira na porta do corredor. Depois senta de
 costas pra ela.)

ANTONIO: Vai. Eu fico no ar.
```

### Texto de morte `MORTE_JANELA`

```
Voce vai ate o comodo dos fundos.

A janela e de vidro canelado, daquelas que deixam ver vulto e
nao deixam ver cara.

Tem um vulto.

Voce chega perto o suficiente pra ver que ele tem a altura de
uma pessoa de dezenove anos, e voce entende, tarde, que essa
janela nao e pra voce.

Voce abre porque abrir e o que a mao faz.
```

### Ecos
- Se `FLAG_GRITOU_NOME` (CAP03), `CONFIANCA(ANTONIO) -1` na entrada do capítulo — o que leva
  ao valor 2 e dispara o bloco Hostil, fechando o capítulo inteiro.
- `FLAG_OUVIU_RADIO` habilita `ESC0407` e substitui a condição de `ESC0202` em partidas
  futuras.
- `FLAG_AVISOU_ANTONIO`, definida aqui, é lida em `CAP06`, `CAP08` e no final `FIM_A_TEMPO`.

---

## CAP05 — A Ponte do Riacho
**01h20 · Ponte de concreto sobre o Riacho do Fogo · Morte possível · Ponto de desistência**

Ensina a regra 4 (água corrente). É o primeiro dos dois pontos onde o jogador pode desistir
e ir para o final `FIM_DESISTE`. Davi reaparece se foi salvo.

### Texto de abertura

```
O riacho e raso e barulhento. Da pra atravessar com agua na
canela em qualquer lugar, mas a ponte e o unico jeito de nao
molhar o que voce carrega.

Do outro lado, a estrada se divide: a direita sobe pro cemiterio.
A esquerda volta pro bairro alto. Pra sua casa.

Voce para no meio da ponte porque e o unico lugar da cidade
onde voce nao sente que tem alguem atras de voce.

Depois voce entende por que.
```

*(Se `ITEM(RADIO)`:)*
```
O radio chia.

ANTONIO: ... para quem estiver perto de agua corrente: fiquem.

ANTONIO: E o unico lugar que eles nao pisam. Nao sei por que.
         Meu avo tambem nao sabia.

ANTONIO: Se puderem, encham alguma coisa.
```

*(Se `FLAG_SALVOU_DAVI`:)*
```
- Moco.

Voce nao precisa virar pra saber. Ele ta com o pijama molhado
ate o joelho e sem o cachorro.

DAVI: A minha mae nao abriu.

DAVI: Eu bati. Eu bati bastante.

DAVI: Ai eu vim atras do senhor porque o senhor foi o unico que
      falou comigo.
```

### Escolhas

**`ESC0501` — "Encher o cantil com água do riacho."**
- Condição: nenhuma
- Consequência: `DAR(CANTIL)` · `FOLEGO -1`
- Destino: continua no capítulo

**`ESC0502` — "Descansar cinco minutos na ponte."**
- Condição: nenhuma
- Consequência: `FOLEGO +3` · `LUCIDEZ +1` · `NERVO -1`
- Destino: continua no capítulo
- Nota: narrativamente custa tempo. O texto deve dar a sensação de erro sem ser um erro.

**`ESC0503` — "Pegar a estrada da esquerda. Voltar pra casa."**
- Condição: `FLAG_PORTA_ABERTA`
- Consequência: encerra em `FIM_DESISTE`
- Nota: sem `FLAG_PORTA_ABERTA` a opção aparece bloqueada, com o texto
  `[—] Voltar pra casa — a porta esta trancada`. Mostrar o bloqueio é essencial: é aqui que
  o jogador entende, tarde, o que aquela escolha do `CAP01` significava.

**`ESC0504` — "Seguir pra direita, subir pro cemitério."**
- Condição: nenhuma
- Consequência: `NERVO +1`
- Destino: `CAP06`

### Bloco Davi (se `FLAG_SALVOU_DAVI`)

**`ESC0505` — "Levar Davi junto."**
- Condição: `FOLEGO >= 3`
- Consequência: `CONFIANCA(DAVI) +3` · `FOLEGO -1` · Davi acompanha até `CAP09`
- Destino: continua

**`ESC0506` — "Deixar Davi na ponte, perto da água."**
- Condição: `LUCIDEZ >= 6`
- Consequência: `CONFIANCA(DAVI) +1` · `SET(FLAG_DAVI_NA_PONTE)`
- Destino: continua
- Nota: é a decisão *correta* segundo a regra 4, e é a que parece abandono. O jogo não avisa
  qual é qual. Em `CAP10`, se `FLAG_DAVI_NA_PONTE`, Davi está vivo no amanhecer.

**`ESC0507` — "Mandar ele voltar pra casa sozinho."**
- Condição: nenhuma
- Consequência: `CONFIANCA(DAVI) -4` · `SET(FLAG_ABANDONOU_DAVI)`
- Destino: continua

### Morte do capítulo

**`ESC0508` — "Atravessar o riacho pela água, fora da ponte, pra ganhar tempo."**
- Condição: `FOLEGO >= 5`
- Consequência: se `ITEM(RADIO)` **ou** `LUCIDEZ >= 7`, o personagemBase desiste no meio e
  volta (`FOLEGO -2`); caso contrário `MORTE(MORTE_AGUA)`

### Texto de morte `MORTE_AGUA`

```
A agua bate na canela e e mais fria do que devia em junho.

No meio do riacho voce olha pra baixo e a agua ta parada.

Nao devagar. Parada. Com voce dentro.

O barulho continua, mas o barulho ta vindo de outro lugar, de um
riacho que corre em algum lugar que nao e mais aqui.

Alguma coisa segura seu tornozelo com a delicadeza de quem
segura a mao de crianca pra atravessar a rua.
```

### Ecos
- `FLAG_PORTA_ABERTA` (CAP01) libera `ESC0503`.
- `FLAG_SALVOU_DAVI` (CAP02) traz Davi para a cena.
- `ITEM(RADIO)` (CAP04) evita `MORTE_AGUA` e adianta a regra 4.
---

## CAP06 — O Hospital Velho
**02h00 · Antigo hospital municipal, desativado desde 2011 · Morte possível**

O caminho para o cemitério passa pelo pátio do hospital velho. Cena escura — depende de
`LANTERNA`. Zulmira reaparece. Antônio pode sair do ar aqui.

### Texto de abertura

```
O hospital fechou em 2011 e ninguem nunca decidiu o que fazer
com o predio. As janelas do terreo estao fechadas com tijolo.
As de cima, nao.

Pra chegar na estrada do cemiterio tem que atravessar o patio,
que e um quadrado de cimento rachado com mato no meio.

O patio esta escuro de um jeito que nao combina com lua cheia.

E tem gente na janela do segundo andar. Nao uma pessoa. Umas
dez, quinze, cada uma numa janela, todas paradas, todas olhando
pro patio.

Nenhuma delas tem vela.
```

*(Se `ITEM(RADIO)` **e** `FLAG_AVISOU_ANTONIO` — o rádio chia e cai. Silêncio. Depois volta.)*
```
ANTONIO: ... eu vou ficar. Eu vou ficar aqui e vou continuar
         falando ate o sol.

ANTONIO: Se tem alguem me ouvindo agora: nao para no patio.
         Atravessa reto, olhando pro chao.

ANTONIO: Eles nao descem. Eles so olham. Mas se voce parar,
         eles entendem que voce ta pedindo.
```
- Efeito de sistema: com este aviso, `ESC0601` custa `NERVO -1` em vez de `NERVO -2`, e
  `MORTE_PATIO` passa a exibir um aviso na cena anterior à morte.

*(Se `ITEM(RADIO)` **e não** `FLAG_AVISOU_ANTONIO`:)*
```
O radio nao chia. Voce liga e desliga duas vezes.

Nada.

Depois, muito baixo, tem alguem respirando na frequencia.

Nao e a respiracao de um homem de setenta anos.

(Ele ia ficar no ar. Ele disse que ia ficar no ar.)
```
- Efeito de sistema: sem o aviso, Antônio abriu a janela dos fundos em algum momento entre
  `CAP04` e `CAP06`. O rádio permanece no inventário mas nunca mais transmite nada útil —
  nem aqui, nem em `CAP08`. `CONFIANCA(ANTONIO)` é congelada em 0 e o texto extra do final
  `FIM_A_TEMPO` não acontece.

*(Se **não** `ITEM(RADIO)`: nenhum dos dois blocos acontece. O jogador atravessa o pátio sem
saber de nada, o que é o caso mais perigoso e o mais comum numa primeira partida.)*

### Escolhas

**`ESC0601` — "Atravessar o pátio reto, olhando pro chão."**
- Condição: nenhuma
- Consequência: `NERVO -1` · `LUCIDEZ +1`
- Destino: bloco da Zulmira

**`ESC0602` — "Acender a lanterna e olhar pra cima."**
- Condição: `ITEM(LANTERNA)`
- Consequência: `NERVO -3` · `LUCIDEZ +2` · `SET(FLAG_VIU_AS_JANELAS)`
- Destino: bloco da Zulmira
- Nota: informação cara. O jogador descobre que uma das figuras nas janelas é Manuela — ou
  é parecida com Manuela. A dúvida é permanente e nunca é resolvida pelo jogo.

```
Voce sobe o facho devagar.

Primeira janela: um homem de camisa de botao.
Segunda: uma mulher com o cabelo preso.
Terceira: uma menina.

A menina tem dezessete anos e um casaco vinho e voce abaixa a
lanterna tao rapido que ela cai da sua mao e apaga.

(Nao era ela.)

(Nao era ela.)
```

**`ESC0603` — "Parar no meio do pátio e contar quantos são."**
- Condição: `LUCIDEZ >= 7`
- Consequência: `MORTE(MORTE_PATIO)`
- Nota: a única escolha do jogo que é bloqueada por lucidez *baixa* e mata quem tem lucidez
  *alta*. É deliberado: o excesso de confiança na própria percepção é o erro que ela cobra.

**`ESC0604` — "Entrar no prédio pra procurar."**
- Condição: `ITEM(LANTERNA)` e `NERVO >= 6`
- Consequência: `DAR(CASACO)` não — o casaco é `CAP09` · aqui: `FOLEGO -2` · `NERVO -2` ·
  `LUCIDEZ -1` · `SET(FLAG_ENTROU_HOSPITAL)`
- Destino: bloco da Zulmira
- Nota: não há nada dentro. Essa é a consequência: gastar três atributos por nada. O jogo
  precisa de pelo menos uma escolha que puna a curiosidade sem matar, para que o jogador
  aprenda que nem todo custo compra informação.

### Texto de morte `MORTE_PATIO`

```
Voce para. Voce olha pra cima e comeca a contar.

Um. Dois. Tres. Quatro.

Na quinta janela voce percebe que eles nao estao mais olhando
pro patio.

Estao olhando pra voce.

Dezesseis. Dezessete.

Voce percebe que parou de contar as janelas ha um tempo e
comecou a contar outra coisa, e essa outra coisa esta contando
tambem, e chega em voce primeiro.
```

### Bloco da Zulmira

```
Do outro lado do patio, sentada no muro, com a sacola de feira
no colo, Dona Zulmira.

ZULMIRA: Demorou.

VICENTE: A senhora ta me esperando?

ZULMIRA: Eu to indo pro mesmo lugar que voce, menino.

ZULMIRA: Faz trinta e um ano que eu vou.
```

**`ESC0605` — "Perguntar o que ela vai fazer no cemitério."**
- Condição: `CONFIANCA(ZULMIRA) >= 6`
- Consequência: `CONFIANCA(ZULMIRA) +2` · `NERVO +1`
- Destino: `CAP07`

```
ZULMIRA: Meu marido ta na fila.

ZULMIRA: Ele entrou em 95. Foi bobagem, foi vela na mao de
         vizinho, foi coisa de quem nao acreditava.

ZULMIRA: Todo ano eu vou la e ando do lado dele um pedaco.

VICENTE: Ele conhece a senhora?

ZULMIRA: Nao.

ZULMIRA: Mas eu conheco ele. Alguem tem que conhecer.
```

**`ESC0606` — "Contar sobre a janela do hospital."**
- Condição: `FLAG_VIU_AS_JANELAS`
- Consequência: `CONFIANCA(ZULMIRA) +1` · `LUCIDEZ +2`
- Destino: `CAP07`

```
ZULMIRA: Aquilo ali nao e gente que morreu. Aquilo e gente que
         nao decidiu.

ZULMIRA: Fica na janela olhando a Procissao passar, ano atras
         de ano, sem entrar e sem sair.

ZULMIRA: Se voce viu a cara da sua irma la, isso e pior que
         ruim e melhor que pessimo.

ZULMIRA: Significa que ela ainda ta escolhendo.
```

**`ESC0607` — "Seguir sem falar com ela."**
- Condição: nenhuma
- Consequência: `CONFIANCA(ZULMIRA) -2`
- Destino: `CAP07`

### Faixa Hostil — Zulmira

*(Se `CONFIANCA(ZULMIRA) <= 2`, o bloco inteiro acima não acontece. `ESC0605` a `ESC0607`
ficam indisponíveis e o jogador segue direto para `CAP07`.)*

```
Do outro lado do patio tem uma sacola de feira em cima do muro.

Vazia.

Voce olha pros dois lados da estrada e nao tem ninguem, e nao
deu tempo de ninguem sumir, e voce entende que ela te viu chegar
e escolheu nao estar ali.
```

- Consequência posterior: sem esta conversa, `CONFIANCA(ZULMIRA)` não sobe, o que
  praticamente garante que `ESC0806` (a `CHAVE`) fique inalcançável em `CAP08`.

### Ecos
- `FLAG_AVISOU_ANTONIO` (CAP04) determina se Antônio está no ar.
- `ITEM(LANTERNA)` (criação do personagemBase) determina o acesso a `ESC0602` e `ESC0604`.
- `FLAG_VIU_AS_JANELAS` habilita `ESC0606` e altera a fala final de `CAP10`.
- `CONFIANCA(ZULMIRA)` acumulada desde `CAP03` decide se ela está no muro.

---

## CAP07 — O Casarão dos Peixoto
**02h40 · Casarão abandonado na subida do cemitério · Morte possível · Item raro**

Capítulo do item raro (`MEDALHA`). Davi tem participação decisiva. É o capítulo mais
"escolha errada mata" do jogo, e o rádio não funciona aqui.

### Texto de abertura

```
O casarao dos Peixoto e a unica construcao entre o hospital e o
cemiterio. Dois andares, portao de ferro, mato ate a cintura.

Na frente tem um homem.

Ele esta de pe no meio do portao, de terno, com uma vela acesa
na mao esquerda e a mao direita estendida.

Ele nao canta. Ele espera.

Na mao direita dele tem outra vela. Apagada.

(Nao aceite vela de ninguem. Nem apagada.)

(Vela apagada e convite.)
```

*(Se o jogador não recebeu a regra 3 — nem por `ESC0305` nem por morte anterior —, o
parêntese não aparece. O jogo não avisa.)*

### Diálogos

```
PEIXOTO: Boa noite.

PEIXOTO: A casa esta aberta. Faz frio.

PEIXOTO: A gente estava esperando.

VICENTE: A gente quem?

PEIXOTO: (Ele sorri como quem nao entendeu a pergunta.)

PEIXOTO: A gente.
```

### Escolhas

**`ESC0701` — "Aceitar a vela apagada."**
- Condição: nenhuma
- Consequência: `DAR(VELA)` · `SET(FLAG_TEM_VELA)` · `LUCIDEZ -2`
- Destino: continua no capítulo
- Nota: **não mata na hora.** O item fica no inventário e só cobra em `CAP08`. É a armadilha
  central do jogo e depende de o jogador ter esquecido a regra 3.

**`ESC0702` — "Recusar e passar direto pelo portão."**
- Condição: `LUCIDEZ >= 4` **ou** regra 3 conhecida
- Consequência: `LUCIDEZ +1` · `NERVO +1`
- Destino: bloco dos fundos

**`ESC0703` — "Recusar e mandar ele embora."**
- Condição: `NERVO >= 7` **ou** `ITEM(FACA)`
- Consequência: `NERVO +2` · `CONFIANCA(DAVI) +1` se Davi presente
- Destino: bloco dos fundos

```
VICENTE: Eu nao vou pegar.

PEIXOTO: (A mao continua estendida.)

VICENTE: Eu nao vou pegar e o senhor vai sair da minha frente.

(Ele sai. Sem discutir, sem ameaca, sem nada.)

(Isso e o pior: que era so falar.)
```

**`ESC0704` — "Entrar na casa com ele."**
- Condição: nenhuma
- Consequência: `MORTE(MORTE_CASARAO)`

### Texto de morte `MORTE_CASARAO`

```
A sala e grande e tem gente sentada em todas as cadeiras.

Ninguem come. Tem prato, tem talher, tem toalha de renda, mas
ninguem come.

Eles abrem um lugar pra voce na ponta da mesa, e a cadeira ja
esta puxada, e tem um prato ali com o seu nome escrito na
borda, escrito ha muito tempo, escrito com a letra da sua mae.

Voce senta porque seria falta de educacao nao sentar.
```

### Bloco dos fundos

```
Nos fundos do casarao tem um alpendre com uma porta pequena, e
essa porta ta com o cadeado arrebentado.

Dentro tem caixote, tem tralha, e tem uma caixinha de metal
que alguem escondeu com pressa em cima da viga.

Ela esta alta demais pra voce alcancar sem apoio.
```

**`ESC0705` — "Subir no caixote e pegar."**
- Condição: `FOLEGO >= 6`
- Consequência: `DAR(MEDALHA)` · `SET(FLAG_PEGOU_MEDALHA)` · `FOLEGO -1`
- Destino: `CAP08`

**`ESC0706` — "Pedir pro Davi subir nos seus ombros."**
- Condição: Davi presente e `CONFIANCA(DAVI) >= 6`
- Consequência: `DAR(MEDALHA)` · `SET(FLAG_PEGOU_MEDALHA)` · `CONFIANCA(DAVI) +2`
- Destino: `CAP08`

```
DAVI: Que que e?

VICENTE: Uma medalha.

DAVI: De que?

VICENTE: Sao Jorge.

DAVI: (Ele devolve pra voce com as duas maos, do jeito que se
      entrega coisa de igreja.)

DAVI: Bota no bolso de dentro. Bolso de fora cai.
```

**`ESC0707` — "Deixar. Não tem tempo."**
- Condição: nenhuma
- Consequência: `NERVO +1`
- Destino: `CAP08`

### Ecos
- `FLAG_SALVOU_DAVI` (CAP02) e a confiança acumulada liberam `ESC0706` — o único caminho
  para a medalha com fôlego baixo.
- `ESC0305` (CAP03) ou uma morte anterior determinam se o jogador conhece a regra 3.
- `FLAG_TEM_VELA` é o gatilho de `CAP08`.

---

## CAP08 — A Rua das Velas
**03h20 · Rua da Saudade, última rua antes do cemitério · Morte possível · Ponto de desistência**

Clímax da armadilha. Segundo e último ponto de desistência. Zulmira entrega a chave.

### Texto de abertura

```
A Rua da Saudade tem duzentos metros e liga a cidade ao portao
do cemiterio.

Nos dois lados, no chao, encostadas no meio-fio, tem velas
acesas. Centenas. De ponta a ponta.

Nenhuma esta apagando. Nao tem vento, e mesmo se tivesse.

No meio da rua, a Procissao esta parada.

Nao esta andando. Esta parada, de frente pro cemiterio, cantando
baixo, esperando alguma coisa.

Voce entende, olhando, que eles nao estao esperando alguma coisa.

Estao esperando alguem que ainda nao chegou.
```

*(Se `FLAG_GRITOU_NOME`:)*
```
E entao voce entende de quem eles estao falando, porque no meio
do canto, no meio daquela lingua que nao e lingua, tem uma
palavra que voce reconhece.

E o nome dela.

Foi voce que ensinou.
```

*(Se `FLAG_TEM_VELA` — a vela no bolso esquenta:)*
```
A vela no seu bolso esta quente.

Ela nao estava quente ha dez minutos.

(Vela apagada e convite.)

(Convite tem hora.)
```

*(Se `ITEM(RADIO)` **e** `FLAG_AVISOU_ANTONIO` — último aviso do Seu Antônio:)*
```
ANTONIO: Sao tres e vinte. Falta uma hora e quarenta.

ANTONIO: E se tiver alguem na Rua da Saudade agora, ouve bem,
         porque eu so vou falar isso uma vez.

ANTONIO: Se voce ta com uma vela no bolso que voce nao acendeu,
         joga fora. Joga longe. Nao apaga, nao guarda, nao
         devolve pra quem deu.

ANTONIO: Joga fora e nao olha onde caiu.

(Silencio de radio.)

ANTONIO: Meu filho guardou a dele no bolso do palito. Eu achei
         depois, no armario, e ela ainda tava quente.
```
- Efeito de sistema: com este aviso, `ESC0802` ganha `LUCIDEZ +3` em vez de `+2`, e a opção
  `ESC0801` (acender) passa a ser exibida com um alerta explícito. Sem ele, o jogador que
  pegou a vela em `CAP07` e não conhece a regra 3 tem uma armadilha sem aviso nenhum.

### Escolhas

**`ESC0801` — "Acender a vela."**
- Condição: `ITEM(VELA)`
- Consequência: `SET(FLAG_ACENDEU_VELA)` · `MORTE(MORTE_VELA)` imediata
- Nota: morte que **não** pode ser evitada por atributo nenhum. Se o jogador tiver voltas,
  ele volta 7 minutos — e a vela continua no bolso, continua quente, e a opção continua ali.
  A única saída é `ESC0802`.

**`ESC0802` — "Jogar a vela fora, longe."**
- Condição: `ITEM(VELA)`
- Consequência: `TIRAR(VELA)` · `LUCIDEZ +2` · `NERVO +1`
- Destino: bloco da Zulmira

**`ESC0803` — "Andar pela calçada, rente ao muro, sem pisar entre as velas."**
- Condição: `LUCIDEZ >= 5`
- Consequência: `LUCIDEZ +1`
- Destino: bloco da Zulmira

**`ESC0804` — "Apagar as velas do seu lado com o pé."**
- Condição: `NERVO >= 8`
- Consequência: `MORTE(MORTE_APAGOU)`

**`ESC0805` — "Voltar. Ir embora. Não vale."**
- Condição: `FLAG_PORTA_ABERTA`
- Consequência: encerra em `FIM_DESISTE`
- Nota: aqui a opção pesa muito mais do que em `CAP05`, porque agora o jogador já viu tudo.

### Texto de morte `MORTE_VELA`

```
Voce acende.

A chama e pequena e amarela e comum, e por um segundo voce se
sente ridiculo.

Depois o canto para.

Duzentas pessoas viram a cabeca ao mesmo tempo, e nenhuma delas
esta surpresa.

Alguem abre um espaco na fila. Do lado direito. Faz sentido ser
do lado direito.

Voce entra porque voce foi convidado, e voce aceitou o convite
la atras, no portao do casarao, quando achou que estava so
sendo educado.
```

### Texto de morte `MORTE_APAGOU`

```
A primeira vela apaga facil.

A segunda tambem.

Na terceira voce percebe que as duas primeiras acenderam de
novo atras de voce, e que ninguem acendeu.

Na quarta voce percebe que a Procissao virou.

Na quinta voce ja nao esta apagando vela. Voce esta segurando
uma.
```

### Bloco da Zulmira

*(**Faixa Hostil** — se `CONFIANCA(ZULMIRA) <= 2`, ela não está no meio-fio. `ESC0806` a
`ESC0808` ficam indisponíveis, o jogador segue direto para `CAP09` sem a `CHAVE`, e a única
entrada no cemitério passa a ser `ESC0903` (com `MEDALHA`) ou `ESC0905` (com `FOLEGO >= 7`).
Se não tiver nenhum dos dois, o jogador está preso numa partida sem saída limpa — e isso é
proposital: é a consequência acumulada de ter tratado mal a única pessoa que conhecia o
caminho.)*

```
No meio-fio, entre duas velas, tem uma marca de sacola de feira
na poeira.

Ela sentou aqui. Faz pouco tempo.

Nao esperou.
```

```
Ela esta sentada no meio-fio, entre duas velas, com a sacola no
colo. Como quem espera onibus.

ZULMIRA: Senta um pouco.

VICENTE: A senhora ta sentada no meio delas.

ZULMIRA: Elas nao sao o problema, menino. Elas sao aviso.

ZULMIRA: O problema e quem acende.
```

**`ESC0806` — "Perguntar como entrar no cemitério."**
- Condição: `CONFIANCA(ZULMIRA) >= 6`
- Consequência: `DAR(CHAVE)` · `CONFIANCA(ZULMIRA) +1`
- Destino: `CAP09`

```
ZULMIRA: Portao da frente nao. Portao da frente e onde eles
         entram.

ZULMIRA: Tem uma porta lateral na capela. (Ela mexe na sacola.)

ZULMIRA: Toma. Eu tenho ha trinta e um ano.

VICENTE: Por que a senhora nunca usou?

ZULMIRA: Usei uma vez. Em 96.

ZULMIRA: Depois nao precisei mais, porque eu aprendi que nao
         adianta entrar se voce nao sabe o que vai falar.

ZULMIRA: Voce sabe o que vai falar?
```

**`ESC0807` — "Prometer que volta pra buscar ela."**
- Condição: `CONFIANCA(ZULMIRA) >= 7`
- Consequência: `SET(FLAG_PROMETEU_VOLTAR)` · `CONFIANCA(ZULMIRA) +2` · `NERVO +1`
- Destino: `CAP09`

**`ESC0808` — "Ir sem falar nada."**
- Condição: nenhuma
- Consequência: `CONFIANCA(ZULMIRA) -1`
- Destino: `CAP09`

### Ecos
- `FLAG_TEM_VELA` (CAP07) cria a ameaça central deste capítulo.
- `FLAG_GRITOU_NOME` (CAP03) muda a natureza do que a Procissão está esperando.
- `FLAG_PORTA_ABERTA` (CAP01) libera a desistência.
- `ITEM(CHAVE)` obtido aqui é a única entrada limpa em `CAP10`.

---

## CAP09 — O Cemitério
**04h10 · Cemitério municipal · Morte possível · Última chance antes do fim**

Encontro com a Procissão de perto. Cobra todas as decisões anteriores de uma vez. Davi
morre aqui se a confiança for máxima — e isso é uma consequência de ter sido bom com ele.

### Texto de abertura

```
O cemiterio de Riacho do Fogo tem tres fileiras de tumulo e um
mausoleu no fundo, que e da familia Peixoto.

A Procissao esta la dentro. Toda ela.

Eles nao estao enterrando ninguem. Estao em circulo, em volta
de alguma coisa que voce nao consegue ver do portao, cantando
a mesma coisa que cantam desde as onze da noite.

No chao, na entrada, tem um casaco vinho.
```

### Escolhas — bloco do casaco

**`ESC0901` — "Pegar o casaco."**
- Condição: nenhuma
- Consequência: `DAR(CASACO)` · `NERVO +1` · `LUCIDEZ -1`
- Destino: continua

**`ESC0902` — "Deixar. É armadilha."**
- Condição: `LUCIDEZ >= 7`
- Consequência: `LUCIDEZ +1`
- Destino: continua
- Nota: não é armadilha. É só o casaco. A punição por excesso de desconfiança é perder o
  diálogo final com Manuela — sem o casaco, uma das falas de `CAP10` não acontece.

### Escolhas — aproximação

**`ESC0903` — "Entrar pelo portão da frente."**
- Condição: nenhuma
- Consequência: se `ITEM(MEDALHA)`, passa (`NERVO -1`); senão `MORTE(MORTE_PORTAO)`

**`ESC0904` — "Entrar pela porta lateral da capela."**
- Condição: `ITEM(CHAVE)`
- Consequência: `LUCIDEZ +1` · `SET(FLAG_ENTRADA_LIMPA)`
- Destino: bloco do círculo

**`ESC0905` — "Pular o muro dos fundos."**
- Condição: `FOLEGO >= 7`
- Consequência: `FOLEGO -3` · `NERVO -1`
- Destino: bloco do círculo

**`ESC0906` — "Chamar o nome dela."**
- Condição: nenhuma
- Consequência: `MORTE(MORTE_CHAMOU)` — sempre, sem exceção, com ou sem medalha

### Texto de morte `MORTE_PORTAO`

```
Voce passa pelo portao e trinta cabecas viram.

Nao e hostil. E pior: e reconhecimento.

Uma senhora na ponta do circulo abre os bracos como quem
recebe neto na porta de casa, e voce sente uma vontade
enorme, absurda, de descansar.

Voce esta cansado ha cinco horas.

Ela tem lugar.
```

### Texto de morte `MORTE_CHAMOU`

```
- MANUELA!

O canto para.

E entao, muito devagar, todos eles respondem.

Duzentas vozes dizem o nome da sua irma ao mesmo tempo, e a
ultima voz a dizer e uma que voce conhece, e vem do meio do
circulo, e e ela.

Ela diz o proprio nome como quem responde a chamada.

E depois ela diz o seu.
```

### Bloco do círculo

```
Do lado de dentro voce ve o que eles estao cercando.

E uma menina de dezessete anos, de pe, no meio do circulo, sem
vela na mao.

Ela ainda nao aceitou.

Do lado dela tem um homem de terno segurando duas velas: uma
acesa e uma apagada.

Eles estao esperando ha cinco horas. Eles tem a noite toda.

E a noite acaba em quarenta minutos.
```

*(Se `FLAG_ABANDONOU_DAVI`:)*
```
Na terceira fileira do circulo, entre um homem de bengala e uma
mulher de vestido azul, tem um menino de pijama.

Ele esta segurando uma vela com as duas maos, com cuidado, do
jeito que se segura coisa de igreja.

Ele nao olha pra voce.
```

*(Se Davi está presente e `CONFIANCA(DAVI) >= 9`:)*
```
DAVI: Moco.

DAVI: Eles ta olhando pro senhor.

VICENTE: Fica atras de mim.

DAVI: (Ele nao fica atras de voce.)

DAVI: O senhor foi o unico que falou comigo.

E ele corre pro meio do circulo gritando, batendo em vela,
derrubando vela, fazendo o barulho mais alto que um menino de
onze anos consegue fazer.

Todas as cabecas viram pra ele.

Todas.

Voce tem seis segundos e um caminho aberto ate sua irma, e voce
vai usar os dois, e voce vai carregar isso.
```

### Escolha final do capítulo

**`ESC0907` — "Avançar."**
- Condição: `ITEM(MEDALHA)` **ou** `FLAG_ENTRADA_LIMPA` **ou** sacrifício de Davi ocorreu
- Consequência: `SET(FLAG_ALCANCOU)`
- Destino: `CAP10`

**`ESC0908` — "Esperar. Procurar uma abertura."**
- Condição: `LUCIDEZ >= 6`
- Consequência: `LUCIDEZ +1` · `SET(FLAG_ESPEROU)`
- Destino: `CAP10`
- Nota: leva a `CAP10` em condição pior — o sol nasce mais perto.

**`ESC0909` — "Recuar."**
- Condição: nenhuma
- Consequência: `NERVO -2`
- Destino: `CAP10` na condição mais fraca possível

### Ecos
Este capítulo é quase inteiro feito de ecos:
`FLAG_PEGOU_MEDALHA` (CAP07), `ITEM(CHAVE)` (CAP08), `FLAG_ABANDONOU_DAVI` (CAP02/CAP05),
`CONFIANCA(DAVI)` acumulada, `FLAG_GRITOU_NOME` (CAP03), `ITEM(TERCO)` (criação).

---

## CAP10 — O Amanhecer
**04h50 · Centro do cemitério · Capítulo final**

Resolução. Determina qual dos três finais o jogador alcança e qual rota narrativa a partida
seguiu. Retorna ao menu inicial ao terminar.

### Texto de abertura

```
Voce chega perto o suficiente pra ver a cara dela.

Manuela nao esta com medo. Isso e a pior parte.

MANUELA: Eu sabia que era voce.

MANUELA: Eu falei pra eles que ia ser voce.
```

### Diálogos

```
VICENTE: Por que voce saiu?

MANUELA: (Ela demora.)

MANUELA: Porque mainha ta aqui.

MANUELA: Todo ano ela passa na nossa rua e todo ano voces dois
         fecham a janela.

MANUELA: Eu so queria ver.

MANUELA: Ai eu vi.
```

> **Confiança da Manuela neste capítulo.** Ela começa a partida em 5 e só varia aqui. Cada
> bloco abaixo que acontece a altera, e o valor final entra no cálculo do desfecho. É o
> vínculo mais curto do jogo, e de propósito: a relação com ela não é construída no caminho,
> é cobrada no fim.

*(Se `ITEM(CASACO)`:)* · `CONFIANCA(MANUELA) +2`
```
Voce estende o casaco.

Ela olha pro casaco como quem olha pra uma coisa de outra vida.

MANUELA: Eu tirei porque ta quente.

VICENTE: Ta cinco graus.

MANUELA: (Ela olha pros bracos.) Ta quente.

(E ai voce entende quanto tempo voce tem.)

MANUELA: Voce carregou isso a noite toda?

VICENTE: Carreguei.

MANUELA: (Ela veste.)
```

*(Se `FLAG_VIU_AS_JANELAS`:)* · `CONFIANCA(MANUELA) +1`
```
VICENTE: Eu vi voce na janela do hospital.

MANUELA: Nao era eu.

VICENTE: Era.

MANUELA: (Ela nao discute.)

MANUELA: Entao ainda da tempo, ne. Se eu tava na janela.
         Quem ta na janela ainda nao decidiu.
```

*(Se `FLAG_SALVOU_DAVI` **ou** `FLAG_DAVI_NA_PONTE`:)* · `CONFIANCA(MANUELA) +1`
```
MANUELA: O Davi?

VICENTE: Como voce sabe do Davi?

MANUELA: Eu passei na frente da casa da Rosangela as onze.
         Ele tava na janela olhando o cachorro na rua.

MANUELA: Eu nao parei.

(Ela olha pro chao.)

MANUELA: Voce parou.
```

*(Se `FLAG_ABANDONOU_DAVI`:)* · `CONFIANCA(MANUELA) -2`
```
MANUELA: Tinha um menino na terceira fileira.

VICENTE: Eu sei.

MANUELA: Ele falou o seu nome quando entrou na fila.

MANUELA: Nao foi acusando. Foi so falando.

MANUELA: Isso e pior.
```

*(Se `FLAG_GRITOU_NOME`:)* · `CONFIANCA(MANUELA) -3`
```
MANUELA: Voce gritou meu nome na praca.

VICENTE: Eu -

MANUELA: Eles me chamaram pelo seu nome depois disso.

MANUELA: Com a sua voz.

MANUELA: Foi por isso que eu vim pro meio.
```

*(Se `CONFIANCA(OTAVIO) >= 6` — o único uso da confiança do Otávio fora do `CAP01`:)*
· `CONFIANCA(MANUELA) +1`
```
VICENTE: Otavio deixou a porta destrancada.

MANUELA: (Ela levanta a cabeca.)

MANUELA: Ele nunca deixa.

VICENTE: Ele deixou hoje.

MANUELA: Pra voce.

VICENTE: Pra nos dois. Ele falou "voces". Ele falou no plural.

(Ela nao diz nada, mas ela olha pro portao pela primeira vez
 desde que voce chegou.)
```

*(Se `FLAG_PROMETEU_VOLTAR` — Zulmira aparece na porta da capela:)*
```
ZULMIRA: Menino.

ZULMIRA: Fala rapido e fala a verdade. Eles nao aguentam
         verdade.
```

### Escolha final

**`ESC1001` — "Dizer que ela precisa vir agora."**
- Condição: nenhuma
- Consequência: avaliação do desfecho (ver tabela abaixo)

**`ESC1002` — "Dizer que mainha não está ali."**
- Condição: `LUCIDEZ >= 6`
- Consequência: `+1` no cálculo de desfecho

```
VICENTE: Ela nao ta aqui, Manu.

VICENTE: Tem uma coisa com a cara dela. Tem uma coisa com a voz
         dela. Nao e ela.

VICENTE: Eu sei porque eu ouvi. Na rua de casa, as onze e meia.
         Ela me chamou e eu quase virei.

VICENTE: Mainha nunca ia me chamar pra um lugar que eu nao devia
         ir.
```

**`ESC1003` — "Segurar a mão dela e não soltar."**
- Condição: `NERVO >= 6`
- Consequência: `+1` no cálculo de desfecho

**`ESC1004` — "Oferecer o terço."**
- Condição: `ITEM(TERCO)`
- Consequência: `+2` no cálculo de desfecho

**`ESC1005` — "Entrar no círculo com ela."**
- Condição: nenhuma
- Consequência: `MORTE(MORTE_CIRCULO)` — e se houver volta, o jogador volta 7 minutos e
  encara a mesma decisão. A noite não impede o sacrifício, só o adia.

### Cálculo do desfecho

Some os pontos:

| Origem | Pontos |
|---|---|
| `FLAG_ALCANCOU` (chegou por `ESC0907`) | +2 |
| `FLAG_ESPEROU` | +1 |
| `ESC1002` usada | +1 |
| `ESC1003` usada | +1 |
| `ESC1004` usada | +2 |
| `CONFIANCA(MANUELA) >= 9` | +2 |
| `CONFIANCA(MANUELA)` entre 6 e 8 | +1 |
| `CONFIANCA(ZULMIRA) >= 7` | +1 |
| `CONFIANCA(ANTONIO) >= 7` | +1 |
| Voltas gastas ≥ 4 | **−1** |
| `CONFIANCA(MANUELA) <= 2` | **−2** |

**Total ≥ 5 → `FIM_A_TEMPO`**
**Total entre 1 e 4 → `FIM_TARDE`**
**Total ≤ 0 → `FIM_TARDE`** (não existe caminho para `FIM_DESISTE` a partir daqui; a
desistência só é possível em `CAP05` e `CAP08`)

> **Mudança em relação à versão anterior deste documento:** `ITEM(CASACO)`,
> `FLAG_VIU_AS_JANELAS` e `FLAG_GRITOU_NOME` saíram da tabela como entradas diretas. Eles
> continuam pesando exatamente igual — só que **através** de `CONFIANCA(MANUELA)`, que sobe
> ou desce nos blocos de diálogo acima. O cálculo passou a ler três relacionamentos
> (`MANUELA`, `ZULMIRA`, `ANTONIO`) em vez de uma lista de flags soltas, e isso é o que
> transforma o vínculo em mecânica de verdade em vez de número decorativo.

### Texto de morte `MORTE_CIRCULO`

```
Voce entra.

Alguem poe uma vela na sua mao e voce agradece, porque foi
gentileza.

Manuela olha pra voce e sorri, e e o sorriso mais aliviado que
voce ja viu na cara dela, e e agora que voce entende: ela nao
queria ser salva.

Ela queria companhia.

O canto recomeca. Voce sabe a letra.
```

---

# PARTE IV — OS FINAIS

## `FIM_A_TEMPO` — Chega a tempo

```
============================================================
                     CHEGA A TEMPO
============================================================

Ela solta a mao do homem de terno.

Nao tem grito, nao tem luta, nao tem nada de bonito de contar.
Ela so solta, e da tres passos pra tras, e os tres passos custam
mais do que a noite inteira custou pra voce.

O homem nao vai atras. Eles nunca vao atras. E o convite ou nao
e nada.

O ceu do lado do posto de gasolina fica cinza. Depois fica menos
cinza.

Quando o primeiro galo canta em Riacho do Fogo, a Procissao ja
se desfez, e nao tem nenhuma vela acesa no cemiterio, e voces
dois estao sentados no meio-fio da Rua da Saudade sem falar
nada.

MANUELA: Otavio deve ta acordado.

VICENTE: Otavio nao dormiu.

MANUELA: (Ela ri, e a risada sai errada, e ela chora um pouco,
         e depois para.)

MANUELA: Ele vai gritar comigo.

VICENTE: Muito.

Voces levantam quando o sol bate na antena da Radio Serra.

De algum lugar, muito longe, tem uma voz de setenta anos dizendo
que sao cinco e vinte, que a noite acabou, e que quem estiver
ouvindo isso conseguiu.
```

*(Se Davi se sacrificou em `CAP09`:)*
```
Na terceira fileira, na saida do cemiterio, tem um pijama azul
dobrado em cima de um tumulo.

Voce nao conta pra ela. Voce vai contar um dia.

Voce vai contar pra Rosangela hoje, que e pior.
```

*(Se `FLAG_DAVI_NA_PONTE`:)*
```
Na ponte do riacho, dormindo encostado no parapeito com o
pijama molhado ate o joelho, tem um menino de onze anos.

Ele acorda quando voces passam.

DAVI: O senhor demorou.
```

*(Se `FLAG_PROMETEU_VOLTAR`:)*
```
Dona Zulmira ta sentada no portao com a sacola de feira no colo.

ZULMIRA: Ano que vem eu venho de novo.

VICENTE: Eu venho com a senhora.

ZULMIRA: (Ela olha pra voce um tempo.)

ZULMIRA: Nao vem nao, meu filho.

ZULMIRA: Fica com sua irma. E pra isso que serve.
```

*(Se `CONFIANCA(ANTONIO) >= 9` — a faixa Devoto do Seu Antônio:)*
```
O radio no seu bolso chia uma ultima vez.

ANTONIO: Sao cinco e vinte e dois.

ANTONIO: A Radio Serra encerra a transmissao especial da
         vespera de Sao Joao.

ANTONIO: Quem estiver ouvindo isso conseguiu.

(Pausa.)

ANTONIO: Eu nao abri a janela.

ANTONIO: Faz trinta e dois anos que eu nao abro, mas hoje foi a
         primeira vez que teve alguem me pedindo pra nao abrir.

ANTONIO: Boa noite, Riacho do Fogo. Ou bom dia. Ja e dia.
```

## `FIM_TARDE` — Chega tarde

```
============================================================
                      CHEGA TARDE
============================================================

O ceu clareia atras do mausoleu.

Manuela olha pro lado como quem ouve alguem chamar da cozinha.

MANUELA: Ela ta chamando.

VICENTE: Nao ta.

MANUELA: Ta sim.

E ela pega a vela.

Nao tem nada dramatico. Ela pega do jeito que se pega um copo
que alguem passa na mesa. A chama nao aumenta. Ela nao muda de
cara. Ela nao vira monstro nenhum.

Ela so entra na fila, entre um homem de bengala e uma mulher de
vestido azul, e comeca a cantar baixo, e canta bem, porque ela
sempre cantou bem.

Quando o sol nasce, a Procissao se desfaz.

Voce fica sozinho no meio de um cemiterio vazio com um casaco
vinho na mao.

============================================================

Voce volta pra casa as sete da manha.

Otavio abre a porta antes de voce bater. Ele olha pro casaco.
Ele nao pergunta.

Ele te abraca de um jeito que ele nunca abracou, e voces dois
ficam de pe na porta um tempo grande.

Ninguem na cidade comenta. Nunca comentam.

No ano que vem tem outra Noite Longa.

E voce vai estar na rua.
```

## `FIM_DESISTE` — Volta pra casa

```
============================================================
                    VOLTA PRA CASA
============================================================

Voce vira as costas.

Isso e mais dificil do que qualquer coisa que voce fez essa
noite, e leva mais tempo, e nao tem nenhum momento em que fica
mais facil.

A estrada de volta e a mesma, e agora voce sabe o caminho, e
sabe onde nao olhar, e onde ajoelhar, e onde nao pisar.

Voce chega em casa as quatro e quarenta.

A porta esta destrancada.

============================================================

Otavio ta sentado na mesa da cozinha com a luz apagada.

Ele olha pra porta. Depois olha pra tras de voce, procurando
uma segunda pessoa que nao vem.

Ele nao fala nada. Nem naquela noite, nem no dia seguinte, nem
nunca.

Voces dois viveram muitos anos nessa casa depois disso.

Em junho, na vespera de Sao Joao, voces fecham a janela cedo.

E toda vez que a Procissao passa na rua, os dois ficam parados
no meio da sala, sem falar, ouvindo, tentando escutar se tem uma
voz de dezessete anos naquele canto.

Tem.

Sempre tem.
```

## `GAME_OVER` — Fim de jogo

```
============================================================
                       FIM DE JOGO
============================================================

Nao tem mais volta.

A noite te devolveu cinco vezes porque voce nasceu nela, e a
noite paga o que deve, e agora nao deve mais nada.

Sao [HORARIO DO CAPITULO]. Faltavam [X] horas para o sol.

Manuela esta no meio do circulo, esperando.

Ela vai esperar mais um pouco.

============================================================

  Capitulo alcancado: [N] de 10
  Voltas usadas: 5 de 5
  Confianca: [DAVI] [ANTONIO] [ZULMIRA]

  ENTER para voltar ao menu
```

---

# PARTE V — RASTREABILIDADE DOS REQUISITOS

Tabela para o relatório: cada requisito da Fase 1 e onde ele está atendido na história.

| Requisito do enunciado | Onde está atendido |
|---|---|
| Menu inicial (nova partida, instruções, créditos, saída) | Parte II — quatro opções |
| Criação/configuração do protagonista | Parte II — nome, 15 pontos entre 3 atributos, escolha de 1 de 3 itens iniciais |
| Introdução narrativa | Parte II — texto de abertura antes de `CAP01` |
| Ao menos 10 unidades identificáveis | `CAP01` a `CAP10`, nomeadas e numeradas |
| Diálogos legíveis no terminal | Formato `NOME: texto`, 72 colunas, definido em 8. Estilo visual |
| Escolhas em diferentes momentos | 47 escolhas distribuídas nos 10 capítulos |
| Consequências imediatas | Todo `ESC` tem bloco de consequência com deltas |
| Consequências posteriores | 10 flags na tabela 4.7, cada uma cobrada em capítulo posterior |
| Mínimo 5 personagens significativos | `OTAVIO`, `DAVI`, `ANTONIO`, `ZULMIRA`, `MANUELA` (+ `PEIXOTO` como figura) |
| Relacionamento com pelo menos 3 | Confiança 0–10 com **cinco** personagens. Detalhamento na tabela abaixo |
| Pelo menos 3 atributos variáveis | `FOLEGO`, `NERVO`, `LUCIDEZ` (0–10) |
| Recurso gerenciável | Voltas (5) + inventário de 9 itens |
| Condições de acesso | 4 tipos de condição (atributo, item, confiança, flag) usados em 31 escolhas |
| Ao menos 3 finais + retorno ao menu | `FIM_A_TEMPO`, `FIM_TARDE`, `FIM_DESISTE` (+ `GAME_OVER` como encerramento prematuro); todos voltam ao menu |

## Detalhamento do requisito de relacionamentos

O enunciado pede vínculo com pelo menos três personagens. O jogo mantém cinco. Esta tabela
é a prova de que cada vínculo **varia** e **é lido** — os dois lados que o requisito cobra.

| NPC | Valor inicial | Onde varia | Onde é lido como condição | Papel mecânico |
|---|---|---|---|---|
| `ZULMIRA` | 3 | CAP03, CAP06, CAP08 | CAP03 (`ESC0305`), CAP06 (`ESC0605` e presença na cena), CAP08 (`ESC0806`, `ESC0807`, presença na cena), CAP10 (desfecho) | Entrega a `CHAVE`; some da cena se Hostil |
| `DAVI` | 3 | CAP02, CAP05, CAP07 | CAP05 (acompanhamento), CAP07 (`ESC0706`), CAP09 (sacrifício em ≥ 9) | Único caminho para a `MEDALHA` com fôlego baixo |
| `ANTONIO` | 3 | CAP04 | CAP04 (`ESC0402`, `ESC0403`, porta fechada se ≤ 2), CAP10 (desfecho e final Devoto) | Entrega o `RADIO`; a rádio fecha se Hostil |
| `MANUELA` | 5 | CAP10 (seis blocos de diálogo) | CAP10 (desfecho, três faixas) | Concentra o efeito de casaco, janelas, Davi e regra 5 |
| `OTAVIO` | 5 | CAP01 | CAP01 (`ESC0102`), CAP10 (bloco de diálogo em ≥ 6) | Decide se existe caminho de volta |

Distribuição por capítulo — nenhum capítulo do meio fica sem relacionamento em jogo:

| | CAP01 | CAP02 | CAP03 | CAP04 | CAP05 | CAP06 | CAP07 | CAP08 | CAP09 | CAP10 |
|---|---|---|---|---|---|---|---|---|---|---|
| varia | OTA | DAVI | ZUL | ANT | DAVI | ZUL | DAVI | ZUL | — | MAN |
| é lido | OTA | — | ZUL | ANT | DAVI | ZUL | DAVI | ZUL | DAVI | todos |

## Contagem geral

- 10 capítulos
- 48 escolhas
- 13 mortes distintas (uma ou mais por capítulo a partir do CAP02)
- 9 itens
- 10 flags de decisão
- 3 atributos
- 5 relacionamentos, todos com efeito mecânico
- 4 faixas de confiança, todas com efeito implementado
- 3 finais + 1 game over
- 3 rotas narrativas

---

*Fim do documento.*
