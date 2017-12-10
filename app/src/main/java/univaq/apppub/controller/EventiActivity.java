package univaq.apppub.controller;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Evento;
import univaq.apppub.util.EventiAdapter;

public class EventiActivity extends AppCompatActivity {

    private RecyclerView mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventi);

        List<Evento> eventi = getEventi();


        mList = (RecyclerView) findViewById(R.id.my_recycler_view);


        mList.setLayoutManager(new LinearLayoutManager(EventiActivity.this));
        System.out.println("ciao");

        EventiAdapter adapter = new EventiAdapter(this, eventi);
        mList.setAdapter(adapter);

    }



    private List<Evento> getEventi(){

        List<Evento> eventi = new ArrayList<>();

        int[] img = new int[]{
                R.drawable.evento1,
                R.drawable.evento2,
                R.drawable.evento3,
                R.drawable.evento4,
        };

        String[] arrayNomi = getResources().getStringArray(R.array.array_nomi);
        String[] arrayData = getResources().getStringArray(R.array.array_data);
        String[] arrayOraIn = getResources().getStringArray(R.array.array_ora_inizio);
        String[] arrayOraFin = getResources().getStringArray(R.array.array_ora_fine);
        String[] arrayDescrizione = getResources().getStringArray(R.array.array_descrizione);

        for (int i = 0; i < arrayNomi.length; i++) {
            Evento evento = new Evento(arrayNomi[i], arrayData[i], arrayOraIn[i], arrayOraFin[i], arrayDescrizione[i], img[i]);
            eventi.add(evento);
        }

        return eventi;

    }
}
