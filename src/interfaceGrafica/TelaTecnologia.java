package interfaceGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;

import entities.*;

public class TelaTecnologia {
    private JPanel painel;
    //    private JTextField campoTexto;
    private JButton okButton;
    private JTextArea areaTexto;
    private JButton clearButton;
    private JLabel informacaoNecessaria;
    private JButton mostrarDadosButton;
    private JButton finalizarButton;
    private JTextArea textoPos;
    private JTextField textoId;
    private JTextField textoModelo;
    private JTextField textoDescricao;
    private JTextField textoValorBase;
    private JTextField textoPeso;
    private JTextField textoTemperatura;
    private JLabel Temperatura;
    private JLabel janelaErro;
    //    private static int contadorOkButton = 0; desnecessário já que nao tem mais o switch case
    private static int contadorEstatico = 0;
//    Tecnologia tecnologia = new Tecnologia(); estava qui antes mas quero mudar de lugar



    // boolean verificadorContador;
    // boolean erroID = false;

    public TelaTecnologia(CatalogoTecnologias catalogoTec) {


        informacaoNecessaria.setText("Para inicializar o programa, digite as informações necessárias e clique em OK.");
//        contadorOkButton = 0;
        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent x){
                //limpando a principal
                textoPos.setText("");
                janelaErro.setForeground(new java.awt.Color(0, 128, 0)); //green era muito lighter, entao coloquei codigo de cor
                janelaErro.setText("Tudo foi limpo!!");
                //limpando os textos digitados
                textoId.setText("");
                textoModelo.setText("");
                textoDescricao.setText("");
                textoValorBase.setText("");
                textoPeso.setText("");
                textoTemperatura.setText("");
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Tecnologia tecnologia = new Tecnologia();

                // variáveis
                String proximaMensagem = "";
                boolean avancarContador = true;


                // Variáveis
                long id;
//                String modelo;  desnecessario
//                String descricao; desnecessario
                double valorBase;
                double peso;
                double temperatura;
                //-------------
                String mensagemConfirmacao = "Ação concluída com sucesso!";
                String mensagemErro = "";
                String novoTexto = textoId.getText().trim();

                // Se esta vazio informa o usuário
                //    private static int contadorOkButton = 0; desnecessário já que nao tem mais o switch case
//                if (novoTexto.isEmpty() && contadorOkButton != 0) { // contadorOkButton != 0 por causa do segundo loop.
//                    informacaoNecessaria.setText("O campo não pode ser vazio! Informe o valor atual.");
//                    return;
//                }

                if(textoId.getText().isEmpty()){
                    proximaMensagem = "Erro de ID (o ID precisa ser preenchido).";
                    mensagemErro = proximaMensagem;
                    avancarContador = false;
                }
                try {
                    id = Long.parseLong(novoTexto);
                    tecnologia.setId(id);
//                            textoPos.append("ID = " + novoTexto + "\n");


                } catch (NumberFormatException x) {
                    mensagemErro = mensagemErro + "\n" + "Erro de ID (precisa ser um número inteiro válido).";
                    avancarContador = false;
                }


                novoTexto = textoModelo.getText().trim();
                tecnologia.setModelo(novoTexto);
//                        textoPos.append("Modelo = " + novoTexto + "\n");


                novoTexto = textoDescricao.getText().trim();
                tecnologia.setDescricao(novoTexto);
//                        textoPos.append("Descrição = " + novoTexto + "\n");



                novoTexto = textoValorBase.getText().trim();
                try {
                    valorBase = Double.parseDouble(novoTexto);
                    tecnologia.setValorBase(valorBase);
//                            textoPos.append("Valor base = " + novoTexto + "\n");
                } catch (NumberFormatException x) {
                    mensagemErro = mensagemErro + "\n" + " / Erro de valor base (precisa ser um número válido)";
                    avancarContador = false;
                }


                // recebendo o PESO
                novoTexto = textoPeso.getText().trim();
                try {
                    peso = Double.parseDouble(novoTexto);
                    tecnologia.setPeso(peso);
//                            textoPos.append("Peso = " + novoTexto + "\n");
                } catch (NumberFormatException x) {
                    mensagemErro = mensagemErro + "\n" + " / Erro de peso (precisa ser um número válido).";
                    avancarContador = false;
                }


                // recebendo a TEMPERATURA
                novoTexto = textoTemperatura.getText().trim();
                try {
                    temperatura = Double.parseDouble(novoTexto);
                    tecnologia.setTemperatura(temperatura);
//                            textoPos.append("Temperatura = " + novoTexto + "\n");


                    textoId.setText(""); // Limpa o campo para uma String sem nada
                    textoModelo.setText("");
                    textoDescricao.setText("");
                    textoValorBase.setText("");
                    textoPeso.setText("");
                    textoTemperatura.setText("");
                } catch (NumberFormatException x) {
                    mensagemErro = mensagemErro + "\n" + " / Erro de temperatura (precisa ser um número válido).";
                    avancarContador = false; // Não avança, tenta de novo
                }

                if(avancarContador){

                    if(catalogoTec.cadastrarTecnologia(tecnologia)){
                        proximaMensagem = "Você já pode iniciar um novo registro.";
                        contadorEstatico++;
                        String montagem = "Tecnologia " + contadorEstatico + " criada:" + "\n";
                        textoPos.setText("");
                        textoPos.append(montagem + tecnologia.toString());
                        janelaErro.setForeground(new java.awt.Color(0, 128, 0)); //green era muito lighter, entao coloquei codigo de cor
                        janelaErro.setText(mensagemConfirmacao);
                    }else{
                        tecnologia.setId(0);
                        tecnologia.setModelo("");
                        tecnologia.setDescricao("");
                        tecnologia.setValorBase(0.0);
                        tecnologia.setPeso(0.0);
                        tecnologia.setTemperatura(0.0);
                        tecnologia.defineFornecedor(null);
                        janelaErro.setForeground(java.awt.Color.RED);
                        mensagemErro = "ERRO, ID não pode ser repetido.";

                        janelaErro.setText(mensagemErro);
                    }
                }else{
                    //resetando a tecnologia já que deu erro.
                    tecnologia.setId(0);
                    tecnologia.setModelo("");
                    tecnologia.setDescricao("");
                    tecnologia.setValorBase(0.0);
                    tecnologia.setPeso(0.0);
                    tecnologia.setTemperatura(0.0);
                    tecnologia.defineFornecedor(null);
                    janelaErro.setForeground(java.awt.Color.RED);
                    proximaMensagem = "Infelizmente aconteceu um erro, tente novamente colocar os dados abaixo.";
                    janelaErro.setText(mensagemErro);
                }





//                // 1. Avança o contador (SÓ SE a validação deu tudo certo)
//                if (avancarContador) {
//                    if (contadorOkButton == 6) {
//                        // Se o último dado foi salvo com sucesso, reinicia o ciclo.
//                        contadorOkButton = 0;
//                    } else {
//                        contadorOkButton++;
//                    }
//                    textoId.setText(""); // Limpa o campo para uma String sem nada
//                    textoModelo.setText("");
//                    textoDescricao.setText("");
//                    textoValorBase.setText("");
//                    textoPeso.setText("");
//                    textoTemperatura.setText("");
//                }

                //  Atualiza para a proxima mensagem
                informacaoNecessaria.setText(proximaMensagem);
            }
        });

        finalizarButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(painel);
            if (window != null) {
                window.dispose();
            }
        });
        mostrarDadosButton.addActionListener(e -> {
            textoPos.setText("");
            textoPos.append(catalogoTec.mostrarDados());
        });

    }
    public JPanel getPainel() {
        return painel;
    }
}
