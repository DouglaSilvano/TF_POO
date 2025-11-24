package interfaceGrafica.ConsultasMaiores;

import entities.CatalogoFornecedores;
import entities.CatalogoTecnologias;
import entities.ResultadoAnalise;
import entities.Tecnologia;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


//2 - Consultar fornecedor com maior número de tecnologias: mostra os dados do
//fornecedor com maior quantidade de tecnologias, e a quantidade de tecnologias
//correspondente. Se houver empate, mostra todos. Se não há fornecedor cadastrada,
//mostra uma mensagem de erro.
//3 - Consultar comprador com maior número de vendas: mostra os dados do
//comprador com maior quantidade de vendas. Se houver empate, mostra todos. Se
//não há comprador cadastrada, mostra uma mensagem de erro.
//4 - Consultar venda com maior valor: mostra os dados da venda de maior valor. Se
//houver empate, mostra todas. Se não há venda cadastrada, mostra uma mensagem
//de erro.
public class ConsultarMaiorFor {
    public ConsultarMaiorFor(CatalogoFornecedores catalogoFor,CatalogoTecnologias catalogoTec){
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });
    }
    private void mostrarDados(CatalogoFornecedores catalogoFor, CatalogoTecnologias catalogoTec) {
        ResultadoAnalise resultado;
        resultado = catalogoFor.consultarFornecedorMaiorQtde(catalogoTec);
        if(resultado.getQuantidade() ==0){
            //codigo para null
            textoPos.setText("ERRO: Nenhuma tecnologia cadastrada ainda.");
            return;
        }
        String resultadoJunto = "Quantidade de tecnologias correspondente: " + resultado.getQuantidade() + "\n"
                                + resultado.getListaFornecedores().toString();

        textoPos.setText(resultadoJunto);
    }
    private JButton finalizarButton;
    private JPanel painel;
    private JTextArea textoPos;

    public JPanel getPainel(CatalogoFornecedores catalogoFor,CatalogoTecnologias catalogoTec) {
        mostrarDados(catalogoFor,catalogoTec);
        return painel;
    }
}
