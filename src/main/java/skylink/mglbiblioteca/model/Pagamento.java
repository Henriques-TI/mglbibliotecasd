package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @Henriques
 */
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idPagamento;
    private Integer idServico;
    private Integer idUtilizador;
    private BigDecimal valorPago;
    private MetodoPagamento metodoPagamento;
    private Date dataPagamento;
    private Date dataRegisto;

    public Pagamento() {
        this.dataPagamento = new Date();
    }

    public Pagamento(Integer idPagamento, Integer idServico, Integer idUtilizador, BigDecimal valorPago, MetodoPagamento metodoPagamento, Date dataPagamento) {
        this.idPagamento = idPagamento;
        this.idServico = idServico;
        this.idUtilizador = idUtilizador;
        this.valorPago = valorPago;
        this.metodoPagamento = metodoPagamento;
        this.dataPagamento = dataPagamento;
    }


    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public Integer getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.idPagamento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Pagamento other = (Pagamento) obj;
        return Objects.equals(this.idPagamento, other.idPagamento);
    }

    @Override
    public String toString() {
        return "Pagamento{" + "idPagamento=" + idPagamento + ", idServico=" + idServico + ", valorPago=" + valorPago + ", dataPagamento=" + dataPagamento + '}';
    }
}