package skylink.mglbiblioteca.DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import skylink.mglbiblioteca.model.MetodoPagamento;
import skylink.mglbiblioteca.model.Pagamento;
import skylink.mglbiblioteca.bdutil.ConnectionDB;

/**
 * @Henriques
 */
public class PagamentoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO pagamentos (tipo_origem, id_referencia, id_utilizador, valor_pago, metodo_pagamento, data_pagamento) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE pagamentos SET tipo_origem = ?, id_referencia = ?, id_utilizador = ?, valor_pago = ?, metodo_pagamento = ? WHERE id_pagamento = ?";
    private static final String DELETE = "DELETE FROM pagamentos WHERE id_pagamento = ?";
    private static final String SELECT_ALL = "SELECT * FROM pagamentos ORDER BY data_pagamento DESC";
    private static final String SELECT_BY_ID = "SELECT * FROM pagamentos WHERE id_pagamento = ?";

    public boolean save(Pagamento p) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setObject(2, p.getIdReferencia());
            ps.setObject(3, p.getIdUtilizador());
            ps.setBigDecimal(4, p.getValorPago());
            
            String metodoStr = p.getMetodoPagamento() != null ? p.getMetodoPagamento().name() : null;
            if ("Transferencia".equals(metodoStr)) metodoStr = "Transferência";
            ps.setString(5, metodoStr);
            
            ps.setTimestamp(6, p.getDataPagamento() != null ? new Timestamp(p.getDataPagamento().getTime()) : new Timestamp(System.currentTimeMillis()));
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Pagamento p) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setObject(2, p.getIdReferencia());
            ps.setObject(3, p.getIdUtilizador());
            ps.setBigDecimal(4, p.getValorPago());
            
            String metodoStr = p.getMetodoPagamento() != null ? p.getMetodoPagamento().name() : null;
            if ("Transferencia".equals(metodoStr)) metodoStr = "Transferência";
            ps.setString(5, metodoStr);
            
            ps.setInt(6, p.getIdPagamento());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Pagamento p) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            
            ps.setInt(1, p.getIdPagamento());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return lista;
    }

    public Pagamento findById(Integer id) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Pagamento mapearResultSet(ResultSet rs) throws SQLException {
        Pagamento p = new Pagamento();
        p.setIdPagamento(rs.getInt("id_pagamento"));
        
        
        p.setIdReferencia(rs.getInt("id_referencia"));
        p.setIdUtilizador(rs.getInt("id_utilizador"));
        p.setValorPago(rs.getBigDecimal("valor_pago"));
        
        String metodoStr = rs.getString("metodo_pagamento");
        if (metodoStr != null) {
            if ("Transferência".equals(metodoStr)) metodoStr = "Transferencia";
            p.setMetodoPagamento(MetodoPagamento.valueOf(metodoStr));
        }
        
        Timestamp timestamp = rs.getTimestamp("data_pagamento");
        if (timestamp != null) p.setDataPagamento(new Date(timestamp.getTime()));
        
        return p;
    }
}