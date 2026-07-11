package skylink.mglbiblioteca.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.MetodoPagamento;
import skylink.mglbiblioteca.model.Pagamento;

/**
 * @Henriques
 */
public class PagamentoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO pagamentos (id_servico, id_utilizador, valor_pago, metodo_pagamento, data_pagamento) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE pagamentos SET id_servico = ?, id_utilizador = ?, valor_pago = ?, metodo_pagamento = ?, data_pagamento = ? WHERE id_pagamento = ?";
    private static final String DELETE = "DELETE FROM pagamentos WHERE id_pagamento = ?";
    private static final String SELECT_ALL = "SELECT * FROM pagamentos ORDER BY data_pagamento DESC";
    private static final String SELECT_BY_ID = "SELECT * FROM pagamentos WHERE id_pagamento = ?";

    public boolean save(Pagamento p) {
        if (p == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setObject(1, p.getIdServico());
            ps.setObject(2, p.getIdUtilizador());
            ps.setBigDecimal(3, p.getValorPago());
            
            String metodoStr = p.getMetodoPagamento() != null ? p.getMetodoPagamento().name() : null;
            if ("Transferencia".equals(metodoStr)) metodoStr = "Transferência";
            ps.setString(4, metodoStr);
            
            ps.setTimestamp(5, p.getDataPagamento() != null ? new Timestamp(p.getDataPagamento().getTime()) : new Timestamp(System.currentTimeMillis()));
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar pagamento: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Pagamento p) {
        if (p == null || p.getIdPagamento() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setObject(1, p.getIdServico());
            ps.setObject(2, p.getIdUtilizador());
            ps.setBigDecimal(3, p.getValorPago());
            
            String metodoStr = p.getMetodoPagamento() != null ? p.getMetodoPagamento().name() : null;
            if ("Transferencia".equals(metodoStr)) metodoStr = "Transferência";
            ps.setString(4, metodoStr);
            
            ps.setTimestamp(5, p.getDataPagamento() != null ? new Timestamp(p.getDataPagamento().getTime()) : null);
            ps.setInt(6, p.getIdPagamento());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pagamento: " + e.getMessage());
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
            System.err.println("Erro ao eliminar pagamento: " + e.getMessage());
            return false;
        }
    }

    public List<Pagamento> findAll() {
        List<Pagamento> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pagamentos: " + e.getMessage());
        }
        return lista;
    }

    public Pagamento findById(Integer id) {
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
            System.err.println("Erro ao buscar pagamento por ID: " + e.getMessage());
        }
        return null;
    }

    private Pagamento mapearResultSet(ResultSet rs) throws SQLException {
        Pagamento p = new Pagamento();
        p.setIdPagamento(rs.getInt("id_pagamento"));
        p.setIdServico(rs.getInt("id_servico"));
        p.setIdUtilizador(rs.getInt("id_utilizador"));
        p.setValorPago(rs.getBigDecimal("valor_pago"));
        
        String metodoStr = rs.getString("metodo_pagamento");
        if (metodoStr != null) {
            if ("Transferência".equals(metodoStr)) metodoStr = "Transferencia";
            try {
                p.setMetodoPagamento(MetodoPagamento.valueOf(metodoStr));
            } catch (IllegalArgumentException e) {
                System.err.println("Método de pagamento inválido no banco: " + metodoStr);
            }
        }
        
        p.setDataPagamento(rs.getTimestamp("data_pagamento"));
        
        p.setDataRegisto(rs.getTimestamp("data_registo"));
        
        return p;
    }
}