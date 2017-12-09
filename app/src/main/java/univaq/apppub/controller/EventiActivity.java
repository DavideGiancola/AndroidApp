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

    }



    private List<Evento> getEventi(){

        List<Evento> eventi = new ArrayList<>();

        String[] arrayNomi = getResources().getStringArray(R.array.array_nomi);
        String[] arrayData = getResources().getStringArray(R.array.array_data);
        String[] arrayOraIn = getResources().getStringArray(R.array.array_ora_inizio);
        String[] arrayOraFin = getResources().getStringArray(R.array.array_ora_fine);
        String[] arrayDescrizione = getResources().getStringArray(R.array.array_descrizione);

        for (int i = 0; i < arrayNomi.length; i++) {
            Evento evento = new Evento(arrayNomi[i], arrayData[i], arrayOraIn[i], arrayOraFin[i], arrayDescrizione[i]);
            eventi.add(evento);
        }

        return eventi;

    }
}
