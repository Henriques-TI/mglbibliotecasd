package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Henriques
 */
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idLivro;
    private String titulo;
    private String autor;
    private String editora;
    private Integer anoPublicacao;
    private CategoriaLivro categoriaLivro; 
    private Prateleira prateleira;       
    private Integer quantidadeDisponivel;

    public Livro() {
        this.categoriaLivro = new CategoriaLivro();
        this.prateleira = new Prateleira();
    }

    public Livro(Integer idLivro, String titulo, String autor, String editora, Integer anoPublicacao, CategoriaLivro categoriaLivro, Prateleira prateleira, Integer quantidadeDisponivel) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.categoriaLivro = categoriaLivro;
        this.prateleira = prateleira;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public CategoriaLivro getCategoriaLivro() {
        return categoriaLivro;
    }

    public void setCategoriaLivro(CategoriaLivro categoriaLivro) {
        this.categoriaLivro = categoriaLivro;
    }

    public Prateleira getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(Prateleira prateleira) {
        this.prateleira = prateleira;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.idLivro);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Livro other = (Livro) obj;
        return Objects.equals(this.idLivro, other.idLivro);
    }
}