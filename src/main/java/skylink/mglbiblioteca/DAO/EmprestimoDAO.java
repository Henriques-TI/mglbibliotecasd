package mglbibliotecasd.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import mglbibliotecasd.modelo.Emprestimo;
import skylink.mglbiblioteca.model.Livro;
import skylink.mglbiblioteca.bdutil.ConnectionDB;

/**
 * @Henriques
 */
public class EmprestimoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO emprestimos (id_livro, id_utilizador, data_retirada, data_devolucao_prevista, status) VALUES (?, ?, ?, ?, ?)";
    private static final String DECREMENTAR_ESTOQUE = "UPDATE livros SET quantidade_disponivel = quantidade_disponivel - 1 WHERE id_livro = ? AND quantidade_disponivel > 0";
    private static final String SELECT_ALL = "SELECT id_emprestimo, id_livro, id_utilizador, data_retirada, data_devolucao_prevista, status FROM emprestimos";
    private static final String SELECT_BY_USER = "SELECT id_emprestimo, id_livro, id_utilizador, data_retirada, data_devolucao_prevista, status FROM emprestimos WHERE id_utilizador = ?";

   
    public boolean registrarEmprestimo(Emprestimo emp) throws SQLException {
        Connection conn = ConnectionDB.getConnection();
        
        conn.setAutoCommit(false); 

        PreparedStatement psUpdateEstoque = conn.prepareStatement(DECREMENTAR_ESTOQUE);
        psUpdateEstoque.setInt(1, emp.getLivro().getIdLivro());
        int linhasAfetadas = psUpdateEstoque.executeUpdate();

        if (linhasAfetadas == 0) {
            conn.rollback();
            psUpdateEstoque.close();
            conn.close();
            return false; 
        }

        PreparedStatement psInsert = conn.prepareStatement(INSERT);
        psInsert.setInt(1, emp.getLivro().getIdLivro());
        psInsert.setInt(2, emp.getIdUtilizador());
        psInsert.setTimestamp(3, new Timestamp(emp.getDataRetirada().getTime()));
        psInsert.setDate(4, new Date(emp.getDataDevolucaoPrevista().getTime()));
        psInsert.setString(5, emp.getStatus());
        
        psInsert.executeUpdate();
        
        conn.commit(); 
        
        psInsert.close();
        psUpdateEstoque.close();
        conn.close();
        
        return true;
    }

    
    public List<Emprestimo> findAll() throws SQLException {
        List<Emprestimo> lista = new ArrayList<>();
        Connection conn = ConnectionDB.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(mapearResultSet(rs));
        }
        
        rs.close();
        ps.close();
        conn.close();
        return lista;
    }

    public List<Emprestimo> buscarPorUtilizador(Integer idUtilizador) throws SQLException {
        List<Emprestimo> lista = new ArrayList<>();
        Connection conn = ConnectionDB.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_USER);
        
        ps.setInt(1, idUtilizador);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            lista.add(mapearResultSet(rs));
        }
        
        rs.close();
        ps.close();
        conn.close();
        return lista;
    }

    private Emprestimo mapearResultSet(ResultSet rs) throws SQLException {
        Emprestimo emp = new Emprestimo();
        emp.setIdEmprestimo(rs.getInt("id_emprestimo"));
        emp.setIdUtilizador(rs.getInt("id_utilizador"));
        emp.setStatus(rs.getString("status"));
        
        Timestamp retirada = rs.getTimestamp("data_retirada");
        if (retirada != null) {
            emp.setDataRetirada(new java.util.Date(retirada.getTime()));
        }
        
        Date dev = rs.getDate("data_devolucao_prevista");
        if (dev != null) {
            emp.setDataDevolucaoPrevista(new java.util.Date(dev.getTime()));
        }

        Livro l = new Livro();
        l.setIdLivro(rs.getInt("id_livro"));
        emp.setLivro(l);
        
        return emp;
    }
}