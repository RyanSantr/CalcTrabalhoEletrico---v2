# Arquitetura do Projeto

## Visao geral

O app atual usa `app.MainView` como tela principal. Ele combina:

- fundo visual em tela cheia;
- personagem com transparencia real;
- paineis JavaFX funcionais;
- calculo fisico separado em `model.PhysicsCalculator`;
- utilitarios de parsing/formatacao em `util.NumberUtils`.

## Fluxo

```text
Usuario digita q e a
        |
        v
InputPanel valida os campos
        |
        v
PhysicsCalculator converte pC/cm e calcula W
        |
        v
ResultPanel mostra W e interpretacao
```

## Classes principais

| Classe | Responsabilidade |
| --- | --- |
| `Main.java` | Inicializa JavaFX, carrega CSS e abre a janela 1600x900. |
| `app.MainView` | Monta fundo, header, personagem, paineis e rodape. |
| `BackgroundLayer` | Exibe `background.png` cobrindo toda a tela. |
| `HeroCharacterPane` | Exibe `hero_character.png` com animacao idle. |
| `ElectricSparksPane` | Gera faíscas eletricas leves com `Line` e `Circle`. |
| `MotionEffects` | Centraliza fade, slide, pulse e animacao flutuante. |
| `InputPanel` | Campos `q` e `a`, validacao e botoes. |
| `ChargeSquarePane` | Desenha a configuracao das quatro cargas. |
| `FormulaPanel` | Mostra formula e conversoes. |
| `ResultPanel` | Mostra resultado em notacao cientifica. |
| `PhysicsCalculator` | Faz conversoes e aplica `U = (kq^2/a)(√2 - 4)`. |
| `NumberUtils` | Aceita virgula/ponto e formata numeros. |

## Assets

```text
src/resources/assets/background.png
src/resources/assets/hero_character.png
src/resources/assets/header_bar.png
src/resources/assets/raw/
```

O script `tools/remove_fake_background.py` remove checkerboard falso de PNGs em `raw/` e salva os arquivos finais com alpha real.

## Scripts

| Arquivo | Funcao |
| --- | --- |
| `build.ps1` | Compila e copia recursos. |
| `run.ps1` | Compila e executa. |
| `package.ps1` | Gera app Windows com `jpackage`. |
| `package-linux.sh` | Gera pacote Linux. |
