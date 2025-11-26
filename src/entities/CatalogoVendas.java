package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CatalogoVendas {

    private ArrayList<Venda> listaVendas = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public CatalogoVendas() {
        sdf.setLenient(false);
    }

    public String cadastrarVenda(String numTexto, String dataTexto,
                                 Tecnologia tecnologia, Comprador comprador) {
        if (numTexto == null || numTexto.isEmpty() ||
                dataTexto == null || dataTexto.isEmpty() ||
                tecnologia == null || comprador == null) {
            return "Preencha todos os campos! :3";
        }
        try {
            long numero = Long.parseLong(numTexto);
            if (buscarVenda(numero) != null) {
                return "Número de venda repetido! <3";
            }
            if (tecnologia.isVendida()) {
                return "Tecnologia já foi vendida! :/ sinto muito";
            }
            Date data = sdf.parse(dataTexto);
            Venda nova = new Venda(numero, data, tecnologia, comprador);
            listaVendas.add(nova);
            listaVendas.sort(Comparator.comparingLong(Venda::getNum).reversed());
            tecnologia.setVendida(true);

            // Incrementar qtdVendas
            comprador.incrementarQtdVendas();
            return "Venda cadastrada com sucesso! <3";
        } catch (NumberFormatException e) {
            return "Número inválido!";
        } catch (ParseException e) {
            return "Data inválida!";
        }
    }

    public Venda buscarVenda(long num) {
        for (Venda v : listaVendas) {
            if (v.getNum() == num) return v;
        }
        return null;
    }

    public String relatorioVendas() {
        if (listaVendas.isEmpty()) {
            return "Nenhuma venda cadastrada.";
        }
        StringBuilder sb = new StringBuilder("RELATÓRIO DE VENDAS");
        for (Venda v : listaVendas) {
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }

    public String removerVenda(String numTexto) {
        try {
            long num = Long.parseLong(numTexto);
            Venda v = buscarVenda(num);
            if (v == null) {
                return "Venda não encontrada. :/";
            }
            if (v.getTecnologia() != null) {
                v.getTecnologia().setVendida(false);
            }

            // Decrementar qtdVendas
            Comprador c = v.getComprador();
            if (c != null) {
                c.decrementarQtdVendas();
            }

            listaVendas.remove(v);
            return "Venda removida com sucesso! :o";
        } catch (NumberFormatException e) {
            return "Número inválido! :(";
        }
    }

    public ArrayList<Venda> getLista() {
        return listaVendas;
    }

    public ArrayList<Venda> consultarVendaMaior() {
        ArrayList<Venda> retornar = new ArrayList<>();
        if (listaVendas.isEmpty()) {
            return retornar;
        }

        Venda maior = listaVendas.getFirst();

        for (Venda v : listaVendas) {
            if (v.getValorFinal() > maior.getValorFinal()) {
                maior = v;
            }
        }

        for (Venda v : listaVendas) {
            if (v.getValorFinal() == maior.getValorFinal()) {
                retornar.add(v);
            }
        }

        return retornar;
    }

    public void limpar() {
        listaVendas.clear();
    }
}
