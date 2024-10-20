import java.util.LinkedList;
import java.util.Queue;

public class FIFO {
    public static int fifo(int[] paginas, int tamanhoQuadro, boolean imprime) {
        EntradaTabelaPaginas[] tabelaPaginas = new EntradaTabelaPaginas[tamanhoQuadro];
        for (int i = 0; i < tamanhoQuadro; i++) {
            tabelaPaginas[i] = new EntradaTabelaPaginas();  // Inicializando os quadros de página
        }

        Queue<Integer> filaMemoria = new LinkedList<>();
        int faltasPagina = 0;

        for (int pagina : paginas) {
            boolean paginaEncontrada = false;

            // Verifica se a página já está presente na tabela
            for (EntradaTabelaPaginas entrada : tabelaPaginas) {
                if (entrada.getNumeroQuadroPagina() == pagina && entrada.isPresente()) {
                    paginaEncontrada = true;
                    break;
                }
            }

            if (!paginaEncontrada) {
                faltasPagina++;
                if (filaMemoria.size() == tamanhoQuadro) {
                    // Substitui a página mais antiga (FIFO)
                    int paginaRemover = filaMemoria.poll();
                    for (EntradaTabelaPaginas entrada : tabelaPaginas) {
                        if (entrada.getNumeroQuadroPagina() == paginaRemover) {
                            entrada.setPresente(false);  // Marca como ausente
                            break;
                        }
                    }
                }
                // Adiciona a nova página à memória
                for (EntradaTabelaPaginas entrada : tabelaPaginas) {
                    if (!entrada.isPresente()) {
                        entrada.setNumeroQuadroPagina(pagina);
                        entrada.setPresente(true);
                        entrada.setReferenciada(false);  // Resetando referência
                        filaMemoria.add(pagina);
                        break;
                    }
                }
            }

            // Exibindo o estado atual da tabela de páginas
            if(imprime) imprimirTabelaPaginas(tabelaPaginas);
        }

        return faltasPagina;
    }

    // Método para imprimir o estado da tabela de páginas
    public static void imprimirTabelaPaginas(EntradaTabelaPaginas[] tabelaPaginas) {
        System.out.println("Estado atual da Tabela de Páginas:");
        for (EntradaTabelaPaginas entrada : tabelaPaginas) {
            System.out.println(entrada);
        }
        System.out.println();
    }
}
