package skylink.mglbiblioteca.model;

import java.io.Serializable;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idEquipamento);
        hash = 97 * hash + Objects.hashCode(this.refEquipamento);
        hash = 97 * hash + Objects.hashCode(this.status);
        hash = 97 * hash + Objects.hashCode(this.localizacao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Computador other = (Computador) obj;
        if (!Objects.equals(this.refEquipamento, other.refEquipamento)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.localizacao, other.localizacao)) {
            return false;
        }
        return Objects.equals(this.idEquipamento, other.idEquipamento);
    }

    @Override
    public String toString() {
        return String.format("%s[idEquipamento=%d]", getClass().getSimpleName(), getIdEquipamento());
    }
}