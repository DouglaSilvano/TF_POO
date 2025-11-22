package interfaceGrafica;

import entities.CatalogoVendas;

import javax.swing.*;
import java.awt.*;

public class TelaRelatorioVenda {

    private JPanel painel;
    private JTextArea textoArea;
    private JButton mostrarButton;
    private JButton fecharButton;

    public TelaRelatorioVenda(CatalogoVendas catalogo) {

        painel.setBackground(Color.PINK);

        mostrarButton.addActionListener(e -> {
            textoArea.setText(catalogo.relatorioVendas());
        });

        fecharButton.addActionListener(e -> {
            Window w = SwingUtilities.getWindowAncestor(painel);
            if (w != null) w.dispose();
        });
    }

    public JPanel getPainel() {
        return painel;
    }
}
