package univaq.apppub.model;

/**
 * Created by Gioele on 09/12/2017.
 */

public class Piatto {

    int id;
    String nome;
    String descrizione;
    String img;
    String prezzo;



    boolean aggiunte;

    public Piatto(int id, String nome, String descrizione, String img,String prezzo,boolean aggiunte,int order) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.img = img;
        this.prezzo = prezzo;
        this.aggiunte = aggiunte;
        this.order = order;
    }

    public Piatto() {
    }

    public boolean isAggiunte() {
        return aggiunte;
    }

    public void setAggiunte(boolean aggiunte) {
        this.aggiunte = aggiunte;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
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


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    int order;
}
