package skylink.mglbiblioteca.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.model.CategoriaComputador;
import skylink.mglbiblioteca.bdutil.ConnectionDB;


/**
 *
 * @Henriques
 */
public class CategoriaComputadorDAO {
    
    
    public List<CategoriaComputador> listarTudo() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CategoriaComputador> lista = new ArrayList<>();

        try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement("SELECT id_categoria, descricao_categoria FROM categoria_computador ORDER BY descricao_categoria");
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoriaComputador cat = new CategoriaComputador();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setDescricaoCategoria(rs.getString("descricao_categoria"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias de computadores: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return lista;
    }



}
