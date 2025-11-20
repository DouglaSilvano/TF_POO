package interfaceGrafica;

import entities.CatalogoTecnologias;

import javax.swing.*;

public class AplicacaoTecnologia extends JFrame {
    private TelaTecnologia telaTecnologia;

    public AplicacaoTecnologia(CatalogoTecnologias catalogoTec) {
        super();
        telaTecnologia = new TelaTecnologia(catalogoTec);
        add(telaTecnologia.getPainel());
        setSize(800, 400);
        setTitle("Cadastro de Tecnologia");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}