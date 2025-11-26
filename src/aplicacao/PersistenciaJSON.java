package aplicacao;

import entidades.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersistenciaJSON {

    private final SimpleDateFormat sdf;
    private final String PASTA_RECURSOS = "recursos" + File.separator;

    public PersistenciaJSON() {
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);
        Locale.setDefault(Locale.US);
    }

    // ========= MÉTODOS PÚBLICOS PRINCIPAIS =========

    public void salvarTudoJSON(String nomeBase,
                               CatalogoFornecedores catalogoFor,
                               CatalogoTecnologias catalogoTec,
                               CatalogoCompradores catalogoCom,
                               CatalogoVendas catalogoVen) throws IOException {

        String nomeArquivo = PASTA_RECURSOS + nomeBase + ".json";

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            writer.write("{");
            writer.newLine();

            // FORNECEDORES
            writer.write("  \"fornecedores\": [");
            writer.newLine();

            boolean primeiro = true;
            for (Fornecedor f : catalogoFor.getTodosFornecedores()) {
                if (!primeiro) {
                    writer.write("    ,");
                    writer.newLine();
                }
                primeiro = false;

                String fundacao = (f.getFundacao() != null) ? sdf.format(f.getFundacao()) : "";
                String area = (f.getArea() != null) ? f.getArea().name() : "";

                writer.write("    {");
                writer.write("\"cod\":\"" + f.getCod() + "\",");
                writer.write("\"nome\":\"" + escapeJson(f.getNome()) + "\",");
                writer.write("\"fundacao\":\"" + fundacao + "\",");
                writer.write("\"area\":\"" + area + "\"");
                writer.write("}");
            }
            writer.newLine();
            writer.write("  ],");
            writer.newLine();

            // TECNOLOGIAS
            writer.write("  \"tecnologias\": [");
            writer.newLine();

            primeiro = true;
            for (Tecnologia t : catalogoTec.getLista()) {
                if (!primeiro) {
                    writer.write("    ,");
                    writer.newLine();
                }
                primeiro = false;

                long codFor = (t.getFornecedor() != null) ? t.getFornecedor().getCod() : 0;

                writer.write("    {");
                writer.write("\"id\":\"" + t.getId() + "\",");
                writer.write("\"modelo\":\"" + escapeJson(t.getModelo()) + "\",");
                writer.write("\"descricao\":\"" + escapeJson(t.getDescricao()) + "\",");
                writer.write("\"valorBase\":\"" + t.getValorBase() + "\",");
                writer.write("\"peso\":\"" + t.getPeso() + "\",");
                writer.write("\"temperatura\":\"" + t.getTemperatura() + "\",");
                writer.write("\"codFornecedor\":\"" + codFor + "\"");
                writer.write("}");
            }
            writer.newLine();
            writer.write("  ],");
            writer.newLine();

            // COMPRADORES
            writer.write("  \"compradores\": [");
            writer.newLine();

            primeiro = true;
            for (Comprador c : catalogoCom.getTodosCompradores()) {
                if (!primeiro) {
                    writer.write("    ,");
                    writer.newLine();
                }
                primeiro = false;

                writer.write("    {");
                writer.write("\"cod\":\"" + c.getCod() + "\",");
                writer.write("\"nome\":\"" + escapeJson(c.getNome()) + "\",");
                writer.write("\"pais\":\"" + escapeJson(c.getPais()) + "\",");
                writer.write("\"email\":\"" + escapeJson(c.getEmail()) + "\"");
                writer.write("}");
            }
            writer.newLine();
            writer.write("  ],");
            writer.newLine();

            // VENDAS
            writer.write("  \"vendas\": [");
            writer.newLine();

            primeiro = true;
            for (Venda v : catalogoVen.getLista()) {
                if (!primeiro) {
                    writer.write("    ,");
                    writer.newLine();
                }
                primeiro = false;

                String dataStr = sdf.format(v.getData());
                long idTec = (v.getTecnologia() != null) ? v.getTecnologia().getId() : 0;
                long codCom = (v.getComprador() != null) ? v.getComprador().getCod() : 0;

                writer.write("    {");
                writer.write("\"num\":\"" + v.getNum() + "\",");
                writer.write("\"data\":\"" + dataStr + "\",");
                writer.write("\"idTecnologia\":\"" + idTec + "\",");
                writer.write("\"codComprador\":\"" + codCom + "\"");
                writer.write("}");
            }
            writer.newLine();
            writer.write("  ]");
            writer.newLine();

            writer.write("}");
            writer.newLine();
        }
    }

    public void carregarTudoJSON(String nomeBase,
                                 CatalogoFornecedores catalogoFor,
                                 CatalogoTecnologias catalogoTec,
                                 CatalogoCompradores catalogoCom,
                                 CatalogoVendas catalogoVen) throws IOException {

        String nomeArquivo = PASTA_RECURSOS + nomeBase + ".json";

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(nomeArquivo),
                        StandardCharsets.UTF_8))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append("\n");
            }
        }

        String json = sb.toString();

        // limpa catálogos
        catalogoFor.limpar();
        catalogoTec.limpar();
        catalogoCom.limpar();
        catalogoVen.limpar();

        try {
            // FORNECEDORES
            String fornecedoresArray = extrairArray(json, "fornecedores");
            if (fornecedoresArray != null) {
                List<String> objetos = quebrarObjetos(fornecedoresArray);
                for (String obj : objetos) {
                    String codStr = extrairCampo(obj, "cod");
                    String nome = extrairCampo(obj, "nome");
                    String fundacao = extrairCampo(obj, "fundacao");
                    String area = extrairCampo(obj, "area");
                    if (codStr != null && nome != null) {
                        catalogoFor.cadastrarFornecedor(codStr, nome, fundacao, area);
                    }
                }
            }

            // TECNOLOGIAS
            String tecArray = extrairArray(json, "tecnologias");
            if (tecArray != null) {
                List<String> objetos = quebrarObjetos(tecArray);
                for (String obj : objetos) {
                    String idStr = extrairCampo(obj, "id");
                    String modelo = extrairCampo(obj, "modelo");
                    String descricao = extrairCampo(obj, "descricao");
                    String valorBaseStr = extrairCampo(obj, "valorBase");
                    String pesoStr = extrairCampo(obj, "peso");
                    String temperaturaStr = extrairCampo(obj, "temperatura");
                    String codForStr = extrairCampo(obj, "codFornecedor");

                    if (idStr == null || modelo == null) {
                        continue;
                    }

                    long id = Long.parseLong(idStr);
                    double valorBase = Double.parseDouble(valorBaseStr);
                    double peso = Double.parseDouble(pesoStr);
                    double temperatura = Double.parseDouble(temperaturaStr);
                    long codFor = Long.parseLong(codForStr);

                    Fornecedor f = catalogoFor.buscarFornecedor(codFor);
                    Tecnologia t = new Tecnologia(id, modelo, descricao, peso, valorBase, temperatura, f);
                    catalogoTec.cadastrarTecnologia(t);
                }
            }

            // COMPRADORES
            String compArray = extrairArray(json, "compradores");
            if (compArray != null) {
                List<String> objetos = quebrarObjetos(compArray);
                for (String obj : objetos) {
                    String codStr = extrairCampo(obj, "cod");
                    String nome = extrairCampo(obj, "nome");
                    String pais = extrairCampo(obj, "pais");
                    String email = extrairCampo(obj, "email");
                    if (codStr == null || nome == null) {
                        continue;
                    }
                    long cod = Long.parseLong(codStr);
                    Comprador c = new Comprador(cod, nome, pais, email);
                    catalogoCom.cadastrar(c);
                }
            }

            // VENDAS
            String venArray = extrairArray(json, "vendas");
            if (venArray != null) {
                List<String> objetos = quebrarObjetos(venArray);
                for (String obj : objetos) {
                    String numStr = extrairCampo(obj, "num");
                    String dataStr = extrairCampo(obj, "data");
                    String idTecStr = extrairCampo(obj, "idTecnologia");
                    String codComStr = extrairCampo(obj, "codComprador");
                    if (numStr == null || dataStr == null || idTecStr == null || codComStr == null) {
                        continue;
                    }

                    long num = Long.parseLong(numStr);
                    Date data = sdf.parse(dataStr);
                    long idTec = Long.parseLong(idTecStr);
                    long codCom = Long.parseLong(codComStr);

                    Tecnologia tec = catalogoTec.buscarPorId(idTec);
                    Comprador com = catalogoCom.buscarPorCodigo(codCom);

                    if (tec == null || com == null) {
                        continue;
                    }

                    Venda v = new Venda(num, data, tec, com);
                    catalogoVen.getLista().add(v);
                    tec.setVendida(true);
                    com.incrementarQtdVendas();
                }
            }

        } catch (NumberFormatException | ParseException e) {
            throw new IOException("Erro ao interpretar JSON: " + e.getMessage(), e);
        }
    }

    // ========= HELPERS SIMPLES DE JSON =========

    private String escapeJson(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // extrai o conteúdo de um array JSON: "campo": [ ... ]
    private String extrairArray(String json, String campo) {
        String chave = "\"" + campo + "\"";
        int idx = json.indexOf(chave);
        if (idx == -1) {
            return null;
        }
        int idxColchete = json.indexOf("[", idx);
        if (idxColchete == -1) {
            return null;
        }

        int nivel = 0;
        int inicio = idxColchete + 1;
        for (int i = idxColchete; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') {
                nivel++;
            } else if (c == ']') {
                nivel--;
                if (nivel == 0) {
                    int fim = i;
                    return json.substring(inicio, fim).trim();
                }
            }
        }
        return null;
    }

    // Quebra array de objetos em strings individuais, devolvendo List<String>
    private List<String> quebrarObjetos(String arraySemColchetes) {
        List<String> lista = new ArrayList<>();

        arraySemColchetes = arraySemColchetes.trim();
        if (arraySemColchetes.isEmpty()) {
            return lista; // lista vazia
        }

        // divide no padrão "} , {", mas sem trabalhar com array de objetos do domínio
        String[] partes = arraySemColchetes.split("\\}\\s*,\\s*\\{");

        for (String parte : partes) {
            String p = parte.trim();
            if (!p.startsWith("{")) {
                p = "{" + p;
            }
            if (!p.endsWith("}")) {
                p = p + "}";
            }
            lista.add(p);
        }
        return lista;
    }

    // extrai um campo string do tipo "campo":"valor"
    private String extrairCampo(String objJson, String campo) {
        String chave = "\"" + campo + "\"";
        int idx = objJson.indexOf(chave);
        if (idx == -1) {
            return null;
        }
        int idxDoisPontos = objJson.indexOf(":", idx);
        if (idxDoisPontos == -1) {
            return null;
        }

        int idxAspa1 = objJson.indexOf("\"", idxDoisPontos + 1);
        if (idxAspa1 == -1) {
            return null;
        }
        int idxAspa2 = objJson.indexOf("\"", idxAspa1 + 1);
        if (idxAspa2 == -1) {
            return null;
        }

        return objJson.substring(idxAspa1 + 1, idxAspa2);
    }
}
