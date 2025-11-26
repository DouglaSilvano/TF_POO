package interfaceGrafica;

import entities.*;
import application.PersistenciaCSV;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import application.PersistenciaJSON;
import application.ACMETech;


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
    private JButton consultarMaiorButton;
    private JButton botaoSalvarCSV;
    private JButton botaoCarregarCSV;
    private JButton botaoSalvarJSON;
    private JButton botaoCarregarJSON;
    private PersistenciaCSV persistenciaCSV = new PersistenciaCSV();
    private PersistenciaJSON persistenciaJSON = new PersistenciaJSON();
    CatalogoFornecedores catalogoFor = new CatalogoFornecedores();
    CatalogoTecnologias catalogoTec = new CatalogoTecnologias();
    CatalogoCompradores catalogoCom = new CatalogoCompradores();
    CatalogoVendas catalogoVen = new CatalogoVendas();
    private ACMETech sistema;
    public JPanel getPainel() {
        return painelMenu;
    }

    public PainelPrincipal() {
        painelMenu.setBackground(Color.PINK);
        botaoFornecedor.addActionListener(e -> abrirTelaFornecedor(catalogoFor));
        botaoTecnologia.addActionListener(e -> abrirTelaTecnologia(catalogoTec,catalogoFor));
        botaoComprador.addActionListener(e -> abrirTelaComprador(catalogoCom));
        botaoRelatorioFor.addActionListener(e -> abrirRelatorioFor());
        botaoVenda.addActionListener(e -> abrirTelaVenda());
        botaoRelatorioVen.addActionListener(e -> abrirRelatorioVen());
        botaoRelatorioTec.addActionListener(e -> abrirRelatorioTec());
        botaoRelatorioCom.addActionListener(e -> abrirRelatorioCom());
        botaoAlterarCom.addActionListener(e -> abrirTelaAlterarCom(catalogoCom));
        easterEggButton.addActionListener(e -> adicionarDadosAutomaticamente(catalogoVen,catalogoFor,catalogoTec,catalogoCom));
        consultarMaiorButton.addActionListener(e -> consultarMaior(catalogoVen,catalogoFor,catalogoCom,catalogoTec));
        botaoSalvarCSV.addActionListener(e -> salvarDadosCSV());
        botaoCarregarCSV.addActionListener(e -> carregarDadosCSV());
        botaoCarregarJSON.addActionListener(e -> carregarDadosJSON());
        botaoSalvarJSON.addActionListener(e -> salvarDadosJSON());
    }

    public PainelPrincipal(ACMETech sistema) {
        this();

        this.sistema = sistema;

        this.catalogoFor = sistema.getCatalogoFornecedores();
        this.catalogoTec = sistema.getCatalogoTecnologias();
        this.catalogoCom = sistema.getCatalogoCompradores();
        this.catalogoVen = sistema.getCatalogoVendas();
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
    private void consultarMaior(CatalogoVendas catalogoVen,
                              CatalogoFornecedores catalogoFor,
                                CatalogoCompradores catalogoCom,
                                CatalogoTecnologias catalogoTec){
        JFrame f = new JFrame("Consultar maior (...)");
        f.setContentPane(new ConsultarMaior(catalogoVen,catalogoFor,catalogoTec,catalogoCom).getPainel());
        f.pack();
        f.setVisible(true);
    }

    private void adicionarDadosAutomaticamente(CatalogoVendas catalogoVen,
                                               CatalogoFornecedores catalogoFor,
                                               CatalogoTecnologias catalogoTec,
                                               CatalogoCompradores catalogoCom) {
        System.out.println("Iniciando carga de dados...");

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
        Tecnologia t1 = null; // Declarando fora do if para uso posterior
        Tecnologia t2 = null;
        Tecnologia t3 = null;

        if (forn1 != null) {
            t1 = new Tecnologia(
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
            t2 = new Tecnologia(
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
            t3 = new Tecnologia(
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

        // -----------------------------------------
        // 4. CADASTRO DE VENDAS (AS 4 VENDAS SOLICITADAS)
        // -----------------------------------------
        // Método: cadastrarVenda(num, data, tec, com)
        System.out.println("Cadastrando 4 Vendas...");

        // Venda 1: Picard compra Chip Neural
        if (t1 != null) {
            catalogoVen.cadastrarVenda("2001", "05/11/2025", t1, comp1);
        }

        // Venda 2: Connor compra Drone Semeador
        if (t2 != null) {
            catalogoVen.cadastrarVenda("2002", "06/11/2025", t2, comp2);
        }

        // Venda 3: Mueller compra Braço Mecânico
        if (t3 != null) {
            catalogoVen.cadastrarVenda("2003", "06/11/2025", t3, comp3);
        }

        // Venda 4: Picard compra Braço Mecânico (Venda duplicada para um comprador)
        if (t3 != null) {
            catalogoVen.cadastrarVenda("2004", "07/11/2025", t3, comp1);
        }

        System.out.println("Carga de dados concluída com sucesso! (Incluindo 4 Vendas)");
    }

    // ===================== SALVAR / CARREGAR CSV =====================

    private void salvarDadosCSV() {   // NOVO
        String nomeBase = JOptionPane.showInputDialog(
                painelMenu,
                "Digite o nome base dos arquivos (sem extensão):",
                "Salvar dados em CSV",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nomeBase == null) {

            return;
        }

        nomeBase = nomeBase.trim();
        if (nomeBase.isEmpty()) {
            JOptionPane.showMessageDialog(
                    painelMenu,
                    "Nome de arquivo inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            persistenciaCSV.salvarTudoCSV(nomeBase, catalogoFor, catalogoTec, catalogoCom, catalogoVen);
            JOptionPane.showMessageDialog(
                    painelMenu,
                    "Dados salvos com sucesso em arquivos CSV.",
                    "Salvar dados",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    painelMenu,
                    "Erro ao salvar dados: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void carregarDadosCSV() {  // NOVO
        String nomeBase = JOptionPane.showInputDialog(
                painelMenu,
                "Digite o nome base dos arquivos (sem extensão):",
                "Carregar dados de CSV",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nomeBase == null) {
            return;
        }

        nomeBase = nomeBase.trim();
        if (nomeBase.isEmpty()) {
            JOptionPane.showMessageDialog(
                    painelMenu,
                    "Nome de arquivo inválido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            persistenciaCSV.carregarTudoCSV(nomeBase, catalogoFor, catalogoTec, catalogoCom, catalogoVen);
            JOptionPane.showMessageDialog(
                    painelMenu,
                    "Dados carregados com sucesso.",
                    "Carregar dados",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    painelMenu,
                    "Erro ao carregar dados: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void salvarDadosJSON() {
        String nomeBase = JOptionPane.showInputDialog(
                painelMenu,
                "Digite o nome base do arquivo JSON (sem extensão):",
                "Salvar dados em JSON",
                JOptionPane.QUESTION_MESSAGE
        );
        if (nomeBase == null) return;
        nomeBase = nomeBase.trim();
        if (nomeBase.isEmpty()) {
            JOptionPane.showMessageDialog(painelMenu, "Nome inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            persistenciaJSON.salvarTudoJSON(nomeBase, catalogoFor, catalogoTec, catalogoCom, catalogoVen);
            JOptionPane.showMessageDialog(painelMenu, "Dados salvos em JSON com sucesso.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(painelMenu, "Erro ao salvar JSON: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarDadosJSON() {
        String nomeBase = JOptionPane.showInputDialog(
                painelMenu,
                "Digite o nome base do arquivo JSON (sem extensão):",
                "Carregar dados de JSON",
                JOptionPane.QUESTION_MESSAGE
        );
        if (nomeBase == null) return;
        nomeBase = nomeBase.trim();
        if (nomeBase.isEmpty()) {
            JOptionPane.showMessageDialog(painelMenu, "Nome inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            persistenciaJSON.carregarTudoJSON(nomeBase, catalogoFor, catalogoTec, catalogoCom, catalogoVen);
            JOptionPane.showMessageDialog(painelMenu, "Dados carregados de JSON com sucesso.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(painelMenu, "Erro ao carregar JSON: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}

