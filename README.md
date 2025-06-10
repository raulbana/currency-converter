# Currency Converter App

Este é um aplicativo Android simples para conversão de moedas, desenvolvido como projeto acadêmico para a disciplina de Programação para Dispositivos Móveis na UFPR.

## Funcionalidades

- **Conversão entre moedas:** Permite converter valores entre Real (BRL), Dólar (USD) e Bitcoin (BTC) utilizando cotações em tempo real da [AwesomeAPI](https://docs.awesomeapi.com.br/api-de-moedas).
- **Carteira virtual:** Exibe os saldos atuais de cada ativo (BRL, USD, BTC) na tela inicial.
- **Controle de saldo:** Só permite conversão se houver saldo suficiente na moeda de origem.
- **ProgressBar:** Indica carregamento durante a consulta de cotações.
- **Atualização automática:** Os saldos são atualizados automaticamente após cada conversão.
- **Interface simples:** Seleção de moeda de origem, destino e valor a ser convertido.

## Tecnologias Utilizadas

- Kotlin
- Android SDK
- Retrofit (para requisições HTTP)
- AwesomeAPI (cotações de moedas)

## Como usar

1. **Tela inicial:** Veja seus saldos em BRL, USD e BTC.
2. **Converter:** Clique em "Converter Recursos", escolha as moedas e o valor, e realize a conversão.
3. **Saldo:** O saldo é atualizado automaticamente após cada operação.

## Observações

- Os saldos são mantidos apenas em memória. Ao fechar o app, os valores retornam ao padrão inicial.
- O app não realiza operações financeiras reais, serve apenas para fins didáticos.

## Alunos

- Matheus Grego do Amaral: GRR20230979 
- Raul Ferreira Costa Bana: GRR20230987 
- Pedro Henrique de Souza: GRR20230982 
