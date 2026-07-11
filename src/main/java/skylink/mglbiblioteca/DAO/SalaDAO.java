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
import skylink.mglbiblioteca.model.Sala;

/**
 * @author Henriques
 */
public class SalaDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO salas (nome_sala, capacidade, preco_hora, status) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE salas SET nome_sala = ?, capacidade = ?, preco_hora = ?, status = ? WHERE id_sala = ?";
    private static final String DELETE = "DELETE FROM salas WHERE id_sala = ?";
    private static final String SELECT_ALL = "SELECT * FROM salas ORDER BY nome_sala ASC";
    private static final String SELECT_BY_ID = "SELECT * FROM salas WHERE id_sala = ?";

    public boolean save(Sala s) {
        if (s == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, s.getNomeSala());
            ps.setObject(2, s.getCapacidade());
            ps.setBigDecimal(3, s.getPrecoHora());
            ps.setString(4, s.getStatus() != null ? s.getStatus() : "Disponível");
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar sala: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Sala s) {
        if (s == null || s.getIdSala() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setString(1, s.getNomeSala());
            ps.setObject(2, s.getCapacidade());
            ps.setBigDecimal(3, s.getPrecoHora());
            ps.setString(4, s.getStatus());
            ps.setInt(5, s.getIdSala());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar sala: " + e.getMessage());
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
            System.err.println("Erro ao eliminar sala: " + e.getMessage());
            return false;
        }
    }

    public List<Sala> findAll() {
        List<Sala> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar salas: " + e.getMessage());
        }
        return lista;
    }

    public Sala findById(Integer id) {
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
            System.err.println("Erro ao buscar sala por ID: " + e.getMessage());
        }
        return null;
    }

    private Sala mapearResultSet(ResultSet rs) throws SQLException {
        Sala s = new Sala();
        s.setIdSala(rs.getInt("id_sala"));
        s.setNomeSala(rs.getString("nome_sala"));
        s.setCapacidade(rs.getInt("capacidade"));
        s.setPrecoHora(rs.getBigDecimal("preco_hora"));
        s.setStatus(rs.getString("status"));
        
        Timestamp timestamp = rs.getTimestamp("data_registo");
        if (timestamp != null) {
            s.setDataRegisto(new java.util.Date(timestamp.getTime()));
        }
        
        return s;
    }
}