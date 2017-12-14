package univaq.apppub.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gioele on 13/12/2017.
 */

public class Menu {

    private int id;
    private int version;

    private List<Categoria> categorie = new ArrayList<>();


    public List<Categoria> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<Categoria> categorie) {
        this.categorie = categorie;
    }

    public Menu(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    public void aggiungiCategoria(Categoria categoria){
        categorie.add(categoria);
    }


}
