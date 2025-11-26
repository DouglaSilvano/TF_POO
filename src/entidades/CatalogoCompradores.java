package entidades;

import java.util.ArrayList;
import java.util.List;

public class CatalogoCompradores {

    // Agora é só List, nada de Map/TreeMap
    private List<Comprador> compradores;

    public CatalogoCompradores() {
        compradores = new ArrayList<>();
    }

    public boolean cadastrar(Comprador novoComprador) {
        // Garante que não tem código repetido
        if (buscarPorCodigo(novoComprador.getCod()) != null) {
            return false;
        }

        compradores.add(novoComprador);

        // Mantém em ordem de código (como o TreeMap fazia antes)
        compradores.sort((c1, c2) -> Long.compare(c1.getCod(), c2.getCod()));

        return true;
    }

    // Antes: Collection<Comprador>
    // Agora: List<Comprador>
    public List<Comprador> getTodosCompradores() {
        // Devolve uma cópia para não expor a lista interna diretamente
        return new ArrayList<>(compradores);
    }

    public String listarCompradores() {
        if (compradores.isEmpty()) {
            return "Nenhum comprador cadastrado no sistema.";
        }
        String retorno ="Aqui estão os compradores cadastrados: \n";
//        StringBuilder sb = new StringBuilder();
//        sb.append("Lista de compradores (ordem por código):\n");
//
//        for (Comprador c : compradores) {
//            sb.append(c.geraDescricao()).append("\n");
//        }
//
//        return sb.toString();

        for (Comprador c : compradores) {
            retorno = retorno + "--------------------------------------\n" + c.toString();
        }
        return retorno;
    }

    public List<Comprador> getLista() {
        // Se você quiser devolver a lista “real”, poderia retornar `compradores` direto,
        // mas mantive o padrão de devolver cópia, igual ao getTodosCompradores.
        return new ArrayList<>(compradores);
    }

    public Comprador buscarPorNome(String nome) {
        for (Comprador c : compradores) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null;
    }

    public Comprador buscarPorCodigo(Long codigo) {
        if (codigo == null) return null;
        for (Comprador c : compradores) {
            if (c.getCod() == codigo) {
                return c;
            }
        }
        return null;
    }

    public boolean atualizarComprador(long cod, String novoNome, String novoPais, String novoEmail) {
        Comprador c = buscarPorCodigo(cod);
        if (c == null) {
            return false;
        }

        c.setNome(novoNome);
        c.setPais(novoPais);
        c.setEmail(novoEmail);

        return true;
    }

    public ArrayList<Comprador> consultarMaiorCom(CatalogoCompradores com) {
        ArrayList<Comprador> retornar = new ArrayList<>();
        List<Comprador> listaCom = com.getLista();

        if (listaCom.isEmpty()) {
            return retornar;
        }

        int maiorQtdVendas = listaCom.get(0).getQtdVendas();

        // Descobre a maior quantidade de vendas
        for (Comprador c : listaCom) {
            if (c.getQtdVendas() > maiorQtdVendas) {
                maiorQtdVendas = c.getQtdVendas();
            }
        }

        // Adiciona todos que têm essa maior quantidade (empate)
        for (Comprador c : listaCom) {
            if (c.getQtdVendas() == maiorQtdVendas) {
                retornar.add(c);
            }
        }

        return retornar;
    }

    public void limpar() {
        compradores.clear();
    }

    @Override
    public String toString() {
        String s = "Compradores cadastrados no ACMETech:\n";
        for (Comprador c : compradores) {
            s += c.toString() + "--------------------------------------\n";
        }
        return s;
    }
}
