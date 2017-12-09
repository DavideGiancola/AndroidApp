package univaq.apppub.model;

import java.util.ArrayList;

/**
 * Created by Gioele on 09/12/2017.
 */

public class Categoria {

    private String nome;
    private String Descrizione;
    private ArrayList<Piatto> Piatti;
    private int img;

    public Categoria(String nome, String descrizione, int img) {
        this.nome = nome;
        Descrizione = descrizione;
        this.img = img;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
