package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
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
    }

    public String save() {
        boolean sucesso;
        
        if (computador.getIdEquipamento() == null) {
            sucesso = computadorDAO.save(computador);
        } else {
            sucesso = computadorDAO.update(computador);
        }

        if (sucesso) {
            limpar();
            recarregarLista();
        }
        return null; 
    }

    public void editar(Computador c) {
        this.computador = c;
    }

    public void delete(int idEquipamento) {
        if (computadorDAO.delete(idEquipamento)) {
            recarregarLista();
            if (computador.getIdEquipamento() != null && computador.getIdEquipamento() == idEquipamento) {
                limpar();
            }
        }
    }

    public void alterarStatus(Computador c, String novoStatus) {
        if (computadorDAO.updateStatus(novoStatus, c.getIdEquipamento())) {
            recarregarLista();
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
        this.listaComputadores = null;
    }

    public Computador getComputador() {
        return computador;
    }

    public void setComputador(Computador computador) {
        this.computador = computador;
    }

    public List<Computador> getListaComputadores() {
        if (listaComputadores == null || listaComputadores.isEmpty()) {
            if (filtroStatus != null && !filtroStatus.isEmpty()) {
                listaComputadores = computadorDAO.listarPorStatus(filtroStatus);
            } else {
                listaComputadores = computadorDAO.listarTudo();
            }
        }
        return listaComputadores;
    }

    public String getFiltroStatus() {
        return filtroStatus;
    }

    public void setFiltroStatus(String filtroStatus) {
        this.filtroStatus = filtroStatus;
    }
}