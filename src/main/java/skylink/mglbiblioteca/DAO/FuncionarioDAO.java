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
public class FuncionarioDAO implements Serializable { // Sem anotações do CDI

    private static final long serialVersionUID = 1L;

    private static final String INSERT = 
            "INSERT INTO funcionario (nome, cargo, email, telefone, usuario, senha_hash, perfil, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE = 
            "UPDATE funcionario SET nome = ?, cargo = ?, email = ?, telefone = ?, usuario = ?, senha_hash = ?, perfil = ?, ativo = ? WHERE id_funcionario = ?";
    
    private static final String DELETE = 
            "DELETE FROM funcionario WHERE id_funcionario = ?";
    
    private static final String SELECT_ALL = 
            "SELECT * FROM funcionario ORDER BY nome";
    
    private static final String SELECT_BY_ID = 
            "SELECT * FROM funcionario WHERE id_funcionario = ?";

    private static final String SELECT_BY_NOME = 
            "SELECT * FROM funcionario WHERE nome LIKE ? ORDER BY nome";

    public boolean save(Funcionario f) {
        if (f == null) return false;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCargo());
            ps.setString(3, f.getEmail());
            ps.setString(4, f.getTelefone());
            ps.setString(5, f.getUsuario());
            ps.setString(6, f.getSenhaHash());
            ps.setString(7, f.getPerfil() != null ? f.getPerfil().name() : null);
            ps.setBoolean(8, f.getAtivo() != null ? f.getAtivo() : true);
            
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
            ps.setString(2, f.getCargo());
            ps.setString(3, f.getEmail());
            ps.setString(4, f.getTelefone());
            ps.setString(5, f.getUsuario());
            ps.setString(6, f.getSenhaHash());
            ps.setString(7, f.getPerfil() != null ? f.getPerfil().name() : null);
            ps.setBoolean(8, f.getAtivo() != null ? f.getAtivo() : true);
            ps.setInt(9, f.getIdFuncionario());
            
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

    public List<Funcionario> buscarPorNome(String nome) {
        List<Funcionario> lista = new ArrayList<>();
        if (nome == null) return lista;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_NOME)) {
            
            ps.setString(1, "%" + nome.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionários por nome: " + e.getMessage());
        }
        return lista;
    }

    private Funcionario mapearResultSet(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setIdFuncionario(rs.getInt("id_funcionario"));
        f.setNome(rs.getString("nome"));
        f.setCargo(rs.getString("cargo"));
        f.setEmail(rs.getString("email"));
        f.setTelefone(rs.getString("telefone"));
        f.setUsuario(rs.getString("usuario"));
        f.setSenhaHash(rs.getString("senha_hash"));
        f.setAtivo(rs.getBoolean("ativo"));
        
        String perfilStr = rs.getString("perfil");
        if (perfilStr != null) {
            try {
                f.setPerfil(PerfilFuncionario.valueOf(perfilStr));
            } catch (IllegalArgumentException e) {
                f.setPerfil(PerfilFuncionario.Atendente);
            }
        }

        f.setDataRegsito(rs.getTimestamp("data_registo")); 
        
        return f;
    }
}