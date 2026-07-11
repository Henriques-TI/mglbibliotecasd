package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.dao.SalaDAO;
import skylink.mglbiblioteca.model.Sala;

/**
 * @author Henriques
 */
@Named(value = "salaBean")
@ViewScoped
public class SalaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Sala sala;
    private List<Sala> salas;
    private SalaDAO salaDAO; 

    @PostConstruct
    public void init() {
        this.salaDAO = new SalaDAO();
        limpar();
        carregarSalas();
    }

    public void carregarSalas() {
        this.salas = salaDAO.findAll();
        if (this.salas == null) {
            this.salas = new ArrayList<>();
        }
    }

    public void salvar() {
        boolean sucesso;

        if (this.sala.getIdSala() == null) {
            sucesso = salaDAO.save(this.sala);
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Sala registada com sucesso!");
            }
        } else {
            sucesso = salaDAO.update(this.sala);
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Dados da sala atualizados!");
            }
        }

        if (sucesso) {
            limpar();
            carregarSalas();
        } else {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao Guardar", "Não foi possível guardar os dados da sala. Verifique os logs do servidor.");
        }
    }

    public void prepararEdicao(Sala s) {
        this.sala = s;
    }

    public void excluir(Sala s) {
        if (s != null && s.getIdSala() != null) {
            boolean sucesso = salaDAO.delete(s.getIdSala());
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Sala eliminada com sucesso!");
                limpar();
                carregarSalas();
            } else {
                adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao Eliminar", "Não foi possível eliminar a sala selecionada.");
            }
        }
    }

    public void limpar() {
        this.sala = new Sala(); 
    }

    private void adicionarMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
}