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
import skylink.mglbiblioteca.DAO.FuncionarioDAO;
import skylink.mglbiblioteca.model.Funcionario;

/**
 * @Henriques
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
        carregarFuncionarios();
    }

    public void carregarFuncionarios() {
        funcionarios = funcionarioDAO.findAll();
    }

    public void pesquisar() {
        if (filtroNome != null && !filtroNome.trim().isEmpty()) {
            funcionarios = funcionarioDAO.buscarPorNome(filtroNome.trim());
            if (funcionarios.isEmpty()) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Informação", "Nenhum funcionário encontrado.");
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

        boolean sucesso;
        if (funcionario.getIdFuncionario() == null) {
            sucesso = funcionarioDAO.save(funcionario);
        } else {
            sucesso = funcionarioDAO.update(funcionario);
        }

        if (sucesso) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Funcionário guardado com sucesso!");
            this.funcionario = new Funcionario(); // Reseta formulário
            carregarFuncionarios(); // Atualiza a tabela
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível guardar os dados.");
        }
    }

    public void excluir() {
        if (funcionarioSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um funcionário na tabela.");
            return;
        }

        if (funcionarioDAO.delete(funcionarioSelecionado)) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Funcionário removido do registo.");
            funcionarios.remove(funcionarioSelecionado);
            this.funcionarioSelecionado = null;
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao tentar excluir.");
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

    // --- GETTERS E SETTERS ---

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Funcionario getFuncionarioSelecionado() {
        return funcionarioSelecionado;
    }

    public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
        this.funcionarioSelecionado = funcionarioSelecionado;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public String getFiltroNome() {
        return filtroNome;
    }

    public void setFiltroNome(String filtroNome) {
        this.filtroNome = filtroNome;
    }
}