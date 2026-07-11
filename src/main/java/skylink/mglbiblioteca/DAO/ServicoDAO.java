package skylink.mglbiblioteca.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.Computador;
import skylink.mglbiblioteca.model.Funcionario;
import skylink.mglbiblioteca.model.Sala;
import skylink.mglbiblioteca.model.Servico;
import skylink.mglbiblioteca.model.Utilizador;

/**
 * @Henriques
 */
public class ServicoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO servicos (id_utilizador, id_funcionario, tipo_servico, id_sala, id_computador, quantidade_paginas, data_servico, hora_inicio, hora_fim, custo_total, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE servicos SET id_utilizador = ?, id_funcionario = ?, tipo_servico = ?, id_sala = ?, id_computador = ?, quantidade_paginas = ?, data_servico = ?, hora_inicio = ?, hora_fim = ?, custo_total = ?, status = ? WHERE id_servico = ?";
    private static final String DELETE = "DELETE FROM servicos WHERE id_servico = ?";
    private static final String SELECT_ALL = "SELECT * FROM servicos ORDER BY data_servico DESC, hora_inicio DESC";
    private static final String SELECT_BY_ID = "SELECT * FROM servicos WHERE id_servico = ?";

    public boolean save(Servico s) {
        if (s == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setObject(1, s.getUtilizador() != null ? s.getUtilizador().getIdUtilizador() : null);
            ps.setObject(2, s.getFuncionario() != null ? s.getFuncionario().getIdFuncionario() : null);
            ps.setString(3, s.getTipoServico());
            ps.setObject(4, s.getSala() != null ? s.getSala().getIdSala() : null);
            ps.setObject(5, s.getComputador() != null ? s.getComputador().getIdEquipamento() : null);
            ps.setObject(6, s.getQuantidadePaginas());
            
            ps.setTimestamp(7, s.getDataServico() != null ? new Timestamp(s.getDataServico().getTime()) : new Timestamp(System.currentTimeMillis()));
            ps.setString(8, s.getHoraInicio());
            ps.setString(9, s.getHoraFim());
            ps.setBigDecimal(10, s.getCustoTotal());
            ps.setString(11, s.getStatus() != null ? s.getStatus() : "Confirmado");
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar serviço: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Servico s) {
        if (s == null || s.getIdServico() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setObject(1, s.getUtilizador() != null ? s.getUtilizador().getIdUtilizador() : null);
            ps.setObject(2, s.getFuncionario() != null ? s.getFuncionario().getIdFuncionario() : null);
            ps.setString(3, s.getTipoServico());
            ps.setObject(4, s.getSala() != null ? s.getSala().getIdSala() : null);
            ps.setObject(5, s.getComputador() != null ? s.getComputador().getIdEquipamento() : null);
            ps.setObject(6, s.getQuantidadePaginas());
            ps.setTimestamp(7, s.getDataServico() != null ? new Timestamp(s.getDataServico().getTime()) : null);
            ps.setString(8, s.getHoraInicio());
            ps.setString(9, s.getHoraFim());
            ps.setBigDecimal(10, s.getCustoTotal());
            ps.setString(11, s.getStatus());
            ps.setInt(12, s.getIdServico());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar serviço: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(Integer id) {
        if (id == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao eliminar serviço: " + e.getMessage());
            return false;
        }
    }

    public List<Servico> findAll() {
        List<Servico> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar serviços: " + e.getMessage());
        }
        return lista;
    }

    public Servico findById(Integer id) {
        if (id == null) return null;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar serviço por ID: " + e.getMessage());
        }
        return null;
    }

    private Servico mapearResultSet(ResultSet rs) throws SQLException {
        Servico s = new Servico();
        s.setIdServico(rs.getInt("id_servico"));
        s.setTipoServico(rs.getString("tipo_servico"));
        
        int idUtilizador = rs.getInt("id_utilizador");
        if (!rs.wasNull()) {
            Utilizador u = new Utilizador();
            u.setIdUtilizador(idUtilizador);
            s.setUtilizador(u);
        } else {
            s.setUtilizador(null);
        }

        int idFuncionario = rs.getInt("id_funcionario");
        if (!rs.wasNull()) {
            Funcionario f = new Funcionario();
            f.setIdFuncionario(idFuncionario);
            s.setFuncionario(f);
        } else {
            s.setFuncionario(null);
        }

        int idSala = rs.getInt("id_sala");
        if (!rs.wasNull()) {
            Sala sala = new Sala();
            sala.setIdSala(idSala);
            s.setSala(sala);
        } else {
            s.setSala(null);
        }

        int idComputador = rs.getInt("id_computador");
        if (!rs.wasNull()) {
            Computador c = new Computador();
            c.setIdEquipamento(idComputador);
            s.setComputador(c);
        } else {
            s.setComputador(null);
        }

        int qtdPaginas = rs.getInt("quantidade_paginas");
        s.setQuantidadePaginas(rs.wasNull() ? null : qtdPaginas);
        
        s.setHoraInicio(rs.getString("hora_inicio"));
        s.setHoraFim(rs.getString("hora_fim"));
        s.setCustoTotal(rs.getBigDecimal("custo_total"));
        s.setStatus(rs.getString("status"));
        
        Timestamp dataServicoTs = rs.getTimestamp("data_servico");
        if (dataServicoTs != null) {
            s.setDataServico(new java.util.Date(dataServicoTs.getTime()));
        }
        
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            s.setDataRegisto(new java.util.Date(createdAtTs.getTime()));
        }
        
       
        
        return s;
    }
}