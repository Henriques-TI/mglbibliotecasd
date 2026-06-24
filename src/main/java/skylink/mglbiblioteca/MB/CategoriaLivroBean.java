package skylink.mglbiblioteca.MB;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import skylink.mglbiblioteca.DAO.CategoriaLivroDAO;
import skylink.mglbiblioteca.MODEL0.CategoriaLivro;

/**
 * @Henriques
 */
@Named(value = "categoriaLivroBean")
@ViewScoped
public class CategoriaLivroBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    private final CategoriaLivroDAO categoriaDAO;
    private List<CategoriaLivro> listaCategorias;

    public CategoriaLivroBean(CategoriaLivroDAO categoriaDAO, List<CategoriaLivro> listCategorias) {
        this.categoriaDAO = new CategoriaLivroDAO();
        this.listaCategorias = new ArrayList<>();
    }
public List<CategoriaLivro>  getListaCategorias() {
if (listaCategorias == null || listaCategorias.isEmpty()){
listaCategorias = categoriaDAO.listarTudo();
}
return listaCategorias;
}
 public void setListaCAtegorias(List<CategoriaLivro> listaCategorias) {
 this.listaCategorias = listaCategorias;
 }
      
}
