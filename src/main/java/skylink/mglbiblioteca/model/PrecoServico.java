package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @Henriques
 */
public class PrecoServico implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoServico;
    private BigDecimal valorUnitario;
    private String unidade; 
    private Date updatedAt;

    public PrecoServico() {
    }

    public PrecoServico(String tipoServico, BigDecimal valorUnitario, String unidade) {
        this.tipoServico = tipoServico;
        this.valorUnitario = valorUnitario;
        this.unidade = unidade;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.tipoServico);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final PrecoServico other = (PrecoServico) obj;
        return Objects.equals(this.tipoServico, other.tipoServico);
    }

    @Override
    public String toString() {
        return "PrecoServico{" + "tipoServico=" + tipoServico + ", valorUnitario=" + valorUnitario + ", unidade=" + unidade + '}';
    }
}