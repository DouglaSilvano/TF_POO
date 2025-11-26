package interfaceGrafica;

import entidades.*;
import aplicacao.PersistenciaCSV;
import aplicacao.PersistenciaJSON;
import aplicacao.ACMETech;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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
    CatalogoTecnologias  catalogoTec = new CatalogoTecnologias();
    CatalogoCompradores  catalogoCom = new CatalogoCompradores();
    CatalogoVendas       catalogoVen = new CatalogoVendas();

    // Referência para o sistema “raiz” (ACMETech)
    private ACMETech sistema;

    public JPanel getPainel() {
        return painelMenu;
    }

    // Construtor padrão (se alguém instanciar sem ACMETech, ainda funciona)
    public PainelPrincipal() {
        painelMenu.setBackground(Color.PINK);

        botaoFornecedor.addActionListener(e -> abrirTelaFornecedor(catalogoFor));
        botaoTecnologia.addActionListener(e -> abrirTelaTecnologia(catalogoTec, catalogoFor));
        botaoComprador.addActionListener(e -> abrirTelaComprador(catalogoCom));
        botaoRelatorioFor.addActionListener(e -> abrirRelatorioFor());
        botaoVenda.addActionListener(e -> abrirTelaVenda());
        botaoRelatorioVen.addActionListener(e -> abrirRelatorioVen());
        botaoRelatorioTec.addActionListener(e -> abrirRelatorioTec());
        botaoRelatorioCom.addActionListener(e -> abrirRelatorioCom());
        botaoAlterarCom.addActionListener(e -> abrirTelaAlterarCom(catalogoCom));
        easterEggButton.addActionListener(e -> adicionarDadosAutomaticamente(catalogoVen, catalogoFor, catalogoTec, catalogoCom));
        consultarMaiorButton.addActionListener(e -> consultarMaior(catalogoVen, catalogoFor, catalogoCom, catalogoTec));
        botaoSalvarCSV.addActionListener(e -> salvarDadosCSV());
        botaoCarregarCSV.addActionListener(e -> carregarDadosCSV());
        botaoCarregarJSON.addActionListener(e -> carregarDadosJSON());
        botaoSalvarJSON.addActionListener(e -> salvarDadosJSON());
    }

    // Construtor usado quando você quer reaproveitar os catálogos do ACMETech
    public PainelPrincipal(ACMETech sistema) {
        this();              // configura os listeners normalmente
        this.sistema = sistema;

        // Sobrescreve os catálogos com aqueles gerenciados pelo ACMETech
        this.catalogoFor = sistema.getCatalogoFornecedores();
        this.catalogoTec = sistema.getCatalogoTecnologias();
        this.catalogoCom = sistema.getCatalogoCompradores();
        this.catalogoVen = sistema.getCatalogoVendas();
    }

    // ===================== ABERTURA DE TELAS =====================

    private void abrirTelaFornecedor(CatalogoFornecedores catalogoFor) {
        abrirJanelaPadrao(
                "Cadastro de Fornecedor",
                new TelaFornecedor(catalogoFor).getPainel()
        );
    }

    private void abrirTelaTecnologia(CatalogoTecnologias catalogoTec,
                                     CatalogoFornecedores catalogoFor) {
        abrirJanelaPadrao(
                "Cadastro de Tecnologia",
                new TelaTecnologia(catalogoTec, catalogoFor).getPainel()
        );
    }

    private void abrirTelaComprador(CatalogoCompradores catalogoCom) {
        abrirJanelaPadrao(
                "Cadastro de Comprador",
                new TelaComprador(catalogoCom).getPainel()
        );
    }

    private void abrirRelatorioFor() {
        abrirJanelaPadrao(
                "Relatório dos fornecedores",
                new TelaRelatorioFor(catalogoFor).getPainel()
        );
    }

    private void abrirTelaVenda() {
        abrirJanelaPadrao(
                "Cadastro de Venda",
                new TelaVenda(catalogoVen, catalogoTec, catalogoCom).getPainel()
        );
    }

    private void abrirRelatorioVen() {
        abrirJanelaPadrao(
                "Relatório de Vendas",
                new TelaRelatorioVenda(catalogoVen).getPainel()
        );
    }

    private void abrirRelatorioTec() {
        abrirJanelaPadrao(
                "Relatório das tecnologias",
                new RelatorioTelaTec(catalogoTec).getPainel(catalogoTec)
        );
    }

    private void abrirRelatorioCom() {
        abrirJanelaPadrao(
                "Relatório Compradores",
                new RelatorioTelaCom(catalogoCom).getPainel(catalogoCom)
        );
    }

    private void abrirTelaAlterarCom(CatalogoCompradores catalogoCom) {
        abrirJanelaPadrao(
                "Alterar dados de comprador",
                new TelaAlterarComprador(catalogoCom).getPainel()
        );
    }

    private void consultarMaior(CatalogoVendas catalogoVen,
                                CatalogoFornecedores catalogoFor,
                                CatalogoCompradores catalogoCom,
                                CatalogoTecnologias catalogoTec) {

        abrirJanelaPadrao(
                "Consultar maior (...)",
                new ConsultarMaior(catalogoVen, catalogoFor, catalogoTec, catalogoCom).getPainel()
        );
    }

    // ===================== EASTER EGG / DADOS AUTOMÁTICOS =====================

    private void adicionarDadosAutomaticamente(CatalogoVendas catalogoVen,
                                               CatalogoFornecedores catalogoFor,
                                               CatalogoTecnologias catalogoTec,
                                               CatalogoCompradores catalogoCom) {
        System.out.println("Iniciando carga de dados...");

        catalogoFor.cadastrarFornecedor("101", "Cyber Dynamics", "15/05/2010", "TI");
        catalogoFor.cadastrarFornecedor("102", "Agro Future", "20/08/1995", "ALIMENTOS");
        catalogoFor.cadastrarFornecedor("103", "RoboCorp Inc", "10/01/2022", "ANDROIDES");

        Fornecedor forn1 = catalogoFor.buscarFornecedor(101);
        Fornecedor forn2 = catalogoFor.buscarFornecedor(102);
        Fornecedor forn3 = catalogoFor.buscarFornecedor(103);

        Tecnologia t1 = null;
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

        Comprador comp1 = new Comprador(9001, "Jean Picard", "França", "jean.picard@enterprise.eu");
        Comprador comp2 = new Comprador(9002, "Sarah Connor", "EUA", "sarah@resistencia.com");
        Comprador comp3 = new Comprador(9003, "Hans Mueller", "Alemanha", "hans@tech.de");

        catalogoCom.cadastrar(comp1);
        catalogoCom.cadastrar(comp2);
        catalogoCom.cadastrar(comp3);



        /*
        System.out.println("Cadastrando 4 Vendas...");
                 if (t1 != null) {
            catalogoVen.cadastrarVenda("2001", "05/11/2025", t1, comp1);
        }
        if (t2 != null) {
            catalogoVen.cadastrarVenda("2002", "06/11/2025", t2, comp2);
        }
        if (t3 != null) {
            catalogoVen.cadastrarVenda("2003", "06/11/2025", t3, comp3);
        }
        if (t3 != null) {
            catalogoVen.cadastrarVenda("2004", "07/11/2025", t3, comp1);
        }

        System.out.println("Carga de dados concluída com sucesso! (Incluindo 4 Vendas)");
     */
    }

    // ===================== SALVAR / CARREGAR CSV =====================

    private void salvarDadosCSV() {
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

    private void carregarDadosCSV() {
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

    // ===================== PADRONIZAÇÃO DAS JANELAS =====================

    private void abrirJanelaPadrao(String titulo, JPanel painel) {
        JFrame f = new JFrame(titulo);
        f.setContentPane(painel);
        f.setSize(900, 600);
        f.setLocationRelativeTo(null);
        f.setResizable(true);
        f.setVisible(true);
    }
}
