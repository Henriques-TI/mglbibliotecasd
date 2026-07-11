package skylink.mglbiblioteca.mb;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import skylink.mglbiblioteca.dao.CategoriaLivroDAO;
import skylink.mglbiblioteca.model.CategoriaLivro;

/**
 * @author Henriques
 */
@Named(value = "categoriaLivroBean")
@ViewScoped
public class CategoriaLivroBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(CategoriaLivroBean.class.getName());
    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    private CategoriaLivro categoriaLivro;
    private CategoriaLivro categoriaSelecionada;
    private List<CategoriaLivro> categorias;

    private final CategoriaLivroDAO categoriaLivroDAO;

    @PostConstruct
    public void inicializar() {
        try {
            carregarCategorias();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao inicializar dados no CategoriaLivroBean", e);
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao carregar categorias do banco de dados.");
        }
    }

    public CategoriaLivroBean() {
        categoriaLivroDAO = new CategoriaLivroDAO();
        categoriaLivro = new CategoriaLivro();
        categorias = new ArrayList<>();
    }

    public void carregarCategorias() {
        try {
            categorias = categoriaLivroDAO.findAll();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar categorias", e);
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao listar categorias.");
        }
    }

    public void limpar() {
        this.categoriaLivro = new CategoriaLivro();
    }

    public String salvar() {
        try {
            if (this.categoriaLivro.getIdCategoriaLivro() == 0) {
                boolean sucesso = categoriaLivroDAO.save(categoriaLivro);
                if (sucesso) {
                    addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Categoria cadastrada com sucesso!");
                    limpar();
                    carregarCategorias();
                    return "cadastro_categoria.faces?faces-redirect=true";
                }
            } else {
                boolean sucesso = categoriaLivroDAO.update(categoriaLivro);
                if (sucesso) {
                    addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Categoria atualizada com sucesso!");
                    limpar();
                    carregarCategorias();
                    return "cadastro_categoria.faces?faces-redirect=true";
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar categoria", e);
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível salvar os dados.");
        }
        return null;
    }

    public void excluir() {
        if (categoriaSelecionada == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione uma categoria na tabela.");
            return;
        }

        try {
            if (categoriaLivroDAO.delete(categoriaSelecionada)) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Categoria removida.");
                categorias.remove(categoriaSelecionada);
                categoriaSelecionada = null;
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir a categoria.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao excluir categoria", e);
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao processar exclusão no banco de dados.");
        }
    }

    private void addMensagem(FacesMessage.Severity severity, String titulo, String message) {
        FacesMessage info = new FacesMessage(titulo, message);
        info.setSeverity(severity);
        facesContext.addMessage(null, info);
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
}