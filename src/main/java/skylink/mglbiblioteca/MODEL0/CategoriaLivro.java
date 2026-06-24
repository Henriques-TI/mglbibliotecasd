package skylink.mglbiblioteca.MODEL0;

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

    
    
    public CategoriaLivro(){
        
        
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
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.idCategoriaLivro);
        hash = 11 * hash + Objects.hashCode(this.descricaoCategoriaLivro);
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
        if (!Objects.equals(this.descricaoCategoriaLivro, other.descricaoCategoriaLivro)) {
            return false;
        }
        return Objects.equals(this.idCategoriaLivro, other.idCategoriaLivro);
    }

    @Override
    public String toString() {
        return "CategoriaLivro{" + "idCategoriaLivro=" + idCategoriaLivro + ", descricaoCategoriaLivro=" + descricaoCategoriaLivro + '}';
    }
    
    
    
}
