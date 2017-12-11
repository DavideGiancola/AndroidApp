package univaq.apppub.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Categoria;
import univaq.apppub.util.CategorieAdapter;
import univaq.apppub.util.Foundation.MySQLiteHelper;

public class CategorieActivity extends Activity implements CategorieAdapter.ItemClickListener {
    private RecyclerView mRecyclerView;
    private CategorieAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Categoria> Categorie;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);


        //initCollapsingToolbar();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerCategorie);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new  GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Categorie = new ArrayList<>();
        mAdapter = new CategorieAdapter(this,Categorie);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        CostruisciCategorie();
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    collapsingToolbar.setTitle("Menù");
                    isShow = true;
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Menù");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("Menù");
                    isShow = false;
                }
            }
        });
    }
*/
    private void CostruisciCategorie(){
        MySQLiteHelper db = new MySQLiteHelper(this);
        Categorie.addAll(db.getCategorie());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, Categoria categoria) {
        Intent intent = new Intent(getApplicationContext(), PiattiActivity.class);
        intent.putExtra("id",String.valueOf(categoria.getId()));
        startActivity(intent);
    }
}
