import java.util.Arrays;

public class EntradaTabelaPaginas {
    private int numeroQuadroPagina;   // Número do quadro de página
    private boolean presente;         // Bit de presente/ausente
    private boolean referenciada;     // Bit referenciada
    private boolean modificada;       // Bit modificada
    private boolean protecao;         // Bit de proteção (permissão de acesso)
    private boolean cacheDesabilitado; // Cache desabilitado

    public EntradaTabelaPaginas() {
        this.numeroQuadroPagina = -1;       // Inicia com -1 indicando que o quadro está vazio
        this.presente = false;              // Página inicialmente ausente
        this.referenciada = false;          // Página inicialmente não referenciada
        this.modificada = false;            // Página inicialmente não modificada
        this.protecao = true;               // Proteção habilitada por padrão
        this.cacheDesabilitado = false;     // Cache habilitado por padrão
    }

    // Métodos Getters e Setters para manipular os campos
    public int getNumeroQuadroPagina() {
        return numeroQuadroPagina;
    }

    public void setNumeroQuadroPagina(int numeroQuadroPagina) {
        this.numeroQuadroPagina = numeroQuadroPagina;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }

    public boolean isReferenciada() {
        return referenciada;
    }

    public void setReferenciada(boolean referenciada) {
        this.referenciada = referenciada;
    }

    public boolean isModificada() {
        return modificada;
    }

    public void setModificada(boolean modificada) {
        this.modificada = modificada;
    }

    public boolean isProtecao() {
        return protecao;
    }

    public void setProtecao(boolean protecao) {
        this.protecao = protecao;
    }

    public boolean isCacheDesabilitado() {
        return cacheDesabilitado;
    }

    public void setCacheDesabilitado(boolean cacheDesabilitado) {
        this.cacheDesabilitado = cacheDesabilitado;
    }

    @Override
    public String toString() {
        return "EntradaTabelaPaginas [numeroQuadroPagina=" + numeroQuadroPagina + ", presente=" + presente + 
               ", referenciada=" + referenciada + ", modificada=" + modificada + 
               ", protecao=" + protecao + ", cacheDesabilitado=" + cacheDesabilitado + "]";
    }
}
