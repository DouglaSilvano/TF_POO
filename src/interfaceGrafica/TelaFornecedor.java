package interfaceGrafica;

import entidades.CatalogoFornecedores;

import javax.swing.*;
import java.awt.*;

public class TelaFornecedor {
    private JPanel painel;
    private JButton okButton;
    private JButton clearButton;
    private JButton mostrarDadosButton;
    private JButton finalizarButton;
    private JTextArea textoPos;
    private JTextField textoCod;
    private JTextField textoNome;
    private JTextField textoFundacao;
    private JTextField textoArea;
    private JLabel informacaoNecessaria;
    private JLabel Cod;
    private JLabel Nome;
    private JLabel Fundacao;
    private JLabel AreaLabel;



    public TelaFornecedor(CatalogoFornecedores catalogo) {
        painel.setBackground(Color.PINK);

        informacaoNecessaria.setText("Pronto para cadastrar fornecedores.");

        okButton.addActionListener(e -> cadastrarFornecedor(catalogo));
        mostrarDadosButton.addActionListener(e -> mostrarDados(catalogo));
        clearButton.addActionListener(e -> limparCampos());
        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });
    }

    private void cadastrarFornecedor(CatalogoFornecedores catalogo) {
        String codTexto = textoCod.getText().trim();
        String nome = textoNome.getText().trim();
        String fundacaoTexto = textoFundacao.getText().trim();
        String areaTexto = textoArea.getText().trim();

        String mensagem = catalogo.cadastrarFornecedor(codTexto, nome, fundacaoTexto, areaTexto);
        informacaoNecessaria.setText(mensagem);

        if (mensagem.startsWith("Fornecedor")) {
            limparCampos();
        }
    }

    private void mostrarDados(CatalogoFornecedores catalogo) {
        textoPos.setText(catalogo.listarFornecedores());
    }

    private void limparCampos() {
        textoPos.setText("");
        textoCod.setText("");
        textoNome.setText("");
        textoFundacao.setText("");
        textoArea.setText("");
        informacaoNecessaria.setText("Campos limpos!!! >.<");
    }

    public JPanel getPainel() {
        return painel;
    }
}
