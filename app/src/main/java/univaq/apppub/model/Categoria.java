package univaq.apppub.model;

import java.util.ArrayList;

/**
 * Created by Gioele on 09/12/2017.
 */

public class Categoria {

    private int id;
    private String nome;
    private String Descrizione;
    private ArrayList<Piatto> Piatti;
    private String img;

    public Categoria(int id,String nome, String descrizione, String img) {
        this.id = id;
        this.nome = nome;
        Descrizione = descrizione;
        this.img = img;
        this.Piatti = new ArrayList<>();
    }
    public Categoria(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public ArrayList<Piatto> getPiatti() {
        return Piatti;
    }

    public void setPiatti(ArrayList<Piatto> piatti) {
        Piatti = piatti;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void aggiungiPiatto(Piatto piatto){
        this.Piatti.add(piatto);
    }

}
