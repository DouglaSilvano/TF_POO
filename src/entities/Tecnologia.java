package entities;

public class Tecnologia {

    private long id;
    private String modelo;
    private String descricao;
    private double valorBase;
    private double peso;
    private double temperatura;

    private Fornecedor fornecedor;

    private boolean vendida = false;

    // ===== CONSTRUTORES =====

    public Tecnologia(long id, String modelo, String descricao, double peso,
                      double valorBase, double temperatura, Fornecedor fornecedor) {
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

    public void defineFornecedor(Fornecedor f) {
        this.fornecedor = f;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor f) {
        this.fornecedor = f;
    }

    public boolean isVendida() {
        return vendida;
    }

    public void setVendida(boolean vendida) {
        this.vendida = vendida;
    }

    @Override
    public String toString() {
        String stringFornecedor;

        if (this.fornecedor != null) {
            stringFornecedor = fornecedor.geraDescricao();
        } else {
            stringFornecedor = "Não cadastrado.";
        }

        return
                "ID = " + id + "\n" +
                        "Modelo = " + modelo + "\n" +
                        "Descrição = " + descricao + "\n" +
                        "Valor Base = " + valorBase + "\n" +
                        "Peso = " + peso + "\n" +
                        "Temperatura = " + temperatura + "\n" +
                        "Fornecedor = " + stringFornecedor + "\n" +
                        "Vendida = " + vendida + "\n";
    }
}
