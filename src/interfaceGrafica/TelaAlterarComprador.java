package interfaceGrafica;

import entities.CatalogoCompradores;
import entities.Comprador;

import javax.swing.*;
import java.awt.*;

public class TelaAlterarComprador {

    private JPanel painel;
    private JTextField textoNome;
    private JTextField textoCod;
    private JTextField textoPais;
    private JTextField textoEmail;
    private JButton limparButton;
    private JButton salvarButton;
    private JButton buscarButton;
    private JButton fecharButton;
    private JLabel avisoLabel;

    private CatalogoCompradores catalogo;
    private Long codigoAtual;

    public TelaAlterarComprador(CatalogoCompradores catalogo) {
        painel.setBackground(Color.PINK);
        ajustarTamanho();
        this.catalogo = catalogo;
        this.codigoAtual = null;

        textoCod.setEnabled(true);
        textoCod.setEditable(true);

        bloquearCamposEdicao();
        salvarButton.setEnabled(false);

        buscarButton.addActionListener(e -> buscar());
        salvarButton.addActionListener(e -> salvar());
        limparButton.addActionListener(e -> limpar());
        fecharButton.addActionListener(e -> fechar());
    }

    public JPanel getPainel() {
        return painel;
    }

    private void buscar() {
        avisoLabel.setText("");
        codigoAtual = null;

        String codTexto = textoCod.getText().trim();
        if (codTexto.isEmpty()) {
            avisoLabel.setText("informe o código do comprador.");
            return;
        }

        long cod;
        try {
            cod = Long.parseLong(codTexto);
        } catch (NumberFormatException e) {
            avisoLabel.setText("Código inválido. Digite um número inteiro.");
            return;
        }

        Comprador c = catalogo.buscarPorCodigo(cod);

        if (c == null) {
            avisoLabel.setText("Não existe comprador com o código " + cod + ".");
            limparCamposDados();
            bloquearCamposEdicao();
            salvarButton.setEnabled(false);
            return;
        }

        textoNome.setText(c.getNome());
        textoPais.setText(c.getPais());
        textoEmail.setText(c.getEmail());

        liberarCamposEdicao();
        salvarButton.setEnabled(true);
        codigoAtual = cod;

        avisoLabel.setText("Comprador encontrado. Altere os dados e clique em salvar.");
    }

    private void salvar() {
        avisoLabel.setText("");

        if (codigoAtual == null) {
            avisoLabel.setText("Busque primeiro um comprador válido pelo código.");
            return;
        }

        String nome = textoNome.getText().trim();
        String pais = textoPais.getText().trim();
        String email = textoEmail.getText().trim();

        if (nome.isEmpty() || pais.isEmpty() || email.isEmpty()) {
            avisoLabel.setText("Nome, País e E-mail devem ser preenchidos.");
            return;
        }

        boolean ok = catalogo.atualizarComprador(codigoAtual, nome, pais, email);

        if (ok) {
            avisoLabel.setText("Dados do comprador atualizados com sucesso.");
        } else {
            avisoLabel.setText("Erro ao atualizar: comprador não encontrado.");
            bloquearCamposEdicao();
            salvarButton.setEnabled(false);
            codigoAtual = null;
        }
    }

    private void limpar() {
        textoCod.setText("");
        limparCamposDados();
        bloquearCamposEdicao();
        salvarButton.setEnabled(false);
        codigoAtual = null;
        avisoLabel.setText("Digite o código do comprador e clique em Buscar.");
    }

    private void fechar() {
        java.awt.Window janela = SwingUtilities.getWindowAncestor(painel);
        if (janela != null) {
            janela.dispose();
        }

    }

    private void limparCamposDados() {
        textoNome.setText("");
        textoPais.setText("");
        textoEmail.setText("");
    }

    private void bloquearCamposEdicao() {
        textoNome.setEnabled(false);
        textoPais.setEnabled(false);
        textoEmail.setEnabled(false);
    }

    private void liberarCamposEdicao() {
        textoNome.setEnabled(true);
        textoPais.setEnabled(true);
        textoEmail.setEnabled(true);
    }

    private void ajustarTamanho() {
        Dimension d = new Dimension(300, 28); // largura, altura
        textoNome.setPreferredSize(d);
        textoCod.setPreferredSize(d);
        textoPais.setPreferredSize(d);
        textoEmail.setPreferredSize(d);
    }
}

