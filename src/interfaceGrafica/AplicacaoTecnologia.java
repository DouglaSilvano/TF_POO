package interfaceGrafica;

import entidades.CatalogoFornecedores;
import entidades.CatalogoTecnologias;

import javax.swing.*;

public class AplicacaoTecnologia extends JFrame {
    private TelaTecnologia telaTecnologia;

    public AplicacaoTecnologia(CatalogoTecnologias catalogoTec, CatalogoFornecedores catalogoFor) {
        super();
        telaTecnologia = new TelaTecnologia(catalogoTec,catalogoFor);
        add(telaTecnologia.getPainel());
        setSize(800, 400);
        setTitle("Cadastro de Tecnologia");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}