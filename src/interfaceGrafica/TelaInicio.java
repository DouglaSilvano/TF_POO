package interfaceGrafica;

import javax.swing.*;
import java.awt.*;

public class TelaInicio extends JFrame {

    public TelaInicio() {

        setTitle("Tela Inicial");
        setSize(900, 600); // Janela menor para caber na tela
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); //nao poder aumentar a tela pra que n fique feia a imagem

        setLayout(new BorderLayout());
        //carregar a imagem que eu criei:
        ImageIcon img = new ImageIcon(getClass().getResource("/imagem/tela_inicial.png"));
        //ajustada pra caber na tela do swing
        Image imgAjustada = img.getImage().getScaledInstance(900, 450, Image.SCALE_SMOOTH);
        ImageIcon imgFinal = new ImageIcon(imgAjustada);

        JLabel imagemFundo = new JLabel(imgFinal);
        imagemFundo.setHorizontalAlignment(SwingConstants.CENTER);

        //adiciona imagem no centro
        add(imagemFundo, BorderLayout.CENTER);
        //botao
        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(new Color(220, 240, 240));
        painelBotao.setPreferredSize(new Dimension(900, 100));

        JButton iniciar = new JButton("Iniciar :3");
        iniciar.setFont(new Font("Comic Sans MS", Font.PLAIN, 28));//fonte do botao
        iniciar.setFocusPainted(false); //quis tirar a borda azul feia que windows users tem
        iniciar.setPreferredSize(new Dimension(200, 50)); //tamanho do botao
        iniciar.setForeground(Color.decode("#eba7a3"));; //botao no tom que escolhi pelo canva

        //tratamento de evento do botao (unico)
        iniciar.addActionListener(e -> {
            dispose();
            new AplicacaoMenu();
        });
        painelBotao.add(iniciar);
        //painel fica no sul (south) por questoes esteticas
        add(painelBotao, BorderLayout.SOUTH);
        setVisible(true);
    }
}