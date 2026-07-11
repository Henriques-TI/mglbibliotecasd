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
import skylink.mglbiblioteca.model.Utilizador;

/**
 * @author Henriques
 */
public class UtilizadorDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO utilizadores (nome, bi, email, telefone, data_cadastro) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE utilizadores SET nome = ?, bi = ?, email = ?, telefone = ?, data_cadastro = ? WHERE id_utilizador = ?";
    private static final String DELETE = "DELETE FROM utilizadores WHERE id_utilizador = ?";
    private static final String SELECT_ALL = "SELECT * FROM utilizadores ORDER BY nome ASC";
    private static final String SELECT_BY_ID = "SELECT * FROM utilizadores WHERE id_utilizador = ?";

    public boolean save(Utilizador u) {
        if (u == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, u.getNome());
            ps.setString(2, u.getBi());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefone());
            
            // Se dataCadastro estiver nula, assume a data/hora atual do sistema
            ps.setTimestamp(5, u.getDataCadastro() != null ? new Timestamp(u.getDataCadastro().getTime()) : new Timestamp(System.currentTimeMillis()));
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar utilizador: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Utilizador u) {
        if (u == null || u.getIdUtilizador() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setString(1, u.getNome());
            ps.setString(2, u.getBi());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefone());
            ps.setTimestamp(5, u.getDataCadastro() != null ? new Timestamp(u.getDataCadastro().getTime()) : null);
            ps.setInt(6, u.getIdUtilizador());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar utilizador: " + e.getMessage());
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
            System.err.println("Erro ao eliminar utilizador: " + e.getMessage());
            return false;
        }
    }

    public List<Utilizador> findAll() {
        List<Utilizador> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar utilizadores: " + e.getMessage());
        }
        return lista;
    }

    public Utilizador findById(Integer id) {
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
            System.err.println("Erro ao buscar utilizador por ID: " + e.getMessage());
        }
        return null;
    }

    private Utilizador mapearResultSet(ResultSet rs) throws SQLException {
        Utilizador u = new Utilizador();
        u.setIdUtilizador(rs.getInt("id_utilizador"));
        u.setNome(rs.getString("nome"));
        u.setBi(rs.getString("bi"));
        u.setEmail(rs.getString("email"));
        u.setTelefone(rs.getString("telefone"));
        
        Timestamp dataCadastroTs = rs.getTimestamp("data_cadastro");
        if (dataCadastroTs != null) {
            u.setDataCadastro(new java.util.Date(dataCadastroTs.getTime()));
        }
        
        
        
        return u;
    }
}