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
import mglbiblioteca.dao.CategoriaLivroDAO;
import mglbiblioteca.dao.PrateleiraDAO;
import mglbibliotecasd.dao.LivroDAO;
import skylink.mglbiblioteca.model.Livro;
import skylink.mglbiblioteca.model.CategoriaLivro;
import skylink.mglbiblioteca.model.Prateleira;

/**
 * @Henriques
 */
@Named(value = "livroBean")
@ViewScoped
public class LivroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LivroDAO livroDAO;

    @Inject
    private CategoriaLivroDAO categoriaLivroDAO;

    @Inject
    private PrateleiraDAO prateleiraDAO;

    private Livro livro;
    private Livro livroSelecionado;
    private List<Livro> livros = new ArrayList<>();
    private List<CategoriaLivro> categorias = new ArrayList<>();
    private List<Prateleira> prateleiras = new ArrayList<>();
    
    private String filtroTitulo;
    private String filtroAutor;

    public LivroBean() {
        this.livro = new Livro();
    }

    @PostConstruct
    public void inicializar() {
        try {
            carregarLivros();
            carregarAuxiliares();
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao carregar os dados iniciais: " + e.getMessage());
        }
    }

    public void carregarLivros() {
        livros = livroDAO.findAll();
    }

    public void carregarAuxiliares() {
        categorias = categoriaLivroDAO.findAll();
        prateleiras = prateleiraDAO.findAll();
    }

    public void pesquisar() {
        boolean temTitulo = (filtroTitulo != null && !filtroTitulo.trim().isEmpty());
        boolean temAutor = (filtroAutor != null && !filtroAutor.trim().isEmpty());

        if (temTitulo || temAutor) {
            String t = temTitulo ? filtroTitulo.trim() : "";
            String a = temAutor ? filtroAutor.trim() : "";
         
            livros = livroDAO.buscarPorTituloEAutor(t, a);
            
            if (livros.isEmpty()) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Informação", "Nenhum livro encontrado com os filtros informados.");
            }
        } else {
            addMensagem(FacesMessage.SEVERITY_WARN, "Atenção", "Informe pelo menos um campo (Título ou Autor) para pesquisar.");
            carregarLivros();
        }
    }

    public void limparPesquisa() {
        this.filtroTitulo = null;
        this.filtroAutor = null;
        this.livro = new Livro(); 
        carregarLivros();
        this.livroSelecionado = null;
    }

    public void salvar() {
        if (livro == null) return;

        boolean sucesso;
        if (livro.getIdLivro() == null) {
            sucesso = livroDAO.save(livro);
        } else {
            sucesso = livroDAO.update(livro);
        }

        if (sucesso) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro guardado com sucesso!");
            this.livro = new Livro(); 
            carregarLivros(); 
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao processar operação na base de dados.");
        }
    }

    public void excluir() {
        if (livroSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um livro na tabela.");
            return;
        }

        if (livroDAO.delete(livroSelecionado)) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro removido com sucesso.");
            livros.remove(livroSelecionado);
            this.livroSelecionado = null;
            this.livro = new Livro(); 
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível eliminar o livro na base de dados.");
        }
    }

    public String editar() {
        if (livroSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um livro para editar.");
            return null;
        }
        this.livro = livroSelecionado;
        return "/livro/cadastro_livro?faces-redirect=true";
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
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

    public List<CategoriaLivro> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaLivro> categorias) {
        this.categorias = categorias;
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

    public String getFiltroAutor() {
        return filtroAutor;
    }

    public void setFiltroAutor(String filtroAutor) {
        this.filtroAutor = filtroAutor;
    }
}