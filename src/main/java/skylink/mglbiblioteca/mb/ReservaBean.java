package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.dao.ReservaDAO;
import skylink.mglbiblioteca.model.Reserva;

/**
 * @author Henriques
 */
@Named(value = "reservaBean")
@ViewScoped
public class ReservaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ReservaDAO reservaDAO = new ReservaDAO();

    private Reserva reserva;
    private Reserva reservaSelecionada;
    private List<Reserva> reservas;

    @PostConstruct
    public void init() {
        limpar();
        carregarReservas();
    }

    public void carregarReservas() {
        try {
            this.reservas = reservaDAO.findAll();
            if (this.reservas == null) {
                this.reservas = new ArrayList<>();
            }
        } catch (Exception e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível carregar as reservas: " + e.getMessage());
            this.reservas = new ArrayList<>();
        }
    }

    public void salvar() {
        if (this.reserva == null) return;

        boolean sucesso;
        if (this.reserva.getIdReserva() == null) {
            sucesso = reservaDAO.save(this.reserva);
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva efetuada com sucesso!");
            }
        } else {
            sucesso = reservaDAO.update(this.reserva);
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva atualizada com sucesso!");
            }
        }

        if (sucesso) {
            limpar();
            carregarReservas();
        } else {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao Guardar", "Não foi possível processar os dados da reserva.");
        }
    }

    public void eliminar(Reserva r) {
        if (r != null && r.getIdReserva() != null) {
            boolean sucesso = reservaDAO.delete(r.getIdReserva());
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva removida com sucesso!");
                limpar();
                carregarReservas();
            } else {
                adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao Eliminar", "Não foi possível remover a reserva.");
            }
        }
    }

    public void prepararEdicao(Reserva r) {
        this.reserva = r;
    }

    public void limpar() {
        this.reserva = new Reserva();
        this.reservaSelecionada = null;
    }

    private void adicionarMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }
    public Reserva getReservaSelecionada() { return reservaSelecionada; }
    public void setReservaSelecionada(Reserva reservaSelecionada) { this.reservaSelecionada = reservaSelecionada; }
    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }
}