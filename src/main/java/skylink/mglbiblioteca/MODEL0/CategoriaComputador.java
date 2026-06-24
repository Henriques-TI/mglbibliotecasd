package skylink.mglbiblioteca.MODEL0;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @Henriques
 */
public class CategoriaComputador implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private Integer idCategoria;
    private String descricaoCategoria;
    
    
    public CategoriaComputador(){
    
    }

    public CategoriaComputador(Integer idCategoria, String descricaoCategoria) {
        this.idCategoria = idCategoria;
        this.descricaoCategoria = descricaoCategoria;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public void setDescricaoCategoria(String descricaoCategoria) {
        this.descricaoCategoria = descricaoCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.idCategoria);
        hash = 73 * hash + Objects.hashCode(this.descricaoCategoria);
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
        final CategoriaComputador other = (CategoriaComputador) obj;
        if (!Objects.equals(this.descricaoCategoria, other.descricaoCategoria)) {
            return false;
        }
        return Objects.equals(this.idCategoria, other.idCategoria);
    }

  
    
}
