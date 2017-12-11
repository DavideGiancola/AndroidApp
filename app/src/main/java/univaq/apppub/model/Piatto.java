package univaq.apppub.model;

/**
 * Created by Gioele on 09/12/2017.
 */

public class Piatto {

    int id;
    String nome;
    String descrizione;
    int img;

    public Piatto(int id, String nome, String descrizione, int img) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.img = img;
    }

    public Piatto() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
