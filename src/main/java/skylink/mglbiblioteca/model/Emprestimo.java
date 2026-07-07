package mglbibliotecasd.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import skylink.mglbiblioteca.model.Livro;

/**
 * @Henriques
 */
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idEmprestimo;
    private Livro livro;                 
    private Integer idUtilizador;    
    private Date dataRetirada;
    private Date dataDevolucaoPrevista;
    private String status;               

    
    public Emprestimo() {
        this.livro = new Livro();
        this.dataRetirada = new Date();  
        this.status = "Ativo";           
    }


    public Integer getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Integer idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Integer getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Integer idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.idEmprestimo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Emprestimo other = (Emprestimo) obj;
        return Objects.equals(this.idEmprestimo, other.idEmprestimo);
    }

    @Override
    public String toString() {
        return "Emprestimo{" + "idEmprestimo=" + idEmprestimo + ", idUtilizador=" + idUtilizador + ", status=" + status + '}';
    }
}