import java.util.Arrays;

public class NFU {
    public static int nfu(int[] paginas, int tamanhoQuadro, boolean imprime) {
        EntradaTabelaPaginas[] tabelaPaginas = new EntradaTabelaPaginas[tamanhoQuadro];
        for (int i = 0; i < tamanhoQuadro; i++) {
            tabelaPaginas[i] = new EntradaTabelaPaginas();
        }

        int[] contadoresUso = new int[tamanhoQuadro];  // Contadores para uso de cada página
        Arrays.fill(contadoresUso, 0);  // Inicializa os contadores com 0
        int faltasPagina = 0;

        for (int pagina : paginas) {
            boolean paginaEncontrada = false;

            // Verifica se a página já está presente na tabela
            for (int i = 0; i < tamanhoQuadro; i++) {
                if (tabelaPaginas[i].getNumeroQuadroPagina() == pagina && tabelaPaginas[i].isPresente()) {
                    contadoresUso[i]++;  // Incrementa o contador de uso
                    paginaEncontrada = true;
                    break;
                }
            }

            if (!paginaEncontrada) {
                faltasPagina++;
                // Encontra a página com o menor contador de uso para substituir
                int indiceSubstituir = 0;
                for (int i = 1; i < tamanhoQuadro; i++) {
                    if (contadoresUso[i] < contadoresUso[indiceSubstituir]) {
                        indiceSubstituir = i;
                    }
                }

                // Substitui a página com o menor uso
                tabelaPaginas[indiceSubstituir].setNumeroQuadroPagina(pagina);
                tabelaPaginas[indiceSubstituir].setPresente(true);
                contadoresUso[indiceSubstituir] = 1;  // Reinicia o contador para a nova página
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
