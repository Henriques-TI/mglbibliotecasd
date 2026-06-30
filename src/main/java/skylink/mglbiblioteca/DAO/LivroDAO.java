package mglbibliotecasd.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.model.Prateleira;
import skylink.mglbiblioteca.model.Livro;
import skylink.mglbiblioteca.model.CategoriaLivro;
import skylink.mglbiblioteca.bdutil.ConnectionDB;

/**
 * @Henriques
 */
public class LivroDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO livros (titulo, autor, editora, ano_publicacao, id_categoria_livro, id_prateleira, quantidade_disponivel) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE livros SET titulo = ?, autor = ?, editora = ?, ano_publicacao = ?, id_categoria_livro = ?, id_prateleira = ?, quantidade_disponivel = ? WHERE id_livro = ?";
    private static final String DELETE = "DELETE FROM livros WHERE id_livro = ?";

    private static final String SELECT_BASE
            = "SELECT l.*, c.descricao_categorias_livros, p.posicao_prateleira AS descricao_prateleira "
            + "FROM livros l "
            + "LEFT JOIN categorias_livros c ON l.id_categoria_livro = c.id_categoria_livro "
            + "LEFT JOIN prateleira p ON l.id_prateleira = p.id_prateleira";

    public boolean save(Livro livro) {
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT)) {

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getEditora());
            ps.setObject(4, livro.getAnoPublicacao());
            ps.setObject(5, (livro.getCategoriaLivro() != null) ? livro.getCategoriaLivro().getIdCategoriaLivro() : null);
            ps.setObject(6, (livro.getPrateleira() != null) ? livro.getPrateleira().getIdPrateleira() : null);
            ps.setInt(7, livro.getQuantidadeDisponivel());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Livro livro) {
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE)) {

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getEditora());
            ps.setObject(4, livro.getAnoPublicacao());
            ps.setObject(5, (livro.getCategoriaLivro() != null) ? livro.getCategoriaLivro().getIdCategoriaLivro() : null);
            ps.setObject(6, (livro.getPrateleira() != null) ? livro.getPrateleira().getIdPrateleira() : null);
            ps.setInt(7, livro.getQuantidadeDisponivel());
            ps.setInt(8, livro.getIdLivro());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Livro livro) {
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE)) {

            ps.setInt(1, livro.getIdLivro());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Livro> findAll() {
        List<Livro> lista = new ArrayList<>();
        String sql = SELECT_BASE;
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Livro findById(Integer id) {
        String sql = SELECT_BASE + " WHERE l.id_livro = ?";
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

    public List<Livro> buscarPorTitulo(String titulo) {
        return buscarPorTituloEAutor(titulo, "");
    }

    public List<Livro> buscarPorTituloEAutor(String titulo, String autor) {
        List<Livro> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(SELECT_BASE);
        sql.append(" WHERE 1=1 ");

        if (titulo != null && !titulo.isEmpty()) {
            sql.append(" AND l.titulo LIKE ? ");
        }
        if (autor != null && !autor.isEmpty()) {
            sql.append(" AND l.autor LIKE ? ");
        }

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (titulo != null && !titulo.isEmpty()) {
                ps.setString(index++, "%" + titulo + "%");
            }
            if (autor != null && !autor.isEmpty()) {
                ps.setString(index++, "%" + autor + "%");
            }

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

    private Livro mapearResultSet(ResultSet rs) throws SQLException {
        Livro l = new Livro();
        l.setIdLivro(rs.getInt("id_livro"));
        l.setTitulo(rs.getString("titulo"));
        l.setAutor(rs.getString("autor"));
        l.setEditora(rs.getString("editora"));

        int ano = rs.getInt("ano_publicacao");
        l.setAnoPublicacao(rs.wasNull() ? null : ano);

        int idCat = rs.getInt("id_categoria_livro");
        if (!rs.wasNull()) {
            CategoriaLivro cat = new CategoriaLivro();
            cat.setIdCategoriaLivro(idCat);
            cat.setDescricaoCategoriaLivro(rs.getString("descricao_categorias_livros"));
            l.setCategoriaLivro(cat);
        }

        int idPrat = rs.getInt("id_prateleira");
        if (!rs.wasNull()) {
            Prateleira prat = new Prateleira();
            prat.setIdPrateleira(idPrat);
            prat.setClassificacao(rs.getString("descricao_prateleira"));
            l.setPrateleira(prat);
        }

        l.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
        return l;
    }
}
