import java.util.ArrayList;

public class LRU {
    public static int lru(int[] paginas, int tamanhoQuadro, boolean imprime) {
        EntradaTabelaPaginas[] tabelaPaginas = new EntradaTabelaPaginas[tamanhoQuadro];
        for (int i = 0; i < tamanhoQuadro; i++) {
            tabelaPaginas[i] = new EntradaTabelaPaginas();
        }

        ArrayList<Integer> listaUso = new ArrayList<>();  // Para rastrear a ordem de uso das páginas
        int faltasPagina = 0;

        for (int pagina : paginas) {
            boolean paginaEncontrada = false;

            // Verifica se a página já está presente na tabela
            for (EntradaTabelaPaginas entrada : tabelaPaginas) {
                if (entrada.getNumeroQuadroPagina() == pagina && entrada.isPresente()) {
                    paginaEncontrada = true;
                    listaUso.remove((Integer) pagina);  // Remove e adiciona ao final (mais recentemente usada)
                    listaUso.add(pagina);
                    break;
                }
            }

            if (!paginaEncontrada) {
                faltasPagina++;
                if (listaUso.size() == tamanhoQuadro) {
                    // Substitui a página menos recentemente usada (primeira da lista)
                    int paginaSubstituir = listaUso.remove(0);
                    for (EntradaTabelaPaginas entrada : tabelaPaginas) {
                        if (entrada.getNumeroQuadroPagina() == paginaSubstituir) {
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
                        entrada.setReferenciada(false);
                        listaUso.add(pagina);  // Adiciona a nova página ao final da lista
                        break;
                    }
                }
            }

            // Exibe o estado atual da tabela de páginas
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
