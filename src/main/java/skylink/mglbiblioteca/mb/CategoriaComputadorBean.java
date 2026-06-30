package skylink.mglbiblioteca.mb;
 
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import skylink.mglbiblioteca.DAO.CategoriaComputadorDAO;
import skylink.mglbiblioteca.model.CategoriaComputador;

/**
 * @author Henriques
 */
@Named(value = "categoriaComputadorBean")
@ViewScoped
public class CategoriaComputadorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final CategoriaComputadorDAO categoriaDAO;
    private List<CategoriaComputador> listaCategorias;

    public CategoriaComputadorBean() {
        this.categoriaDAO = new CategoriaComputadorDAO();
        this.listaCategorias = new ArrayList<>();
    }

   
    public List<CategoriaComputador> getListaCategorias() {
        if (listaCategorias == null || listaCategorias.isEmpty()) {
            listaCategorias = categoriaDAO.listarTudo();
        }
        return listaCategorias;
    }

    public void setListaCategorias(List<CategoriaComputador> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
}
