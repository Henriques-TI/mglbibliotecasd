package skylink.mglbiblioteca.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.bdutil.ConnectionDB;
import skylink.mglbiblioteca.model.Funcionario;
import skylink.mglbiblioteca.model.PerfilFuncionario;

/**
 * @Henriques
 */
public class PerfilFuncionarioDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INSERT = "INSERT INTO funcionarios (nome, email, senha, perfil, ativo) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE funcionarios SET nome = ?, email = ?, senha = ?, perfil = ?, ativo = ? WHERE id_funcionario = ?";
    private static final String DELETE = "DELETE FROM funcionarios WHERE id_funcionario = ?";
    private static final String SELECT_ALL = "SELECT * FROM funcionarios ORDER BY nome ASC";
    private static final String SELECT_BY_ID = "SELECT * FROM funcionarios WHERE id_funcionario = ?";

    public boolean save(Funcionario f) {
        if (f == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, f.getNome());
            ps.setString(2, f.getEmail());
            ps.setString(3, f.getSenhaHash());
            
            ps.setString(4, f.getPerfil() != null ? f.getPerfil().name() : null);
            ps.setBoolean(5, f.getAtivo());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar funcionário: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Funcionario f) {
        if (f == null || f.getIdFuncionario() == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            
            ps.setString(1, f.getNome());
            ps.setString(2, f.getEmail());
            ps.setString(3, f.getSenhaHash());
            ps.setString(4, f.getPerfil() != null ? f.getPerfil().name() : null);
            ps.setBoolean(5, f.getAtivo());
            ps.setInt(6, f.getIdFuncionario());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
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
            System.err.println("Erro ao eliminar funcionário: " + e.getMessage());
            return false;
        }
    }

    public List<Funcionario> findAll() {
        List<Funcionario> lista = new ArrayList<>();
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    public Funcionario findById(Integer id) {
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
            System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
        }
        return null;
    }

    private Funcionario mapearResultSet(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getInt("id_funcionario"));
        f.setNome(rs.getString("nome"));
        f.setEmail(rs.getString("email"));
        f.setSenhaHash(rs.getString("senha"));
        f.setAtivo(rs.getBoolean("ativo"));
        
        String perfilStr = rs.getString("perfil");
        if (perfilStr != null) {
            try {
                f.setPerfil(PerfilFuncionario.valueOf(perfilStr));
            } catch (IllegalArgumentException e) {
                f.setPerfil(buscarPorDescricao(perfilStr));
            }
        }
        
        return f;
    }

    private PerfilFuncionario buscarPorDescricao(String descricao) {
        for (PerfilFuncionario perfil : PerfilFuncionario.values()) {
            if (perfil.getDescricao().equalsIgnoreCase(descricao)) {
                return perfil;
            }
        }
        System.err.println("Aviso: Perfil desconhecido encontrado no banco: " + descricao);
        return null;
    }
}
