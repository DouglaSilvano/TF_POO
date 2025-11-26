package interfaceGrafica;

import javax.swing.*;
import entidades.CatalogoFornecedores;

import java.awt.*;

public class TelaRelatorioFor {

    private JPanel painel;
    private JButton mostrarDadosButton;
    private JButton finalizarButton;
    private JTextArea textoPos;

    public TelaRelatorioFor(CatalogoFornecedores catalogo) {
        painel.setBackground(Color.PINK);

        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });

        mostrarDados(catalogo);
    }

    private void mostrarDados(CatalogoFornecedores catalogo) {
        textoPos.setText(catalogo.listarFornecedores());
    }

    public JPanel getPainel() {
        return painel;
    }
}
