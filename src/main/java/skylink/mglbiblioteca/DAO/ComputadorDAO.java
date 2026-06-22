package skylink.mglbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglarmazem.bdutil.ConnectionDB;
import skylink.mglbiblioteca.MODEL0.Computador;


public class ComputadorDAO {

    private static final String INSERT = "INSERT INTO Computadores (ref_equipamento, status, localizacao) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE Computadores SET ref_equipamento = ?, status = ?, localizacao = ? WHERE id_equipmento = ?";
    private static final String DELETE = "DELETE FROM Computadores WHERE id_equipamento = ?";
    private static final String BUSCAR_POR_CODIGO = "SELECT * FROM Computadores WHERE id_equipamento = ?";
    private static final String LISTAR_TUDO = "SELECT id_equipamento, ref_equipamento, status, localizacao FROM Computadores ORDER BY ref_equipamento";
    private static final String LISTAR_POR_STATUS = "SELECT id_equipamento, ref_equipamento, status, localizacao FROM Computadores WHERE status = ? ORDER BY ref_equipamento";
    
    private static final String ATUALIZAR_STATUS = "UPDATE Computadores SET status = ? WHERE id_equipamento = ?";

    public boolean save(Computador computador) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(INSERT);
            ps.setString(1, computador.getRefEquipamento());
            ps.setString(2, computador.getStatus());
            ps.setString(3, computador.getLocalizacao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir computador: " + e.getMessage());
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public boolean update(Computador computador) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(UPDATE);
            ps.setString(1, computador.getRefEquipamento());
            ps.setString(2, computador.getStatus());
            ps.setString(3, computador.getLocalizacao());
            ps.setInt(4, computador.getIdEquipamento());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao actualizar computador: " + e.getMessage());
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public boolean delete(int idEquipamento) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(DELETE);
            ps.setInt(1, idEquipamento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao eliminar computador: " + e.getMessage());
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public List<Computador> listarTudo() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Computador> lista = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(LISTAR_TUDO);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar computadores: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return lista;
    }

    public List<Computador> listarPorStatus(String statusFiltro) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Computador> lista = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(LISTAR_POR_STATUS);
            ps.setString(1, statusFiltro);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao filtrar computadores por status: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return lista;
    }

    public boolean updateStatus(String novoStatus, Integer idEquipamento) {
        boolean bl;
        PreparedStatement ps = null;
        Connection conn = null;
        boolean flagControlo = false;
        try {
            System.out.println("Alterando status do equipamento para >>>>>>>>>> " + novoStatus);
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false);
            
            ps = conn.prepareStatement(ATUALIZAR_STATUS);
            ps.setString(1, novoStatus);
            ps.setInt(2, idEquipamento);
            int retorno = ps.executeUpdate();
            conn.commit();
            
            if (retorno > 0) {
                System.out.println("ComputadorDAO:updateStatus Dados alterados com sucesso: " + ps.getUpdateCount());
                flagControlo = true;
            }
            bl = flagControlo;
        }
        catch (SQLException e) {
            boolean bl2;
            try {
                System.out.println("Erro ao atualizar status do equipamento: " + e.getMessage());
                bl2 = false;
            }
            catch (Throwable throwable) {
                ConnectionDB.closeConnection(conn, ps);
                throw throwable;
            }
            ConnectionDB.closeConnection((Connection)conn, (PreparedStatement)ps);
            return bl2;
        }
        ConnectionDB.closeConnection((Connection)conn, (PreparedStatement)ps);
        return bl;
    }

    public Computador buscarPorCodigo(int idEquipamento) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Computador computador = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(BUSCAR_POR_CODIGO);
            ps.setInt(1, idEquipamento);
            rs = ps.executeQuery();
            if (rs.next()) {
                computador = mapearResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar computador: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return computador;
    }

    private Computador mapearResultSet(ResultSet rs) throws SQLException {
        Computador c = new Computador();
        c.setIdEquipamento(rs.getInt("id_equipamento"));
        c.setRefEquipamento(rs.getString("ref_equipamento"));
        c.setStatus(rs.getString("status"));
        c.setLocalizacao(rs.getString("localizacao"));
        return c;
    }

    public String buscarStatusAtual(int idEquipamento) throws SQLException {
        String sql = "SELECT status FROM Computadores WHERE id_equipamento = ?";
        
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idEquipamento);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status"); 
                }
            }
        }
        return "Desconhecido"; 
    }
}