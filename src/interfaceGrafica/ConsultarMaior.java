package interfaceGrafica;

import entities.CatalogoCompradores;
import entities.CatalogoFornecedores;
import entities.CatalogoTecnologias;
import entities.CatalogoVendas;
import interfaceGrafica.ConsultasMaiores.*;

import javax.swing.*;
import java.awt.*;
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
        painel.setBackground(Color.PINK);
        consultarTecnologiaComMaiorButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent x){
                JFrame f = new JFrame("Consulta maiores tecnologias");
                f.setContentPane(new ConsultarMaiorTec(catalogoTec).getPainel(catalogoTec));
                f.pack();
                f.setVisible(true);
            }
        });
        consultarFornecedorComMaiorButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent x) {
                JFrame f = new JFrame("Consultar maiores fornecedores");
                f.setContentPane(new ConsultarMaiorFor(catalogoFor,catalogoTec).getPainel(catalogoFor,catalogoTec));
                f.pack();
                f.setVisible(true);
            }
        });
        consultarCompradorComMaiorButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent x) {
                JFrame f = new JFrame("Consultar maiores compradores");
                f.setContentPane(new ConsultarMaiorCom(catalogoCom).getPainel(catalogoCom));
                f.pack();
                f.setVisible(true);
            }
        });
        consultarVendaComMaiorButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent x) {
                JFrame f = new JFrame("Consultar maiores vendas");
                f.setContentPane(new ConsultarMaiorVen(catalogoVen).getPainel(catalogoVen));
                f.pack();
                f.setVisible(true);
            }
        });

    }

    public JPanel getPainel() {
        return painel;
    }
}
