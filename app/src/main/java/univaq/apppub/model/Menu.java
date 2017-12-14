package univaq.apppub.model;

/**
 * Created by Gioele on 13/12/2017.
 */

public class Menu {

    private int id;
    private int version;


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
}
