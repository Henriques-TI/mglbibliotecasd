package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**

 * @Henriques
 */
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idReserva;
    private Integer idUtilizador;
    private Integer idLivro;
    private Date dataReserva;
    private String status; 
    private Date createdAt;
    private Date updatedAt;

    public Reserva() {
        this.dataReserva = new Date();
        this.status = "Ativa";
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        hash = 67 * hash + Objects.hashCode(this.idReserva);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Reserva other = (Reserva) obj;
        return Objects.equals(this.idReserva, other.idReserva);
    }

    @Override
    public String toString() {
        return "Reserva{" + "idReserva=" + idReserva + ", idUtilizador=" + idUtilizador + ", idLivro=" + idLivro + ", status=" + status + '}';
    }
}