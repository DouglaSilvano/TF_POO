package entities;

import entities.Participante;

public class Comprador extends Participante {

    private String pais;
    private String email;

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

    @Override
    public String geraDescricao() {
        return getCod() + ";" + getNome() + ";" + pais + ";" + email;
    }

    @Override
    public String toString() {
        return super.toString() + ", País: " + pais + ", Email: " + email;
    }
}

