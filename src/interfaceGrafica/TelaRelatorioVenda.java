package interfaceGrafica;

import entidades.CatalogoVendas;

import javax.swing.*;
import java.awt.*;

public class TelaRelatorioVenda {

    private JPanel painel;
    private JButton finalizarButton;
    private JTextArea textoPos;

    public TelaRelatorioVenda(CatalogoVendas catalogo) {
        painel.setBackground(Color.PINK);

        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });

        textoPos.setText(catalogo.relatorioVendas());
    }

    public JPanel getPainel() {
        return painel;
    }
}
