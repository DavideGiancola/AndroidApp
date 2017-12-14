package univaq.apppub.model;

import java.util.Date;

/**
 * Created by Davide on 08/12/2017.
 */

public class Evento {


    private int id;
    private String nome;
    private String data;
    private String oraInizio;
    private String oraFine;
    private String descrizione;
    private int img;

    public Evento(int id, String nome, String data, String oraInizio, String oraFine, String descrizione, int img) {
        this.id=id;
        this.nome = nome;
        //strings ("YYYY-MM-DD HH:MM:SS.SSS")
        this.data = data;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.descrizione = descrizione;
        this.img =img;
    }

    public Evento() {
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(String oraInizio) {
        this.oraInizio = oraInizio;
    }

    public String getOraFine() {
        return oraFine;
    }

    public void setOraFine(String oraFine) {
        this.oraFine = oraFine;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
