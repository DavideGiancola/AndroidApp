package univaq.apppub.model;

/**
 * Created by Gioele on 09/12/2017.
 */

public class Piatto {

    int id;
    String nome;
    String descrizione;
    String img;
    double prezzo;


    boolean aggiunte;

    public Piatto(int id, String nome, String descrizione, String img,double prezzo,boolean aggiunte) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.img = img;
        this.prezzo = prezzo;
        this.aggiunte = aggiunte;
    }


    public Piatto() {
    }

    public boolean isAggiunte() {
        return aggiunte;
    }

    public void setAggiunte(boolean aggiunte) {
        this.aggiunte = aggiunte;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
