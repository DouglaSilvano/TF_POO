package aplicacao;

import entidades.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ACMETech {

    private CatalogoFornecedores catalogoFor;
    private CatalogoTecnologias catalogoTec;
    private CatalogoCompradores catalogoCom;
    private CatalogoVendas catalogoVen;
    private final String PASTA_RECURSOS = "recursos" + File.separator;
    private final String ARQ_PARTICIPANTES = PASTA_RECURSOS + "PARTICIPANTESENTRADA.CSV";
    private final String ARQ_TECNOLOGIAS   = PASTA_RECURSOS + "TECNOLOGIASENTRADA.CSV";
    private final String ARQ_VENDAS        = PASTA_RECURSOS + "VENDASENTRADA.CSV";

    public ACMETech() {
        catalogoFor = new CatalogoFornecedores();
        catalogoTec = new CatalogoTecnologias();
        catalogoCom = new CatalogoCompradores();
        catalogoVen = new CatalogoVendas();
    }

    public void inicializar() {
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

    public void executar() { }

    public CatalogoFornecedores getCatalogoFornecedores() { return catalogoFor; }
    public CatalogoTecnologias  getCatalogoTecnologias()  { return catalogoTec; }
    public CatalogoCompradores  getCatalogoCompradores()  { return catalogoCom; }
    public CatalogoVendas       getCatalogoVendas()       { return catalogoVen; }

    private void carregarParticipantesEntrada(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {

            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                List<String> partes = new ArrayList<>(Arrays.asList(linha.split(";")));
                if (partes.size() < 5) {
                    continue;
                }

                String codStr = partes.get(0).trim();
                String nome   = partes.get(1).trim();
                String tipoStr= partes.get(2).trim();
                String c4     = partes.get(3).trim();
                String c5     = partes.get(4).trim();

                long cod;
                int tipo;
                try {
                    cod  = Long.parseLong(codStr);
                    tipo = Integer.parseInt(tipoStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (tipo == 1) {
                    catalogoFor.cadastrarFornecedor(codStr, nome, c4, c5);
                } else if (tipo == 2) {
                    String pais  = c4;
                    String email = c5;
                    Comprador c = new Comprador(cod, nome, pais, email);
                    catalogoCom.cadastrar(c);
                } else {
                    continue;
                }
            }
        }
    }

    private void carregarTecnologiasEntrada(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                List<String> partes = new ArrayList<>(Arrays.asList(linha.split(";")));
                if (partes.size() < 7) {
                    continue;
                }

                try {
                    long   id     = Long.parseLong(partes.get(0).trim());
                    String modelo = partes.get(1).trim();
                    String desc   = partes.get(2).trim();
                    double valor  = Double.parseDouble(partes.get(3).trim());
                    double peso   = Double.parseDouble(partes.get(4).trim());
                    double temp   = Double.parseDouble(partes.get(5).trim());
                    long   codFor = Long.parseLong(partes.get(6).trim());

                    Fornecedor f = catalogoFor.buscarFornecedor(codFor);
                    Tecnologia t = new Tecnologia(id, modelo, desc, peso, valor, temp, f);
                    catalogoTec.cadastrarTecnologia(t);

                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
    }

    private void carregarVendasEntradaComFila(String nomeArquivo) throws IOException {
        Queue<VendaEntrada> fila = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                List<String> partes = new ArrayList<>(Arrays.asList(linha.split(";")));
                if (partes.size() < 4) {
                    continue;
                }

                VendaEntrada ve = new VendaEntrada();
                ve.numTexto  = partes.get(0).trim();
                ve.dataTexto = partes.get(1).trim();
                try {
                    ve.codComprador = Long.parseLong(partes.get(2).trim());
                    ve.idTecnologia = Long.parseLong(partes.get(3).trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                fila.add(ve);
            }
        }

        while (!fila.isEmpty()) {
            VendaEntrada ve = fila.poll();

            Tecnologia tec = catalogoTec.buscarPorId(ve.idTecnologia);
            Comprador com  = catalogoCom.buscarPorCodigo(ve.codComprador);

            if (tec == null || com == null) {
                continue;
            }

            catalogoVen.cadastrarVenda(ve.numTexto, ve.dataTexto, tec, com);
        }
    }

    private static class VendaEntrada {
        String numTexto;
        String dataTexto;
        long idTecnologia;
        long codComprador;
    }
}
