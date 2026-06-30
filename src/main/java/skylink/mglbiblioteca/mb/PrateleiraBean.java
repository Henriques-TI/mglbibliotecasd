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
import skylink.mglbiblioteca.model.Prateleira;

/**
 * @Henriques
 */
@Named(value = "prateleiraBean")
@ViewScoped
public class PrateleiraBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PrateleiraDAO prateleiraDAO;

    private Prateleira prateleira;
    private Prateleira prateleiraSelecionada;
    private List<Prateleira> prateleiras = new ArrayList<>();
    private String filtroPosicao;

    public PrateleiraBean() {
        this.prateleira = new Prateleira();
    }

    @PostConstruct
    public void inicializar() {
        carregarPrateleiras();
    }

    public void carregarPrateleiras() {
        prateleiras = prateleiraDAO.findAll();
    }

    public void pesquisar() {
        if (filtroPosicao != null && !filtroPosicao.trim().isEmpty()) {
            prateleiras = prateleiraDAO.buscarPorPosicao(filtroPosicao.trim());
            if (prateleiras.isEmpty()) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Informação", "Nenhuma prateleira encontrada nesta posição.");
            }
        } else {
            addMensagem(FacesMessage.SEVERITY_WARN, "Atenção", "Informe uma posição para pesquisar.");
            carregarPrateleiras();
        }
    }

    public void limparPesquisa() {
        this.filtroPosicao = null;
        carregarPrateleiras();
        this.prateleiraSelecionada = null;
    }

    public void salvar() {
        if (prateleira == null) return;

        boolean sucesso;
        if (prateleira.getIdPrateleira() == null) {
            sucesso = prateleiraDAO.save(prateleira);
        } else {
            sucesso = prateleiraDAO.update(prateleira);
        }

        if (sucesso) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Prateleira guardada com sucesso!");
            this.prateleira = new Prateleira(); 
            carregarPrateleiras(); 
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao processar operação na base de dados.");
        }
    }

    public void excluir() {
        if (prateleiraSelecionada == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione uma prateleira na tabela.");
            return;
        }

        if (prateleiraDAO.delete(prateleiraSelecionada)) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Prateleira removida.");
            prateleiras.remove(prateleiraSelecionada);
            this.prateleiraSelecionada = null;
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir a prateleira.");
        }
    }

    public String editar() {
        if (prateleiraSelecionada == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione uma prateleira para editar.");
            return null;
        }
        this.prateleira = prateleiraSelecionada;
        return "/prateleira/cadastro_prateleira?faces-redirect=true";
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }


    public Prateleira getPrateleira() {
        return prateleira;
    }

    public void setPrateleira(Prateleira prateleira) {
        this.prateleira = prateleira;
    }

    public Prateleira getPrateleiraSelecionada() {
        return prateleiraSelecionada;
    }

    public void setPrateleiraSelecionada(Prateleira prateleiraSelecionada) {
        this.prateleiraSelecionada = prateleiraSelecionada;
    }

    public List<Prateleira> getPrateleiras() {
        return prateleiras;
    }

    public void setPrateleiras(List<Prateleira> prateleiras) {
        this.prateleiras = prateleiras;
    }

    public String getFiltroPosicao() {
        return filtroPosicao;
    }

    public void setFiltroPosicao(String filtroPosicao) {
        this.filtroPosicao = filtroPosicao;
    }
}