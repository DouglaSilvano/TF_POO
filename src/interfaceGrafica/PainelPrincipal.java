package interfaceGrafica;

import entities.CatalogoCompradores;
import entities.CatalogoFornecedores;
import entities.CatalogoTecnologias;

import javax.swing.*;

public class PainelPrincipal {
    private JPanel painelMenu;
    private JButton botaoFornecedor;
    private JButton botaoTecnologia;
    private JButton botaoComprador;
    private JButton botaoVenda;
    private JButton botaoRelatorioFor;
    private JButton botaoRelatorioTec;
    private JButton botaoRelatorioCom;
    private JButton botaoRelatorioVen;
    CatalogoFornecedores catalogoFor = new CatalogoFornecedores();
    CatalogoTecnologias catalogoTec = new CatalogoTecnologias();
    CatalogoCompradores catalogoCom = new CatalogoCompradores();
    public JPanel getPainel() {
        return painelMenu;
    }

    public PainelPrincipal() {
        botaoFornecedor.addActionListener(e -> abrirTelaFornecedor(catalogoFor));
        botaoTecnologia.addActionListener(e -> abrirTelaTecnologia(catalogoTec));
        botaoComprador.addActionListener(e -> abrirTelaComprador(catalogoCom));
        botaoRelatorioFor.addActionListener(e -> abrirRelatorioFor());
//        botaoRelatorioTec.addActionListener(e -> abrirRelatorioTec());
//        botaoRelatorioCom.addActionListener(e -> abrirRelatorioCom());
    }

    private void abrirTelaFornecedor(CatalogoFornecedores catalogoFor) {
        JFrame f = new JFrame("Cadastro de Fornecedor");
        f.setContentPane(new TelaFornecedor(catalogoFor).getPainel());
        f.pack();
        f.setVisible(true);
    }

   private void abrirTelaTecnologia(CatalogoTecnologias catalogoTec) {
        JFrame f = new JFrame("Cadastro de Tecnologia");
        f.setContentPane(new TelaTecnologia(catalogoTec).getPainel());
        f.pack();
        f.setVisible(true);
    }

    private void abrirTelaComprador(CatalogoCompradores catalogoCom) {
        JFrame f = new JFrame("Cadastro de Comprador");
        f.setContentPane(new TelaComprador(catalogoCom).getPainel());
        f.pack();
        f.setVisible(true);
    }
    private void abrirRelatorioFor(){
        JFrame f = new JFrame("Relatório dos fornecedores");
        f.setContentPane(new TelaRelatorioFor(catalogoFor).getPainel());
        f.pack();
        f.setVisible(true);
    }
//    private void abrirRelatorioTec(){
//        JFrame f = new JFrame("Relatório das tecnologias");
//        f.setContentPane(new TelaRelatorioTec(catalogoTec).getPainel());
//        f.pack();
//        f.setVisible(true);
//    }
//    private void abrirRelatorioCom(){
//        JFrame f = new JFrame("Relatório das compras");
//        f.setContentPane(new TelaRelatorioTec().getPainel());
//        f.pack();
//        f.setVisible(true);
//    }
}

