package skylink.mglbiblioteca.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.Leitura;

/**
 * @author Henriques
 */
public class LeituraDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = 
            "INSERT INTO leitura (id_utilizador, id_livro, id_funcionario, data_retirada, data_devolucao_prevista, data_devolucao_real, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE = 
            "UPDATE leitura SET id_utilizador = ?, id_livro = ?, id_funcionario = ?, data_retirada = ?, data_devolucao_prevista = ?, data_devolucao_real = ?, status = ? WHERE id_leitura = ?";
    
    private static final String DELETE = 
            "DELETE FROM leitura WHERE id_leitura = ?";
    
    private static final String SELECT_ALL = 
            "SELECT * FROM leitura ORDER BY data_retirada DESC";
    
    private static final String SELECT_BY_ID = 
            "SELECT * FROM leitura WHERE id_leitura = ?";

    public boolean save(Leitura l) {
        if (l == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setInt(1, l.getIdUtilizador());
            ps.setInt(2, l.getIdLivro());
            ps.setInt(3, l.getIdFuncionario());
            
            ps.setTimestamp(4, l.getDataRetirada() != null ? new java.sql.Timestamp(l.getDataRetirada().getTime()) : null);
            ps.setTimestamp(5, l.getDataDevolucaoPrevista() != null ? new java.sql.Timestamp(l.getDataDevolucaoPrevista().getTime()) : null);
            
            if (l.getDataDevolucaoReal() != null) {
                ps.setTimestamp(6, new java.sql.Timestamp(l.getDataDevolucaoReal().getTime()));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }
            
            ps.setString(7, l.getStatus());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar leitura: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Leitura l) {
        if (l == null || l.getIdLeitura() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setInt(1, l.getIdUtilizador());
            ps.setInt(2, l.getIdLivro());
            ps.setInt(3, l.getIdFuncionario());
            
            ps.setTimestamp(4, l.getDataRetirada() != null ? new java.sql.Timestamp(l.getDataRetirada().getTime()) : null);
            ps.setTimestamp(5, l.getDataDevolucaoPrevista() != null ? new java.sql.Timestamp(l.getDataDevolucaoPrevista().getTime()) : null);
            
            if (l.getDataDevolucaoReal() != null) {
                ps.setTimestamp(6, new java.sql.Timestamp(l.getDataDevolucaoReal().getTime()));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }
            
            ps.setString(7, l.getStatus());
            ps.setInt(8, l.getIdLeitura());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar leitura: " + e.getMessage());
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
            System.err.println("Erro ao eliminar leitura: " + e.getMessage());
            return false;
        }
    }

    public List<Leitura> findAll() {
        List<Leitura> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar leituras: " + e.getMessage());
        }
        return lista;
    }

    public Leitura findById(Integer id) {
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
            System.err.println("Erro ao buscar leitura por ID: " + e.getMessage());
        }
        return null;
    }

    private Leitura mapearResultSet(ResultSet rs) throws SQLException {
        Leitura l = new Leitura();
        l.setIdLeitura(rs.getInt("id_leitura"));
        l.setIdUtilizador(rs.getInt("id_utilizador"));
        l.setIdLivro(rs.getInt("id_livro"));
        l.setIdFuncionario(rs.getInt("id_funcionario"));
        l.setDataRetirada(rs.getTimestamp("data_retirada"));
        l.setDataDevolucaoPrevista(rs.getTimestamp("data_devolucao_prevista"));
        l.setDataDevolucaoReal(rs.getTimestamp("data_devolucao_real"));
        l.setStatus(rs.getString("status"));
        
        l.setDataRegisto(rs.getTimestamp("data_registo"));
        
        return l;
    }
}