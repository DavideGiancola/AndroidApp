package univaq.apppub.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Davide on 15/12/2017.
 */

public class DateParser {

    private String nomeGiorno;
    private String nomeMese;
    private int giornoDelMese;
    private String ora;
    private String minuto;

    public Date parsificaData(String dataStringata) {

        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.ITALIAN);

        Date data=new Date();
        try {
            data = df.parse(dataStringata);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }

        String dataFormattata = df.format(data.getTime());
        //System.out.println(dataFormattata);

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        int numeroGiorno = cal.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat dfNomeGiorno = new SimpleDateFormat("EEEE", Locale.ITALIAN);
        cal.set(Calendar.DAY_OF_WEEK, numeroGiorno);
        this.nomeGiorno = dfNomeGiorno.format(cal.getTime());


        int numeroMese = cal.get(Calendar.MONTH);
        SimpleDateFormat dfNomeMese = new SimpleDateFormat("MMM", Locale.ITALIAN);
        cal.set(Calendar.MONTH, numeroMese);
        this.nomeMese = dfNomeMese.format(cal.getTime());


        this.giornoDelMese = cal.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dfOra = new SimpleDateFormat("HH", Locale.ITALIAN);
        this.ora = dfOra.format(cal.getTime());

        SimpleDateFormat dfMinuti = new SimpleDateFormat("mm", Locale.ITALIAN);
        this.minuto = dfMinuti.format(cal.getTime());


        return data;
    }

    public String getNomeGiorno() {
        return nomeGiorno;
    }

    public String getNomeMese() {
        return nomeMese;
    }

    public int getGiornoDelMese() {
        return giornoDelMese;
    }

    public String getOra() {
        return ora;
    }

    public String getMinuto() {
        return minuto;
    }

}
