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
    private TipoOrigem tipoOrigem;         
    private Integer idReferencia;          
    private Integer idUtilizador;          
    private BigDecimal valorPago;
    private MetodoPagamento metodoPagamento; 
    private Date dataPagamento;

    public Pagamento() {
        this.dataPagamento = new Date(); 
    }

    public Pagamento(Integer idPagamento, TipoOrigem tipoOrigem, Integer idReferencia, Integer idUtilizador, BigDecimal valorPago, MetodoPagamento metodoPagamento, Date dataPagamento) {
        this.idPagamento = idPagamento;
        this.tipoOrigem = tipoOrigem;
        this.idReferencia = idReferencia;
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

    public TipoOrigem getTipoOrigem() {
        return tipoOrigem;
    }

    public void setTipoOrigem(TipoOrigem tipoOrigem) {
        this.tipoOrigem = tipoOrigem;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
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
        return "Pagamento{" + "idPagamento=" + idPagamento + ", valorPago=" + valorPago + ", dataPagamento=" + dataPagamento + '}';
    }
}