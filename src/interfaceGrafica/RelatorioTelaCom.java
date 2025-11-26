package interfaceGrafica;

import entidades.CatalogoCompradores;

import javax.swing.*;
import java.awt.*;

public class RelatorioTelaCom {

    private JPanel painel;
    private JButton finalizarButton;
    private JTextArea textoPos;

    public RelatorioTelaCom(CatalogoCompradores catalogo) {
        painel.setBackground(Color.PINK);

        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });

        mostrarDados(catalogo);
    }

    private void mostrarDados(CatalogoCompradores catalogo) {
        textoPos.setText(catalogo.listarCompradores());
    }

    public JPanel getPainel(CatalogoCompradores catalogo) {
        mostrarDados(catalogo);
        return painel;
    }

    public JPanel getPainel() {
        return painel;
    }
}
