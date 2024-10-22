# Introdução
Este projeto tem como objetivo comparar o desempenho de quatro algoritmos clássicos de substituição de páginas: FIFO (First In, First Out), LRU (Least Recently Used), Relógio (Clock) e NFU (Not Frequently Used). A substituição de páginas é uma técnica essencial para o gerenciamento de memória em sistemas operacionais, especialmente em cenários de multitarefa, onde os processos precisam compartilhar o espaço limitado de memória disponível.

Cada algoritmo tem suas particularidades. O FIFO é simples, mas pode acabar substituindo páginas que ainda estão sendo usadas com frequência, já que ele não considera o uso recente. O LRU é mais sofisticado, levando em conta quais páginas foram usadas recentemente, mas isso pode implicar em uma maior complexidade na sua implementação. O Relógio, por sua vez, é uma versão otimizada do FIFO, que utiliza um bit de referência para dar uma "segunda chance" às páginas, tentando encontrar um equilíbrio entre eficiência e simplicidade. Por fim, o NFU mantém um contador de frequência de uso das páginas e substitui a que foi menos usada ao longo do tempo, o que pode funcionar bem em cenários com padrões de acesso previsíveis.

Diante dessas características, podemos deduzir que o LRU provavelmente deve ter a melhor performance entre os quatro algoritmos, já que ele tenta garantir que as páginas mais usadas recentemente permaneçam na memória, evitando a substituição de páginas importantes. O Relógio também pode apresentar um bom desempenho, pois é uma otimização do FIFO. Já o FIFO, por sua simplicidade, tende a ter uma performance inferior, pois ele não leva em consideração o uso das páginas. O NFU, por se basear em contadores, pode se sair bem em alguns casos, mas pode apresentar problemas ao não considerar o tempo desde o último acesso.




# Algoritmo FIFO (First In First Out)

## Objetivo do Algoritmo FIFO
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
- `filaMemoria`: Armazena as páginas na ordem em que são carregadas na memória. Quando uma nova página é carregada, ela é colocada no final da fila, e a página mais antiga é removida.

- `faltasPagina`: Contador que armazena o número de faltas de página (quando uma página que não está na memória é solicitada e precisa ser carregada).

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


# Algoritmo LRU (Least Recently Used)

## Objetivo do Algoritmo LRU
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
- `listaUso`: Armazena as páginas na ordem em que foram acessadas. A página menos recentemente usada está no início da lista.

- `faltasPagina`: Contador de faltas de página.

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

## Conclusão
O algoritmo LRU (Least Recently Used) é uma solução eficaz para a substituição de páginas, utilizando a lógica de que a página menos recentemente usada é a mais provável a não ser necessária no futuro próximo. Isso o torna uma melhoria significativa em relação ao algoritmo FIFO, que simplesmente substitui as páginas com base na ordem de chegada, sem considerar seu uso recente.

O LRU se destaca por ser intuitivo e eficiente em cenários onde o padrão de acesso às páginas é previsível, garantindo que as páginas mais usadas recentemente permaneçam na memória. No entanto, sua implementação pode exigir estruturas de dados adicionais, como listas ou pilhas, para rastrear a ordem de uso das páginas, o que pode adicionar uma sobrecarga computacional, dependendo da abordagem escolhida.

Ainda assim, o LRU oferece uma boa relação custo-benefício em termos de desempenho para muitos sistemas, especialmente onde a localidade temporal (uso recente de páginas) é um fator chave no padrão de acessos.

# Algoritmo NFU (Not Frequently Used)
## Objetivo do Algoritmo NFU
O algoritmo NFU (Not Frequently Used), ou "Não Frequentemente Usado", é uma técnica de substituição de páginas que prioriza as páginas que são usadas com menos frequência. Cada página tem um contador associado que é incrementado toda vez que a página é acessada. Quando é necessário substituir uma página, a página com o menor valor no contador é a escolhida, assumindo que ela é a menos utilizada.

Esse algoritmo é útil em cenários onde o uso das páginas pode ser esporádico, e queremos manter na memória as páginas que são acessadas mais vezes.

Explicação do Código Passo a Passo
1. Declaração de Dependências
O código para o NFU não requer bibliotecas externas além das comuns do Java. Ele opera diretamente sobre os arrays de páginas e quadros de memória.

``` java
import java.util.Arrays;
```
Essa dependência é usada para inicializar e manipular arrays.

2. Método Principal nfu()
``` java
public static int nfu(int[] paginas, int tamanhoQuadro, boolean imprime) {
```
`int[] paginas`: O array que contém a sequência de páginas solicitadas.
`int tamanhoQuadro`: O número de quadros disponíveis na memória.
`boolean imprime`: Um parâmetro opcional que, se verdadeiro, imprime o estado da memória após cada operação.

3. Inicialização da Tabela de Páginas e Contadores
``` java
EntradaTabelaPaginas[] tabelaPaginas = new EntradaTabelaPaginas[tamanhoQuadro];
int[] contadoresUso = new int[tamanhoQuadro]; // Contadores para o uso de cada página
Arrays.fill(contadoresUso, 0);
```

