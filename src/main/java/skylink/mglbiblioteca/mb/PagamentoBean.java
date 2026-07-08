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
import skylink.mglbiblioteca.DAO.PagamentoDAO;
import skylink.mglbiblioteca.model.MetodoPagamento;
import skylink.mglbiblioteca.model.Pagamento;


/**
 * @Henriques
 */
@Named(value = "pagamentoBean")
@ViewScoped
public class PagamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PagamentoDAO pagamentoDAO;

    private Pagamento pagamento;
    private Pagamento pagamentoSelecionado;
    private List<Pagamento> pagamentos = new ArrayList<>();

    public PagamentoBean() {
        this.pagamento = new Pagamento();
    }

    @PostConstruct
    public void inicializar() {
        carregarPagamentos();
    }

    public void carregarPagamentos() {
        pagamentos = pagamentoDAO.findAll();
    }

    public void salvar() {
        if (pagamento == null) return;

        boolean sucesso;
        if (pagamento.getIdPagamento() == null) {
            sucesso = pagamentoDAO.save(pagamento);
        } else {
            sucesso = pagamentoDAO.update(pagamento);
        }

        if (sucesso) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento registado com sucesso!");
            this.pagamento = new Pagamento(); 
            carregarPagamentos(); 
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível processar o pagamento.");
        }
    }

    public void estornar() {
        if (pagamentoSelecionado == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um pagamento para remover/estornar.");
            return;
        }

        if (pagamentoDAO.delete(pagamentoSelecionado)) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento removido.");
            pagamentos.remove(pagamentoSelecionado);
            this.pagamentoSelecionado = null;
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir o registo do pagamento.");
        }
    }

  
    public MetodoPagamento[] getMetodosPagamento() {
        return MetodoPagamento.values();
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }


    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Pagamento getPagamentoSelecionado() {
        return pagamentoSelecionado;
    }

    public void setPagamentoSelecionado(Pagamento pagamentoSelecionado) {
        this.pagamentoSelecionado = pagamentoSelecionado;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }
}