package interfaceGrafica;

import entities.CatalogoTecnologias;

import javax.swing.*;
import java.awt.*;

public class RelatorioTelaTec {

    private JPanel painel;
    private JButton finalizarButton;
    private JTextArea textoPos;

    public RelatorioTelaTec(CatalogoTecnologias catalogo) {
        painel.setBackground(Color.PINK);

        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });

        mostrarDados(catalogo);
    }

    private void mostrarDados(CatalogoTecnologias catalogo) {
        textoPos.setText(catalogo.mostrarDados());
    }

    public JPanel getPainel(CatalogoTecnologias catalogo) {
        mostrarDados(catalogo);
        return painel;
    }

    public JPanel getPainel() {
        return painel;
    }
}
