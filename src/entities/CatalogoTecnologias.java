package entities;


import java.util.ArrayList;
import java.util.List;

public class CatalogoTecnologias {
    private List<Tecnologia> tecnologias = new ArrayList<>();

    public boolean cadastrarTecnologia(Tecnologia t) {
        for(int i = 0; i<tecnologias.size();i++){
            if(tecnologias.get(i).getId() == t.getId()){
                return false;
            }
        }
        adicionarOrdenado(t);
        return true;
    }
    public void adicionarOrdenado(Tecnologia t){
        if(tecnologias.isEmpty()){ // se ta vazio só entra
            this.tecnologias.add(t);
            return;
        }

        int indexColocar = 0;
        //metodo que verifica o ultimo numero que o id da tecnologia atual é maior, e coloca na frente
        for(Tecnologia tecnologia : tecnologias) {
            if(tecnologia.getId()<t.getId()){

                indexColocar = tecnologias.indexOf(tecnologia) + 1;
            }
        }
        this.tecnologias.add(indexColocar,t);
    }

    public String mostrarDados() {
        if(tecnologias.isEmpty()) {
            return "Nenhum dado foi cadastrado.";
        }

        return toString();
    }

    @Override
    public String toString() {
        String concatenamento = "Tecnologias cadastradas no ACMETech:" + "\n";
        for(Tecnologia tecnologia : tecnologias) {
            concatenamento = concatenamento + tecnologia.toString() + "--------------------------------------\n";
        }
        return concatenamento;
    }
}

