package skylink.mglbiblioteca.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import skylink.mglarmazem.bdutil.ConnectionDB;
import skylink.mglbiblioteca.MODEL0.CategoriaLivro;

/**
 * @Henriques
 */
public class CategoriaLivroDAO {
    
    
    public List<CategoriaLivro> listarTudo(){
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<CategoriaLivro> lista = new ArrayList<>();
    
    try {
            conn = ConnectionDB.getConnection();
            ps = conn.prepareStatement("SELECT id_categoria, descricao_categoria FROM categoria_computador ORDER BY descricao_categoria");
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoriaLivro cat = new CategoriaLivro();
                cat.setIdCategoriaLivro(rs.getInt("id_categoria_livro"));
                cat.setDescricaoCategoriaLivro(rs.getString("descricao"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias de livros: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, ps, rs);
        }
        return lista;
    }



}

    
    

