package skylink.mglbiblioteca.model;

import java.sql.Timestamp;

public class CategoriaLivro {
    private int idCategoriaLivro;
    private String descricao;
    private Timestamp dataRegisto;

    public CategoriaLivro() {
    }

    public CategoriaLivro(int idCategoriaLivro, String descricao, Timestamp dataRegisto) {
        this.idCategoriaLivro = idCategoriaLivro;
        this.descricao = descricao;
        this.dataRegisto = dataRegisto;
    }

    public int getIdCategoriaLivro() {
        return idCategoriaLivro;
    }

    public void setIdCategoriaLivro(int idCategoriaLivro) {
        this.idCategoriaLivro = idCategoriaLivro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Timestamp dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
}