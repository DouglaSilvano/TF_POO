package interfaceGrafica;

import entities.CatalogoCompradores;
import entities.CatalogoFornecedores;
import entities.CatalogoTecnologias;
import entities.CatalogoVendas;
import interfaceGrafica.ConsultasMaiores.*;

import javax.swing.*;
import java.awt.event.ActionListener;


public class ConsultarMaior {
    private JPanel painel;
    private JButton consultarTecnologiaComMaiorButton;
    private JButton consultarFornecedorComMaiorButton;
    private JButton consultarCompradorComMaiorButton;
    private JButton consultarVendaComMaiorButton;

    public ConsultarMaior(CatalogoVendas catalogoVen,
                          CatalogoFornecedores catalogoFor,
                          CatalogoTecnologias catalogoTec,
                          CatalogoCompradores catalogoCom){
        consultarTecnologiaComMaiorButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent x){
                JFrame f = new JFrame("Consultar maior tecnologia?");
                f.setContentPane(new ConsultarMaiorTec(catalogoTec).getPainel());
                f.pack();
                f.setVisible(true);
            }
        });
        consultarFornecedorComMaiorButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent x) {
                JFrame f = new JFrame("Consultar maior fornecedor?");
                f.setContentPane(new ConsultarMaiorFor(catalogoFor).getPainel());
                f.pack();
                f.setVisible(true);
            }
        });
        consultarCompradorComMaiorButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent x) {
                JFrame f = new JFrame("Consultar maior comprador?");
                f.setContentPane(new ConsultarMaiorCom(catalogoCom).getPainel());
                f.pack();
                f.setVisible(true);
            }
        });
        consultarVendaComMaiorButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent x) {
                JFrame f = new JFrame("Consultar maior venda?");
                f.setContentPane(new ConsultarMaiorVen(catalogoVen).getPainel());
                f.pack();
                f.setVisible(true);
            }
        });

    }

    public JPanel getPainel() {
        return painel;
    }
}
