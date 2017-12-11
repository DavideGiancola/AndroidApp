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
import android.widget.TextView;

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
        //recupero i dati dalla precedente activity



    }

    private void CostruisciPiatti() {

        // prendi dati dal db
        MySQLiteHelper db = new MySQLiteHelper(this);
        System.out.println(categoria_piatto_id);
        Piatti.addAll(db.getPiatti(categoria_piatto_id));

        List<Piatto> PiattiOrdinati = new ArrayList<>();
        for (Piatto piatto:Piatti) {
            if(piatto.getId() == piatto_selezionato_id){
                System.out.println("Trovato");
                PiattiOrdinati.add(piatto);
            }
        }
        for (Piatto piatto:Piatti) {
            if(piatto.getId() != piatto_selezionato_id){
                PiattiOrdinati.add(piatto);
            }
        }

        for (Piatto piatto:PiattiOrdinati) {
            System.out.println(piatto.getNome());
        }


        Piatti.clear();
        Piatti.addAll(PiattiOrdinati);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PiattiDettaglioActivity.PlaceholderFragment newInstance(int sectionNumber,List<Piatto> piatti) {
            PiattiDettaglioActivity.PlaceholderFragment fragment = new PiattiDettaglioActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("nome",piatti.get(sectionNumber).getNome());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            String nome = args.getString("nome");
            View rootView = inflater.inflate(R.layout.fragment_piatti_dettaglio, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.nome_piatto);
            textView.setText(nome);
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
