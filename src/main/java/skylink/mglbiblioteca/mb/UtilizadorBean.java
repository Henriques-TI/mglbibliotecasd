package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import skylink.mglbiblioteca.dao.UtilizadorDAO;
import skylink.mglbiblioteca.model.Utilizador;

/**
 * @author Henriques
 */
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
        this.listaUtilizadores = utilizadorDAO.findAll();
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean sucesso;

        if (this.utilizador.getIdUtilizador() != null) {
            sucesso = utilizadorDAO.update(this.utilizador);
        } else {
            sucesso = utilizadorDAO.save(this.utilizador);
        }

        if (sucesso) {
            String mensagem = this.utilizador.getIdUtilizador() != null ? "atualizado" : "cadastrado";
            context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Sucesso", "Utilizador " + mensagem + " com sucesso!"));
            limparFormulario();
            carregarUtilizadores();
        } else {
            context.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao salvar. Verifique os dados fornecidos."));
        }
    }

    public void prepararEdicao(Utilizador u) {
        this.utilizador = u;
    }

    public void eliminar(Utilizador u) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (u != null && u.getIdUtilizador() != null) {
            boolean sucesso = utilizadorDAO.delete(u.getIdUtilizador());
            if (sucesso) {
                context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Sucesso", "Utilizador eliminado com sucesso!"));
                carregarUtilizadores();
                if (this.utilizador.equals(u)) {
                    limparFormulario();
                }
            } else {
                context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível eliminar o utilizador."));
            }
        }
    }

    public void limparFormulario() {
        this.utilizador = new Utilizador();
    }

    public Utilizador getUtilizador() { 
        return utilizador; 
    }
    
    public void setUtilizador(Utilizador utilizador) { 
        this.utilizador = utilizador; 
    }
    
    public List<Utilizador> getListaUtilizadores() { 
        return listaUtilizadores; 
    }
}