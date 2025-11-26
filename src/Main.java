import application.ACMETech;
import interfaceGrafica.AplicacaoMenu;
import interfaceGrafica.TelaInicio;
public class Main {
    public static void main(String[] args) {
        ACMETech sistema = new ACMETech();
        sistema.inicializar();        // lê arquivos de entrada com FILA
        new TelaInicio();             // abre a tela gráfica
    }
}


