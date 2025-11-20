package interfaceGrafica;

import entities.*;

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
    private JButton botaoDadosAutomatico;
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
        botaoDadosAutomatico.addActionListener(e-> adicionarDadosAutomaticamente(catalogoFor,catalogoTec,catalogoCom));
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
    private void adicionarDadosAutomaticamente(CatalogoFornecedores catalogoFor,CatalogoTecnologias catalogoTec,CatalogoCompradores catalogoCom){
        System.out.println("Iniciando carga de dados...");
        // Utilização da IA para adicionar dados automaticamente
        // -----------------------------------------
        // 1. CADASTRO DE FORNECEDORES
        // -----------------------------------------
        // O seu metodo requer Strings: (cod, nome, data, area)
        catalogoFor.cadastrarFornecedor("101", "Cyber Dynamics", "15/05/2010", "TI");
        catalogoFor.cadastrarFornecedor("102", "Agro Future", "20/08/1995", "ALIMENTOS");
        catalogoFor.cadastrarFornecedor("103", "RoboCorp Inc", "10/01/2022", "ANDROIDES");

        // Recuperamos os objetos reais para vincular às tecnologias
        Fornecedor forn1 = catalogoFor.buscarFornecedor(101);
        Fornecedor forn2 = catalogoFor.buscarFornecedor(102);
        Fornecedor forn3 = catalogoFor.buscarFornecedor(103);

        // -----------------------------------------
        // 2. CADASTRO DE TECNOLOGIAS
        // -----------------------------------------
        // Construtor: (id, modelo, descricao, peso, valorBase, temperatura, fornecedor)

        if (forn1 != null) {
            Tecnologia t1 = new Tecnologia(
                    5001,
                    "Chip Neural A1",
                    "Processador de IA avançado",
                    0.05,
                    1500.00,
                    45.5,
                    forn1
            );
            catalogoTec.cadastrarTecnologia(t1);
        }

        if (forn2 != null) {
            Tecnologia t2 = new Tecnologia(
                    5002,
                    "Drone Semeador X",
                    "Drone autônomo para plantio",
                    12.5,
                    8500.00,
                    30.0,
                    forn2
            );
            catalogoTec.cadastrarTecnologia(t2);
        }

        if (forn3 != null) {
            Tecnologia t3 = new Tecnologia(
                    5003,
                    "Braço Mecânico V2",
                    "Auxiliar de montagem industrial",
                    80.0,
                    12000.00,
                    60.0,
                    forn3
            );
            catalogoTec.cadastrarTecnologia(t3);
        }

        // -----------------------------------------
        // 3. CADASTRO DE COMPRADORES
        // -----------------------------------------
        // Construtor: (cod, nome, pais, email)

        Comprador comp1 = new Comprador(9001, "Jean Picard", "França", "jean.picard@enterprise.eu");
        Comprador comp2 = new Comprador(9002, "Sarah Connor", "EUA", "sarah@resistencia.com");
        Comprador comp3 = new Comprador(9003, "Hans Mueller", "Alemanha", "hans@tech.de");

        catalogoCom.cadastrar(comp1);
        catalogoCom.cadastrar(comp2);
        catalogoCom.cadastrar(comp3);

        System.out.println("Carga de dados concluída com sucesso!");
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

