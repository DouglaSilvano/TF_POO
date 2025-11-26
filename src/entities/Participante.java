package entities;



public abstract class Participante {

    private long cod;
    private String nome;

    public Participante(long cod, String nome) {
        this.cod = cod;
        this.nome = nome;
    }

    public Participante() {

    }

    public long getCod() {
        return cod;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract String geraDescricao();

    @Override
    public String toString() {
        return "Código: " + cod + ", Nome: " + nome;
    }
}

