package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import skylink.mglbiblioteca.DAO.UtilizadorDAO;
import skylink.mglbiblioteca.model.Utilizador;

@Named(value = "utilizadorBean")
@ViewScoped
public class UtilizadorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Utilizador utilizador;
    private List<Utilizador> listaUtilizadores;
    private final UtilizadorDAO utilizadorDAO = new UtilizadorDAO();

    @PostConstruct
    public void init() {
        limparFormulario();
        carregarUtilizadores();
    }

    public void carregarUtilizadores() {
        this.listaUtilizadores = utilizadorDAO.listarTodos();
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        boolean sucesso = utilizadorDAO.inserir(this.utilizador);
        
        if (sucesso) {
            context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Sucesso", "Utilizador cadastrado com sucesso!"));
            limparFormulario();
            carregarUtilizadores();
        } else {
            context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao salvar. Verifique se o BI ou E-mail já existem."));
        }
    }

    public void limparFormulario() {
        this.utilizador = new Utilizador();
    }

    public Utilizador getUtilizador() { return utilizador; }
    public void setUtilizador(Utilizador utilizador) { this.utilizador = utilizador; }
    public List<Utilizador> getListaUtilizadores() { return listaUtilizadores; }
}