public class SimuladorDeSubstituicaoDePaginas {

    public static void main(String[] args) {
        // Sequência de páginas a serem acessadas
        int[] paginas = {1, 2, 3, 4, 2, 1, 5, 6, 2, 3, 4, 7, 1, 2, 3, 4, 5, 5};
        
        // Tamanho do quadro de páginas
        int tamanhoQuadro = 4;

        // Executando os algoritmos e exibindo os resultados
        System.out.println("Executando o algoritmo FIFO:");
        int faltasFIFO = FIFO.fifo(paginas, tamanhoQuadro, false);
        System.out.println("FIFO - Faltas de página: " + faltasFIFO);
        System.out.println();

        System.out.println("Executando o algoritmo LRU:");
        int faltasLRU = LRU.lru(paginas, tamanhoQuadro, false);
        System.out.println("LRU - Faltas de página: " + faltasLRU);
        System.out.println();

        System.out.println("Executando o algoritmo do Relógio:");
        int faltasRelogio = Relogio.relogio(paginas, tamanhoQuadro, false);
        System.out.println("Relógio - Faltas de página: " + faltasRelogio);
        System.out.println();

        System.out.println("Executando o algoritmo NFU:");
        int faltasNFU = NFU.nfu(paginas, tamanhoQuadro, false);
        System.out.println("NFU - Faltas de página: " + faltasNFU);
        System.out.println();
    }
}
