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

    // Nomes dos arquivos de ENTRADA exatamente como no Apêndice
    private final String ARQ_PARTICIPANTES = "PARTICIPANTESENTRADA.CSV";
    private final String ARQ_TECNOLOGIAS   = "TECNOLOGIASENTRADA.CSV";
    private final String ARQ_VENDAS        = "VENDASENTRADA.CSV";

    public ACMETech() {
        catalogoFor = new CatalogoFornecedores();
        catalogoTec = new CatalogoTecnologias();
        catalogoCom = new CatalogoCompradores();
        catalogoVen = new CatalogoVendas();
    }

    /**
     * Lê arquivos de ENTRADA (formatos do Apêndice) e
     * usa UMA FILA para armazenar as vendas antes de cadastrá-las.
     */
    public void inicializar() {

        // Garante que começa tudo limpo
        catalogoFor.limpar();
        catalogoTec.limpar();
        catalogoCom.limpar();
        catalogoVen.limpar();

        try {
            carregarParticipantesEntrada(ARQ_PARTICIPANTES);
            carregarTecnologiasEntrada(ARQ_TECNOLOGIAS);
            carregarVendasEntradaComFila(ARQ_VENDAS);
        } catch (IOException e) {
            System.out.println("Erro ao inicializar dados de entrada: " + e.getMessage());
        }
    }

    public void executar() {
        // Se fosse interface de texto, o laço do menu iria aqui.
        // No teu caso, o main pode:
        // 1) criar ACMETech
        // 2) chamar inicializar()
        // 3) abrir a TelaInicio / AplicacaoMenu usando os catálogos, se quiser.
    }

    // Getters – caso queira integrar os catálogos com a GUI
    public CatalogoFornecedores getCatalogoFornecedores() { return catalogoFor; }
    public CatalogoTecnologias  getCatalogoTecnologias()  { return catalogoTec; }
    public CatalogoCompradores  getCatalogoCompradores()  { return catalogoCom; }
    public CatalogoVendas       getCatalogoVendas()       { return catalogoVen; }

    // =========================================================
    //      1) PARTICIPANTESENTRADA.CSV
    // =========================================================
    //
    // Formato (do PDF):
    // cod;nome;tipo;fundacao_pais;area_email
    //
    // tipo = 1 → fornecedor:
    //   fundacao_pais = data fundação (dd/MM/yyyy)
    //   area_email    = área (TI, ANDROIDES, EMERGENTE, ALIMENTOS)
    //
    // tipo = 2 → comprador:
    //   fundacao_pais = país
    //   area_email    = e-mail
    //
    private void carregarParticipantesEntrada(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {

            String linha = br.readLine(); // cabeçalho: cod;nome;tipo;fundacao_pais;area_email

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linha.split(";");
                if (partes.length < 5) {
                    continue; // linha incompleta
                }

                String codStr = partes[0].trim();
                String nome   = partes[1].trim();
                String tipoStr= partes[2].trim();
                String c4     = partes[3].trim(); // fundacao_pais
                String c5     = partes[4].trim(); // area_email

                long cod;
                int tipo;
                try {
                    cod  = Long.parseLong(codStr);
                    tipo = Integer.parseInt(tipoStr);
                } catch (NumberFormatException e) {
                    // Linha mal-formada, ignora
                    continue;
                }

                if (tipo == 1) {
                    // FORNECEDOR
                    // reaproveita validação do catálogo (data, área, etc.)
                    catalogoFor.cadastrarFornecedor(codStr, nome, c4, c5);

                } else if (tipo == 2) {
                    // COMPRADOR
                    String pais  = c4;
                    String email = c5;
                    Comprador c = new Comprador(cod, nome, pais, email);
                    catalogoCom.cadastrar(c);
                } else {
                    // tipo desconhecido → ignora
                    continue;
                }
            }
        }
    }

    // =========================================================
    //      2) TECNOLOGIASENTRADA.CSV
    // =========================================================
    //
    // Formato (do PDF):
    // id;modelo;descricao;valorBase;peso;temperatura;fornecedor
    //
    private void carregarTecnologiasEntrada(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho: id;modelo;...

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linha.split(";");
                if (partes.length < 7) {
                    continue;
                }

                try {
                    long   id       = Long.parseLong(partes[0].trim());
                    String modelo   = partes[1].trim();
                    String desc     = partes[2].trim();
                    double valor    = Double.parseDouble(partes[3].trim());
                    double peso     = Double.parseDouble(partes[4].trim());
                    double temp     = Double.parseDouble(partes[5].trim());
                    long   codFor   = Long.parseLong(partes[6].trim());

                    Fornecedor f = catalogoFor.buscarFornecedor(codFor);
                    Tecnologia t = new Tecnologia(id, modelo, desc, peso, valor, temp, f);
                    catalogoTec.cadastrarTecnologia(t);

                } catch (NumberFormatException e) {
                    // linha com número inválido → ignora
                    continue;
                }
            }
        }
    }

    // =========================================================
    //      3) VENDASENTRADA.CSV + FILA
    // =========================================================
    //
    // Formato (do PDF):
    // num;data;cod;id
    //
    //  - num  = número da venda
    //  - data = dd/MM/yyyy
    //  - cod  = código do comprador
    //  - id   = identificador da tecnologia
    //
    // 1) Lê TODAS as vendas e coloca em uma FILA (Queue<VendaEntrada>)
    // 2) Depois processa a fila usando CatalogoVendas.cadastrarVenda(...)
    //
    private void carregarVendasEntradaComFila(String nomeArquivo) throws IOException {
        Queue<VendaEntrada> fila = new LinkedList<>();

        // 1) Ler arquivo e ENFILEIRAR as vendas
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho: num;data;cod;id

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linha.split(";");
                if (partes.length < 4) {
                    continue;
                }

                VendaEntrada ve = new VendaEntrada();
                ve.numTexto     = partes[0].trim();  // num
                ve.dataTexto    = partes[1].trim();  // data
                // ATENÇÃO: no PDF, 3ª coluna é cod (comprador) e 4ª é id (tecnologia)
                try {
                    ve.codComprador = Long.parseLong(partes[2].trim()); // cod
                    ve.idTecnologia = Long.parseLong(partes[3].trim()); // id
                } catch (NumberFormatException e) {
                    // linha inválida → ignora
                    continue;
                }

                fila.add(ve);   // >>> vai para a FILA (requisito do trabalho) <<<
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

            // Usa TODA a lógica já pronta do catálogo (regras, desconto, qtdVendas, etc.)
            catalogoVen.cadastrarVenda(ve.numTexto, ve.dataTexto, tec, com);
        }
    }

    // Classe interna só pra guardar temporariamente os dados que vão para a fila
    private static class VendaEntrada {
        String numTexto;
        String dataTexto;
        long idTecnologia;
        long codComprador;
    }
}
