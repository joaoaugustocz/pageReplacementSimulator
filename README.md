# Objetivo do Algoritmo FIFO
O algoritmo FIFO substitui a página mais antiga que está na memória para dar espaço a uma nova página. Ele mantém uma fila das páginas em ordem de chegada e remove sempre a página que está no início da fila, independentemente da importância ou do quanto ela está sendo acessada.

## Explicação do Código Passo a Passo:
1. Declaração de Dependências

``` java
import java.util.LinkedList;
import java.util.Queue;
```

LinkedList e Queue: O código usa uma estrutura de fila (Queue) para armazenar as páginas na ordem em que são carregadas na memória. Como uma fila FIFO (Primeiro a Entrar, Primeiro a Sair), a página que está há mais tempo na memória é a primeira a ser removida.

2. Método Principal fifo()

``` java
public static int fifo(int[] paginas, int tamanhoQuadro, boolean imprime) {
```
- **int[] paginas**: Um array de inteiros que representa a sequência de páginas a serem acessadas.

- **int tamanhoQuadro**: O número de quadros de página disponíveis na memória. Esse número limita a quantidade de páginas que podem ser mantidas na memória ao mesmo tempo.

boolean imprime: Um parâmetro opcional que, se verdadeiro, imprime o estado da tabela de páginas a cada iteração. Isso ajuda a visualizar o comportamento do algoritmo.

3. Inicialização da Tabela de Páginas

``` java
EntradaTabelaPaginas[] tabelaPaginas = new EntradaTabelaPaginas[tamanhoQuadro];
for (int i = 0; i < tamanhoQuadro; i++) {
    tabelaPaginas[i] = new EntradaTabelaPaginas();  // Inicializando os quadros de página
}
```
Aqui, criamos uma tabela de páginas com o tamanho do quadro definido (tamanhoQuadro). Cada elemento da tabela é uma instância da classe EntradaTabelaPaginas, que representa uma página na memória.
Inicialmente, todos os quadros estão vazios (sem páginas).

4. Inicialização da Fila e Contador de Faltas de Página
``` java
Queue<Integer> filaMemoria = new LinkedList<>();
int faltasPagina = 0;
```
- **filaMemoria**: Armazena as páginas na ordem em que são carregadas na memória. Quando uma nova página é carregada, ela é colocada no final da fila, e a página mais antiga é removida.

- **faltasPagina**: Contador que armazena o número de faltas de página (quando uma página que não está na memória é solicitada e precisa ser carregada).

5. Iteração pelas Páginas
``` java
for (int pagina : paginas) {
    boolean paginaEncontrada = false;
```
O algoritmo começa iterando sobre o array de páginas paginas. Para cada página solicitada, a variável paginaEncontrada é inicializada como false, indicando que ainda não foi encontrada a página na memória.

6. Verificação se a Página já Está na Memória

``` java
for (EntradaTabelaPaginas entrada : tabelaPaginas) {
    if (entrada.getNumeroQuadroPagina() == pagina && entrada.isPresente()) {
        paginaEncontrada = true;
        break;
    }
}
```

Para cada página solicitada, o código verifica se ela já está na tabela de páginas. Se a página for encontrada na memória (`entrada.getNumeroQuadroPagina() == pagina && entrada.isPresente()`), paginaEncontrada é definida como true, e não será necessário carregar a página (evitando uma falta de página).

7. Tratamento de Faltas de Página
``` java
if (!paginaEncontrada) {
    faltasPagina++;
```

Se a página não foi encontrada na memória, ocorre uma falta de página, e o contador faltasPagina é incrementado.

8. Substituição da Página Mais Antiga (FIFO)

``` java
if (filaMemoria.size() == tamanhoQuadro) {
    int paginaRemover = filaMemoria.poll();
    for (EntradaTabelaPaginas entrada : tabelaPaginas) {
        if (entrada.getNumeroQuadroPagina() == paginaRemover) {
            entrada.setPresente(false);  // Marca como ausente
            break;
        }
    }
}
```

Se a fila de memória já está cheia (ou seja, o número de páginas na memória é igual ao tamanho dos quadros), a página mais antiga é removida usando `filaMemoria.poll()`. Isso segue o princípio do FIFO, onde a primeira página a entrar é a primeira a sair.

O código marca a página removida como ausente (`entrada.setPresente(false)`), indicando que ela não está mais na memória.

9. Adicionando a Nova Página à Memória
``` java
for (EntradaTabelaPaginas entrada : tabelaPaginas) {
    if (!entrada.isPresente()) {
        entrada.setNumeroQuadroPagina(pagina);
        entrada.setPresente(true);
        entrada.setReferenciada(false);  // Resetando referência
        filaMemoria.add(pagina);
        break;
    }
}
```
Agora, a nova página solicitada é carregada na memória. O código procura um quadro vazio (`!entrada.isPresente()`) e carrega a nova página nesse quadro. A página é marcada como presente (`entrada.setPresente(true)`), e a referência é redefinida como false.

A nova página é então adicionada ao final da fila de memória (`filaMemoria.add(pagina)`), seguindo a ordem FIFO.

10. Impressão do Estado da Tabela de Páginas
``` java
if(imprime) imprimirTabelaPaginas(tabelaPaginas);
```
Se o parâmetro imprime for verdadeiro, o estado da tabela de páginas é impresso a cada iteração, permitindo que você veja como as páginas estão sendo gerenciadas na memória.

