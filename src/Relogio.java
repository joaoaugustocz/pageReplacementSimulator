public class Relogio {
    public static int relogio(int[] paginas, int tamanhoQuadro, boolean imprime) {
        EntradaTabelaPaginas[] tabelaPaginas = new EntradaTabelaPaginas[tamanhoQuadro];
        for (int i = 0; i < tamanhoQuadro; i++) {
            tabelaPaginas[i] = new EntradaTabelaPaginas();
        }

        int ponteiro = 0;  // Ponteiro do relógio
        int faltasPagina = 0;

        for (int pagina : paginas) {
            boolean paginaEncontrada = false;

            // Verifica se a página já está presente na tabela
            for (EntradaTabelaPaginas entrada : tabelaPaginas) {
                if (entrada.getNumeroQuadroPagina() == pagina && entrada.isPresente()) {
                    paginaEncontrada = true;
                    entrada.setReferenciada(true);  // Marca como referenciada
                    break;
                }
            }

            if (!paginaEncontrada) {
                faltasPagina++;
                while (tabelaPaginas[ponteiro].isPresente() && tabelaPaginas[ponteiro].isReferenciada()) {
                    tabelaPaginas[ponteiro].setReferenciada(false);  // Dá uma segunda chance
                    ponteiro = (ponteiro + 1) % tamanhoQuadro;  // Avança o ponteiro de forma circular
                }
                // Substitui a página não referenciada
                tabelaPaginas[ponteiro].setNumeroQuadroPagina(pagina);
                tabelaPaginas[ponteiro].setPresente(true);
                tabelaPaginas[ponteiro].setReferenciada(true);  // Marca a nova página como referenciada
                ponteiro = (ponteiro + 1) % tamanhoQuadro;
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
