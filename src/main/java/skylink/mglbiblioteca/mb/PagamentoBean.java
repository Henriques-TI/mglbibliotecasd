package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.dao.PagamentoDAO;
import skylink.mglbiblioteca.model.MetodoPagamento;
import skylink.mglbiblioteca.model.Pagamento;

/**
 * @author Henriques
 */
@Named(value = "pagamentoBean")
@ViewScoped
public class PagamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final PagamentoDAO pagamentoDAO = new PagamentoDAO();

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
        try {
            pagamentos = pagamentoDAO.findAll();
            if (pagamentos == null) {
                pagamentos = new ArrayList<>();
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível carregar os pagamentos: " + e.getMessage());
            pagamentos = new ArrayList<>();
        }
    }

    public void salvar() {
        if (pagamento == null) return;

        try {
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
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível processar o pagamento na base de dados.");
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro fatal", "Erro ao salvar: " + e.getMessage());
        }
    }

    public void estornar() {
        if (pagamentoSelecionado == null || pagamentoSelecionado.getIdPagamento() == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um pagamento para remover/estornar.");
            return;
        }

        try {
            if (pagamentoDAO.delete(pagamentoSelecionado.getIdPagamento())) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento removido.");
                pagamentos.remove(pagamentoSelecionado);
                this.pagamentoSelecionado = null;
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir o registo do pagamento.");
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao estornar: " + e.getMessage());
        }
    }

    public void prepararEdicao(Pagamento p) {
        this.pagamento = p;
    }

    public MetodoPagamento[] getMetodosPagamento() {
        return MetodoPagamento.values();
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, new FacesMessage(severidade, resumo, detalhe));
        }
    }

    public Pagamento getPagamento() { return pagamento; }
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
    public Pagamento getPagamentoSelecionado() { return pagamentoSelecionado; }
    public void setPagamentoSelecionado(Pagamento pagamentoSelecionado) { this.pagamentoSelecionado = pagamentoSelecionado; }
    public List<Pagamento> getPagamentos() { return pagamentos; }
    public void setPagamentos(List<Pagamento> pagamentos) { this.pagamentos = pagamentos; }
}