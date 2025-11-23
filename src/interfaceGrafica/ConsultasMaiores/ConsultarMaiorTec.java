package interfaceGrafica.ConsultasMaiores;

import entities.CatalogoTecnologias;

import javax.swing.*;
import java.awt.*;

//1 - Consultar tecnologia com maior valor: mostra os dados da tecnologia com maior
//valor cadastrado. Se houver empate, mostra todas. Se não há tecnologia cadastrada,
//mostra uma mensagem de erro.
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
public class ConsultarMaiorTec {
    public ConsultarMaiorTec(CatalogoTecnologias tec){
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });


    }
    private JButton finalizarButton;
    private JPanel painel;
    public JPanel getPainel() {
        return painel;
    }
}
