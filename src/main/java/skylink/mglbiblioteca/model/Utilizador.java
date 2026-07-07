package mglbibliotecasd.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Utilizador implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Integer idUtilizador;
    private String nome;
    private String bi;
    private String email;
    private String telefone;
    private Date dataCadastro;

    public Utilizador() {
    }

    public Utilizador(Integer idUtilizador, String nome, String bi, String email, String telefone, Date dataCadastro) {
        this.idUtilizador = idUtilizador;
        this.nome = nome;
        this.bi = bi;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
    }

    // Getters e Setters
    public Integer getIdUtilizador() { return idUtilizador; }
    public void setIdUtilizador(Integer idUtilizador) { this.idUtilizador = idUtilizador; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getBi() { return bi; }
    public void setBi(String bi) { this.bi = bi; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilizador other = (Utilizador) obj;
        return Objects.equals(idUtilizador, other.idUtilizador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilizador);
    }

    @Override
    public String toString() {
        return "Utilizador{" + "idUtilizador=" + idUtilizador + ", nome='" + nome + "'}";
    }
}