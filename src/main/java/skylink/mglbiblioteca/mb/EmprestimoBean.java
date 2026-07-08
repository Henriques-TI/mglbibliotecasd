package skylink.mglbiblioteca.DAO;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mglbibliotecasd.dao.EmprestimoDAO;
import mglbibliotecasd.modelo.Emprestimo;
import skylink.mglbiblioteca.model.Livro;

/**
 * @Henriques
 */
@Named(value = "emprestimoBean")
@ViewScoped
public class EmprestimoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EmprestimoDAO emprestimoDAO; 
    
    @Inject
    private LivroDAO livroDAO;

    private Emprestimo emprestimo;
    private List<Emprestimo> listaEmprestimos = new ArrayList<>();
    private List<Livro> livrosDisponiveis = new ArrayList<>();
    private Integer filtroIdUtilizador;

    public EmprestimoBean() {
        this.emprestimo = new Emprestimo();
    }

    @PostConstruct
    public void inicializar() {
        try {
            carregarDados();
        } catch (SQLException e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro de Conexão", "Não foi possível carregar os dados iniciais.");
            e.printStackTrace();
        }
    }

    public void carregarDados() throws SQLException {
        this.listaEmprestimos = this.emprestimoDAO.findAll(); 
        this.livrosDisponiveis = this.livroDAO.findAll();
    }

    public void salvar() {
        if (emprestimo.getLivro().getIdLivro() == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um livro.");
            return;
        }

        try {
            if (emprestimoDAO.registrarEmprestimo(emprestimo)) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Empréstimo registado com sucesso!");
                this.emprestimo = new Emprestimo(); 
                carregarDados(); 
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível registar o empréstimo (Verifique o Stock do livro).");
            }
        } catch (SQLException e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro Crítico", "Falha na base de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void pesquisar() {
        try {
            if (filtroIdUtilizador != null) {
                listaEmprestimos = emprestimoDAO.buscarPorUtilizador(filtroIdUtilizador);
            } else {
                carregarDados();
            }
        } catch (SQLException e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro na Pesquisa", "Não foi possível filtrar os registos.");
            e.printStackTrace();
        }
    }

    public void limparPesquisa() {
        this.filtroIdUtilizador = null;
        try {
            carregarDados();
        } catch (SQLException e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar", "Falha ao recarregar após limpar.");
            e.printStackTrace();
        }
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    public Emprestimo getEmprestimo() { return emprestimo; }
    public void setEmprestimo(Emprestimo emprestimo) { this.emprestimo = emprestimo; }
    public List<Emprestimo> getListaEmprestimos() { return listaEmprestimos; }
    public List<Livro> getLivrosDisponiveis() { return livrosDisponiveis; }
    public Integer getFiltroIdUtilizador() { return filtroIdUtilizador; }
    public void setFiltroIdUtilizador(Integer filtroIdUtilizador) { this.filtroIdUtilizador = filtroIdUtilizador; }
}