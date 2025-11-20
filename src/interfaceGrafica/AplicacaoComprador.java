package interfaceGrafica;

import entities.CatalogoCompradores;

import javax.swing.*;

public class AplicacaoComprador extends JFrame {

        private TelaComprador telaComprador;

        public AplicacaoComprador(CatalogoCompradores catalogoCom) {
            super("Cadastro de Compradores");
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Falha ao carregar o Look and Feel do sistema.");
            }

            telaComprador = new TelaComprador(catalogoCom);

            this.add(telaComprador.getPainelPrincipal());

            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(800, 420);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
}

