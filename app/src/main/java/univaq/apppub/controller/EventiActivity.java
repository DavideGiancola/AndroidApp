package univaq.apppub.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import univaq.apppub.R;

public class EventiActivity extends AppCompatActivity {

    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventi);

       /* String[] cat = getResources().getStringArray(R.array.categorie);

        mList = (RecyclerView) findViewById(R.id.my_recycler_view);
        mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        MyAdapter adapter = new MyAdapter(cat);
        mList.setAdapter(adapter);
        */
    }
}
