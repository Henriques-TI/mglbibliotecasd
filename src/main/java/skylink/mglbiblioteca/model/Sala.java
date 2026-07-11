package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author Henriques
 */
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idSala;
    private String nomeSala;
    private Integer capacidade;
    private BigDecimal precoHora;
    private String status;
    private Date dataRegisto;

    public Sala() {
        this.status = "Disponível";
    }

    public Sala(Integer idSala, String nomeSala, Integer capacity, BigDecimal precoHora, String status) {
        this.idSala = idSala;
        this.nomeSala = nomeSala;
        this.capacidade = capacity;
        this.precoHora = precoHora;
        this.status = status;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public BigDecimal getPrecoHora() {
        return precoHora;
    }

    public void setPrecoHora(BigDecimal precoHora) {
        this.precoHora = precoHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        hash = 41 * hash + Objects.hashCode(this.idSala);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Sala other = (Sala) obj;
        return Objects.equals(this.idSala, other.idSala);
    }
}