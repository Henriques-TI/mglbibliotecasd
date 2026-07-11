package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Henriques
 */
public class Computador implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idEquipamento;
    private String refEquipamento;
    private String status;
    private String localizacao;
    private Date dataRegisto;

    public Computador() {
    }

    public Computador(Integer idEquipamento, String refEquipamento, String status, String localizacao) {
        this.idEquipamento = idEquipamento;
        this.refEquipamento = refEquipamento;
        this.status = status;
        this.localizacao = localizacao;
    }

    public Integer getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Integer idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public String getRefEquipamento() {
        return refEquipamento;
    }

    public void setRefEquipamento(String refEquipamento) {
        this.refEquipamento = refEquipamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idEquipamento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Computador other = (Computador) obj;
        return Objects.equals(this.idEquipamento, other.idEquipamento);
    }
}