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
import skylink.mglbiblioteca.model.Reserva;

/**
 * @Henriques
 */
public class ReservaDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO reservas (id_utilizador, id_livro, data_reserva, status) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE reservas SET id_utilizador = ?, id_livro = ?, data_reserva = ?, status = ? WHERE id_reserva = ?";
    private static final String DELETE = "DELETE FROM reservas WHERE id_reserva = ?";
    private static final String SELECT_ALL = "SELECT * FROM reservas ORDER BY data_reserva DESC";
    private static final String SELECT_BY_ID = "SELECT * FROM reservas WHERE id_reserva = ?";

    public boolean save(Reserva r) {
        if (r == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setObject(1, r.getIdUtilizador());
            ps.setObject(2, r.getIdLivro());
            
            ps.setTimestamp(3, r.getDataReserva() != null ? new Timestamp(r.getDataReserva().getTime()) : new Timestamp(System.currentTimeMillis()));
            
            ps.setString(4, r.getStatus() != null ? r.getStatus() : "Ativa");
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar reserva: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Reserva r) {
        if (r == null || r.getIdReserva() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setObject(1, r.getIdUtilizador());
            ps.setObject(2, r.getIdLivro());
            ps.setTimestamp(3, r.getDataReserva() != null ? new Timestamp(r.getDataReserva().getTime()) : null);
            ps.setString(4, r.getStatus());
            ps.setInt(5, r.getIdReserva());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar reserva: " + e.getMessage());
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
            System.err.println("Erro ao eliminar reserva: " + e.getMessage());
            return false;
        }
    }

    public List<Reserva> findAll() {
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar reservas: " + e.getMessage());
        }
        return lista;
    }

    public Reserva findById(Integer id) {
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
            System.err.println("Erro ao buscar reserva por ID: " + e.getMessage());
        }
        return null;
    }

    private Reserva mapearResultSet(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setIdReserva(rs.getInt("id_reserva"));
        r.setIdUtilizador(rs.getInt("id_utilizador"));
        r.setIdLivro(rs.getInt("id_livro"));
        r.setStatus(rs.getString("status"));
        
        Timestamp dataReservaTs = rs.getTimestamp("data_reserva");
        if (dataReservaTs != null) {
            r.setDataReserva(new java.util.Date(dataReservaTs.getTime()));
        }
        
        // Mapeamento dos campos de auditoria / timestamps automáticos da base de dados
        Timestamp createdAtTs = rs.getTimestamp("created_at");
        if (createdAtTs != null) {
            r.setCreatedAt(new java.util.Date(createdAtTs.getTime()));
        }
        
        Timestamp updatedAtTs = rs.getTimestamp("updated_at");
        if (updatedAtTs != null) {
            r.setUpdatedAt(new java.util.Date(updatedAtTs.getTime()));
        }
        
        return r;
    }
}
