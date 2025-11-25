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
    public ArrayList<Tecnologia> consultarTecnologiaMaior(){
        ArrayList<Tecnologia> retornar = new ArrayList();
        if(tecnologias.isEmpty()){
            return retornar;
        }
        int contador = 0;

        Tecnologia maior = tecnologias.getFirst();
        for(Tecnologia t: tecnologias){
            if(maior.getValorBase() < t.getValorBase()){
                maior = t;
            }
        }
        retornar.add(maior);
        for(Tecnologia t: tecnologias){
            if(maior.getValorBase() == t.getValorBase()){
                if(contador != 0){
                    retornar.add(t);
                }
                contador++;
            }
        }
        return retornar;
    }

    public List<Tecnologia> getLista() {
        return tecnologias;
    }

    public void limpar() {
        tecnologias.clear();
    }

    public Tecnologia buscarPorId(long id) {
        for (Tecnologia t : tecnologias) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
//    public int size() {
//        return tecnologias.size();
//    }
}
