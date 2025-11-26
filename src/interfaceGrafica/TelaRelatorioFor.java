package interfaceGrafica;

import javax.swing.*;
import entities.CatalogoFornecedores;

import javax.swing.*;
import java.awt.*;
public class TelaRelatorioFor {

    private JPanel painel;
    private JButton mostrarDadosButton;
    private JButton finalizarButton;
    private JTextArea textoPos;

    public TelaRelatorioFor(CatalogoFornecedores catalogo) {
        painel.setBackground(Color.PINK);
        mostrarDadosButton.addActionListener(e -> mostrarDados(catalogo));
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void mostrarDados(CatalogoFornecedores catalogo) {
        textoPos.setText(catalogo.listarFornecedores());
    }
    public JPanel getPainel() {
        return painel;
    }
}
