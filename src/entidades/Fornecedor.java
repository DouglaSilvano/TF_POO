package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fornecedor extends Participante {

    private Date fundacao;
    private Area area;

    public Fornecedor() {
        super();
        this.fundacao = null;
        this.area = null;
    }

    public Fornecedor(long cod, String nome, Date fundacao, Area area) {
        super(cod, nome);
        this.fundacao = fundacao;
        this.area = area;
    }

    public Fornecedor(long cod, String nome, Date fundacao) {
        super(cod, nome);
        this.fundacao = fundacao;
        this.area = null;
    }

    public Date getFundacao() {
        return fundacao;
    }

    public void setFundacao(Date fundacao) {
        this.fundacao = fundacao;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = (fundacao != null) ? sdf.format(fundacao) : "N/A";

        return "\nFornecedor\n" +
                "Código: " + getCod() + "\n" +
                "Nome: " + getNome() + "\n" +
                "Fundação: " + data + "\n" +
                "Área: " + (area != null ? area : "N/A") + "\n";
    }

    @Override
    public String geraDescricao() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = (fundacao != null) ? sdf.format(fundacao) : "N/A";

        return "Código: " + getCod() + "\n" +
                "Nome: " + getNome() + "\n" +
                "Fundação: " + data + "\n" +
                "Área de Atuação: " + (area != null ? area : "N/A");
    }
}
