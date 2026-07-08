package skylink.mglbiblioteca.DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.model.CategoriaLivro;
import skylink.mglbiblioteca.bdutil.ConnectionDB;

/**
 * @Henriques
 */
public class CategoriaLivroDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO categoria_livro (descricao) VALUES (?)";
    private static final String UPDATE = "UPDATE categoria_livro SET descricao = ? WHERE id_categoria_livro = ?";
    private static final String DELETE = "DELETE FROM categoria_livro WHERE id_categoria_livro = ?";
    private static final String SELECT_ALL = "SELECT * FROM categoria_livro";
    private static final String SELECT_BY_ID = "SELECT * FROM categoria_livro WHERE id_categoria_livro = ?";
    private static final String SELECT_BY_DESCRICAO = "SELECT * FROM categoria_livro WHERE descricao LIKE ?";

    public boolean save(CategoriaLivro c) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, c.getDescricao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(CategoriaLivro c) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setString(1, c.getDescricao());
            ps.setInt(2, c.getIdCategoriaLivro());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer id) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CategoriaLivro> findAll() {
        List<CategoriaLivro> lista = new ArrayList<>();
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

    public CategoriaLivro findById(Integer id) {
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

    public List<CategoriaLivro> buscarPorDescricao(String descricao) {
        List<CategoriaLivro> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_DESCRICAO)) {

            ps.setString(1, "%" + descricao + "%");
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

    private CategoriaLivro mapearResultSet(ResultSet rs) throws SQLException {
        CategoriaLivro c = new CategoriaLivro();
        c.setIdCategoriaLivro(rs.getInt("id_categoria_livro"));
        c.setDescricao(rs.getString("descricao")); 
        return c;
    }
}