Aqui, criamos dois arrays:


`tabelaPaginas`: Para armazenar o número de páginas presentes em cada quadro de memória.
`contadoresUso`: Um array para registrar quantas vezes cada página foi acessada. Inicialmente, todos os contadores são preenchidos com zero.

4. Iteração pelas Páginas
``` java
for (int pagina : paginas) {
    boolean paginaEncontrada = false;

    // Verifica se a página já está na memória
    for (int i = 0; i < tamanhoQuadro; i++) {
        if (tabelaPaginas[i].getNumeroQuadroPagina() == pagina && tabelaPaginas[i].isPresente()) {
            contadoresUso[i]++; // Incrementa o contador de uso
            paginaEncontrada = true;
            break;
        }
    }
```
O código percorre a sequência de páginas solicitadas e verifica se a página já está na memória. Se a página estiver presente, o contador de uso associado a essa página é incrementado.

5. Tratamento de Faltas de Página
``` java
if (!paginaEncontrada) {
    faltasPagina++;
    // Encontrar a página com o menor contador de uso
    int indiceSubstituir = 0;
    for (int i = 1; i < tamanhoQuadro; i++) {
        if (contadoresUso[i] < contadoresUso[indiceSubstituir]) {
            indiceSubstituir = i;
        }
    }
```
Se a página solicitada não estiver na memória (falta de página), o contador de faltas de página é incrementado. O algoritmo então busca a página com o menor contador de uso, que será substituída.

6. Substituição da Página
``` java
tabelaPaginas[indiceSubstituir].setNumeroQuadroPagina(pagina);
tabelaPaginas[indiceSubstituir].setPresente(true);
contadoresUso[indiceSubstituir] = 1; // Inicializa o contador para a nova página
```
Uma vez encontrada a página menos usada, ela é removida da memória e substituída pela nova página. O contador de uso para essa nova página é inicializado com 1.

7. Impressão do Estado da Memória (Opcional)
Se a variável imprime for verdadeira, o estado atual da tabela de páginas é exibido após cada operação.

``` java
if (imprime) {
    imprimirTabelaPaginas(tabelaPaginas);
}
```
Esse método é útil para debugar o comportamento do algoritmo, exibindo quais páginas estão sendo armazenadas na memória e suas contagens de uso.

8. Retorno do Número de Faltas de Página
``` java
return faltasPagina;
```
No final da execução do algoritmo, o número total de faltas de página é retornado.

## Conclusão
O algoritmo NFU (Not Frequently Used) mantém um histórico simples da frequência de uso das páginas para decidir quais substituir. As páginas que são acessadas com menos frequência são removidas da memória, e as mais acessadas permanecem. É um algoritmo relativamente simples, mas eficaz em ambientes onde o uso das páginas segue padrões previsíveis.

Seu principal ponto forte é a simplicidade de implementação, mas pode não ser ideal em todos os cenários, pois o algoritmo não considera o tempo desde o último acesso, o que pode resultar na substituição de páginas que podem ser acessadas em breve.


# Algoritmo Relógio (Clock)
## Objetivo do Algoritmo Relógio (Clock)
O algoritmo Relógio (Clock) é uma versão otimizada do FIFO que simula o funcionamento de um ponteiro de relógio. Ele utiliza um bit de referência para dar uma "segunda chance" às páginas que foram acessadas recentemente. Se o bit de referência de uma página é 1, ela não é imediatamente removida quando o ponteiro a alcança; ao invés disso, o ponteiro avança e o bit é resetado. Se o bit é 0, a página é substituída.

Esse algoritmo tenta evitar a substituição de páginas que podem ser reutilizadas logo em seguida, sendo uma melhoria sobre o FIFO clássico.

## Explicação do Código Passo a Passo
1. Declaração de Dependências
Assim como no NFU, o algoritmo Relógio utiliza apenas as dependências padrão do Java:

``` java
import java.util.Arrays;
```
Essa dependência é usada para manipular arrays e garantir a inicialização adequada.

2. Método Principal relogio()
``` java
public static int relogio(int[] paginas, int tamanhoQuadro, boolean imprime) {
```
`int[] paginas`: O array que contém a sequência de páginas acessadas.
`int tamanhoQuadro`: O número de quadros disponíveis na memória.
`boolean imprime`: Um parâmetro opcional que, se verdadeiro, imprime o estado da memória a cada operação.


3. Inicialização da Tabela de Páginas e Ponteiro do Relógio
``` java
EntradaTabelaPaginas[] tabelaPaginas = new EntradaTabelaPaginas[tamanhoQuadro];
int ponteiro = 0;  // Ponteiro que simula o ponteiro de um relógio
```
Aqui, inicializamos a tabela de páginas e o ponteiro. O ponteiro funciona como um marcador circular que percorre as páginas na memória, da mesma forma que o ponteiro das horas percorre um relógio.

