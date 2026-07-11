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
import skylink.mglbiblioteca.model.PrecoServico;

/**
 * @Henriques
 */
public class PrecoServicoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO precos_servicos (tipo_servico, valor_unitario, unidade) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE precos_servicos SET valor_unitario = ?, unidade = ? WHERE tipo_servico = ?";
    private static final String DELETE = "DELETE FROM precos_servicos WHERE tipo_servico = ?";
    private static final String SELECT_ALL = "SELECT * FROM precos_servicos ORDER BY tipo_servico ASC";
    private static final String SELECT_BY_TIPO = "SELECT * FROM precos_servicos WHERE tipo_servico = ?";

    public boolean save(PrecoServico p) {
        if (p == null || p.getTipoServico() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, p.getTipoServico());
            ps.setBigDecimal(2, p.getValorUnitario());
            ps.setString(3, p.getUnidade());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar preço do serviço: " + e.getMessage());
            return false;
        }
    }

    public boolean update(PrecoServico p) {
        if (p == null || p.getTipoServico() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setBigDecimal(1, p.getValorUnitario());
            ps.setString(2, p.getUnidade());
            ps.setString(3, p.getTipoServico());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar preço do serviço: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(String tipoServico) {
        if (tipoServico == null || tipoServico.trim().isEmpty()) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            
            ps.setString(1, tipoServico);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao eliminar preço do serviço: " + e.getMessage());
            return false;
        }
    }

    public List<PrecoServico> findAll() {
        List<PrecoServico> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar preços dos serviços: " + e.getMessage());
        }
        return lista;
    }

    public PrecoServico findByTipo(String tipoServico) {
        if (tipoServico == null || tipoServico.trim().isEmpty()) return null;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_TIPO)) {

            ps.setString(1, tipoServico);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar preço do serviço por tipo: " + e.getMessage());
        }
        return null;
    }

    private PrecoServico mapearResultSet(ResultSet rs) throws SQLException {
        PrecoServico p = new PrecoServico();
        p.setTipoServico(rs.getString("tipo_servico"));
        p.setValorUnitario(rs.getBigDecimal("valor_unitario"));
        p.setUnidade(rs.getString("unidade"));
        
        Timestamp timestamp = rs.getTimestamp("updated_at");
        if (timestamp != null) {
            p.setUpdatedAt(new java.util.Date(timestamp.getTime()));
        }
        
        return p;
    }
}