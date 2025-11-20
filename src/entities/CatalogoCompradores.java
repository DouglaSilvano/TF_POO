package entities;

import entities.Comprador;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;

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
}