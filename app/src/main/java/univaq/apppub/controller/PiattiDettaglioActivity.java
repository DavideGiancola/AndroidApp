package univaq.apppub.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Piatto;
import univaq.apppub.util.Foundation.MySQLiteHelper;

public class PiattiDettaglioActivity extends AppCompatActivity {


    private PiattiDettaglioActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private int piatto_selezionato_id;
    private int categoria_piatto_id;

    private List<Piatto> Piatti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piatti_dettaglio);
        Bundle extras = getIntent().getExtras();
        Piatti = new ArrayList<>();

        this.piatto_selezionato_id = Integer.parseInt(extras.get("id_piatto").toString());
        this.categoria_piatto_id = Integer.parseInt(extras.get("id_categoria").toString());
        CostruisciPiatti();

        mSectionsPagerAdapter = new PiattiDettaglioActivity.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(piatto_selezionato_id-1);

        //recupero i dati dalla precedente activity



    }

    private void CostruisciPiatti() {

        // prendi dati dal db
        MySQLiteHelper db = new MySQLiteHelper(this);
        Piatti.addAll(db.getPiatti(categoria_piatto_id));

    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        public static PiattiDettaglioActivity.PlaceholderFragment newInstance(int sectionNumber,List<Piatto> piatti) {
            PiattiDettaglioActivity.PlaceholderFragment fragment = new PiattiDettaglioActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("nome",piatti.get(sectionNumber).getNome());
            args.putString("descrizione",piatti.get(sectionNumber).getDescrizione());
            args.putString("img",String.valueOf(piatti.get(sectionNumber).getImg()));
            args.putString("prezzo",String.valueOf(piatti.get(sectionNumber).getPrezzo()));

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_piatti_dettaglio, container, false);

            Bundle args = getArguments();

            String nome = args.getString("nome");
            String descrizione = args.getString("descrizione");
            int img = Integer.parseInt(args.getString("img"));
            String prezzo = "Prezzo: " + String.valueOf(args.getString("prezzo"));


            TextView textView_nome = (TextView) rootView.findViewById(R.id.nome_piatto);
            ImageView imgView = (ImageView) rootView.findViewById(R.id.image_piatto_dettaglio);
            TextView textView_descrizione = (TextView) rootView.findViewById(R.id.descrizione_piatto);
            TextView textView_prezzo = (TextView) rootView.findViewById(R.id.prezzo_piatto);


            textView_nome.setText(nome);
            textView_descrizione.setText(descrizione);
            textView_prezzo.setText(prezzo);
            Glide.with(this).load(img).into(imgView);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PiattiDettaglioActivity.PlaceholderFragment.newInstance(position,Piatti);
        }

        @Override
        public int getCount() {
            return Piatti.size();
        }
    }


}
