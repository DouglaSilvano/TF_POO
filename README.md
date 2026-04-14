# ACMETech — Sistema de Vendas de Tecnologias

Trabalho Final da disciplina de Programação Orientada a Objetos (PUCRS), desenvolvido em grupo com Sofia e Paulo, sob orientação do professor Yamaguti.

O sistema simula uma empresa intermediadora de tecnologias. A gente modelou tudo do zero em Java puro: entidades, catálogos, lógica de negócio e uma interface gráfica feita em Swing.

## O que o sistema faz?

* **Cadastro de Fornecedores:** Registra fornecedores com data de fundação e área de atuação (TI, Androides, Emergente ou Alimentos).
* **Cadastro de Tecnologias:** Vincula cada tecnologia a um fornecedor, com modelo, valor base, peso e temperatura.
* **Cadastro de Compradores:** Registra compradores com país e e-mail.
* **Registro de Vendas:** Calcula o valor final automaticamente — aplica um acréscimo pela área do fornecedor (ex: EMERGENTE = +25%) e um desconto progressivo de 1% por compra anterior do mesmo comprador, limitado a 10%.
* **Consultas:** Tecnologia com maior valor, fornecedor com maior número de tecnologias, comprador com mais vendas e venda de maior valor — todas com suporte a empate.
* **Relatórios:** Visualização completa de fornecedores, tecnologias, compradores e vendas direto na tela.
* **Persistência de dados:** Salva e carrega tudo em CSV ou JSON, implementado sem bibliotecas externas, só Java puro.

## Tecnologias usadas

* **Linguagem:** Java
* **Interface gráfica:** Swing com IntelliJ GUI Designer (arquivos `.form`)
* **Coleções:** `ArrayList`, `List`, `Queue` / `LinkedList`
* **Persistência:** CSV e JSON manual, sem libs externas

## Como rodar

1. Clone o repositório ou baixe o ZIP com `git clone <url-do-repositorio>`
2. Abra o projeto no IntelliJ IDEA
3. Certifique-se de que os arquivos `PARTICIPANTESENTRADA.CSV`, `TECNOLOGIASENTRADA.CSV` e `VENDASENTRADA.CSV` estão na pasta `recursos/`
4. Execute o `Main.java`

## Colaboradores

Código feito, testado e documentado por:

* [Douglas Silvano](https://github.com/DouglaSilvano)
* [Paulo Henrique Johann Trevisan](https://github.com/paulojohann)
* [Iohanna Sofia Dietz Maldaner](https://github.com/iohannamaldaner)
