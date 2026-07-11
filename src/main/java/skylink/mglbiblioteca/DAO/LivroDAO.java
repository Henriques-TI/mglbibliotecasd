package skylink.mglbiblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.Livro;
import skylink.mglbiblioteca.model.CategoriaLivro;
import skylink.mglbiblioteca.model.Prateleira;

/**
 *
 * @author Henriques
 */
public class LivroDAO {

    private static final String INSERT = "INSERT INTO livros (titulo, autor, isbn, editora, ano_publicacao, id_categoria_livro, id_prateleira, quantidade_total, quantidade_disponivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE livros SET titulo = ?, autor = ?, isbn = ?, editora = ?, ano_publicacao = ?, id_categoria_livro = ?, id_prateleira = ?, quantidade_total = ?, quantidade_disponivel = ? WHERE id_livro = ?";
    private static final String DELETE = "DELETE FROM livros WHERE id_livro = ?";

    private static final String BUSCA_POR_ID = "SELECT l.*, cl.descricao, p.posicao_prateleira, p.classificacao FROM livros l JOIN categoria_livro cl ON l.id_categoria_livro = cl.id_categoria_livro JOIN prateleiras p ON l.id_prateleira = p.id_prateleira WHERE l.id_livro = ?";
    private static final String LISTAR_TODOS = "SELECT l.*, cl.descricao, p.posicao_prateleira, p.classificacao FROM livros l JOIN categoria_livro cl ON l.id_categoria_livro = cl.id_categoria_livro JOIN prateleiras p ON l.id_prateleira = p.id_prateleira ORDER BY l.titulo";
    private static final String BUSCA_POR_TITULO = "SELECT l.*, cl.descricao, p.posicao_prateleira, p.classificacao FROM livros l JOIN categoria_livro cl ON l.id_categoria_livro = cl.id_categoria_livro JOIN prateleiras p ON l.id_prateleira = p.id_prateleira WHERE l.titulo LIKE ? ORDER BY l.titulo";

    public boolean save(Livro livro) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(INSERT);

            ajustarQuantidades(livro);

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getIsbn());
            ps.setString(4, livro.getEditora());
            ps.setInt(5, (livro.getAnoPublicacao() != null) ? livro.getAnoPublicacao() : 0);
            ps.setInt(6, (livro.getCategoriaLivro() != null) ? livro.getCategoriaLivro().getIdCategoriaLivro() : 0);
            ps.setInt(7, (livro.getPrateleira() != null) ? livro.getPrateleira().getIdPrateleira() : 0);
            ps.setInt(8, livro.getQuantidadeTotal());
            ps.setInt(9, livro.getQuantidadeDisponivel());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SEVERE: Erro ao inserir livro: " + e.getMessage());
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public boolean update(Livro livro) {
        if (livro == null || livro.getIdLivro() == null) {
            return false;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(UPDATE);

            ajustarQuantidades(livro);

            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            ps.setString(3, livro.getIsbn());
            ps.setString(4, livro.getEditora());

            ps.setInt(5, (livro.getAnoPublicacao() != null) ? livro.getAnoPublicacao() : 0);
            ps.setInt(6, (livro.getCategoriaLivro() != null) ? livro.getCategoriaLivro().getIdCategoriaLivro() : 0);
            ps.setInt(7, (livro.getPrateleira() != null) ? livro.getPrateleira().getIdPrateleira() : 0);
            ps.setInt(8, livro.getQuantidadeTotal());
            ps.setInt(9, livro.getQuantidadeDisponivel());
            ps.setInt(10, livro.getIdLivro());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SEVERE: Erro ao atualizar livro: " + e.getMessage());
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public boolean delete(Livro livro) {
        if (livro == null || livro.getIdLivro() == null) {
            return false;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(DELETE);
            ps.setInt(1, livro.getIdLivro());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SEVERE: Erro ao eliminar livro: " + e.getMessage());
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public List<Livro> findAll() {
        List<Livro> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(LISTAR_TODOS); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Livro l = new Livro();
                popularComDados(l, rs);
                lista.add(l);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao listar livros: " + ex.getMessage());
        }
        return lista;
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        List<Livro> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(BUSCA_POR_TITULO);
            ps.setString(1, "%" + titulo + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Livro l = new Livro();
                popularComDados(l, rs);
                lista.add(l);
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar livro por título: " + ex.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return lista;
    }

    private void popularComDados(Livro livro, ResultSet rs) throws SQLException {
        livro.setIdLivro(rs.getInt("id_livro"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setIsbn(rs.getString("isbn"));
        livro.setEditora(rs.getString("editora"));
        livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
        livro.setQuantidadeTotal(rs.getInt("quantidade_total"));
        livro.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
        livro.setDataRegisto(rs.getTimestamp("data_registotimestamp") != null ? rs.getTimestamp("data_registotimestamp") : rs.getTimestamp("data_registo"));

        CategoriaLivro categoria = new CategoriaLivro();
        categoria.setIdCategoriaLivro(rs.getInt("id_categoria_livro"));
        categoria.setDescricao(rs.getString("descricao"));
        livro.setCategoriaLivro(categoria);

        Prateleira prateleira = new Prateleira();
        prateleira.setIdPrateleira(rs.getInt("id_prateleira"));
        prateleira.setPosicaoPrateleira(rs.getString("posicao_prateleira")); 
        prateleira.setClassificacao(rs.getString("classificacao"));
        livro.setPrateleira(prateleira);
    }

  
    private void ajustarQuantidades(Livro livro) {
        int disponivel = (livro.getQuantidadeDisponivel() != null) ? livro.getQuantidadeDisponivel() : 0;
        int total = (livro.getQuantidadeTotal() != null) ? livro.getQuantidadeTotal() : 0;

        if (total < disponivel || total == 0) {
            total = disponivel;
        }

        livro.setQuantidadeDisponivel(disponivel);
        livro.setQuantidadeTotal(total);
    }
}