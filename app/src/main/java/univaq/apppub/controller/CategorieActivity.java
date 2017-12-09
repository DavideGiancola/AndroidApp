package univaq.apppub.controller;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Categoria;
import univaq.apppub.util.CategorieAdapter;

public class CategorieActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Categoria> Categorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);




        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerCategorie);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new  GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Categorie = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new CategorieAdapter(this,Categorie);
        mRecyclerView.setAdapter(mAdapter);

        CostruisciCategorie();

    }


    private void CostruisciCategorie(){
        int[] img = new int[]{
                R.drawable.categoria1,
                R.drawable.categoria2,
                R.drawable.categoria3,
                R.drawable.categoria4,
                R.drawable.categoria5,
                R.drawable.categoria6,
        };

        Categoria a = new Categoria("Primi","primi Piatti",img[1]);
        Categorie.add(a);
        a = new Categoria("Hamburger","hamburge",img[2]);
        Categorie.add(a);
        a = new Categoria("Secondi","secondi",img[4]);
        Categorie.add(a);
        a = new Categoria("Contorni","contorni",img[3]);
        Categorie.add(a);
        a = new Categoria("Bevande","Bevande",img[0]);
        Categorie.add(a);
        a = new Categoria("Alcolici","alcolici",img[5]);
        Categorie.add(a);

        mAdapter.notifyDataSetChanged();

    }
}
