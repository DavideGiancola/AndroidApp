package univaq.apppub.controller;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Evento;
import univaq.apppub.util.DateParser;
import univaq.apppub.util.Foundation.MySQLiteHelper;

public class EventiDettaglioActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private List<Evento> eventi;
    private int posizione_evento_selezionato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventi_dettaglio);
        Bundle extras = getIntent().getExtras();
        eventi = new ArrayList<>();

        costruisciEventi(Integer.parseInt(extras.get("id_evento").toString()));

        mSectionsPagerAdapter = new EventiDettaglioActivity.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(posizione_evento_selezionato);



    }


    private void costruisciEventi(int idEvento){



        MySQLiteHelper db = new MySQLiteHelper(this);
        eventi.addAll(db.getEventi());

        int i=0;
        for (Evento evento:eventi) {
            if(evento.getId()==idEvento){
                posizione_evento_selezionato=i;
            }
            i++;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        public static EventiDettaglioActivity.PlaceholderFragment newInstance(int sectionNumber, List<Evento> eventi) {
            EventiDettaglioActivity.PlaceholderFragment fragment = new EventiDettaglioActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("nome",eventi.get(sectionNumber).getNome());
            args.putString("descrizione",eventi.get(sectionNumber).getDescrizione());
            args.putString("img",String.valueOf(eventi.get(sectionNumber).getImg()));
            args.putString("data",String.valueOf(eventi.get(sectionNumber).getData()));
            args.putString("oraInizio",String.valueOf(eventi.get(sectionNumber).getOraInizio()));
            args.putString("oraFine",String.valueOf(eventi.get(sectionNumber).getOraFine()));

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_eventi_dettaglio, container, false);
            Bundle args = getArguments();

            String nome = args.getString("nome");
            String descrizione = args.getString("descrizione");
            String img = args.getString("img");
            String data = args.getString("data");
            String oraInizio = args.getString("oraInizio");
            String oraFine = args.getString("oraFine");

            DateParser dateParser = new DateParser();
            Date dataParsificata = dateParser.parsificaData(data);
            //System.out.println(dateParser.getNomeGiorno()+" "+dateParser.getGiornoDelMese()+" "+dateParser.getNomeMese()+" "+dateParser.getOra()+":"+dateParser.getMinuto());


            TextView textView_nome = (TextView) rootView.findViewById(R.id.nome_evento);
            TextView textView_descrizione = (TextView) rootView.findViewById(R.id.descrizione_evento);
            ImageView imgView_immagine = (ImageView) rootView.findViewById(R.id.immagine_dettaglio_evento);

            TextView textView_giorno = (TextView) rootView.findViewById(R.id.giorno_evento);
            TextView textView_mese = (TextView) rootView.findViewById(R.id.mese_evento);
            TextView textView_ora_inizio = (TextView) rootView.findViewById(R.id.ora_inizio_evento);
            TextView textView_ora_fine = (TextView) rootView.findViewById(R.id.ora_fine_evento);


            textView_nome.setText(nome);
            textView_descrizione.setText(descrizione);
            Glide.with(this).load(img).into(imgView_immagine);
            textView_giorno.setText(dateParser.getGiornoDelMese());
            textView_mese.setText(dateParser.getNomeMese());

            dateParser.parsificaData(oraInizio);
            textView_ora_inizio.setText("Dalle "+dateParser.getOra()+":"+dateParser.getMinuto());

            dateParser.parsificaData(oraFine);
            textView_ora_fine.setText("alle "+dateParser.getOra()+":"+dateParser.getMinuto());

            return rootView;
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return EventiDettaglioActivity.PlaceholderFragment.newInstance(position, eventi);
        }

        @Override
        public int getCount() {
            return eventi.size();
        }
    }
}
