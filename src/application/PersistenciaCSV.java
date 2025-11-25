package application;

import entities.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersistenciaCSV {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    // ========= MÉTODOS PÚBLICOS PRINCIPAIS =========

    public static void salvarTudoCSV(String nomeBase,
                                     CatalogoFornecedores catalogoFor,
                                     CatalogoTecnologias catalogoTec,
                                     CatalogoCompradores catalogoCom,
                                     CatalogoVendas catalogoVen) throws IOException {

        salvarFornecedoresCSV(nomeBase + "_fornecedores.csv", catalogoFor);
        salvarTecnologiasCSV(nomeBase + "_tecnologias.csv", catalogoTec);
        salvarCompradoresCSV(nomeBase + "_compradores.csv", catalogoCom);
        salvarVendasCSV(nomeBase + "_vendas.csv", catalogoVen);
    }

    public static void carregarTudoCSV(String nomeBase,
                                       CatalogoFornecedores catalogoFor,
                                       CatalogoTecnologias catalogoTec,
                                       CatalogoCompradores catalogoCom,
                                       CatalogoVendas catalogoVen) throws IOException {

        carregarFornecedoresCSV(nomeBase + "_fornecedores.csv", catalogoFor);
        carregarTecnologiasCSV(nomeBase + "_tecnologias.csv", catalogoTec, catalogoFor);
        carregarCompradoresCSV(nomeBase + "_compradores.csv", catalogoCom);
        carregarVendasCSV(nomeBase + "_vendas.csv", catalogoVen, catalogoTec, catalogoCom);
    }

    // ========= SALVAR =========

    private static void salvarFornecedoresCSV(String nomeArquivo,
                                              CatalogoFornecedores catalogoFor) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            out.println("cod;nome;fundacao;area");
            for (Fornecedor f : catalogoFor.getTodosFornecedores()) {
                String dataStr = f.getFundacao() != null ? SDF.format(f.getFundacao()) : "";
                String areaStr = (f.getArea() != null) ? f.getArea().name() : "";
                out.printf("%d;%s;%s;%s%n",
                        f.getCod(),
                        f.getNome(),
                        dataStr,
                        areaStr
                );
            }
        }
    }

    private static void salvarTecnologiasCSV(String nomeArquivo,
                                             CatalogoTecnologias catalogoTec) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            out.println("id;modelo;descricao;valorBase;peso;temperatura;codFornecedor");
            for (Tecnologia t : catalogoTec.getLista()) {
                long codFor = (t.getFornecedor() != null) ? t.getFornecedor().getCod() : 0;
                out.printf("%d;%s;%s;%.2f;%.2f;%.2f;%d%n",
                        t.getId(),
                        t.getModelo(),
                        t.getDescricao(),
                        t.getValorBase(),
                        t.getPeso(),
                        t.getTemperatura(),
                        codFor
                );
            }
        }
    }

    private static void salvarCompradoresCSV(String nomeArquivo,
                                             CatalogoCompradores catalogoCom) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            out.println("cod;nome;pais;email");
            for (Comprador c : catalogoCom.getTodosCompradores()) {
                // geraDescricao() já retorna cod;nome;pais;email
                out.println(c.geraDescricao());
            }
        }
    }

    private static void salvarVendasCSV(String nomeArquivo,
                                        CatalogoVendas catalogoVen) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            out.println("num;data;idTecnologia;codComprador");
            for (Venda v : catalogoVen.getLista()) {
                String dataStr = SDF.format(v.getData());
                long idTec = (v.getTecnologia() != null) ? v.getTecnologia().getId() : 0;
                long codCom = (v.getComprador() != null) ? v.getComprador().getCod() : 0;
                out.printf("%d;%s;%d;%d%n",
                        v.getNum(),
                        dataStr,
                        idTec,
                        codCom
                );
            }
        }
    }

    // ========= CARREGAR =========

    private static void carregarFornecedoresCSV(String nomeArquivo,
                                                CatalogoFornecedores catalogoFor)
            throws IOException {

        catalogoFor.limpar();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 4) continue;

                String codStr = partes[0];
                String nome = partes[1];
                String dataStr = partes[2];
                String areaStr = partes[3];

                // reaproveita a validação do próprio catálogo
                catalogoFor.cadastrarFornecedor(codStr, nome, dataStr, areaStr);
            }
        }
    }

    private static void carregarTecnologiasCSV(String nomeArquivo,
                                               CatalogoTecnologias catalogoTec,
                                               CatalogoFornecedores catalogoFor)
            throws IOException {

        catalogoTec.limpar();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 7) continue;

                long id = Long.parseLong(partes[0]);
                String modelo = partes[1];
                String descricao = partes[2];
                double valorBase = Double.parseDouble(partes[3]);
                double peso = Double.parseDouble(partes[4]);
                double temperatura = Double.parseDouble(partes[5]);
                long codFor = Long.parseLong(partes[6]);

                Fornecedor f = catalogoFor.buscarFornecedor(codFor);
                Tecnologia t = new Tecnologia(id, modelo, descricao, peso, valorBase, temperatura, f);
                catalogoTec.cadastrarTecnologia(t);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Erro numérico em tecnologias: " + e.getMessage(), e);
        }
    }

    private static void carregarCompradoresCSV(String nomeArquivo,
                                               CatalogoCompradores catalogoCom)
            throws IOException {

        catalogoCom.limpar();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 4) continue;

                long cod = Long.parseLong(partes[0]);
                String nome = partes[1];
                String pais = partes[2];
                String email = partes[3];

                Comprador c = new Comprador(cod, nome, pais, email);
                catalogoCom.cadastrar(c);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Erro numérico em compradores: " + e.getMessage(), e);
        }
    }

    private static void carregarVendasCSV(String nomeArquivo,
                                          CatalogoVendas catalogoVen,
                                          CatalogoTecnologias catalogoTec,
                                          CatalogoCompradores catalogoCom)
            throws IOException {

        catalogoVen.limpar();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine(); // cabeçalho

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length < 4) continue;

                long num = Long.parseLong(partes[0]);
                String dataStr = partes[1];
                long idTec = Long.parseLong(partes[2]);
                long codCom = Long.parseLong(partes[3]);

                Date data = SDF.parse(dataStr);
                Tecnologia tec = catalogoTec.buscarPorId(idTec);
                Comprador com = catalogoCom.buscarPorCodigo(codCom);

                if (tec == null || com == null) {
                    // dado inconsistente (referência sem existir no catálogo) → ignora essa venda
                    continue;
                }

                Venda v = new Venda(num, data, tec, com);
                catalogoVen.getLista().add(v);
                tec.setVendida(true);
                com.incrementarQtdVendas();
            }
        } catch (NumberFormatException | ParseException e) {
            throw new IOException("Erro ao ler vendas: " + e.getMessage(), e);
        }
    }
}
