package interfaceGrafica;

import javax.swing.*;

public class AplicacaoMenu extends JFrame {

    public AplicacaoMenu() {
        super("Menu Principal");
        PainelPrincipal menu = new PainelPrincipal();
        this.setContentPane(menu.getPainel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
