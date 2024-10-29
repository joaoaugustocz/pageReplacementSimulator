import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class SimuladorDeSubstituicaoPaginasComGraficos extends JFrame {

    private JTextField campoPaginas;
    private JTextField campoTamanhoQuadro;
    private JTextArea areaResultados;
    private JPanel painelGraficos;

    public SimuladorDeSubstituicaoPaginasComGraficos() {
        setTitle("Simulador de Substituição de Páginas 2");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior para entrada de dados e botões
        JPanel painelEntrada = new JPanel();
        painelEntrada.setLayout(new GridLayout(3, 2, 10, 10));  // Configura o layout com 3 linhas e 2 colunas

        // Campo para inserir sequência de páginas
        painelEntrada.add(new JLabel("Sequência de Páginas:"));
        campoPaginas = new JTextField(20);
        painelEntrada.add(campoPaginas);

        // Campo para inserir o tamanho do quadro de memória
        painelEntrada.add(new JLabel("Tamanho do Quadro de Memória:"));
        campoTamanhoQuadro = new JTextField(5);
        painelEntrada.add(campoTamanhoQuadro);

        // Botão para executar os algoritmos
        JButton botaoExecutar = new JButton("Executar Algoritmos");
        painelEntrada.add(botaoExecutar);

        // Botão para gerar dados aleatórios
        JButton botaoAleatorio = new JButton("Gerar Sequência Aleatória");
        painelEntrada.add(botaoAleatorio);

        // Área de texto para exibir os resultados
        areaResultados = new JTextArea(10, 40);
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);  // Para permitir rolagem
        add(scrollPane, BorderLayout.SOUTH);  // Coloca na parte inferior

        add(painelEntrada, BorderLayout.NORTH);

        // Painel inferior para gráficos
        painelGraficos = new JPanel();
        add(painelGraficos, BorderLayout.CENTER);

        // Ação ao clicar no botão Executar Algoritmos
        botaoExecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarAlgoritmos();
            }
        });

        // Ação ao clicar no botão Gerar Sequência Aleatória
        botaoAleatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarSequenciaAleatoria();
            }
        });
    }

    private void executarAlgoritmos() {
        String sequenciaPaginas = campoPaginas.getText();
        String tamanhoQuadroStr = campoTamanhoQuadro.getText();

        // Validação dos inputs
        if (sequenciaPaginas.isEmpty() || tamanhoQuadroStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira todos os dados!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Conversão da sequência de páginas e tamanho do quadro (usa espaços)
        String[] paginasStr = sequenciaPaginas.split("\\s+"); // Agora usa espaços em vez de vírgulas
        int[] paginas = new int[paginasStr.length];
        try {
            for (int i = 0; i < paginasStr.length; i++) {
                paginas[i] = Integer.parseInt(paginasStr[i].trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Sequência de páginas inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tamanhoQuadro;
        try {
            tamanhoQuadro = Integer.parseInt(tamanhoQuadroStr.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tamanho do quadro inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Executar os algoritmos e mostrar os resultados
        int resultadoFIFO = FIFO.fifo(paginas, tamanhoQuadro, false);
        int resultadoLRU = LRU.lru(paginas, tamanhoQuadro, false);
        int resultadoRelogio = Relogio.relogio(paginas, tamanhoQuadro, false);
        int resultadoNFU = NFU.nfu(paginas, tamanhoQuadro, false);

        // Exibir os resultados na área de texto
        areaResultados.setText("");
        areaResultados.append("Resultados dos Algoritmos:\n");
        areaResultados.append("FIFO: " + resultadoFIFO + " faltas de página\n");
        areaResultados.append("LRU: " + resultadoLRU + " faltas de página\n");
        areaResultados.append("Relógio: " + resultadoRelogio + " faltas de página\n");
        areaResultados.append("NFU: " + resultadoNFU + " faltas de página\n");
        areaResultados.append("Melhor: " + getMin(resultadoFIFO, resultadoLRU, resultadoRelogio, resultadoNFU) + "\n");
        

        // Atualizar o gráfico com os resultados
        atualizarGrafico(resultadoFIFO, resultadoLRU, resultadoRelogio, resultadoNFU);
    }

    private int getMin(int a, int b, int c, int d)
    {
         
        // Usa a função Math.min para comparar os valores
        int menorPrimeiroPar = Math.min(a, b);
        int menorSegundoPar = Math.min(c, d);
        
        // Compara os menores valores dos dois pares
        return Math.min(menorPrimeiroPar, menorSegundoPar);
        
    }
    // Função para gerar uma sequência aleatória de páginas
    private void gerarSequenciaAleatoria() {
        Random random = new Random();
        int tamanhoSequencia = random.nextInt(50) + 20; // Gera uma sequência entre 5 e 15 páginas
        StringBuilder sequencia = new StringBuilder();

        for (int i = 0; i < tamanhoSequencia; i++) {
            int paginaAleatoria = random.nextInt(20) + 10; // Páginas aleatórias entre 1 e 10
            sequencia.append(paginaAleatoria).append(" ");
        }

        campoPaginas.setText(sequencia.toString().trim()); // Coloca a sequência gerada no campo de páginas
    }

    // Função para atualizar o gráfico de barras
    private void atualizarGrafico(int resultadoFIFO, int resultadoLRU, int resultadoRelogio, int resultadoNFU) {
        painelGraficos.removeAll();

        // Criar dataset com os resultados
        CategoryDataset dataset = criarDataset(resultadoFIFO, resultadoLRU, resultadoRelogio, resultadoNFU);

        // Criar gráfico de barras
        JFreeChart grafico = ChartFactory.createBarChart(
                "Comparativo de Faltas de Página",       // Título do gráfico
                "Algoritmos",                           // Eixo X
                "Faltas de Página",                     // Eixo Y
                dataset                                 // Dados
        );

        // Exibir o gráfico no painel
        ChartPanel painelDoGrafico = new ChartPanel(grafico);
        painelGraficos.setLayout(new BorderLayout());
        painelGraficos.add(painelDoGrafico, BorderLayout.CENTER);
        painelGraficos.validate();
    }

    // Função para criar o dataset do gráfico
    private CategoryDataset criarDataset(int resultadoFIFO, int resultadoLRU, int resultadoRelogio, int resultadoNFU) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(resultadoFIFO, "Faltas de Página", "FIFO");
        dataset.addValue(resultadoLRU, "Faltas de Página", "LRU");
        dataset.addValue(resultadoRelogio, "Faltas de Página", "Relógio");
        dataset.addValue(resultadoNFU, "Faltas de Página", "NFU");
        
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimuladorDeSubstituicaoPaginasComGraficos().setVisible(true);
            }
        });
    }
}


//FIFO desvantagem:
//1 2 3 4 1 2 5 1 2 3 4 5
//3

//  LRU  vantagem
//7 0 1 2 0 3 0 4 2 3 0 3 2
//4
//Desvantagem:
//0 1 2 3 0 1 4 0 1 2 3 4
//3


//Relogio Equilibrio
//1 2 3 4 1 2 5 1 2 3 4 5
//3
//Vantagem
//2 3 2 1 5 2 4 5 3 2 1 4 5
//3

//NFU desvantagem
//1 1 1 2 2 2 3 3 3 4 4 4 1 2 3 4
//3