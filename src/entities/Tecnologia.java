package entities;

public class Tecnologia {

    private long id;

    private String modelo;

    private String descricao;

    private double valorBase;

    private double peso;

    private double temperatura;

    private Fornecedor fornecedor;

    public void defineFornecedor(Fornecedor f) { //????
        this.fornecedor = f;
    }

    public Tecnologia(long id, String modelo, String descricao, double peso, double valorBase, double temperatura, Fornecedor fornecedor) {
        this.id = id;
        this.modelo = modelo;
        this.descricao = descricao;
        this.peso = peso;
        this.valorBase = valorBase;
        this.temperatura = temperatura;
        this.fornecedor = fornecedor;
    }

    public Tecnologia() {
        this.id = 0;
        this.modelo = "";
        this.descricao = "";
        this.peso = 0;
        this.valorBase = 0;
        this.temperatura = 0;
        this.fornecedor = null;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        String stringFornecedor;
        if(this.fornecedor != null){
            stringFornecedor = fornecedor.geraDescricao();
        }else {
            stringFornecedor = "Não cadastrado.";
        }

        return
                "ID = " + id + "\n" +
                        "Modelo = " + modelo + "\n" +
                        "Descrição = " + descricao + "\n" +
                        "Valor Base = " + valorBase + "\n" +
                        "Peso = " + peso + "\n" +
                        "Temperatura = " + temperatura +"\n" +
                        "Fornecedor = " + stringFornecedor + '.' + "\n";
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getPeso() {
        return peso;
    }

    public double getValorBase() {
        return valorBase;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getModelo() {
        return modelo;
    }

    public long getId() {
        return id;
    }


}

