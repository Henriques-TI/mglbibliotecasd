package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.dao.FuncionarioDAO;
import skylink.mglbiblioteca.model.Funcionario;

/**
 * @author Henriques
 */
@Named(value = "funcionarioBean")
@ViewScoped
public class FuncionarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FuncionarioDAO funcionarioDAO;

    private Funcionario funcionario;
    private Funcionario funcionarioSelecionado;
    private List<Funcionario> funcionarios = new ArrayList<>();
    private String filtroNome;

    public FuncionarioBean() {
        this.funcionario = new Funcionario();
    }

    @PostConstruct
    public void inicializar() {
        if (this.funcionario == null) {
            this.funcionario = new Funcionario();
        }
        carregarFuncionarios();
    }

    public void carregarFuncionarios() {
        try {
            this.funcionarios = funcionarioDAO.findAll();
            if (this.funcionarios == null) {
                this.funcionarios = new ArrayList<>();
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro de Banco", "Não foi possível carregar os funcionários: " + e.getMessage());
            this.funcionarios = new ArrayList<>();
        }
    }

    public void pesquisar() {
        if (filtroNome != null && !filtroNome.trim().isEmpty()) {
            try {
                this.funcionarios = funcionarioDAO.buscarPorNome(filtroNome.trim());
                if (this.funcionarios == null || this.funcionarios.isEmpty()) {
                    this.funcionarios = new ArrayList<>();
                    addMensagem(FacesMessage.SEVERITY_INFO, "Informação", "Nenhum funcionário encontrado.");
                }
            } catch (Exception e) {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao pesquisar: " + e.getMessage());
            }
        } else {
            addMensagem(FacesMessage.SEVERITY_WARN, "Atenção", "Informe um nome para pesquisar.");
            carregarFuncionarios();
        }
    }

    public void limparPesquisa() {
        this.filtroNome = null;
        carregarFuncionarios();
        this.funcionarioSelecionado = null;
    }

    public void salvar() {
        if (funcionario == null) return;

        try {
            boolean sucesso;
            if (funcionario.getIdFuncionario() == null) {
                sucesso = funcionarioDAO.save(funcionario);
            } else {
                sucesso = funcionarioDAO.update(funcionario);
            }

            if (sucesso) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Funcionário guardado com sucesso!");
                this.funcionario = new Funcionario();
                carregarFuncionarios(); 
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível guardar os dados no sistema.");
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro fatal", "Ocorreu uma falha ao salvar: " + e.getMessage());
        }
    }

    public void excluir() {
        if (funcionarioSelecionado == null || funcionarioSelecionado.getIdFuncionario() == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um funcionário na tabela.");
            return;
        }

        try {
            if (funcionarioDAO.delete(funcionarioSelecionado.getIdFuncionario())) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Funcionário removido do registo.");
                funcionarios.remove(funcionarioSelecionado);
                this.funcionarioSelecionado = null;
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao tentar excluir. Verifique as dependências.");
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro fatal", "Erro ao eliminar: " + e.getMessage());
        }
    }

    public String editar() {
        if (funcionarioSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um funcionário para editar.");
            return null;
        }
        this.funcionario = funcionarioSelecionado;
        return "/funcionario/cadastro_funcionario?faces-redirect=true";
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
    public String getFiltroNome() { return filtroNome; }
    public void setFiltroNome(String filtroNome) { this.filtroNome = filtroNome; }
}