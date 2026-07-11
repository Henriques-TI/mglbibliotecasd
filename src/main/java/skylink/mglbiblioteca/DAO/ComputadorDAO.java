package skylink.mglbiblioteca.dao;

import jakarta.enterprise.context.Dependent;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.Computador;

@Dependent
public class ComputadorDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT =
            "INSERT INTO computadores (ref_equipamento, status, localizacao) VALUES (?, ?, ?)";

    private static final String UPDATE =
            "UPDATE computadores SET ref_equipamento = ?, status = ?, localizacao = ? WHERE id_equipamento = ?";

    private static final String DELETE =
            "DELETE FROM computadores WHERE id_equipamento = ?";

    private static final String BUSCAR_POR_CODIGO =
            "SELECT * FROM computadores WHERE id_equipamento = ?";

    private static final String LISTAR_TUDO =
            "SELECT * FROM computadores ORDER BY ref_equipamento";

    private static final String LISTAR_POR_STATUS =
            "SELECT * FROM computadores WHERE status = ? ORDER BY ref_equipamento";

    private static final String ATUALIZAR_STATUS = 
            "UPDATE computadores SET status = ? WHERE id_equipamento = ?";

    public boolean save(Computador computador) {
        if (computador == null) return false;
        
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setString(1, computador.getRefEquipamento());
            ps.setString(2, computador.getStatus());
            ps.setString(3, computador.getLocalizacao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar computador: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Computador computador) {
        if (computador == null || computador.getIdEquipamento() == null) return false;
        
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, computador.getRefEquipamento());
            ps.setString(2, computador.getStatus());
            ps.setString(3, computador.getLocalizacao());
            ps.setInt(4, computador.getIdEquipamento());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar computador: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idEquipamento) {
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, idEquipamento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir computador: " + e.getMessage());
            return false;
        }
    }

    public List<Computador> listarTudo() {
        List<Computador> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(LISTAR_TUDO); 
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar computadores: " + e.getMessage());
        }
        return lista;
    }

    public List<Computador> listarPorStatus(String status) {
        List<Computador> lista = new ArrayList<>();
        if (status == null) return lista;
        
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(LISTAR_POR_STATUS)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar computadores por status: " + e.getMessage());
        }
        return lista;
    }

    public boolean updateStatus(String status, int idEquipamento) {
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(ATUALIZAR_STATUS)) {
            ps.setString(1, status);
            ps.setInt(2, idEquipamento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status do computador: " + e.getMessage());
            return false;
        }
    }

    public Computador buscarPorCodigo(int idEquipamento) {
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_CODIGO)) {
            ps.setInt(1, idEquipamento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar computador: " + e.getMessage());
        }
        return null;
    }

    public String buscarStatusAtual(int idEquipamento) {
        String sql = "SELECT status FROM computadores WHERE id_equipamento = ?";
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquipamento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar status: " + e.getMessage());
        }
        return null;
    }

    private Computador mapearResultSet(ResultSet rs) throws SQLException {
        Computador c = new Computador();
        c.setIdEquipamento(rs.getInt("id_equipamento"));
        c.setRefEquipamento(rs.getString("ref_equipamento"));
        c.setStatus(rs.getString("status"));
        c.setLocalizacao(rs.getString("localizacao"));
        
        c.setDataRegisto(rs.getTimestamp("dataRegisto"));        
        return c;
    }
}