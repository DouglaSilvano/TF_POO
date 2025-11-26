package interfaceGrafica;

import entidades.CatalogoFornecedores;

import javax.swing.*;
import java.awt.*;

public class AplicacaoFornecedores extends JFrame {
    private TelaFornecedor telaFornecedor;
    private CatalogoFornecedores catalogo = new CatalogoFornecedores();

    public AplicacaoFornecedores() {
        super("Cadastro de Fornecedores");
        telaFornecedor = new TelaFornecedor(catalogo);
        add(telaFornecedor.getPainel());
        getContentPane().setBackground(Color.PINK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);
        setVisible(true);
    }

}
