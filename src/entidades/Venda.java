package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Venda {

    private long num;
    private Date data;
    private double valorFinal;
    private Tecnologia tecnologia;
    private Comprador comprador;

    public Venda(long num, Date data, Tecnologia tecnologia, Comprador comprador) {
        this.num = num;
        this.data = data;
        this.tecnologia = tecnologia;
        this.comprador = comprador;
        this.valorFinal = calculaValorFinal();
    }

    private double calculaValorFinal() {
        double valorBase = tecnologia.getValorBase();
        double acrescimo = 0;

        Fornecedor fornecedor = tecnologia.getFornecedor();
        if (fornecedor != null) {
            Area area = fornecedor.getArea();
            switch (area) {
                case TI: acrescimo = 0.20; break;
                case ANDROIDES: acrescimo = 0.15; break;
                case EMERGENTE: acrescimo = 0.25; break;
                case ALIMENTOS: acrescimo = 0.10; break;
            }
        }
        double valorComAcrescimo = valorBase * (1 + acrescimo);
        int n = comprador.getQtdVendas();
        double desconto = Math.min(n * 0.01, 0.10);
        return valorComAcrescimo * (1 - desconto);
    }

    public long getNum() { return num; }
    public Date getData() { return data; }
    public double getValorFinal() { return valorFinal; }
    public Tecnologia getTecnologia() { return tecnologia; }
    public Comprador getComprador() { return comprador; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        return "\nNúmero: " + num +
                "\nData: " + sdf.format(data) +
                "\nValor Final: R$ " + valorFinal +
                "\n Tecnologia \n" + tecnologia +
                "\n Comprador \n" + comprador +
                "                   ";
    }
}
