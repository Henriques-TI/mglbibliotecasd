package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import skylink.mglbiblioteca.dao.PerfilFuncionarioDAO;
import skylink.mglbiblioteca.model.Funcionario;
import skylink.mglbiblioteca.model.PerfilFuncionario;

/**
 * @author Henriques
 */
@Named(value = "perfilFuncionarioBean")
@ViewScoped
public class PerfilFuncionarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final PerfilFuncionarioDAO perfilFuncionarioDAO = new PerfilFuncionarioDAO();

    private Funcionario funcionario;
    private Funcionario funcionarioSelecionado;
    private List<Funcionario> funcionarios = new ArrayList<>();
    private List<PerfilFuncionario> perfisDisponiveis;

    public PerfilFuncionarioBean() {
        this.funcionario = new Funcionario();
    }

    @PostConstruct
    public void inicializar() {
        carregarFuncionarios();
        this.perfisDisponiveis = Arrays.asList(PerfilFuncionario.values());
    }

    public void carregarFuncionarios() {
        try {
            this.funcionarios = perfilFuncionarioDAO.findAll();
            if (this.funcionarios == null) {
                this.funcionarios = new ArrayList<>();
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível listar as permissões: " + e.getMessage());
            this.funcionarios = new ArrayList<>();
        }
    }

    public void salvar() {
        if (funcionario == null) return;

        try {
            boolean sucesso;
            if (funcionario.getIdFuncionario() == null) {
                sucesso = perfilFuncionarioDAO.save(funcionario);
            } else {
                sucesso = perfilFuncionarioDAO.update(funcionario);
            }

            if (sucesso) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Credenciais e perfis atualizados!");
                limpar();
                carregarFuncionarios();
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível aplicar as alterações.");
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro Fatal", "Falha na persistência: " + e.getMessage());
        }
    }

    public void excluir() {
        if (funcionarioSelecionado == null || funcionarioSelecionado.getIdFuncionario() == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um funcionário para remover o perfil.");
            return;
        }

        try {
            if (perfilFuncionarioDAO.delete(funcionarioSelecionado.getIdFuncionario())) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Registo de perfil removido com sucesso.");
                this.funcionarios.remove(funcionarioSelecionado);
                this.funcionarioSelecionado = null;
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível remover o utilizador.");
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro Fatal", "Falha ao eliminar: " + e.getMessage());
        }
    }

    public String prepararEdicao() {
        if (funcionarioSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um funcionário da tabela para alterar as permissões.");
            return null;
        }
        this.funcionario = this.funcionarioSelecionado;
        return "/funcionario/gestao_perfis?faces-redirect=true";
    }

    public void limpar() {
        this.funcionario = new Funcionario();
        this.funcionarioSelecionado = null;
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }
    public Funcionario getFuncionarioSelecionado() { return funcionarioSelecionado; }
    public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) { this.funcionarioSelecionado = funcionarioSelecionado; }
    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public void setFuncionarios(List<Funcionario> funcionarios) { this.funcionarios = funcionarios; }
    public List<PerfilFuncionario> getPerfisDisponiveis() { return perfisDisponiveis; }
    public void setPerfisDisponiveis(List<PerfilFuncionario> perfisDisponiveis) { this.perfisDisponiveis = perfisDisponiveis; }
}