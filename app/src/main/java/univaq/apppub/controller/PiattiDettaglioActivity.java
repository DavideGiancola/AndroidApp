package univaq.apppub.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Categoria;
import univaq.apppub.model.Piatto;
import univaq.apppub.util.Foundation.MySQLiteHelper;

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
    protected void onCreate(Bundle savedInstanceState){
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

    static boolean firstOpened = true;

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
            args.putInt("sectionNumber",sectionNumber);

            args.putString("aggiunte",String.valueOf(piatti.get(sectionNumber).isAggiunte()));

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
            String prezzo = "Prezzo \n" + String.valueOf(args.getString("prezzo"));

            boolean aggiunte = Boolean.parseBoolean(args.getString("aggiunte"));

            int sectionNumber = args.getInt("sectionNumber");


            TextView textView_nome = (TextView) rootView.findViewById(R.id.nome_piatto);
            textView_nome.setTypeface(font);

            ImageView imgView = (ImageView) rootView.findViewById(R.id.image_piatto_dettaglio);

            TextView textView_descrizione = (TextView) rootView.findViewById(R.id.descrizione_piatto);
            textView_descrizione.setTypeface(font);

            TextView textView_prezzo = (TextView) rootView.findViewById(R.id.prezzo_piatto);
            textView_prezzo.setTypeface(font);

            final ImageView arrow = (ImageView) rootView.findViewById(R.id.arrow);

            if (firstOpened == false){
                arrow.setVisibility(View.INVISIBLE);
            }
            else {
                arrow.setVisibility(View.VISIBLE);
                firstOpened = true;
            }

            if(firstOpened == true) {

                new CountDownTimer(1000, 1000) { // 5000 = 5 sec

                    private void fadeOutAndHideImage(final ImageView img) {
                        Animation fadeOut = new AlphaAnimation(1, 0);
                        fadeOut.setInterpolator(new AccelerateInterpolator());
                        fadeOut.setDuration(800);

                        fadeOut.setAnimationListener(new Animation.AnimationListener() {


                            public void onAnimationEnd(Animation animation) {
                                img.setVisibility(View.GONE);
                            }

                            public void onAnimationRepeat(Animation animation) {
                            }

                            public void onAnimationStart(Animation animation) {
                            }
                        });

                        img.startAnimation(fadeOut);
                    }

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        firstOpened = false;
                        //arrow.setVisibility(View.INVISIBLE);
                        fadeOutAndHideImage(arrow);
                    }
                }.start();


            }


                Button aggiunteButton = rootView.findViewById(R.id.Aggiunte);
                aggiunteButton.setTypeface(font);
                aggiunteButton.setTextColor(getResources().getColor(R.color.categorie_title));
                aggiunteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), PiattiActivity.class);
                        intent.putExtra("name", "Aggiunte");
                        intent.putExtra("id", AggiunteCategoriaId);
                        startActivity(intent);
                        firstOpened = true;
                    }
                });

            if(!aggiunte) {
                aggiunteButton.setVisibility(View.INVISIBLE);
            }

            textView_nome.setText(nome);
            textView_descrizione.setText(descrizione);
            textView_prezzo.setText(prezzo);
            //Glide.with(this).load(img).into(imgView);


            Glide.with(this).load(img).into(imgView);

            final ImageView upArrow;
            final SlidingUpPanelLayout slide;
            final RelativeLayout panelDescrizione;

            slide = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding);
            upArrow= (ImageView) rootView.findViewById(R.id.uparrow);

            panelDescrizione = (RelativeLayout) rootView.findViewById(R.id.descrizioneSlide);

            slide.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {


                @Override
                public void onPanelSlide(View panel, float slideOffset) {

                    if(slideOffset > 0.1){
                        upArrow.setImageResource(R.drawable.downarrow);
                        panelDescrizione.setBackgroundResource(R.drawable.sfondo_trasparente);
                    }
                    if(slideOffset < 0.1) {
                        upArrow.setImageResource(R.drawable.uparrow);
                        panelDescrizione.setBackgroundResource(R.drawable.gradient_piatti);
                    }
                }

                @Override
                public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                }
            });

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

    @Override
    public void onBackPressed() {
        firstOpened = true;
        super.onBackPressed();
    }



}
