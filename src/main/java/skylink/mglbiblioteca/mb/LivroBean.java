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
import mglbiblioteca.dao.PrateleiraDAO;
import skylink.mglbiblioteca.DAO.CategoriaLivroDAO;
import skylink.mglbiblioteca.DAO.LivroDAO;
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

    public LivroBean() {
        this.livro = new Livro();
    }

    @PostConstruct
    public void inicializar() {
        try {
            carregarLivros();
            carregarCombos();
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao carregar dados da página: " + e.getMessage());
        }
    }

    public void carregarLivros() {
        this.livros = livroDAO.findAll();
    }

    private void carregarCombos() {
        this.categorias = categoriaLivroDAO.findAll();
        this.prateleiras = prateleiraDAO.findAll();
    }

    public void salvar() {
        boolean sucesso;
        
        if (livro.getCategoriaLivro() != null && livro.getCategoriaLivro().getIdCategoriaLivro() == null) {
            livro.setCategoriaLivro(null);
        }
        if (livro.getPrateleira() != null && livro.getPrateleira().getIdPrateleira() == null) {
            livro.setPrateleira(null);
        }

        if (livro.getIdLivro() == null) {
            sucesso = livroDAO.save(livro);
            if (sucesso) addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro registrado com sucesso!");
        } else {
            sucesso = livroDAO.update(livro);
            if (sucesso) addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro atualizado com sucesso!");
        }

        if (sucesso) {
            limpar();
            carregarLivros();
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível processar a operação no acervo.");
        }
    }

    public void excluir() {
        if (livroSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Por favor, selecione um livro para remover.");
            return;
        }

        if (livroDAO.delete(livroSelecionado.getIdLivro())) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro removido com sucesso!");
            carregarLivros();
            this.livroSelecionado = null;
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir o livro. Certifique-se de que não há empréstimos ou leituras ativas associadas.");
        }
    }

    public String prepararEdicao() {
        if (livroSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um livro para prosseguir com a edição.");
            return null;
        }
        this.livro = this.livroSelecionado;

        if (this.livro.getCategoriaLivro() == null) this.livro.setCategoriaLivro(new CategoriaLivro());
        if (this.livro.getPrateleira() == null) this.livro.setPrateleira(new Prateleira());
        
        return "/livro/cadastro_livro?faces-redirect=true";
    }

    public void limpar() {
        this.livro = new Livro();
        this.livro.setCategoriaLivro(new CategoriaLivro());
        this.livro.setPrateleira(new Prateleira());
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    // Getters e Setters
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
}