4. Iteração pelas Páginas e Verificação
``` java
for (int pagina : paginas) {
    boolean paginaEncontrada = false;

    // Verifica se a página já está na memória
    for (EntradaTabelaPaginas entrada : tabelaPaginas) {
        if (entrada.getNumeroQuadroPagina() == pagina && entrada.isPresente()) {
            entrada.setReferenciada(true);  // Marca a página como referenciada
            paginaEncontrada = true;
            break;
        }
    }
```
O algoritmo percorre a sequência de páginas e verifica se cada página já está presente na memória. Se a página já estiver na memória, o bit de referência é ativado, indicando que ela foi acessada recentemente.

5. Tratamento de Faltas de Página e Uso do Ponteiro
Se a página não estiver na memória, ocorre uma falta de página, e o ponteiro começa a avançar:

``` java
if (!paginaEncontrada) {
    faltasPagina++;

    // Enquanto o ponteiro encontrar páginas referenciadas, avança o ponteiro
    while (tabelaPaginas[ponteiro].isPresente() && tabelaPaginas[ponteiro].isReferenciada()) {
        tabelaPaginas[ponteiro].setReferenciada(false);  // Reseta o bit de referência
        ponteiro = (ponteiro + 1) % tamanhoQuadro;  // Move o ponteiro de forma circular
    }
```
Aqui, se a página solicitada não estiver na memória:

O ponteiro percorre a memória em busca de uma página não referenciada (ou seja, com o bit de referência igual a 0).
Caso o bit de referência seja 1, ele é resetado, e o ponteiro avança.
Esse comportamento simula a "segunda chance" que é dada às páginas que foram recentemente referenciadas.

6. Substituição da Página
``` java
tabelaPaginas[ponteiro].setNumeroQuadroPagina(pagina);
tabelaPaginas[ponteiro].setPresente(true);
tabelaPaginas[ponteiro].setReferenciada(true);  // A nova página é referenciada
ponteiro = (ponteiro + 1) % tamanhoQuadro;  // Avança o ponteiro após a substituição
```
Quando o ponteiro encontra uma página com o bit de referência igual a 0, essa página é substituída pela nova página. O bit de referência da nova página é ativado, e o ponteiro avança para a próxima posição.

7. Impressão do Estado da Memória (Opcional)
``` java
if (imprime) {
    imprimirTabelaPaginas(tabelaPaginas);
}
```

Se o parâmetro imprime for verdadeiro, o estado da memória é exibido após cada operação. Isso ajuda a entender como o ponteiro avança e as páginas são substituídas.


8. Retorno do Número de Faltas de Página
``` java
return faltasPagina;
```
No final da execução do algoritmo, o número total de faltas de página é retornado.

## Conclusão
O algoritmo Relógio (Clock) é uma alternativa eficiente ao FIFO, utilizando um bit de referência para identificar páginas que foram acessadas recentemente. Ele fornece uma "segunda chance" às páginas que são frequentemente acessadas, evitando que sejam substituídas de maneira prematura.

Em termos práticos, o algoritmo simula o funcionamento de um ponteiro de relógio que avança circularmente pela memória. Se ele encontrar uma página que foi referenciada recentemente, a página não é substituída imediatamente, e o ponteiro continua avançando. Isso torna o algoritmo mais eficiente do que o FIFO tradicional, especialmente em sistemas onde o padrão de acesso às páginas é previsível.


# Considerações Finais

Após a implementação e análise dos algoritmos FIFO, LRU, Relógio e NFU, podemos observar que cada um apresenta vantagens e desvantagens em diferentes cenários de uso. O LRU, como esperado, geralmente performa melhor em ambientes com padrões de uso previsíveis, já que ele sempre tenta manter as páginas mais recentemente usadas na memória. No entanto, sua implementação pode ser mais custosa devido à necessidade de rastrear a ordem de uso das páginas.

O Relógio se mostrou uma alternativa eficiente ao FIFO, oferecendo uma solução simples e com boa performance, ao fornecer uma "segunda chance" às páginas referenciadas recentemente. Ele representa um bom compromisso entre a simplicidade do FIFO e a complexidade do LRU.

O FIFO, apesar de ser o algoritmo mais simples, tem o pior desempenho nos testes, já que ele não leva em consideração o uso recente das páginas. Isso faz com que ele acabe substituindo páginas que podem ainda ser úteis, especialmente em sistemas com padrões de acesso irregulares.

Por fim, o NFU apresentou resultados medianos, sendo eficaz em cenários onde as páginas menos frequentemente usadas podem realmente ser substituídas com segurança. No entanto, como ele não considera o tempo desde o último uso, em alguns casos, ele pode acabar retendo páginas que foram usadas muito no passado, mas que não são mais relevantes.

Portanto, para a maioria dos cenários, o LRU deve ser a escolha mais apropriada, com o Relógio sendo uma boa alternativa em sistemas onde o custo de implementação do LRU não compensa os benefícios esperados.