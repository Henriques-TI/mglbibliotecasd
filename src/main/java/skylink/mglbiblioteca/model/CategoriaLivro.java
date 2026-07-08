package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Henriques
 */
public class CategoriaLivro implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idCategoriaLivro;
    private String descricao;

    public CategoriaLivro() {
    }

    public CategoriaLivro(Integer idCategoriaLivro, String descricao) {
        this.idCategoriaLivro = idCategoriaLivro;
        this.descricao = descricao;
    }

    public Integer getIdCategoriaLivro() {
        return idCategoriaLivro;
    }

    public void setIdCategoriaLivro(Integer idCategoriaLivro) {
        this.idCategoriaLivro = idCategoriaLivro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
}