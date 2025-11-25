package interfaceGrafica.ConsultasMaiores;


import entities.CatalogoFornecedores;
import entities.CatalogoVendas;
import entities.Venda;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


//4 - Consultar venda com maior valor: mostra os dados da venda de maior valor. Se
//houver empate, mostra todas. Se não há venda cadastrada, mostra uma mensagem
//de erro.
public class ConsultarMaiorVen {
    public ConsultarMaiorVen(CatalogoVendas ven){
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });


    }
    private void mostrarDados(CatalogoVendas catalogoVen){
        ArrayList<Venda> maiorVendas = catalogoVen.consultarVendaMaior();
        if(maiorVendas.isEmpty()){
            textoPos.setText("ERRO: Nenhuma venda cadastrada ainda.");
            return;
        }
        textoPos.setText(maiorVendas.toString());
        return;
    }
    private JButton finalizarButton;
    private JPanel painel;
    private JTextArea textoPos;

    public JPanel getPainel(CatalogoVendas catalogoVen) {
        mostrarDados(catalogoVen);
        return painel;
    }
}
