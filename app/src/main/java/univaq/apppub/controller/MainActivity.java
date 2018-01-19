package univaq.apppub.controller;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;

import univaq.apppub.R;
import univaq.apppub.util.Network.ServerFacade;

public class MainActivity extends AppCompatActivity {

    private CardView mMenu;
    private CardView mEventi;
    private TextView menu;
    private TextView eventi;
    private Typeface font;


    private static final String TAG = "FireBaseService";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        font = Typeface.createFromAsset(getAssets(),"berkshire.ttf");
        menu = (TextView) findViewById(R.id.textMenu);
        eventi= (TextView) findViewById(R.id.textEventi);

        menu.setTypeface(font);
        eventi.setTypeface(font);


        mMenu=(CardView) findViewById(R.id.menuCard);
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategorieActivity.class);
                startActivity(intent);
            }
        });

        mEventi=(CardView) findViewById(R.id.eventiCard);
        mEventi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventiActivity.class);
                startActivity(intent);
            }
        });


    }


}
