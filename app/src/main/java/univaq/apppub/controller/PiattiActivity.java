package univaq.apppub.controller;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Categoria;
import univaq.apppub.model.Piatto;
import univaq.apppub.util.CategorieAdapter;
import univaq.apppub.util.Foundation.MySQLiteHelper;
import univaq.apppub.util.PiattiAdapter;

public class PiattiActivity extends AppCompatActivity implements PiattiAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private PiattiAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Piatto> Piatti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_piatti);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerPiatti);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Piatti = new ArrayList<>();

        mAdapter = new PiattiAdapter(this,Piatti);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        CostruisciPiatti();
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop_piatti));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void CostruisciPiatti() {
        Bundle extras = getIntent().getExtras();
        // prendi dati dal db
        MySQLiteHelper db = new MySQLiteHelper(this);
        Piatti.addAll(db.getPiatti(Integer.parseInt(extras.get("id").toString())));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, Piatto piatto) {

        System.out.println("qui");
    }
}
