package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import skylink.mglbiblioteca.dao.CategoriaLivroDAO;
import skylink.mglbiblioteca.dao.LivroDAO;
import skylink.mglbiblioteca.dao.PrateleiraDAO;
import skylink.mglbiblioteca.model.Livro;
import skylink.mglbiblioteca.model.CategoriaLivro;
import skylink.mglbiblioteca.model.Prateleira;

/**
 * @author Henriques
 */
@Named(value = "livroBean")
@ViewScoped
public class LivroBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LivroBean.class.getName());
    private static final long serialVersionUID = 1L;

    private Livro livro;
    private Livro livroSelecionado;
    private List<Livro> livros;
    private List<CategoriaLivro> categoriasLivro;
    private List<Prateleira> prateleiras;

    private final LivroDAO livroDAO;
    private final CategoriaLivroDAO categoriaLivroDAO;
    private final PrateleiraDAO prateleiraDAO;

    private String filtroTitulo;

    public LivroBean() {
        livroDAO = new LivroDAO();
        categoriaLivroDAO = new CategoriaLivroDAO();
        prateleiraDAO = new PrateleiraDAO();
        livro = new Livro();
        livros = new ArrayList<>();
        categoriasLivro = new ArrayList<>();
        prateleiras = new ArrayList<>();
    }

    @PostConstruct
    public void inicializar() {
        try {
            prateleiras = prateleiraDAO.findAll();
            categoriasLivro = categoriaLivroDAO.findAll();
            livros = livroDAO.findAll();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao inicializar dados no LivroBean", e);
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro de Sistema", "Não foi possível carregar os dados iniciais do banco de dados.");
        }
    }

    public void limpar() {
        this.livro = new Livro();
    }

    private void carregarCombos() throws SQLException {
        categoriasLivro = categoriaLivroDAO.findAll();
        prateleiras = prateleiraDAO.findAll();
    }

    public void pesquisar() {
        if (filtroTitulo != null && !filtroTitulo.trim().isEmpty()) {
            livros = livroDAO.buscarPorTitulo(filtroTitulo.trim());
            if (livros.isEmpty()) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Informação", "Nenhum livro encontrado com esse título.");
            }
        } else {
            addMensagem(FacesMessage.SEVERITY_WARN, "Atenção", "Informe um título para pesquisar.");
            livros = new ArrayList<>();
        }
    }

    public void limparPesquisa() {
        this.filtroTitulo = null;
        this.livros = new ArrayList<>();
        this.livroSelecionado = null;
    }

    public String salvar() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        if (this.livro.getIdLivro() == null) {
            boolean sucesso = livroDAO.save(livro);
            if (sucesso) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro registado com sucesso!");
                return "cadastro_livros.xhtml?faces-redirect=true";
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao processar operação no banco de dados.");
                return null;
            }
        } else {
            boolean sucesso = livroDAO.update(livro);
            if (sucesso) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro actualizado com sucesso!");
                return "cadastro_livros.xhtml?faces-redirect=true";
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao processar operação no banco de dados.");
                return null;
            }
        }
    }

    public void excluir() {
        if (livroSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um livro na tabela.");
            return;
        }

        if (livroDAO.delete(livroSelecionado)) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro removido.");
            livros.remove(livroSelecionado);
            livroSelecionado = null;
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir o livro.");
        }
    }

    public String editar() {
        if (livroSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Seleccione um livro para editar.");
            return null;
        }
        this.livro = livroSelecionado;
        return "cadastro_livros.xhtml?faces-redirect=true";
    }

    public void carregarLivros() {
        livros = livroDAO.findAll();
    }

    public void carregarCategoriasLivro() throws SQLException {
        categoriasLivro = categoriaLivroDAO.findAll();
    }

    public void carregarPrateleiras() throws SQLException {
        prateleiras = prateleiraDAO.findAll();
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(Livro livroSelecionado) {
        this.livroSelecionado = livroSelecionado;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<CategoriaLivro> getCategoriasLivro() {
        return categoriasLivro;
    }

    public void setCategoriasLivro(List<CategoriaLivro> categoriasLivro) {
        this.categoriasLivro = categoriasLivro;
    }

    public List<Prateleira> getPrateleiras() {
        return prateleiras;
    }

    public void setPrateleiras(List<Prateleira> prateleiras) {
        this.prateleiras = prateleiras;
    }

    public String getFiltroTitulo() {
        return filtroTitulo;
    }

    public void setFiltroTitulo(String filtroTitulo) {
        this.filtroTitulo = filtroTitulo;
    }

    private void addMensagem(FacesMessage.Severity severity, String titulo, String message) {
        FacesMessage info = new FacesMessage(titulo, message);
        info.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null, info);
    }
}