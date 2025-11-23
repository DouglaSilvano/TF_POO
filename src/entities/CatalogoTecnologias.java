package entities;

import java.util.ArrayList;
import java.util.List;

public class CatalogoTecnologias {

    private List<Tecnologia> tecnologias = new ArrayList<>();

    public boolean cadastrarTecnologia(Tecnologia t) {
        for (Tecnologia tec : tecnologias) {
            if (tec.getId() == t.getId()) {
                return false;
            }
        }
        adicionarOrdenado(t);
        return true;
    }

    public void adicionarOrdenado(Tecnologia t) {
        if (tecnologias.isEmpty()) {
            tecnologias.add(t);
            return;
        }

        int index = 0;
        for (Tecnologia tec : tecnologias) {
            if (tec.getId() < t.getId()) {
                index = tecnologias.indexOf(tec) + 1;
            }
        }
        tecnologias.add(index, t);
    }

    public String mostrarDados() {
        if (tecnologias.isEmpty()) {
            return "Nenhum dado foi cadastrado.";
        }
        return toString();
    }

    @Override
    public String toString() {
        String s = "Tecnologias cadastradas no ACMETech:\n";
        for (Tecnologia tec : tecnologias) {
            s += tec.toString() + "--------------------------------------\n";
        }
        return s;
    }

    public Tecnologia buscarPorModelo(String modelo) {
        for (Tecnologia t : tecnologias) {
            if (t.getModelo().equalsIgnoreCase(modelo)) {
                return t;
            }
        }
        return null;
    }

    public List<Tecnologia> getLista() {
        return tecnologias;
    }
}
