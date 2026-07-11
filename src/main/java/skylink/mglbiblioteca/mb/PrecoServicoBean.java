package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.dao.PrecoServicoDAO;
import skylink.mglbiblioteca.model.PrecoServico;

/**
 * @author Henriques
 */
@Named(value = "precoServicoBean")
@ViewScoped
public class PrecoServicoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final PrecoServicoDAO precoServicoDAO = new PrecoServicoDAO();

    private PrecoServico precoServico;
    private PrecoServico precoServicoSelecionado;
    private List<PrecoServico> precosServicos;

    @PostConstruct
    public void init() {
        limpar();
        carregarPrecos();
    }

    public void carregarPrecos() {
        try {
            this.precosServicos = precoServicoDAO.findAll();
            if (this.precosServicos == null) {
                this.precosServicos = new ArrayList<>();
            }
        } catch (Exception e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível carregar os preços: " + e.getMessage());
            this.precosServicos = new ArrayList<>();
        }
    }

    public void salvar() {
        if (this.precoServico == null || this.precoServico.getTipoServico() == null) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "O tipo de serviço deve ser especificado.");
            return;
        }

        boolean sucesso;
        PrecoServico existente = precoServicoDAO.findByTipo(this.precoServico.getTipoServico().trim());

        if (existente == null) {
            sucesso = precoServicoDAO.save(this.precoServico);
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Preço de serviço registado com sucesso!");
            }
        } else {
            sucesso = precoServicoDAO.update(this.precoServico);
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Preço de serviço atualizado com sucesso!");
            }
        }

        if (sucesso) {
            limpar();
            carregarPrecos();
        } else {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao Guardar", "Não foi possível guardar os dados.");
        }
    }

    public void eliminar(PrecoServico p) {
        if (p != null && p.getTipoServico() != null) {
            boolean sucesso = precoServicoDAO.delete(p.getTipoServico());
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Preço de serviço eliminado!");
                limpar();
                carregarPrecos();
            } else {
                adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao Eliminar", "Não foi possível eliminar o registo.");
            }
        }
    }

    public void prepararEdicao(PrecoServico p) {
        this.precoServico = p;
    }

    public void limpar() {
        this.precoServico = new PrecoServico();
        this.precoServicoSelecionado = null;
    }

    private void adicionarMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    public PrecoServico getPrecoServico() { return precoServico; }
    public void setPrecoServico(PrecoServico precoServico) { this.precoServico = precoServico; }
    public PrecoServico getPrecoServicoSelecionado() { return precoServicoSelecionado; }
    public void setPrecoServicoSelecionado(PrecoServico precoServicoSelecionado) { this.precoServicoSelecionado = precoServicoSelecionado; }
    public List<PrecoServico> getPrecosServicos() { return precosServicos; }
    public void setPrecosServicos(List<PrecoServico> precosServicos) { this.precosServicos = precosServicos; }
}