package interfaceGrafica.ConsultasMaiores;

import entidades.*;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//3 - Consultar comprador com maior número de vendas: mostra os dados do
//comprador com maior quantidade de vendas. Se houver empate, mostra todos. Se
//não há comprador cadastrada, mostra uma mensagem de erro.
//4 - Consultar venda com maior valor: mostra os dados da venda de maior valor. Se
//houver empate, mostra todas. Se não há venda cadastrada, mostra uma mensagem
//de erro.
public class ConsultarMaiorCom {
    public ConsultarMaiorCom(CatalogoCompradores com){
        painel.setBackground(Color.PINK);
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });


    }
    private JButton finalizarButton;
    private JPanel painel;
    private JTextArea textoPos;

    private void mostrarDados(CatalogoCompradores com) {
        ArrayList<Comprador> results= com.consultarMaiorCom(com);
        if(results.isEmpty()){
            //codigo para null
            textoPos.setText("ERRO: Nenhum comprador cadastrada.");
            return;
        }
        textoPos.setText(results.toString());
    }


    public JPanel getPainel(CatalogoCompradores com) {
        mostrarDados(com);
        return painel;
    }
}
