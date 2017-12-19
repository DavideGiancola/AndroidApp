package univaq.apppub.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davide on 18/12/2017.
 */

public class Schedario {

    private int id;
    private int version;

    private List<Evento> eventi = new ArrayList<>();


    public List<Evento> getEventi() {
        return eventi;
    }

    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }

    public Schedario(int id, int version) {
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


    public void aggiungiEvento(Evento evento){
        eventi.add(evento);
    }
}
