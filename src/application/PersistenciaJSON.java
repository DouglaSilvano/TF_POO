package application;

import entities.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersistenciaJSON {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    // ========= MÉTODOS PÚBLICOS PRINCIPAIS =========

    public static void salvarTudoJSON(String nomeBase,
                                      CatalogoFornecedores catalogoFor,
                                      CatalogoTecnologias catalogoTec,
                                      CatalogoCompradores catalogoCom,
                                      CatalogoVendas catalogoVen) throws IOException {

        String nomeArquivo = nomeBase + ".json";

        try (PrintWriter out = new PrintWriter(new FileWriter(nomeArquivo))) {
            out.println("{");

            // FORNECEDORES
            out.println("  \"fornecedores\": [");
            boolean primeiro = true;
            for (Fornecedor f : catalogoFor.getTodosFornecedores()) {
                if (!primeiro) {
                    out.println("    ,");
                }
                primeiro = false;
                String fundacao = (f.getFundacao() != null) ? SDF.format(f.getFundacao()) : "";
                String area = (f.getArea() != null) ? f.getArea().name() : "";
                out.print("    {");
                out.print("\"cod\":\"" + f.getCod() + "\",");
                out.print("\"nome\":\"" + escapeJson(f.getNome()) + "\",");
                out.print("\"fundacao\":\"" + fundacao + "\",");
                out.print("\"area\":\"" + area + "\"");
                out.print("}");
            }
            out.println();
            out.println("  ],");

            // TECNOLOGIAS
            out.println("  \"tecnologias\": [");
            primeiro = true;
            for (Tecnologia t : catalogoTec.getLista()) {
                if (!primeiro) {
                    out.println("    ,");
                }
                primeiro = false;
                long codFor = (t.getFornecedor() != null) ? t.getFornecedor().getCod() : 0;
                out.print("    {");
                out.print("\"id\":\"" + t.getId() + "\",");
                out.print("\"modelo\":\"" + escapeJson(t.getModelo()) + "\",");
                out.print("\"descricao\":\"" + escapeJson(t.getDescricao()) + "\",");
                out.print("\"valorBase\":\"" + t.getValorBase() + "\",");
                out.print("\"peso\":\"" + t.getPeso() + "\",");
                out.print("\"temperatura\":\"" + t.getTemperatura() + "\",");
                out.print("\"codFornecedor\":\"" + codFor + "\"");
                out.print("}");
            }
            out.println();
            out.println("  ],");

            // COMPRADORES
            out.println("  \"compradores\": [");
            primeiro = true;
            for (Comprador c : catalogoCom.getTodosCompradores()) {
                if (!primeiro) {
                    out.println("    ,");
                }
                primeiro = false;
                out.print("    {");
                out.print("\"cod\":\"" + c.getCod() + "\",");
                out.print("\"nome\":\"" + escapeJson(c.getNome()) + "\",");
                out.print("\"pais\":\"" + escapeJson(c.getPais()) + "\",");
                out.print("\"email\":\"" + escapeJson(c.getEmail()) + "\"");
                out.print("}");
            }
            out.println();
            out.println("  ],");

            // VENDAS
            out.println("  \"vendas\": [");
            primeiro = true;
            for (Venda v : catalogoVen.getLista()) {
                if (!primeiro) {
                    out.println("    ,");
                }
                primeiro = false;
                String dataStr = SDF.format(v.getData());
                long idTec = (v.getTecnologia() != null) ? v.getTecnologia().getId() : 0;
                long codCom = (v.getComprador() != null) ? v.getComprador().getCod() : 0;
                out.print("    {");
                out.print("\"num\":\"" + v.getNum() + "\",");
                out.print("\"data\":\"" + dataStr + "\",");
                out.print("\"idTecnologia\":\"" + idTec + "\",");
                out.print("\"codComprador\":\"" + codCom + "\"");
                out.print("}");
            }
            out.println();
            out.println("  ]");

            out.println("}");
        }
    }

    public static void carregarTudoJSON(String nomeBase,
                                        CatalogoFornecedores catalogoFor,
                                        CatalogoTecnologias catalogoTec,
                                        CatalogoCompradores catalogoCom,
                                        CatalogoVendas catalogoVen) throws IOException {

        String nomeArquivo = nomeBase + ".json";

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
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
                String[] objetos = quebrarObjetos(fornecedoresArray);
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
                String[] objetos = quebrarObjetos(tecArray);
                for (String obj : objetos) {
                    String idStr = extrairCampo(obj, "id");
                    String modelo = extrairCampo(obj, "modelo");
                    String descricao = extrairCampo(obj, "descricao");
                    String valorBaseStr = extrairCampo(obj, "valorBase");
                    String pesoStr = extrairCampo(obj, "peso");
                    String temperaturaStr = extrairCampo(obj, "temperatura");
                    String codForStr = extrairCampo(obj, "codFornecedor");

                    if (idStr == null || modelo == null) continue;

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
                String[] objetos = quebrarObjetos(compArray);
                for (String obj : objetos) {
                    String codStr = extrairCampo(obj, "cod");
                    String nome = extrairCampo(obj, "nome");
                    String pais = extrairCampo(obj, "pais");
                    String email = extrairCampo(obj, "email");
                    if (codStr == null || nome == null) continue;
                    long cod = Long.parseLong(codStr);
                    Comprador c = new Comprador(cod, nome, pais, email);
                    catalogoCom.cadastrar(c);
                }
            }

            // VENDAS
            String venArray = extrairArray(json, "vendas");
            if (venArray != null) {
                String[] objetos = quebrarObjetos(venArray);
                for (String obj : objetos) {
                    String numStr = extrairCampo(obj, "num");
                    String dataStr = extrairCampo(obj, "data");
                    String idTecStr = extrairCampo(obj, "idTecnologia");
                    String codComStr = extrairCampo(obj, "codComprador");
                    if (numStr == null || dataStr == null || idTecStr == null || codComStr == null) continue;

                    long num = Long.parseLong(numStr);
                    Date data = SDF.parse(dataStr);
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

    // Escapa aspas e barras invertidas básicas
    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // extrai o conteúdo de um array JSON: "campo": [ ... ]
    private static String extrairArray(String json, String campo) {
        String chave = "\"" + campo + "\"";
        int idx = json.indexOf(chave);
        if (idx == -1) return null;
        int idxColchete = json.indexOf("[", idx);
        if (idxColchete == -1) return null;

        int nivel = 0;
        int inicio = idxColchete + 1;
        for (int i = idxColchete; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') nivel++;
            else if (c == ']') {
                nivel--;
                if (nivel == 0) {
                    int fim = i;
                    return json.substring(inicio, fim).trim();
                }
            }
        }
        return null;
    }

    // Quebra array de objetos em strings individuais, assumindo formato { ... },{ ... },{ ... }
    private static String[] quebrarObjetos(String arraySemColchetes) {
        arraySemColchetes = arraySemColchetes.trim();
        if (arraySemColchetes.isEmpty()) return new String[0];

        // quebra em "},{" mas preserva as chaves
        String[] partes = arraySemColchetes.split("\\},\\s*\\{");

        for (int i = 0; i < partes.length; i++) {
            String p = partes[i].trim();
            if (!p.startsWith("{")) {
                p = "{" + p;
            }
            if (!p.endsWith("}")) {
                p = p + "}";
            }
            partes[i] = p;
        }
        return partes;
    }

    // extrai um campo string do tipo "campo":"valor"
    private static String extrairCampo(String objJson, String campo) {
        String chave = "\"" + campo + "\"";
        int idx = objJson.indexOf(chave);
        if (idx == -1) return null;
        int idxDoisPontos = objJson.indexOf(":", idx);
        if (idxDoisPontos == -1) return null;

        int idxAspa1 = objJson.indexOf("\"", idxDoisPontos + 1);
        if (idxAspa1 == -1) return null;
        int idxAspa2 = objJson.indexOf("\"", idxAspa1 + 1);
        if (idxAspa2 == -1) return null;

        return objJson.substring(idxAspa1 + 1, idxAspa2);
    }
}
