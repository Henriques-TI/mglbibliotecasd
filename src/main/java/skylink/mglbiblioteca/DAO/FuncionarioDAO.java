
package skylink.mglbiblioteca.DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.Funcionario;

/**
 * @Henriques
 */
public class FuncionarioDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO funcionarios (nome, cargo, email, telefone) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE funcionarios SET nome = ?, cargo = ?, email = ?, telefone = ? WHERE id_funcionario = ?";
    private static final String DELETE = "DELETE FROM funcionarios WHERE id_funcionario = ?";
    private static final String SELECT_ALL = "SELECT * FROM funcionarios";
    private static final String SELECT_BY_ID = "SELECT * FROM funcionarios WHERE id_funcionario = ?";
    private static final String SELECT_BY_NOME = "SELECT * FROM funcionarios WHERE nome LIKE ?";

    public boolean save(Funcionario f) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCargo());
            ps.setString(3, f.getEmail());
            ps.setString(4, f.getTelefone());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Funcionario f) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCargo());
            ps.setString(3, f.getEmail());
            ps.setString(4, f.getTelefone());
            ps.setInt(5, f.getIdFuncionario());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Funcionario f) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            
            ps.setInt(1, f.getIdFuncionario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Funcionario> findAll() {
        List<Funcionario> lista = new ArrayList<>();
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

    public Funcionario findById(Integer id) {
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

    public List<Funcionario> buscarPorNome(String nome) {
        List<Funcionario> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_NOME)) {

            ps.setString(1, "%" + nome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Funcionario mapearResultSet(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getInt("id_funcionario"));
        f.setNome(rs.getString("nome"));
        f.setCargo(rs.getString("cargo"));
        f.setEmail(rs.getString("email"));
        f.setTelefone(rs.getString("telefone"));
        return f;
    }
}