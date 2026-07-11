package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.dao.LeituraDAO;
import skylink.mglbiblioteca.model.Leitura;

/**
 * @author Henriques
 */
@Named(value = "leituraBean")
@ViewScoped
public class LeituraBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LeituraDAO leituraDAO = new LeituraDAO();

    private Leitura leitura;
    private Leitura leituraSelecionada;
    private List<Leitura> leituras = new ArrayList<>();

    public LeituraBean() {
        this.leitura = new Leitura();
    }

    @PostConstruct
    public void inicializar() {
        carregarLeituras();
    }

    public void carregarLeituras() {
        try {
            this.leituras = leituraDAO.findAll();
            if (this.leituras == null) {
                this.leituras = new ArrayList<>();
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível listar o histórico de leituras: " + e.getMessage());
            this.leituras = new ArrayList<>();
        }
    }

    public void salvar() {
        if (this.leitura == null) return;

        try {
            boolean sucesso;
            if (this.leitura.getIdLeitura() == null) {
                sucesso = leituraDAO.save(this.leitura);
                if (sucesso) {
                    addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Fluxo de leitura iniciado com sucesso!");
                }
            } else {
                sucesso = leituraDAO.update(this.leitura);
                if (sucesso) {
                    addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Dados de leitura atualizados com sucesso!");
                }
            }

            if (sucesso) {
                limpar();
                carregarLeituras();
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível processar a operação.");
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro Fatal", "Falha de processamento: " + e.getMessage());
        }
    }

    public void eliminar() {
        if (leituraSelecionada == null || leituraSelecionada.getIdLeitura() == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um registo de leitura na tabela para eliminar.");
            return;
        }

        try {
            if (leituraDAO.delete(leituraSelecionada.getIdLeitura())) {
                addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Registo de leitura removido.");
                this.leituras.remove(leituraSelecionada);
                this.leituraSelecionada = null;
            } else {
                addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível remover o registo.");
            }
        } catch (Exception e) {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro Fatal", "Falha ao eliminar: " + e.getMessage());
        }
    }

    public String prepararEdicao() {
        if (leituraSelecionada == null) {
            addMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione uma leitura para modificar ou efetuar devolução.");
            return null;
        }
        this.leitura = this.leituraSelecionada;
        return "/leitura/cadastro_leitura?faces-redirect=true";
    }

    public void limpar() {
        this.leitura = new Leitura();
        this.leituraSelecionada = null;
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    public Leitura getLeitura() { return leitura; }
    public void setLeitura(Leitura leitura) { this.leitura = leitura; }
    public Leitura getLeituraSelecionada() { return leituraSelecionada; }
    public void setLeituraSelecionada(Leitura leituraSelecionada) { this.leituraSelecionada = leituraSelecionada; }
    public List<Leitura> getLeituras() { return leituras; }
    public void setLeituras(List<Leitura> leituras) { this.leituras = leituras; }
}