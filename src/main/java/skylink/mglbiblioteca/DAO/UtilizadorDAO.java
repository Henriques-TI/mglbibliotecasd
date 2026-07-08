package skylink.mglbiblioteca.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.Utilizador;

public class UtilizadorDAO {

    public boolean inserir(Utilizador utilizador) {
        String sql = "INSERT INTO utilizadores (nome, bi, email, telefone) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, utilizador.getNome());
            ps.setString(2, utilizador.getBi());
            ps.setString(3, utilizador.getEmail());
            ps.setString(4, utilizador.getTelefone());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Utilizador> listarTodos() {
        List<Utilizador> lista = new ArrayList<>();
        String sql = "SELECT id_utilizador, nome, bi, email, telefone, data_cadastro FROM utilizadores ORDER BY nome";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Utilizador u = new Utilizador();
                u.setIdUtilizador(rs.getInt("id_utilizador"));
                u.setNome(rs.getString("nome"));
                u.setBi(rs.getString("bi"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                
                if (rs.getTimestamp("data_cadastro") != null) {
                    u.setDataCadastro(new java.util.Date(rs.getTimestamp("data_cadastro").getTime()));
                }
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean existeId(int id) {
        String sql = "SELECT 1 FROM utilizadores WHERE id_utilizador = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
