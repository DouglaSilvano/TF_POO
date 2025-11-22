package interfaceGrafica;

import entities.*;

import javax.swing.*;
import java.awt.*;

public class TelaVenda {

    private JPanel painel;
    private JComboBox<String> comboComprador;
    private JComboBox<String> comboTecnologia;
    private JTextField textoNum;
    private JTextField textoData;
    private JButton botaoCadastrar;
    private JButton botaoLimpar;
    private JButton botaoFechar;
    private JTextArea textoPos;

    public TelaVenda(CatalogoVendas catalogoVen,
                     CatalogoTecnologias catalogoTec,
                     CatalogoCompradores catalogoCom) {

        painel.setBackground(Color.PINK);

        carregarCombos(catalogoTec, catalogoCom);

        botaoCadastrar.addActionListener(e -> {
            String num = textoNum.getText().trim();
            String data = textoData.getText().trim();

            Tecnologia tec = catalogoTec.buscarPorModelo(
                    (String) comboTecnologia.getSelectedItem()
            );

            Comprador com = catalogoCom.buscarPorNome(
                    (String) comboComprador.getSelectedItem()
            );

            String msg = catalogoVen.cadastrarVenda(num, data, tec, com);
            textoPos.setText(msg);

            if (msg.startsWith("Venda cadastrada")) {
                textoNum.setText("");
                textoData.setText("");
            }
        });

        botaoLimpar.addActionListener(e -> {
            textoNum.setText("");
            textoData.setText("");
            textoPos.setText("");
        });

        botaoFechar.addActionListener(e -> {
            Window w = SwingUtilities.getWindowAncestor(painel);
            if (w != null) w.dispose();
        });
    }

    private void carregarCombos(CatalogoTecnologias catalogoTec, CatalogoCompradores catalogoCom) {
        for (Tecnologia t : catalogoTec.getLista()) {
            comboTecnologia.addItem(t.getModelo());
        }

        for (Comprador c : catalogoCom.getLista()) {
            comboComprador.addItem(c.getNome());
        }
    }

    public JPanel getPainel() {
        return painel;
    }
}
