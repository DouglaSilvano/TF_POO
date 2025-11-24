package interfaceGrafica.ConsultasMaiores;


import entities.CatalogoVendas;

import javax.swing.*;
import java.awt.*;


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
    private JButton finalizarButton;
    private JPanel painel;
    private JTextArea textoPos;

    public JPanel getPainel() {
        return painel;
    }
}
