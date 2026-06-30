package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @Henriques
 */
public class CategoriaLivro implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idCategoriaLivro;
    private String descricaoCategoriaLivro;

    public CategoriaLivro() {
    }

    public CategoriaLivro(Integer idCategoriaLivro, String descricaoCategoriaLivro) {
        this.idCategoriaLivro = idCategoriaLivro;
        this.descricaoCategoriaLivro = descricaoCategoriaLivro;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getIdCategoriaLivro() {
        return idCategoriaLivro;
    }

    public void setIdCategoriaLivro(Integer idCategoriaLivro) {
        this.idCategoriaLivro = idCategoriaLivro;
    }

    public String getDescricaoCategoriaLivro() {
        return descricaoCategoriaLivro;
    }

    public void setDescricaoCategoriaLivro(String descricaoCategoriaLivro) {
        this.descricaoCategoriaLivro = descricaoCategoriaLivro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idCategoriaLivro);
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
        final CategoriaLivro other = (CategoriaLivro) obj;
        return Objects.equals(this.idCategoriaLivro, other.idCategoriaLivro);
    }

    @Override
    public String toString() {
        return "CategoriaLivro{" + "idCategoriaLivro=" + idCategoriaLivro + ", descricaoCategoriaLivro=" + descricaoCategoriaLivro + '}';
    }
}