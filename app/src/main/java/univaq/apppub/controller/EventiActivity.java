package univaq.apppub.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Evento;
import univaq.apppub.util.EventiAdapter;
import univaq.apppub.util.Foundation.MySQLiteHelper;

public class EventiActivity extends AppCompatActivity implements EventiAdapter.ItemClickListener {

    private RecyclerView mList;
    private List<Evento> eventi;
    private EventiAdapter adapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Typeface font;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventi);

        mList = (RecyclerView) findViewById(R.id.my_recycler_view);
        mList.setLayoutManager(new LinearLayoutManager(EventiActivity.this));
        eventi = new ArrayList<>();
        adapter = new EventiAdapter(this, eventi);
        adapter.setClickListener(this);
        mList.setAdapter(adapter);
        getEventi();

        font = Typeface.createFromAsset(getAssets(),"berkshire.ttf");

        collapsingToolbarLayout = findViewById(R.id.CollapsingToolbarEventi);

        collapsingToolbarLayout.setExpandedTitleTypeface(font);
        collapsingToolbarLayout.setCollapsedTitleTypeface(font);

        try {
            Glide.with(this).load(R.drawable.emotionheader).into((ImageView) findViewById(R.id.backdropEventi));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void getEventi(){

        MySQLiteHelper db = new MySQLiteHelper(this);

        eventi.addAll(db.getEventi());

        adapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(View view, Evento evento) {
        Intent intent = new Intent(getApplicationContext(), EventiDettaglioActivity.class);
        intent.putExtra("id_evento",String.valueOf(evento.getId()));
        startActivity(intent);

    }
}
