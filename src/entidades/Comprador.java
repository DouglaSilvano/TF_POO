package entidades;

public class Comprador extends Participante {

    private String pais;
    private String email;
    private int qtdVendas = 0;

    public Comprador(long cod, String nome, String pais, String email) {
        super(cod, nome);
        this.pais = pais;
        this.email = email;
    }

    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getQtdVendas() {
        return qtdVendas;
    }

    // Incrementar qtdVendas
    public void incrementarQtdVendas() {
        this.qtdVendas++;
    }

    // Decrementar qtdVendas
    public void decrementarQtdVendas() {
        if (this.qtdVendas > 0) {
            this.qtdVendas--;
        }
    }

    @Override
    public String geraDescricao() {
        return getCod() + ";" + getNome() + ";" + pais + ";" + email;
    }

    @Override
    public String toString() {
//        return super.toString() + ", País: " + pais + ", Email: " + email;
        return  "Código = " + getCod() + "\n" +
                "Nome = " + getNome() + "\n" +
                "Pais = " + this.pais + "\n" +
                "Email = " + this.email + "\n";
    }
}

