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
import skylink.mglbiblioteca.model.Prateleira;

/**
 * @author Henriques
 */
public class PrateleiraDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO prateleiras (posicao_prateleira, classificacao) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE prateleiras SET posicao_prateleira = ?, classificacao = ? WHERE id_prateleira = ?";
    private static final String DELETE = "DELETE FROM prateleiras WHERE id_prateleira = ?";
    private static final String SELECT_ALL = "SELECT * FROM prateleiras ORDER BY posicao_prateleira ASC";
    private static final String SELECT_BY_ID = "SELECT * FROM prateleiras WHERE id_prateleira = ?";
    private static final String SELECT_BY_POSICAO = "SELECT * FROM prateleiras WHERE posicao_prateleira LIKE ? ORDER BY posicao_prateleira ASC";

    public boolean save(Prateleira p) {
        if (p == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, p.getPosicaoPrateleira());
            ps.setString(2, p.getClassificacao());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar prateleira: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Prateleira p) {
        if (p == null || p.getIdPrateleira() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setString(1, p.getPosicaoPrateleira());
            ps.setString(2, p.getClassificacao());
            ps.setInt(3, p.getIdPrateleira());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar prateleira: " + e.getMessage());
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
            System.err.println("Erro ao eliminar prateleira: " + e.getMessage());
            return false;
        }
    }

    public List<Prateleira> findAll() {
        List<Prateleira> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar prateleiras: " + e.getMessage());
        }
        return lista;
    }

    public Prateleira findById(Integer id) {
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
            System.err.println("Erro ao buscar prateleira por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Método adicionado para corrigir o vínculo com o PrateleiraBean.
     * Permite pesquisar prateleiras por correspondência parcial de texto (ex: "A" traz "A1", "A2", etc).
     */
    public List<Prateleira> buscarPorPosicao(String posicao) {
        List<Prateleira> lista = new ArrayList<>();
        if (posicao == null || posicao.trim().isEmpty()) return lista;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_POSICAO)) {

            // Define o parâmetro com coringas % para pesquisa com o operador LIKE
            ps.setString(1, "%" + posicao.trim() + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar prateleiras por posição: " + e.getMessage());
        }
        return lista;
    }

    private Prateleira mapearResultSet(ResultSet rs) throws SQLException {
        Prateleira p = new Prateleira();
        p.setIdPrateleira(rs.getInt("id_prateleira"));
        p.setPosicaoPrateleira(rs.getString("posicao_prateleira"));
        p.setClassificacao(rs.getString("classificacao"));
        
        Timestamp timestamp = rs.getTimestamp("data_registo");
        if (timestamp != null) {
            p.setDataRegisto(new java.util.Date(timestamp.getTime()));
        }
        
        return p;
    }
}