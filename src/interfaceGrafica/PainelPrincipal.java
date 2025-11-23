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
    private JButton botaoAlterarCom;
    private JButton easterEggButton;
    CatalogoFornecedores catalogoFor = new CatalogoFornecedores();
    CatalogoTecnologias catalogoTec = new CatalogoTecnologias();
    CatalogoCompradores catalogoCom = new CatalogoCompradores();
    CatalogoVendas catalogoVen = new CatalogoVendas();
    public JPanel getPainel() {
        return painelMenu;
    }

    public PainelPrincipal() {
        botaoFornecedor.addActionListener(e -> abrirTelaFornecedor(catalogoFor));
        botaoTecnologia.addActionListener(e -> abrirTelaTecnologia(catalogoTec,catalogoFor));
        botaoComprador.addActionListener(e -> abrirTelaComprador(catalogoCom));
        botaoRelatorioFor.addActionListener(e -> abrirRelatorioFor());
        botaoVenda.addActionListener(e -> abrirTelaVenda());
        botaoRelatorioVen.addActionListener(e -> abrirRelatorioVen());
        botaoRelatorioTec.addActionListener(e -> abrirRelatorioTec());
        botaoRelatorioCom.addActionListener(e -> abrirRelatorioCom());
        botaoAlterarCom.addActionListener(e -> abrirTelaAlterarCom(catalogoCom));
        easterEggButton.addActionListener(e -> adicionarDadosAutomaticamente(catalogoFor,catalogoTec,catalogoCom));
    }

    private void abrirTelaFornecedor(CatalogoFornecedores catalogoFor) {
        JFrame f = new JFrame("Cadastro de Fornecedor");
        f.setContentPane(new TelaFornecedor(catalogoFor).getPainel());
        f.pack();
        f.setVisible(true);
    }

   private void abrirTelaTecnologia(CatalogoTecnologias catalogoTec,CatalogoFornecedores catalogoFor) {
        JFrame f = new JFrame("Cadastro de Tecnologia");
        f.setContentPane(new TelaTecnologia(catalogoTec,catalogoFor).getPainel());
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
    private void abrirTelaVenda() {
        JFrame f = new JFrame("Cadastro de Venda");
        f.setContentPane(new TelaVenda(catalogoVen, catalogoTec, catalogoCom).getPainel());
        f.pack();
        f.setVisible(true);
    }
    private void abrirRelatorioVen() {
        JFrame f = new JFrame("Relatório de Vendas");
        f.setContentPane(new TelaRelatorioVenda(catalogoVen).getPainel());
        f.pack();
        f.setVisible(true);
    }
    private void abrirRelatorioTec(){
        JFrame f = new JFrame("Relatório das tecnologias");
        f.setContentPane(new RelatorioTelaTec(catalogoTec).getPainel(catalogoTec));
        f.pack();
        f.setVisible(true);
    }
    private void abrirRelatorioCom(){
        JFrame f = new JFrame("Relatório das compras");
        f.setContentPane(new RelatorioTelaCom(catalogoCom).getPainel(catalogoCom));
        f.pack();
        f.setVisible(true);
    }
    private void abrirTelaAlterarCom(CatalogoCompradores catalogoCom) {
        JFrame f = new JFrame("Alterar dados de comprador");
        f.setContentPane(new TelaAlterarComprador(catalogoCom).getPainel());
        f.pack();
        f.setLocationRelativeTo(null);
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
        // Utilização da IA para adicionar dados automaticamente
    }
}

