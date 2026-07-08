package skylink.mglbiblioteca.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.model.Leitura;
import skylink.mglbiblioteca.bdutil.ConnectionDB;

public class LeituraDAO {

    private static final String INSERT = "INSERT INTO leitura (id_utilizador, id_livro, data_devolucao_prevista, status) VALUES (?, ?, ?, 'Lendo')";
    private static final String UPDATE = "UPDATE leitura SET id_utilizador = ?, id_livro = ?, data_devolucao_prevista = ? WHERE id_leitura = ?";
    private static final String DELETE = "DELETE FROM leitura WHERE id_leitura = ?";
    private static final String BUSCAR_POR_CODIGO = "SELECT l.*, u.nome AS nome_utilizador, li.titulo AS titulo_livro FROM leitura l INNER JOIN utilizadores u ON l.id_utilizador = u.id_utilizador INNER JOIN livros li ON l.id_livro = li.id_livro WHERE l.id_leitura = ?";
    private static final String LISTAR_TUDO = "SELECT l.*, u.nome AS nome_utilizador, li.titulo AS titulo_livro FROM leitura l INNER JOIN utilizadores u ON l.id_utilizador = u.id_utilizador INNER JOIN livros li ON l.id_livro = li.id_livro ORDER BY l.data_retirada DESC";
    private static final String LISTAR_POR_STATUS = "SELECT l.*, u.nome AS nome_utilizador, li.titulo AS titulo_livro FROM leitura l INNER JOIN utilizadores u ON l.id_utilizador = u.id_utilizador INNER JOIN livros li ON l.id_livro = li.id_livro WHERE l.status = ? ORDER BY l.data_retirada DESC";
    
    private static final String ATUALIZAR_STATUS = "UPDATE leitura SET data_devolucao_real = CURRENT_TIMESTAMP, status = ? WHERE id_leitura = ?";

    public boolean save(Leitura leitura) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(INSERT);
            ps.setInt(1, leitura.getIdUtilizador());
            ps.setInt(2, leitura.getIdLivro());
            ps.setDate(3, new java.sql.Date(leitura.getDataDevolucaoPrevista().getTime()));
            return ps.executeUpdate() > 0;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public boolean update(Leitura leitura) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(UPDATE);
            ps.setInt(1, leitura.getIdUtilizador());
            ps.setInt(2, leitura.getIdLivro());
            ps.setDate(3, new java.sql.Date(leitura.getDataDevolucaoPrevista().getTime()));
            ps.setInt(4, leitura.getIdLeitura());
            return ps.executeUpdate() > 0;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public boolean delete(int idLeitura) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(DELETE);
            ps.setInt(1, idLeitura);
            return ps.executeUpdate() > 0;
        } finally {
            ConnectionDB.closeConnection(conn, ps);
        }
    }

    public List<Leitura> listarTudo() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Leitura> lista = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(LISTAR_TUDO);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return lista;
    }

    public List<Leitura> listarPorStatus(String statusFiltro) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Leitura> lista = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(LISTAR_POR_STATUS);
            ps.setString(1, statusFiltro);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return lista;
    }

    public boolean updateStatus(String novoStatus, Integer idLeitura) throws SQLException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            System.out.println("Alterando status da leitura para >>>>>>>>>> " + novoStatus);
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false);
            
            ps = conn.prepareStatement(ATUALIZAR_STATUS);
            ps.setString(1, novoStatus);
            ps.setInt(2, idLeitura);
            int retorno = ps.executeUpdate();
            conn.commit();
            
            if (retorno > 0) {
                System.out.println("LeituraDAO:updateStatus Dados alterados com sucesso: " + ps.getUpdateCount());
                ConnectionDB.closeConnection(conn, ps);
                return true;
            }
            ConnectionDB.closeConnection(conn, ps);
            return false;
        } catch (Throwable throwable) {
            ConnectionDB.closeConnection(conn, ps);
            throw throwable;
        }
    }

    public Leitura buscarPorCodigo(int idLeitura) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Leitura leitura = null;
        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement(BUSCAR_POR_CODIGO);
            ps.setInt(1, idLeitura);
            rs = ps.executeQuery();
            if (rs.next()) {
                leitura = mapearResultSet(rs);
            }
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return leitura;
    }

    private Leitura mapearResultSet(ResultSet rs) throws SQLException {
    Leitura leitura = new Leitura();
    
    leitura.setIdLeitura(rs.getInt("id_leitura"));
    leitura.setIdUtilizador(rs.getInt("id_utilizador"));
    leitura.setIdLivro(rs.getInt("id_livro"));
    leitura.setDataRetirada(rs.getTimestamp("data_retirada"));
    leitura.setDataDevolucaoPrevista(rs.getDate("data_devolucao_prevista"));
    leitura.setDataDevolucaoReal(rs.getTimestamp("data_devolucao_real"));
    leitura.setStatus(rs.getString("status"));
    
    return leitura; 
}

    public String buscarStatusAtual(int idLeitura) throws SQLException {
        String sql = "SELECT status FROM leitura WHERE id_leitura = ?";
        
        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLeitura);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status"); 
                }
            }
        }
        return "Desconhecido"; 
    }
}