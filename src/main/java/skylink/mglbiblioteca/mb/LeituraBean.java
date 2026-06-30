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
import skylink.mglbiblioteca.model.Leitura;
import skylink.mglbiblioteca.dao.LeituraDAO;

/**
 * @author Henriques
 */
@Named(value = "leituraBean")
@ViewScoped
public class LeituraBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Leitura leitura;
    private final LeituraDAO leituraDAO;
    private List<Leitura> listaLeituras;

    public LeituraBean() {
        this.leitura = new Leitura();
        this.leituraDAO = new LeituraDAO();
        this.listaLeituras = new ArrayList<>();
    }
    
     @PostConstruct
    public void inicializar() {
        
    }

    public String save() throws SQLException {
        boolean sucesso;
        FacesContext context = FacesContext.getCurrentInstance();

        if (leitura.getIdLeitura() == null) {
            sucesso = leituraDAO.save(leitura);
            if (sucesso) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Empréstimo registado com sucesso!"));
            }
        } else {
            sucesso = leituraDAO.update(leitura);
            if (sucesso) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Dados do empréstimo atualizados!"));
            }
        }

        if (sucesso) {
            limpar();
            recarregarLista();
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível gravar a operação."));
        }
        return null;
    }

    public void editar(Leitura l) {
        this.leitura = l;
    }

    public void devolver(int idLeitura) throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (leituraDAO.updateStatus("Devolvido", idLeitura)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Devolvido", "Livro devolvido com sucesso!"));
            recarregarLista();
            if (leitura.getIdLeitura() != null && leitura.getIdLeitura() == idLeitura) {
                limpar();
            }
        }
    }

    public void delete(int idLeitura) throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (leituraDAO.delete(idLeitura)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", "Registo de leitura removido!"));
            recarregarLista();
        }
    }

    public void limpar() {
        this.leitura = new Leitura();
    }

    private void recarregarLista() {
        this.listaLeituras = null;
    }

   
    public Leitura getLeitura() {
        return leitura;
    }

    public void setLeitura(Leitura leitura) {
        this.leitura = leitura;
    }

    public List<Leitura> getListaLeituras() throws SQLException {
        if (listaLeituras == null || listaLeituras.isEmpty()) {
            listaLeituras = leituraDAO.listarTudo();
        }
        return listaLeituras;
    }

    public void setListaLeituras(List<Leitura> listaLeituras) {
        this.listaLeituras = listaLeituras;
    }
}
