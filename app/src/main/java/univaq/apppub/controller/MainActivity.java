package univaq.apppub.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import univaq.apppub.R;

public class MainActivity extends AppCompatActivity {

    private Button mButtonMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonMenu=(Button) findViewById(R.id.MenuButton);
        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategorieMenu.class);
                startActivity(intent);
            }
        });
    }
}
