package skylink.mglbiblioteca.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author Henriques
 */
public class Servico implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idServico;
    private Utilizador utilizador;
    private Funcionario funcionario;
    private String tipoServico;
    private Sala sala;
    private Computador computador;
    private Integer quantidadePaginas;
    private Date dataServico;
    private String horaInicio;
    private String horaFim;
    private BigDecimal custoTotal;
    private String status;
    private Date dataRegisto;
  

    public Servico() {
        this.utilizador = new Utilizador();
        this.funcionario = new Funcionario();
        this.sala = new Sala();
        this.computador = new Computador();
        this.dataServico = new Date();
        this.custoTotal = BigDecimal.ZERO;
        this.status = "Confirmado";
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Computador getComputador() {
        return computador;
    }

    public void setComputador(Computador computador) {
        this.computador = computador;
    }

    public Integer getQuantidadePaginas() {
        return quantidadePaginas;
    }

    public void setQuantidadePaginas(Integer quantidadePaginas) {
        this.quantidadePaginas = quantidadePaginas;
    }

    public Date getDataServico() {
        return dataServico;
    }

    public void setDataServico(Date dataServico) {
        this.dataServico = dataServico;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public BigDecimal getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(BigDecimal custoTotal) {
        this.custoTotal = custoTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Date dataRegisto) {
        this.dataRegisto = dataRegisto;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idServico);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Servico other = (Servico) obj;
        return Objects.equals(this.idServico, other.idServico);
    }
}