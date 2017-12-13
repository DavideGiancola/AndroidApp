package univaq.apppub.controller;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

import univaq.apppub.R;
import univaq.apppub.util.Foundation.DataBaseGenerator;
import univaq.apppub.util.Foundation.MySQLiteHelper;

public class MainActivity extends AppCompatActivity {

    private Button mButtonEventi;
    private Button mButtonMenu;


    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (shouldAskPermissions()) {
            askPermissions();
        }

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

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "appPub");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }
    }

}
