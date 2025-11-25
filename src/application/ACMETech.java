package application;

import entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ACMETech {

    private CatalogoFornecedores catalogoFor;
    private CatalogoTecnologias catalogoTec;
    private CatalogoCompradores catalogoCom;
    private CatalogoVendas catalogoVen;

    // Nomes dos arquivos de ENTRADA (ajuste se o Apêndice tiver nomes diferentes)
    private static final String ARQ_FORNECEDORES = "FORNECEDORESENTRADA.CSV";
    private static final String ARQ_COMPRADORES  = "COMPRADORESENTRADA.CSV";
    private static final String ARQ_TECNOLOGIAS  = "TECNOLOGIASENTRADA.CSV";
    private static final String ARQ_VENDAS       = "VENDASENTRADA.CSV";

    public ACMETech() {
        catalogoFor = new CatalogoFornecedores();
        catalogoTec = new CatalogoTecnologias();
        catalogoCom = new CatalogoCompradores();
        catalogoVen = new CatalogoVendas();
    }

    /**
     * Lê arquivos de ENTRADA (definidos no Apêndice do enunciado)
     * e usa UMA FILA para armazenar as vendas antes de cadastrá-las.
     */
    public void inicializar() {
        try {
            carregarFornecedoresEntrada(ARQ_FORNECEDORES);
            carregarCompradoresEntrada(ARQ_COMPRADORES);
            carregarTecnologiasEntrada(ARQ_TECNOLOGIAS);
            carregarVendasEntradaComFila(ARQ_VENDAS);
        } catch (IOException e) {
            System.out.println("Erro ao inicializar dados de entrada: " + e.getMessage());
        }
    }

    public void executar() {
        // Se existisse interface de texto, o loop iria aqui.
        // Como você usa Swing, o "main" pode criar um ACMETech,
        // chamar inicializar() e depois abrir a tela inicial, se quiser.
    }

    // Getters – se quiser usar esses catálogos na GUI em vez de criar novos
    public CatalogoFornecedores getCatalogoFornecedores() { return catalogoFor; }
    public CatalogoTecnologias  getCatalogoTecnologias()  { return catalogoTec; }
    public CatalogoCompradores  getCatalogoCompradores()  { return catalogoCom; }
    public CatalogoVendas       getCatalogoVendas()       { return catalogoVen; }

    // =========================================================
    //      LEITURA DOS ARQUIVOS DE ENTRADA (SEM FILA AINDA)
    // =========================================================

    /**
     * Formato sugerido (ajuste se o Apêndice tiver outro):
     * cod;nome;fundacao;area
     */
    private void carregarFornecedoresEntrada(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho, se existir

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 4) continue;

                String codStr   = partes[0].trim();
                String nome     = partes[1].trim();
                String fundacao = partes[2].trim();
                String area     = partes[3].trim();

                // Reaproveita validação do catálogo:
                catalogoFor.cadastrarFornecedor(codStr, nome, fundacao, area);
            }
        }
    }

    /**
     * Formato sugerido:
     * cod;nome;pais;email
     */
    private void carregarCompradoresEntrada(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho, se existir

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 4) continue;

                long cod    = Long.parseLong(partes[0].trim());
                String nome = partes[1].trim();
                String pais = partes[2].trim();
                String email= partes[3].trim();

                Comprador c = new Comprador(cod, nome, pais, email);
                catalogoCom.cadastrar(c);
            }
        }
    }

    /**
     * Formato sugerido:
     * id;modelo;descricao;valorBase;peso;temperatura;codFornecedor
     */
    private void carregarTecnologiasEntrada(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho, se existir

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 7) continue;

                long id       = Long.parseLong(partes[0].trim());
                String modelo = partes[1].trim();
                String desc   = partes[2].trim();
                double valor  = Double.parseDouble(partes[3].trim());
                double peso   = Double.parseDouble(partes[4].trim());
                double temp   = Double.parseDouble(partes[5].trim());
                long codFor   = Long.parseLong(partes[6].trim());

                Fornecedor f = catalogoFor.buscarFornecedor(codFor);
                Tecnologia t = new Tecnologia(id, modelo, desc, peso, valor, temp, f);
                catalogoTec.cadastrarTecnologia(t);
            }
        }
    }

    // =========================================================
    //      AQUI ENTRA A FILA (REQUISITO DO PDF)
    // =========================================================

    /**
     * Formato sugerido:
     * num;data;idTecnologia;codComprador
     *
     * 1) Lê todas as vendas e põe em uma FILA (Queue<VendaEntrada>)
     * 2) Depois que o arquivo termina, processa a fila cadastrando
     *    cada venda com CatalogoVendas.cadastrarVenda(...)
     */
    private void carregarVendasEntradaComFila(String nomeArquivo) throws IOException {
        Queue<VendaEntrada> fila = new LinkedList<>();

        // 1) Ler arquivo e ENFILEIRAR as vendas
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho, se existir

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 4) continue;

                VendaEntrada ve = new VendaEntrada();
                ve.numTexto     = partes[0].trim();
                ve.dataTexto    = partes[1].trim();
                ve.idTecnologia = Long.parseLong(partes[2].trim());
                ve.codComprador = Long.parseLong(partes[3].trim());

                fila.add(ve);   // >>> AQUI VAI PRA FILA <<<
            }
        }

        // 2) Processar a FILA e cadastrar de verdade no sistema
        while (!fila.isEmpty()) {
            VendaEntrada ve = fila.poll();   // retira da fila (ordem FIFO)

            Tecnologia tec = catalogoTec.buscarPorId(ve.idTecnologia);
            Comprador com  = catalogoCom.buscarPorCodigo(ve.codComprador);

            if (tec == null || com == null) {
                // Dados inconsistentes → ignora esta venda
                continue;
            }

            // Usa TODA a lógica já pronta do catálogo (regras, desconto, qtdVendas etc)
            catalogoVen.cadastrarVenda(ve.numTexto, ve.dataTexto, tec, com);
        }
    }

    // Classezinha interna só pra guardar temporariamente as vendas lidas
    private static class VendaEntrada {
        String numTexto;
        String dataTexto;
        long idTecnologia;
        long codComprador;
    }
}
