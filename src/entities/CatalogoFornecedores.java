package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CatalogoFornecedores {

    private ArrayList<Fornecedor> listaFornecedores = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public CatalogoFornecedores() {
        sdf.setLenient(false);
    }

    public String cadastrarFornecedor(String codTexto, String nome, String fundacaoTexto, String areaTexto) {

        if (codTexto == null || codTexto.isEmpty() ||
                nome == null || nome.isEmpty() ||
                fundacaoTexto == null || fundacaoTexto.isEmpty() ||
                areaTexto == null || areaTexto.isEmpty()) {
            return "Preencha todos os campos! :3";
        }

        try {
            long cod = Long.parseLong(codTexto);

            String areaNormalized = areaTexto.trim().toUpperCase().replaceAll("\\s+", "_");
            Area area = Area.valueOf(areaNormalized);

            Date fundacao = sdf.parse(fundacaoTexto);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fundacao);
            int ano = cal.get(Calendar.YEAR);

            Date hoje = new Date();
            if (ano < 1800 || fundacao.after(hoje)) {
                return "Data de fundação inválida! Deve ser entre 1800 e hoje!";
            }

            if (buscarFornecedor(cod) != null) {
                return "Código já cadastrado! :3";
            }

            Fornecedor novo = new Fornecedor(cod, nome, fundacao, area);
            listaFornecedores.add(novo);
            listaFornecedores.sort(Comparator.comparingLong(Fornecedor::getCod));

            return "Fornecedor " + nome + " cadastrado! <3";

        } catch (NumberFormatException ex) {
            return "Código inválido! Apenas números.";
        } catch (IllegalArgumentException ex) {
            return "Área inválida! Valores válidos: TI, ANDROIDES, EMERGENTE, ALIMENTOS.";
        } catch (ParseException ex) {
            return "Data inválida (use dd/MM/yyyy)!";
        }
    }

    public Fornecedor buscarFornecedor(long cod) {
        for (Fornecedor f : listaFornecedores) {
            if (f.getCod() == cod) return f;
        }
        return null;
    }

    public boolean isVazio() {
        return listaFornecedores.isEmpty();
    }

    public String listarFornecedores() {
        if (listaFornecedores.isEmpty()) {
            return "Nenhum fornecedor cadastrado ainda :(";
        }

        StringBuilder sb = new StringBuilder("    Dados dos fornecedores cadastrados:\n\n");
        for (Fornecedor f : listaFornecedores) {
            String fund = (f.getFundacao() != null) ? sdf.format(f.getFundacao()) : "N/A";
            sb.append("Código: ").append(f.getCod()).append("\n")
                    .append("Nome: ").append(f.getNome()).append("\n")
                    .append("Fundação: ").append(fund).append("\n")
                    .append("Área: ").append(f.getArea() != null ? f.getArea().name() : "N/A").append("\n")
                    .append("");
        }
        return sb.toString();
    }
    public ResultadoAnalise consultarFornecedorMaiorQtde(CatalogoTecnologias tec){

        ArrayList<Fornecedor> listaVencedores = new ArrayList<>();
        List<Tecnologia> listaDeTecnologias = tec.getLista();
        if(listaDeTecnologias.isEmpty()){
            ResultadoAnalise nada = new ResultadoAnalise(listaVencedores,0);
            return nada;
        }
        int maiorQuantidade = 0;
        Fornecedor forn = listaDeTecnologias.getFirst().getFornecedor();
        for(Fornecedor f : listaFornecedores){
            int contadorAtual = 0;
            for (Tecnologia t : listaDeTecnologias){
                if(t.getFornecedor().getCod() == f.getCod()){
                    contadorAtual++;
                }

            }
            if (contadorAtual > 0) {
                if (contadorAtual > maiorQuantidade) {
                    // quando tiver um novo contador, limpa a lista atual e adiciona o novo
                    maiorQuantidade = contadorAtual;
                    listaVencedores.clear();
                    listaVencedores.add(f);
                } else if (contadorAtual == maiorQuantidade) {
                    // adiciona empate tbm
                    listaVencedores.add(f);
                }
            }

        }
        ResultadoAnalise retornar = new ResultadoAnalise(listaVencedores,maiorQuantidade);
        return retornar;
    }
    public List<Fornecedor> getTodosFornecedores() {
        return listaFornecedores;
    }

    public void limpar() {
        listaFornecedores.clear();
    }
}
