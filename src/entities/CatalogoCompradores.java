package entities;

import entities.Comprador;

import java.util.*;

public class CatalogoCompradores {

    private Map<Long, Comprador> compradores;

    public CatalogoCompradores() {
        compradores = new TreeMap<>();
    }

    public boolean cadastrar(Comprador novoComprador) {
        if (compradores.containsKey(novoComprador.getCod())) {
            return false;
        }

        compradores.put(novoComprador.getCod(), novoComprador);
        return true;
    }

    public Collection<Comprador> getTodosCompradores() {

        return compradores.values();
    }

    public String listarCompradores() {
        if (compradores.isEmpty()) {
            return "Nenhum comprador cadastrado no sistema.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Lista de compradores (ordem por código):\n");

        for (Comprador c : compradores.values()) {
            sb.append(c.geraDescricao()).append("\n");
        }

        return sb.toString();
    }

    public List<Comprador> getLista() {
        return new ArrayList<>(compradores.values());
    }

    public Comprador buscarPorNome(String nome) {
        for (Comprador c : compradores.values()) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null;
    }

    public Comprador buscarPorCodigo(Long codigo) {
        return compradores.get(codigo);
    }

    public boolean atualizarComprador(long cod, String novoNome, String novoPais, String novoEmail) {
        Comprador c = compradores.get(cod);
        if (c == null) {
            return false;
        }

        c.setNome(novoNome);
        c.setPais(novoPais);
        c.setEmail(novoEmail);

        return true;

    }
    public ArrayList<Comprador> consultarMaiorCom(CatalogoCompradores com){
        ArrayList<Comprador> retornar = new ArrayList();
        List<Comprador> listaCom= com.getLista();
        if(listaCom.isEmpty()){
            return retornar;
        }
        int maiorQtdVendas = listaCom.get(0).getQtdVendas();
        for(Comprador c :listaCom){
            if(c.getQtdVendas()>maiorQtdVendas){
                maiorQtdVendas=c.getQtdVendas();
            }
        }
        for(Comprador c :listaCom){
            if(c.getQtdVendas()==maiorQtdVendas){

                retornar.add(c);
            }

        }
        return retornar;
    }

    public void limpar() {
        compradores.clear();
    }
}