11. Retorno do Número de Faltas de Página
``` java
return faltasPagina;
```
Ao final do método, o número total de faltas de página é retornado.

12. Função de Impressão
``` java
public static void imprimirTabelaPaginas(EntradaTabelaPaginas[] tabelaPaginas) {
    System.out.println("Estado atual da Tabela de Páginas:");
    for (EntradaTabelaPaginas entrada : tabelaPaginas) {
        System.out.println(entrada);
    }
    System.out.println();
}
```
Essa função imprime o estado atual da tabela de páginas, exibindo quais páginas estão presentes na memória e quais estão ausentes, junto com suas respectivas propriedades.

## Conclusão:
Este código implementa o algoritmo FIFO de acordo com a explicação vista em aula:

A página mais antiga (a que está no início da fila) é removida sempre que uma nova página precisa ser carregada e não há espaço disponível.
O algoritmo não leva em consideração se a página mais antiga ainda está sendo usada com frequência, o que é uma das suas principais desvantagens.
Ele tem uma implementação simples, mas, como mencionado, não oferece os melhores resultados, especialmente quando as páginas mais antigas ainda são relevantes para o sistema.



# Objetivo do Algoritmo LRU
O algoritmo LRU (Least Recently Used) substitui a página que foi menos recentemente usada. Ele é mais eficiente que o FIFO porque leva em consideração o uso das páginas, substituindo aquela que não foi acessada por mais tempo.

## Explicação do Código Passo a Passo:
1. Declaração de Dependências
``` java
import java.util.ArrayList;
```
O código usa uma estrutura de lista (ArrayList) para rastrear a ordem de uso das páginas, atualizando a ordem sempre que uma página é acessada.

2. Método Principal lru()
``` java
public static int lru(int[] paginas, int tamanhoQuadro, boolean imprime) {
```
- `int[] paginas`: Um array de inteiros que representa a sequência de páginas a serem acessadas.
- `int tamanhoQuadro`: O número de quadros de página disponíveis na memória.
- `boolean imprime`: Um parâmetro opcional que, se verdadeiro, imprime o estado da tabela de páginas a cada iteração.

3. Inicialização da Tabela de Páginas
``` java
EntradaTabelaPaginas[] tabelaPaginas = new EntradaTabelaPaginas[tamanhoQuadro];
for (int i = 0; i < tamanhoQuadro; i++) {
    tabelaPaginas[i] = new EntradaTabelaPaginas();  // Inicializando os quadros de página
}
```
A tabela de páginas é criada com base no número de quadros disponíveis. Cada entrada na tabela representa uma página na memória.

4. Inicialização da Lista de Uso
``` java
ArrayList<Integer> listaUso = new ArrayList<>();  // Para rastrear a ordem de uso das páginas
int faltasPagina = 0;
```
- **listaUso**: Armazena as páginas na ordem em que foram acessadas. A página menos recentemente usada está no início da lista.

- **faltasPagina**: Contador de faltas de página.

5. Iteração pelas Páginas
``` java
for (int pagina : paginas) {
    boolean paginaEncontrada = false;
```
O código percorre todas as páginas solicitadas, verificando se elas já estão na memória.

6. Verificação se a Página já Está na Memória
``` java
for (EntradaTabelaPaginas entrada : tabelaPaginas) {
    if (entrada.getNumeroQuadroPagina() == pagina && entrada.isPresente()) {
        paginaEncontrada = true;
        listaUso.remove((Integer) pagina);  // Move para o final da lista
        listaUso.add(pagina);
        break;
    }
}
```
Se a página já estiver presente na memória, ela é removida da lista de uso e adicionada ao final, indicando que foi recentemente usada.

7. Tratamento de Faltas de Página
``` java
if (!paginaEncontrada) {
    faltasPagina++;
```
Se a página não for encontrada na memória, ocorre uma falta de página e o contador é incrementado.

8. Substituição da Página Menos Recentemente Usada
``` java
if (listaUso.size() == tamanhoQuadro) {
    int paginaSubstituir = listaUso.remove(0);  // Remove a primeira página (menos usada)
    for (EntradaTabelaPaginas entrada : tabelaPaginas) {
        if (entrada.getNumeroQuadroPagina() == paginaSubstituir) {
            entrada.setPresente(false);  // Marca como ausente
            break;
        }
    }
}
```
Se a memória estiver cheia, a página menos recentemente usada (no início da lista) é removida para dar lugar à nova página.

9. Adicionando a Nova Página à Memória
``` java
for (EntradaTabelaPaginas entrada : tabelaPaginas) {
    if (!entrada.isPresente()) {
        entrada.setNumeroQuadroPagina(pagina);
        entrada.setPresente(true);
        entrada.setReferenciada(false);
        listaUso.add(pagina);
        break;
    }
}
```
A nova página é carregada na memória e adicionada ao final da lista de uso, indicando que foi recentemente acessada.

10. Impressão do Estado da Tabela de Páginas
``` java
if(imprime) imprimirTabelaPaginas(tabelaPaginas);
```
Se o parâmetro imprime for verdadeiro, a tabela de páginas é impressa para visualização.

11. Retorno do Número de Faltas de Página
``` java
return faltasPagina;
```
O número total de faltas de página é retornado.