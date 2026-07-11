package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.util.Date;
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
    private String usuario;
    private String senhaHash;
    private PerfilFuncionario perfil;
    private Boolean ativo;
    private Date dataRegsito;

    public Funcionario() {
        this.perfil = PerfilFuncionario.Atendente;
        this.ativo = Boolean.TRUE;
    }

    public Funcionario(Integer idFuncionario, String nome, String cargo, String email, String telefone) {
        this();
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public PerfilFuncionario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilFuncionario perfil) {
        this.perfil = perfil;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataRegsito() {
        return dataRegsito;
    }

    public void setDataRegsito(Date dataRegsito) {
        this.dataRegsito = dataRegsito;
    }

  

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idFuncionario);
        hash = 41 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Funcionario other = (Funcionario) obj;
        return Objects.equals(this.idFuncionario, other.idFuncionario);
    }

    @Override
    public String toString() {
        return "Funcionario{" + "idFuncionario=" + idFuncionario + ", nome=" + nome + ", cargo=" + cargo + ", email=" + email + ", perfil=" + perfil + '}';
    }
}