package interfaceGrafica;

import entities.CatalogoCompradores;

import javax.swing.*;
import java.awt.*;
public class RelatorioTelaCom {

    private JPanel painel;
//    private JButton mostrarDadosButton;
    private JButton finalizarButton;
    private JTextArea textoPos;

    public RelatorioTelaCom(CatalogoCompradores catalogo) {

//        mostrarDadosButton.addActionListener(e -> mostrarDados(catalogo));
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void mostrarDados(CatalogoCompradores catalogo) {
        textoPos.setText(catalogo.listarCompradores());
    }
    public JPanel getPainel(CatalogoCompradores catalogo) {


        mostrarDados(catalogo);
        return painel;
    }
}
