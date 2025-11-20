package interfaceGrafica;

import entities.CatalogoCompradores;
import entities.Comprador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.awt.Window;

public class TelaComprador implements ActionListener {

    private JPanel painelPrincipal;
    private JTextField codigo;
    private JTextField nome;
    private JTextField pais;
    private JTextField email;
    private JTextArea areaMensagens;
    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnMostrarTodos;
    private JButton btnFechar;

    CatalogoCompradores catalogoCom;

    public TelaComprador(CatalogoCompradores catalogoCom) {
        this.catalogoCom = catalogoCom;

        btnCadastrar.addActionListener(this);
        btnLimpar.addActionListener(this);
        btnMostrarTodos.addActionListener(this);

        btnFechar.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painelPrincipal);
            if (window != null) {
                window.dispose();
            }
        });
    }

    public JPanel getPainelPrincipal() {
        return painelPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnCadastrar) {
                cadastrarComprador();
            } else if (e.getSource() == btnLimpar) {
                limparCampos();
            } else if (e.getSource() == btnMostrarTodos) {
                mostrarTodos();
            }
        } catch (Exception ex) {
            areaMensagens.setText("ERRO INESPERADO: " + ex.getMessage());
        }
    }

    private void cadastrarComprador() {
        long cod;
        areaMensagens.setText("");

        try {
            cod = Long.parseLong(codigo.getText().trim());
        } catch (NumberFormatException ex) {
            areaMensagens.setText("ERRO: O código deve ser um valor numérico.");
            return;
        }

        String nome = this.nome.getText().trim();
        String pais = this.pais.getText().trim();
        String email = this.email.getText().trim();

        if (nome.isEmpty() || pais.isEmpty() || email.isEmpty() || codigo.getText().trim().isEmpty()) {
            areaMensagens.setText("ERRO: Todos os campos devem ser preenchidos.");
            return;
        }

        Comprador novoComprador = new Comprador(cod, nome, pais, email);

        boolean sucesso = catalogoCom.cadastrar(novoComprador);

        if (sucesso) {
            areaMensagens.setText("SUCESSO: Comprador cadastrado!\n" + novoComprador.toString());
            limparCamposSemMensagem();
        } else {
            areaMensagens.setText("ERRO: Código (" + cod + ") já cadastrado.");
        }
    }

    private void mostrarTodos() {
        areaMensagens.setText("");

        Collection<Comprador> todos = catalogoCom.getTodosCompradores();

        if (todos.isEmpty()) {
            areaMensagens.setText("Nenhum comprador cadastrado no sistema.");
        } else {
            areaMensagens.setText("--- LISTA DE COMPRADORES (Ordem por Código) ---\n");
            for (Comprador c : todos) {
                areaMensagens.append(c.geraDescricao() + "\n");
            }
        }
    }

    private void limparCampos() {
        codigo.setText("");
        nome.setText("");
        pais.setText("");
        email.setText("");
        areaMensagens.setText("");
    }

    private void limparCamposSemMensagem() {
        codigo.setText("");
        nome.setText("");
        pais.setText("");
        email.setText("");
    }
    public JPanel getPainel() {
        return painelPrincipal;
    }
}