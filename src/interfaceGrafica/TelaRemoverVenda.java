package interfaceGrafica;

import entidades.CatalogoVendas;

import javax.swing.*;
import java.awt.*;

public class TelaRemoverVenda {

    private JPanel painel;
    private JButton finalizarButton;
    private JButton removerButton;
    private JTextField textoNumeroVenda;
    private JTextArea textoPos;

    public TelaRemoverVenda(CatalogoVendas catalogo) {
        painel.setBackground(Color.PINK);
        removerButton.addActionListener(e -> {
            String numero = textoNumeroVenda.getText().trim();
            if (numero.isEmpty()) {
                textoPos.setText("Informe um número de venda válido... hehe");
                return;
            }
            String resposta = catalogo.removerVenda(numero); //chamada do metodo que remove!!
            textoPos.setText(resposta);
            textoNumeroVenda.setText("");
        });
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });
    }

    public JPanel getPainel() {
        return painel;
    }
}
