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
import skylink.mglbiblioteca.model.Computador;
import skylink.mglbiblioteca.dao.ComputadorDAO;

@Named(value = "computadorBean")
@ViewScoped
public class ComputadorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Computador computador;
    
    @Inject 
    private ComputadorDAO computadorDAO; 
    
    private List<Computador> listaComputadores;
    private String filtroStatus;

    public ComputadorBean() {
    }

    @PostConstruct
    public void init() {
        this.computador = new Computador();
        this.listaComputadores = new ArrayList<>();
        this.computador.setStatus("Disponível"); 
        recarregarLista();
    }

    public String save() {
        boolean sucesso;
        
        if (computador.getIdEquipamento() == null) {
            sucesso = computadorDAO.save(computador);
            if (sucesso) addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Computador cadastrado com sucesso!");
        } else {
            sucesso = computadorDAO.update(computador);
            if (sucesso) addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Computador atualizado com sucesso!");
        }

        if (sucesso) {
            limpar();
            recarregarLista();
            return "/computador/lista_computadores?faces-redirect=true";
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar as informações do computador.");
            return null;
        }
    }

    public void excluir(Computador c) {
        if (computadorDAO.delete(c.getIdEquipamento())) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Computador removido com sucesso!");
            recarregarLista();
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível remover o computador selecionado.");
        }
    }

    public void alterarStatus(Computador c, String novoStatus) {
        if (computadorDAO.updateStatus(novoStatus, c.getIdEquipamento())) {
            addMensagem(FacesMessage.SEVERITY_INFO, "Status Atualizado", "O status do equipamento foi alterado.");
            recarregarLista();
        } else {
            addMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao alterar o status do equipamento.");
        }
    }

    public void filtrar() {
        if (filtroStatus == null || filtroStatus.isEmpty()) {
            listaComputadores = computadorDAO.listarTudo();
        } else {
            listaComputadores = computadorDAO.listarPorStatus(filtroStatus);
        }
    }

    public void limpar() {
        this.computador = new Computador();
        this.computador.setStatus("Disponível");
    }

    private void recarregarLista() {
        if (filtroStatus != null && !filtroStatus.isEmpty()) {
            this.listaComputadores = computadorDAO.listarPorStatus(filtroStatus);
        } else {
            this.listaComputadores = computadorDAO.listarTudo();
        }
    }

    public void addMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    public Computador getComputador() {
        return computador;
    }

    public void setComputador(Computador computador) {
        this.computador = computador;
    }

    public List<Computador> getListaComputadores() {
        return listaComputadores;
    }

    public String getFiltroStatus() {
        return filtroStatus;
    }

    public void setFiltroStatus(String filtroStatus) {
        this.filtroStatus = filtroStatus;
    }
}