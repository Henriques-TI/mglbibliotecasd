package skylink.mglbiblioteca.mb;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import skylink.mglbiblioteca.dao.ServicoDAO;
import skylink.mglbiblioteca.dao.UtilizadorDAO;
import skylink.mglbiblioteca.dao.FuncionarioDAO;
import skylink.mglbiblioteca.dao.SalaDAO;

import skylink.mglbiblioteca.model.Servico;
import skylink.mglbiblioteca.model.Computador;
import skylink.mglbiblioteca.model.Utilizador;
import skylink.mglbiblioteca.model.Funcionario;
import skylink.mglbiblioteca.model.Sala;

/**
 * @author Henriques
 */
@Named(value = "servicoBean")
@ViewScoped
public class ServicoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Servico servico;
    private List<Servico> servicos;
    
    private List<Utilizador> utilizadores;
    private List<Funcionario> funcionarios;
    private List<Sala> salas;
    private List<Computador> computadores;
    
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private final UtilizadorDAO utilizadorDAO = new UtilizadorDAO();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final SalaDAO salaDAO = new SalaDAO();

    @PostConstruct
    public void init() {
        limpar();
        carregarServicos();
        carregarCombosAuxiliares();
    }

    public void carregarServicos() {
        this.servicos = servicoDAO.findAll();
        if (this.servicos == null) {
            this.servicos = new ArrayList<>();
        }
    }
    
    public void carregarCombosAuxiliares() {
        try {
            this.utilizadores = utilizadorDAO.findAll();
            this.funcionarios = funcionarioDAO.findAll();
            this.salas = salaDAO.findAll();
            
            this.computadores = new ArrayList<>(); 
        } catch (Exception e) {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro Auxiliar", "Erro ao carregar dados de suporte: " + e.getMessage());
        }
    }
    
    public void salvar() {
        boolean sucesso;

        if (this.servico.getIdServico() == null) {
            sucesso = servicoDAO.save(this.servico);
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Serviço registado com sucesso!");
            }
        } else {
            sucesso = servicoDAO.update(this.servico);
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Serviço atualizado com sucesso!");
            }
        }

        if (sucesso) {
            limpar();
            carregarServicos();
        } else {
            adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao Guardar", "Não foi possível guardar os dados do serviço. Verifique os logs.");
        }
    }

    public void prepararEdicao(Servico s) {
        this.servico = s;
    }
   
    public void excluir(Servico s) {
        if (s != null && s.getIdServico() != null) {
            boolean sucesso = servicoDAO.delete(s.getIdServico());
            if (sucesso) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Sucesso", "Serviço eliminado com sucesso!");
                limpar();
                carregarServicos();
            } else {
                adicionarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao Eliminar", "Não foi possível eliminar o serviço selecionado.");
            }
        }
    }
    
    public void limpar() {
        this.servico = new Servico(); 
    }

    private void adicionarMensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, resumo, detalhe));
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public List<Computador> getComputadores() {
        return computadores;
    }

    public void setComputadores(List<Computador> computadores) {
        this.computadores = computadores;
    }
}