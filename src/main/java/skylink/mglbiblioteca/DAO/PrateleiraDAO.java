package mglbiblioteca.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.model.Prateleira;
import skylink.mglbiblioteca.bdutil.ConnectionDB;

/**
 * @Henriques
 */
public class PrateleiraDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO prateleira (posicao_prateleira, classificacao) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE prateleira SET posicao_prateleira = ?, classificacao = ? WHERE id_prateleira = ?";
    private static final String DELETE = "DELETE FROM prateleira WHERE id_prateleira = ?";
    private static final String SELECT_ALL = "SELECT * FROM prateleira";
    private static final String SELECT_BY_ID = "SELECT * FROM prateleira WHERE id_prateleira = ?";
    private static final String SELECT_BY_POSICAO = "SELECT * FROM prateleira WHERE posicao_prateleira LIKE ?";

    public boolean save(Prateleira p) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, p.getPosicaoPrateleira());
            ps.setString(2, p.getClassificacao());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Prateleira p) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setString(1, p.getPosicaoPrateleira());
            ps.setString(2, p.getClassificacao());
            ps.setInt(3, p.getIdPrateleira());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Prateleira p) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            
            ps.setInt(1, p.getIdPrateleira());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return lista;
    }

    public Prateleira findById(Integer id) {
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

    public List<Prateleira> buscarPorPosicao(String posicao) {
        List<Prateleira> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_POSICAO)) {

            ps.setString(1, "%" + posicao + "%");
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

    private Prateleira mapearResultSet(ResultSet rs) throws SQLException {
        Prateleira p = new Prateleira();
        p.setIdPrateleira(rs.getInt("id_prateleira"));
        p.setPosicaoPrateleira(rs.getString("posicao_prateleira"));
        p.setClassificacao(rs.getString("classificacao"));
        return p;
    }
}