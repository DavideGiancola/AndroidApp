package univaq.apppub.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import univaq.apppub.R;
import univaq.apppub.model.Categoria;
import univaq.apppub.util.CategorieAdapter;

public class MainActivity extends AppCompatActivity {

    private Button mButtonEventi;
    private Button mButtonMenu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonMenu=(Button) findViewById(R.id.MenuButton);
        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategorieActivity.class);
                startActivity(intent);
            }
        });


        mButtonEventi=(Button) findViewById(R.id.EventiButton);
        mButtonEventi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventiActivity.class);
                startActivity(intent);
            }
        });

    }

}
