package univaq.apppub.controller;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

        /*
        String s="ven dic 15 01:07:36 2017";
        DateParser dateParser = new DateParser();
        Date data = dateParser.parsificaData(s);
        System.out.println(dateParser.getNomeGiorno()+" "+dateParser.getGiornoDelMese()+" "+dateParser.getNomeMese()+" "+dateParser.getOra()+":"+dateParser.getMinuto());
        */
    }



    private void getEventi(){

        MySQLiteHelper db = new MySQLiteHelper(this);

        eventi.addAll(db.getEventi());

        //System.out.println(eventi.get(0).getNome());
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onItemClick(View view, Evento evento) {
        Intent intent = new Intent(getApplicationContext(), EventiDettaglioActivity.class);
        intent.putExtra("id_evento",String.valueOf(evento.getId()));
        startActivity(intent);

    }
}
