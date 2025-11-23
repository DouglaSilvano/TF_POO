package interfaceGrafica;


import entities.CatalogoTecnologias;

import javax.swing.*;
import java.awt.*;
public class RelatorioTelaTec {

    private JPanel painel;
//    private JButton mostrarDadosButton;
    private JButton finalizarButton;
    private JTextArea textoPos;

    public RelatorioTelaTec(CatalogoTecnologias catalogo) {

//        mostrarDadosButton.addActionListener(e -> mostrarDados(catalogo));
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void mostrarDados(CatalogoTecnologias catalogo) {
        textoPos.setText(catalogo.mostrarDados());
    }
    public JPanel getPainel(CatalogoTecnologias catalogo) {


        mostrarDados(catalogo);
        return painel;
    }
}
