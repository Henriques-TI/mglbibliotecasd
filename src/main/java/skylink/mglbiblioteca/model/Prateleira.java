package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Henriques
 */
public class Prateleira implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idPrateleira;
    private String posicaoPrateleira;
    private String classificacao;

    public Prateleira() {
    }

    public Prateleira(Integer idPrateleira, String posicaoPrateleira, String classificacao) {
        this.idPrateleira = idPrateleira;
        this.posicaoPrateleira = posicaoPrateleira;
        this.classificacao = classificacao;
    }


    public Integer getIdPrateleira() {
        return idPrateleira;
    }

    public void setIdPrateleira(Integer idPrateleira) {
        this.idPrateleira = idPrateleira;
    }

    public String getPosicaoPrateleira() {
        return posicaoPrateleira;
    }

    public void setPosicaoPrateleira(String posicaoPrateleira) {
        this.posicaoPrateleira = posicaoPrateleira;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.idPrateleira);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Prateleira other = (Prateleira) obj;
        return Objects.equals(this.idPrateleira, other.idPrateleira);
    }

    @Override
    public String toString() {
        return "Prateleira{" + "idPrateleira=" + idPrateleira + ", posicaoPrateleira=" + posicaoPrateleira + ", classificacao=" + classificacao + '}';
    }
}