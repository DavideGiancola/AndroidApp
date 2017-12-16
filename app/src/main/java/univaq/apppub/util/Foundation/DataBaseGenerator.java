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

        Categoria a = new Categoria(1,"Primi","primi Piatti","");
        addPiatti(a);
        categorie.add(a);
        a = new Categoria(2,"Hamburger","hamburge","");
        addPiatti(a);
        categorie.add(a);
        a = new Categoria(3,"Secondi","secondi","");
        addPiatti(a);
        categorie.add(a);
        a = new Categoria(4,"Contorni","contorni","");
        categorie.add(a);
        a = new Categoria(5,"Bevande","Bevande","");
        categorie.add(a);
        a = new Categoria(6,"Alcolici","alcolici","");
        categorie.add(a);
        a = new Categoria(7,"Prova","alcolici","");
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
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
        };


        int[] arrayId = {1,2, 3, 4};
        String[] arrayNomi = {"Ludica Live Music", "the NEINTEENHEY! TEEs live", "Spaghetti Rocchenroll", "4 Anni Insieme"};
        String[] arrayData = {"ven dic 1 00:00:00 2017", "ven nov 10 00:00:00 2017", "ven ott 6 00:00:00 2017", "gio mag 25 00:00:00 2017"};
        String[] arrayOraIn = {"ven dic 1 22:00:00 2017", "ven nov 10 22:00:00 2017", "ven ott 6 22:00:00 2017", "gio mag 25 19:00:00 2017" };
        String[] arrayOraFin = {"sab dic 2 01:00:00 2017", "sab nov 11 01:00:00 2017", "sab ott 7 01:00:00 2017", "ven mag 26 03:00:00 2017"};
        String[] arrayDescrizione = {"Siamo tornati al Monthy's! Vi aspettiamo tutti per una splendida serata Rock! Non mancate! Suonera la cover band Ludica con repertorio formato dai classici del Rock e R&B per soddisfare tutti i gusti....dai più maturi ai più giovani",
                                    "Venerdì 10 novembre, a Stoccolma, di sera, deve fare parecchio freddo, e lì i ragazzi di Ventura, una delle peggiori nazionali della storia, cercheranno di evitarvi un dolore atroce, l’Italia fuori dai mondiali.\n" +
                                            "\n" +
                                            "Una mezz’oretta più tardi, a qualche migliaio di chilometri di distanza, al Monthy’s Irish Pub dell’Aquila, i ragazzi di Max Chiappini, una delle peggiori rock-band della storia, si sintonizzeranno con i vostri cuori di tifosi ma pure con quelli di chi se ne frega di Ventura e Stoccolma.\n" +
                                            "\n" +
                                            "Se la squadra del vostro cuore avrà vinto, brindate con Stock ‘84 e 4 pinte di Guinness, se avrà perso consolatevi con Stock ‘84 e 4 pinte di Guinness.\n" +
                                            "\n" +
                                            "Sullo sfondo, i 1980s vi proporranno una succosa selezione di canzoni anni '80 eseguite con una certa rabbia... Si, ci sarà anche Don’t you forget about me dei Simple Minds, in una versione grezza come certi pomeriggi di domenica passati in discoteca.\n" +
                                            "\n" +
                                            "Vi aspettiamo, carichi di sciovinismo anti-scandinavo e di malinconia reattiva.\n" +
                                            "\n" +
                                            "Viva MondoConvenienza, abbasso l’IKEA.\n" +
                                            "\n" +
                                            "#NonSiEsceViviDagliAnniOttanta",
                                    "♪♫♪♪♪♫ Rocchenroll Show® ♪♫♪♫♫♪♫\n" +
                                            "►►Lo scatenato trio Spaghetti Rocchenroll sarà di scena a l'Aquila al Monthy's Irish Pub per dare il via alla nuova stagione musicale.\n" +
                                            "Il loro Rocchenroll Show®, a base di Rock and Roll, Rockabilly e Surf music vi accompagnerà e travolgerà, cercando di non far stare fermo nessuno nemmeno per un attimo.\n" +
                                            "►►L'appuntamento con il divertimento è per venerdì 6 ottobre dalle ore 22:00 in poi per una serata all'insegna del puro divertimento.\n" +
                                            "►►Spaghetti Rocchenroll...\"Rock 'n roll allo stato brado!!!\".\n" +
                                            "►►Spaghetti Rocchenroll:\n" +
                                            "Samuele Di Filippo: Voce & chitarra & piccole evoluzioni\n" +
                                            "Willy Rocchenroll TheKill: Contrabbasso, voce, cori, armonica ed \n" +
                                            "evoluzioni varie\n" +
                                            "Massimo Sacripante: Batteria, strilli vari, sorrisi & mimiche facciali",
                                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum id justo justo. Praesent dapibus urna gravida nunc finibus, eget viverra nunc porttitor. Ut porttitor nibh a mi feugiat hendrerit non ultricies ligula. Etiam eget arcu eget odio bibendum faucibus et at magna. Praesent elementum elementum mauris, quis cursus nisl dignissim sit amet. In finibus massa velit, non semper mauris malesuada sit amet. Praesent dolor magna, luctus eget rutrum vel, lacinia pulvinar diam."};

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
            Piatto piatto = new Piatto(i,cat,desc,"",prezzo);
            categoria.aggiungiPiatto(piatto);
        }

    }




}
