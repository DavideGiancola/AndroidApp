package univaq.apppub.controller;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import univaq.apppub.R;
import univaq.apppub.util.MyAdapter;

public class CategorieMenu extends AppCompatActivity {

    private RecyclerView mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_menu);

        String[] cat = getResources().getStringArray(R.array.categorie);

        mList = (RecyclerView) findViewById(R.id.my_recycler_view);
        mList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        MyAdapter adapter = new MyAdapter(cat);
        mList.setAdapter(adapter);
    }

}


