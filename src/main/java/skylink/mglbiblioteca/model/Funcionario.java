package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Henriques
 */
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idFuncionario;
    private String nome;
    private String cargo;
    private String email;
    private String telefone;

    public Funcionario() {
    }

    public Funcionario(Integer idFuncionario, String nome, String cargo, String email, String telefone) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.cargo = cargo;
        this.email = email;
        this.telefone = telefone;
    }


    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idFuncionario);
        hash = 41 * hash + Objects.hashCode(this.nome);
        hash = 41 * hash + Objects.hashCode(this.cargo);
        hash = 41 * hash + Objects.hashCode(this.email);
        hash = 41 * hash + Objects.hashCode(this.telefone);
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
        final Funcionario other = (Funcionario) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cargo, other.cargo)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        return Objects.equals(this.idFuncionario, other.idFuncionario);
    }

    @Override
    public String toString() {
        return "Funcionario{" + "idFuncionario=" + idFuncionario + ", nome=" + nome + ", cargo=" + cargo + ", email=" + email + ", telefone=" + telefone + '}';
    }
}
