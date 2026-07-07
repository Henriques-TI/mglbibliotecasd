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
import skylink.mglbiblioteca.model.CategoriaLivro;

/**
 * @Henriques
 */
@Named(value = "categoriaLivroBean")
@ViewScoped
public class CategoriaLivroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CategoriaLivroDAO categoriaLivroDAO;

    private CategoriaLivro categoriaLivro;
    private CategoriaLivro categoriaSelecionada;
    private List<CategoriaLivro> categorias = new ArrayList<>();
    private String filtroDescricao;

    public CategoriaLivroBean() {
        this.categoriaLivro = new CategoriaLivro();
    }

    @PostConstruct
    public void inicializar() {
        try {
            carregarCategorias();
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao inicializar categorias: " + e.getMessage());
        }
    }

    public void carregarCategorias() {
        categorias = categoriaLivroDAO.findAll();
    }

    public void pesquisar() {
        if (filtroDescricao != null && !filtroDescricao.trim().isEmpty()) {
            categorias = categoriaLivroDAO.buscarPorDescricao(filtroDescricao.trim());
            if (categorias.isEmpty()) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Informação", "Nenhuma categoria encontrada com esta descrição.");
            }
        } else {
            addMensagem(FacesMessage.SEVERITY_WARN, "Atenção", "Informe uma descrição para pesquisar.");
            carregarCategorias();
        }
    }

    public void limparPesquisa() {
        this.filtroDescricao = null;
        this.categoriaLivro = new CategoriaLivro();
        carregarCategorias();
        this.categoriaSelecionada = null;
    }

    public void salvar() {
        if (categoriaLivro == null) return;

        boolean sucesso;
        if (categoriaLivro.getIdCategoriaLivro() == null) {
            sucesso = categoriaLivroDAO.save(categoriaLivro);
        } else {
            sucesso = categoriaLivroDAO.update(categoriaLivro);
        }

        if (sucesso) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Categoria guardada com sucesso!");
            this.categoriaLivro = new CategoriaLivro();
            carregarCategorias();
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao processar operação na base de dados.");
        }
    }

    public void excluir() {
        if (categoriaSelecionada == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione uma categoria na tabela.");
            return;
        }

        if (categoriaLivroDAO.delete(categoriaSelecionada)) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Categoria removida com sucesso.");
            categorias.remove(categoriaSelecionada);
            this.categoriaSelecionada = null;
            this.categoriaLivro = new CategoriaLivro();
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir a categoria. Verifique se existem livros vinculados a ela.");
        }
    }

    public String editar() {
        if (categoriaSelecionada == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione uma categoria para editar.");
            return null;
        }
        this.categoriaLivro = categoriaSelecionada;
        return "/categoria/cadastro_categoria?faces-redirect=true";
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }


    public CategoriaLivro getCategoriaLivro() {
        return categoriaLivro;
    }

    public void setCategoriaLivro(CategoriaLivro categoriaLivro) {
        this.categoriaLivro = categoriaLivro;
    }

    public CategoriaLivro getCategoriaSelecionada() {
        return categoriaSelecionada;
    }

    public void setCategoriaSelecionada(CategoriaLivro categoriaSelecionada) {
        this.categoriaSelecionada = categoriaSelecionada;
    }

    public List<CategoriaLivro> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaLivro> categorias) {
        this.categorias = categorias;
    }

    public String getFiltroDescricao() {
        return filtroDescricao;
    }

    public void setFiltroDescricao(String filtroDescricao) {
        this.filtroDescricao = filtroDescricao;
    }
}