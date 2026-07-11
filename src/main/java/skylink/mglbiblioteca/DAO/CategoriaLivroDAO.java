package skylink.mglbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.CategoriaLivro;

public class CategoriaLivroDAO {

    public boolean save(CategoriaLivro categoria) {
        String sql = "INSERT INTO categoria_livro (descricao) VALUES (?)";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getDescricao());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean update(CategoriaLivro categoria) {
        String sql = "UPDATE categoria_livro SET descricao = ? WHERE id_categoria_livro = ?";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getDescricao());
            stmt.setInt(2, categoria.getIdCategoriaLivro());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(CategoriaLivro categoria) {
        String sql = "DELETE FROM categoria_livro WHERE id_categoria_livro = ?";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoria.getIdCategoriaLivro());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir categoria: " + e.getMessage());
            return false;
        }
    }

    public List<CategoriaLivro> findAll() throws SQLException {
        List<CategoriaLivro> lista = new ArrayList<>();
        String sql = "SELECT id_categoria_livro, descricao, data_registo FROM categoria_livro ORDER BY descricao";

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CategoriaLivro categoria = new CategoriaLivro();
                categoria.setIdCategoriaLivro(rs.getInt("id_categoria_livro"));
                categoria.setDescricao(rs.getString("descricao"));
                categoria.setDataRegisto(rs.getTimestamp("data_registo"));

                lista.add(categoria);
            }
        }
        return lista;
    }
}
