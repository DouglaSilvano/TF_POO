package application;

import entities.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PersistenciaCSV {

    private final SimpleDateFormat sdf;
    private final String PASTA_RECURSOS = "RECURSOS" + File.separator;
    public PersistenciaCSV() {
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
        Locale.setDefault(Locale.US);
    }


    public void salvarTudoCSV(String nomeBase,
                              CatalogoFornecedores catalogoFor,
                              CatalogoTecnologias catalogoTec,
                              CatalogoCompradores catalogoCom,
                              CatalogoVendas catalogoVen) throws IOException {

        salvarFornecedoresCSV(PASTA_RECURSOS + nomeBase + "_fornecedores.csv", catalogoFor);
        salvarTecnologiasCSV(PASTA_RECURSOS + nomeBase + "_tecnologias.csv", catalogoTec);
        salvarCompradoresCSV(PASTA_RECURSOS + nomeBase + "_compradores.csv", catalogoCom);
        salvarVendasCSV(PASTA_RECURSOS + nomeBase + "_vendas.csv", catalogoVen);
    }

    public void carregarTudoCSV(String nomeBase,
                                CatalogoFornecedores catalogoFor,
                                CatalogoTecnologias catalogoTec,
                                CatalogoCompradores catalogoCom,
                                CatalogoVendas catalogoVen) throws IOException {

        carregarFornecedoresCSV(PASTA_RECURSOS + nomeBase + "_fornecedores.csv", catalogoFor);
        carregarTecnologiasCSV(PASTA_RECURSOS + nomeBase + "_tecnologias.csv", catalogoTec, catalogoFor);
        carregarCompradoresCSV(PASTA_RECURSOS + nomeBase + "_compradores.csv", catalogoCom);
        carregarVendasCSV(PASTA_RECURSOS + nomeBase + "_vendas.csv", catalogoVen, catalogoTec, catalogoCom);
    }


    private void salvarFornecedoresCSV(String nomeArquivo,
                                       CatalogoFornecedores catalogoFor) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            writer.write("cod;nome;fundacao;area");
            writer.newLine();

            for (Fornecedor f : catalogoFor.getTodosFornecedores()) {
                String dataStr = f.getFundacao() != null ? sdf.format(f.getFundacao()) : "";
                String areaStr = (f.getArea() != null) ? f.getArea().name() : "";
                String linha = String.format(
                        "%d;%s;%s;%s",
                        f.getCod(),
                        f.getNome(),
                        dataStr,
                        areaStr
                );
                writer.write(linha);
                writer.newLine();
            }
        }
    }

    private void salvarTecnologiasCSV(String nomeArquivo,
                                      CatalogoTecnologias catalogoTec) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            writer.write("id;modelo;descricao;valorBase;peso;temperatura;codFornecedor");
            writer.newLine();

            for (Tecnologia t : catalogoTec.getLista()) {
                long codFor = (t.getFornecedor() != null) ? t.getFornecedor().getCod() : 0;
                String linha = String.format(
                        "%d;%s;%s;%.2f;%.2f;%.2f;%d",
                        t.getId(),
                        t.getModelo(),
                        t.getDescricao(),
                        t.getValorBase(),
                        t.getPeso(),
                        t.getTemperatura(),
                        codFor
                );
                writer.write(linha);
                writer.newLine();
            }
        }
    }

    private void salvarCompradoresCSV(String nomeArquivo,
                                      CatalogoCompradores catalogoCom) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            writer.write("cod;nome;pais;email");
            writer.newLine();

            for (Comprador c : catalogoCom.getTodosCompradores()) {
                writer.write(c.geraDescricao()); // já vem cod;nome;pais;email
                writer.newLine();
            }
        }
    }

    private void salvarVendasCSV(String nomeArquivo,
                                 CatalogoVendas catalogoVen) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            writer.write("num;data;idTecnologia;codComprador");
            writer.newLine();

            for (Venda v : catalogoVen.getLista()) {
                String dataStr = sdf.format(v.getData());
                long idTec = (v.getTecnologia() != null) ? v.getTecnologia().getId() : 0;
                long codCom = (v.getComprador() != null) ? v.getComprador().getCod() : 0;

                String linha = String.format(
                        "%d;%s;%d;%d",
                        v.getNum(),
                        dataStr,
                        idTec,
                        codCom
                );
                writer.write(linha);
                writer.newLine();
            }
        }
    }


    private void carregarFornecedoresCSV(String nomeArquivo,
                                         CatalogoFornecedores catalogoFor) throws IOException {

        catalogoFor.limpar();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            String linha = br.readLine(); // cabeçalho

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                List<String> partes = new ArrayList<>(Arrays.asList(linha.split(";")));
                if (partes.size() < 4) {
                    continue;
                }

                String codStr  = partes.get(0);
                String nome    = partes.get(1);
                String dataStr = partes.get(2);
                String areaStr = partes.get(3);

                catalogoFor.cadastrarFornecedor(codStr, nome, dataStr, areaStr);
            }
        }
    }

    private void carregarTecnologiasCSV(String nomeArquivo,
                                        CatalogoTecnologias catalogoTec,
                                        CatalogoFornecedores catalogoFor) throws IOException {

        catalogoTec.limpar();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            String linha = br.readLine(); // cabeçalho

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                List<String> partes = new ArrayList<>(Arrays.asList(linha.split(";")));
                if (partes.size() < 7) {
                    continue;
                }

                long id           = Long.parseLong(partes.get(0));
                String modelo     = partes.get(1);
                String descricao  = partes.get(2);
                double valorBase  = Double.parseDouble(partes.get(3));
                double peso       = Double.parseDouble(partes.get(4));
                double temperatura= Double.parseDouble(partes.get(5));
                long codFor       = Long.parseLong(partes.get(6));

                Fornecedor f = catalogoFor.buscarFornecedor(codFor);
                Tecnologia t = new Tecnologia(id, modelo, descricao, peso, valorBase, temperatura, f);
                catalogoTec.cadastrarTecnologia(t);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Erro numérico em tecnologias: " + e.getMessage(), e);
        }
    }

    private void carregarCompradoresCSV(String nomeArquivo,
                                        CatalogoCompradores catalogoCom) throws IOException {

        catalogoCom.limpar();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            String linha = br.readLine(); // cabeçalho

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                List<String> partes = new ArrayList<>(Arrays.asList(linha.split(";")));
                if (partes.size() < 4) {
                    continue;
                }

                long   cod   = Long.parseLong(partes.get(0));
                String nome  = partes.get(1);
                String pais  = partes.get(2);
                String email = partes.get(3);

                Comprador c = new Comprador(cod, nome, pais, email);
                catalogoCom.cadastrar(c);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Erro numérico em compradores: " + e.getMessage(), e);
        }
    }

    private void carregarVendasCSV(String nomeArquivo,
                                   CatalogoVendas catalogoVen,
                                   CatalogoTecnologias catalogoTec,
                                   CatalogoCompradores catalogoCom) throws IOException {

        catalogoVen.limpar();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            String linha = br.readLine(); // cabeçalho

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                List<String> partes = new ArrayList<>(Arrays.asList(linha.split(";")));
                if (partes.size() < 4) {
                    continue;
                }

                long   num    = Long.parseLong(partes.get(0));
                String dataStr= partes.get(1);
                long   idTec  = Long.parseLong(partes.get(2));
                long   codCom = Long.parseLong(partes.get(3));

                Date data = sdf.parse(dataStr);
                Tecnologia tec = catalogoTec.buscarPorId(idTec);
                Comprador  com = catalogoCom.buscarPorCodigo(codCom);

                if (tec == null || com == null) {
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
