package entities;

import java.util.List;

//Classe criada para poder retornar a analise da consulta das maiores tecnologias, já que precisa
// do resultado da maior quantidade e da lista de fornecedores

public class ResultadoAnalise {
    private List<Fornecedor> listaFornecedores;
    private int quantidade;

    public ResultadoAnalise(List<Fornecedor> listaFornecedores, int quantidade) {
        this.listaFornecedores = listaFornecedores;
        this.quantidade = quantidade;
    }
    public ResultadoAnalise() {
        this.listaFornecedores = null;
        this.quantidade = 0;
    }

    public List<Fornecedor> getListaFornecedores() {
        return listaFornecedores;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
