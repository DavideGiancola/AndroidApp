package univaq.apppub.controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Categoria;
import univaq.apppub.model.Piatto;
import univaq.apppub.util.Foundation.MySQLiteHelper;
import univaq.apppub.util.Network.ServerFacade;

public class PiattiDettaglioActivity extends AppCompatActivity{


    private PiattiDettaglioActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private int piatto_selezionato;
    private static int categoria_piatto_id;

    private Context context;

    private static Typeface font;

    private List<Piatto> piatti;

    private List<Categoria> categorie;

    private static int AggiunteCategoriaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piatti_dettaglio);

        this.context = this;
        //prendo il font

        font = Typeface.createFromAsset(getAssets(),"berkshire.ttf");




        Bundle extras = getIntent().getExtras();
        piatti = new ArrayList<>();
        categorie = new ArrayList<>();


        setAggiunteCategoriaid();


        this.categoria_piatto_id = Integer.parseInt(extras.get("id_categoria").toString());
        CostruisciPiatti(Integer.parseInt(extras.get("id_piatto").toString()));

        mSectionsPagerAdapter = new PiattiDettaglioActivity.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        mViewPager.setCurrentItem(piatto_selezionato);

        //recupero i dati dalla precedente activity




    }

    private void CostruisciPiatti(int id_piatto_selezionato) {

        // prendi dati dal db
        MySQLiteHelper db = new MySQLiteHelper(this);
        piatti.addAll(db.getPiatti(categoria_piatto_id));

        int i = 0;
        for (Piatto piatto: piatti) {
            if (piatto.getId() == id_piatto_selezionato){
                this.piatto_selezionato = i;
            }
            i = i + 1;
        }
    }


    private void setAggiunteCategoriaid(){
        MySQLiteHelper db = new MySQLiteHelper(this);
        categorie.addAll(db.getCategorie());
        for (Categoria categoria: categorie) {
            if(categoria.getNome().equals("Aggiunte")){
                AggiunteCategoriaId = categoria.getId();
            }
        }
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
            String img = args.getString("img");
            String prezzo = "Prezzo: " + String.valueOf(args.getString("prezzo"));


            TextView textView_nome = (TextView) rootView.findViewById(R.id.nome_piatto);
            textView_nome.setTypeface(font);

            ImageView imgView = (ImageView) rootView.findViewById(R.id.image_piatto_dettaglio);

            TextView textView_descrizione = (TextView) rootView.findViewById(R.id.descrizione_piatto);
            textView_descrizione.setTypeface(font);

            TextView textView_prezzo = (TextView) rootView.findViewById(R.id.prezzo_piatto);
            textView_prezzo.setTypeface(font);




                Button aggiunteButton = rootView.findViewById(R.id.Aggiunte);
                aggiunteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), PiattiDettaglioActivity.class);
                        intent.putExtra("id_piatto", String.valueOf(0));
                        intent.putExtra("id_categoria", AggiunteCategoriaId);
                        startActivity(intent);
                    }
                });
            if(categoria_piatto_id == AggiunteCategoriaId) {
                aggiunteButton.setVisibility(View.INVISIBLE);
            }

            textView_nome.setText(nome);
            textView_descrizione.setText(descrizione);
            textView_prezzo.setText(prezzo);
            //Glide.with(this).load(img).into(imgView);


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
            return PiattiDettaglioActivity.PlaceholderFragment.newInstance(position, piatti);
        }

        @Override
        public int getCount() {
            return piatti.size();
        }
    }

}
