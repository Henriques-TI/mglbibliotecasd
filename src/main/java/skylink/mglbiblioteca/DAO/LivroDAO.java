package skylink.mglbiblioteca.DAO;

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
            = "SELECT l.*, c.descricao AS descricao_categoria, p.posicao_prateleira AS descricao_prateleira "
            + "FROM livros l "
            + "LEFT JOIN categoria_livro c ON l.id_categoria_livro = c.id_categoria_livro "
            + "LEFT JOIN prateleira p ON l.id_prateleira = p.id_prateleira";

    public boolean save(Livro livro) {
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getEditora());
            if (livro.getAnoPublicacao() != null) ps.setInt(4, livro.getAnoPublicacao()); else ps.setNull(4, java.sql.Types.INTEGER);
            if (livro.getCategoriaLivro() != null) ps.setInt(5, livro.getCategoriaLivro().getIdCategoriaLivro()); else ps.setNull(5, java.sql.Types.INTEGER);
            if (livro.getPrateleira() != null) ps.setInt(6, livro.getPrateleira().getIdPrateleira()); else ps.setNull(6, java.sql.Types.INTEGER);
            ps.setInt(7, livro.getQuantidadeDisponivel());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Livro livro) {
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getEditora());
            if (livro.getAnoPublicacao() != null) ps.setInt(4, livro.getAnoPublicacao()); else ps.setNull(4, java.sql.Types.INTEGER);
            if (livro.getCategoriaLivro() != null) ps.setInt(5, livro.getCategoriaLivro().getIdCategoriaLivro()); else ps.setNull(5, java.sql.Types.INTEGER);
            if (livro.getPrateleira() != null) ps.setInt(6, livro.getPrateleira().getIdPrateleira()); else ps.setNull(6, java.sql.Types.INTEGER);
            ps.setInt(7, livro.getQuantidadeDisponivel());
            ps.setInt(8, livro.getIdLivro());
            
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

    public List<Livro> findAll() {
        List<Livro> lista = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY l.titulo";
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {
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
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
            cat.setDescricao(rs.getString("descricao_categoria")); 
            l.setCategoriaLivro(cat);
        }

        int idPrat = rs.getInt("id_prateleira");
        if (!rs.wasNull()) {
            Prateleira prat = new Prateleira();
            prat.setIdPrateleira(idPrat);
            prat.setPosicaoPrateleira(rs.getString("descricao_prateleira"));
            l.setPrateleira(prat);
        }
        
        l.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
        return l;
    }
}