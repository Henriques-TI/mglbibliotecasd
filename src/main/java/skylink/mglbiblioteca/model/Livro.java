package skylink.mglbiblioteca.model;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Henriques
 */
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idLivro;
    private String titulo;
    private String autor;
    private String isbn;
    private String editora;
    private Integer anoPublicacao;
    private CategoriaLivro categoriaLivro; 
    private Prateleira prateleira;   
    private Integer quantidadeTotal;
    private Integer quantidadeDisponivel;
    private Date dataRegisto;

    public Livro() {
        this.categoriaLivro = new CategoriaLivro();
        this.prateleira = new Prateleira();
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
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
        hash = 71 * hash + Objects.hashCode(this.idLivro);
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
        final Livro other = (Livro) obj;
        return Objects.equals(this.idLivro, other.idLivro);
    }

    @Override
    public String toString() {
        return "Livro{" + "titulo=" + titulo + '}';
    }
}