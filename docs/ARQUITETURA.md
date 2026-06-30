# Arquitetura do Projeto

## Visao geral

O projeto agora possui duas camadas visuais:

- `app/`: dashboard premium atual, usado por `Main.java`;
- `view/`: componentes da versao anterior, mantidos como historico/evolucao do trabalho.

A tela principal em uso e `app.MainView`.

## Fluxo

```text
Main.java
  |
  v
app.MainView
  |
  |-- HeaderBar
  |-- HeroPanel
  |-- InputParametersPanel
  |-- SquareConfigurationPanel
  |-- CoulombLawPanel
  |-- ConversionsPanel
  |-- ResultPanel
  |-- QuickPresetsPanel
  `-- FooterStatusBar
        |
        v
ChargeCalculatorModel
```

## Pacotes

### `app.components`

Componentes reutilizaveis de interface:

- `TechCard`: card tecnico com titulo e detalhe azul;
- `TechButton`: botao primario/secundario no estilo do dashboard;
- `TechTextField`: campo de texto com foco azul e estado de erro;
- `UnitField`: linha composta por simbolo, input e unidade.

### `app.layout`

Estrutura fixa da tela:

- `HeaderBar`: nome do sistema e tabs superiores;
- `HeroPanel`: area visual com `hero_character.png`;
- `FooterStatusBar`: status inferior do sistema.

### `app.panels`

Paineis funcionais:

- `InputParametersPanel`: entrada de `q1`, `q2`, `q3`, `q4` e `a`;
- `SquareConfigurationPanel`: desenho do quadrado com cargas;
- `CoulombLawPanel`: formula principal;
- `ConversionsPanel`: conversoes comuns de carga;
- `ResultPanel`: modulo, direcao, componentes e vetor;
- `QuickPresetsPanel`: presets de configuracao.

### `app.model`

`ChargeCalculatorModel` calcula a forca resultante no centro.

Ele usa uma carga teste positiva de `1 nC` no centro `O`, calcula a contribuicao vetorial de cada carga nos vertices e soma as componentes:

```text
Fx_total = Fx1 + Fx2 + Fx3 + Fx4
Fy_total = Fy1 + Fy2 + Fy3 + Fy4
Fnet = sqrt(Fx_total^2 + Fy_total^2)
angulo = atan2(Fy_total, Fx_total)
```

## Recursos

```text
src/resources/style.css
src/resources/assets/hero_character.png
```

O `style.css` define a identidade sci-fi/anime: fundo branco frio, bordas pretas finas, cards tecnicos, azul eletrico, inputs, botoes, tabs e tipografia forte.

## Scripts

| Arquivo | Funcao |
| --- | --- |
| `build.ps1` | Baixa JavaFX se necessario, compila e copia todos os recursos. |
| `run.ps1` | Compila e executa a aplicacao. |
| `package.ps1` | Gera executavel Windows com `jpackage`. |
| `package-linux.sh` | Gera pacote Linux. |

## Observacao

`model/PhysicsCalculator.java` permanece no projeto porque pertence ao problema original de trabalho/energia potencial. A UI nova usa `app.model.ChargeCalculatorModel` para a calculadora visual de forca no centro.
