package univaq.apppub.util.Foundation;

import java.util.ArrayList;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Categoria;
import univaq.apppub.model.Evento;
import univaq.apppub.model.Menu;
import univaq.apppub.model.Piatto;

/**
 * Created by Gioele on 10/12/2017.
 */

public class DataBaseGenerator {


    public DataBaseGenerator() {
    }

    public List<Categoria> generateData(){

        List<Categoria> categorie = new ArrayList<>();

        int[] img = new int[]{
                R.drawable.categoria1,
                R.drawable.categoria2,
                R.drawable.categoria3,
                R.drawable.categoria4,
                R.drawable.categoria5,
                R.drawable.categoria6,
        };

        Categoria a = new Categoria(1,"Primi","primi Piatti",img[1]);
        addPiatti(a);
        categorie.add(a);
        a = new Categoria(2,"Hamburger","hamburge",img[2]);
        addPiatti(a);
        categorie.add(a);
        a = new Categoria(3,"Secondi","secondi",img[4]);
        addPiatti(a);
        categorie.add(a);
        a = new Categoria(4,"Contorni","contorni",img[3]);
        categorie.add(a);
        a = new Categoria(5,"Bevande","Bevande",img[0]);
        categorie.add(a);
        a = new Categoria(6,"Alcolici","alcolici",img[5]);
        categorie.add(a);
        a = new Categoria(7,"Prova","alcolici",img[2]);
        categorie.add(a);


        return categorie;
    }

    public Menu generateMenu(){
        int versione = 1;
        int id = 0;
        Menu menu = new Menu(id ,versione);
        return menu;
    }

    public List<Evento> generaEventi(){

        List<Evento> eventi = new ArrayList<>();



        int[] img = new int[]{
                R.drawable.ev1,
                R.drawable.ev2,
                R.drawable.ev3,
                R.drawable.ev4,
        };

        int[] arrayId = {1,2, 3, 4};
        String[] arrayNomi = {"Ludica Live Music", "the NEINTEENHEY! TEEs live", "Spaghetti Rocchenroll", "4 Anni Insieme"};
        String[] arrayData = {"Venerdì 1 Dicembre", "Venerdì 10 Novembre", "Venerdì 6 Ottobre", "Giovedì 25 Maggio"};
        String[] arrayOraIn = {"22:00", "22:00", "22:00", "19:00" };
        String[] arrayOraFin = {"01:00", "01:00", "01:00", "03:00"};
        String[] arrayDescrizione = {"Bello", "Brutto", "Non lo so", "Bei tempi"};

        for (int i = 0; i < arrayNomi.length; i++) {
            Evento evento = new Evento(arrayId[i], arrayNomi[i], arrayData[i], arrayOraIn[i], arrayOraFin[i], arrayDescrizione[i], img[i]);
            eventi.add(evento);
        }

        return eventi;

    }

    private void addPiatti(Categoria categoria){

        double prezzo = 3.2d;
        System.out.println(prezzo);
        for (int i=0; i< 10; i++){
            String cat = categoria.getNome() +" "+ String.valueOf(i);
            String desc = "Carne di chianina dal nostro macellaio di fiducia e da allevamenti italiani";
            prezzo = prezzo + i;
            Piatto piatto = new Piatto(i,cat,desc,R.drawable.categoria3,prezzo);
            categoria.aggiungiPiatto(piatto);
        }

    }



}
