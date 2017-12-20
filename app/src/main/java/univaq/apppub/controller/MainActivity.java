package univaq.apppub.controller;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;

import univaq.apppub.R;
import univaq.apppub.util.Network.ServerFacade;

public class MainActivity extends AppCompatActivity {

    private Button mButtonEventi;
    private Button mButtonMenu;
    private Button mButtonIniziale;

    private static boolean First_Opened = false;


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

    private static final String TAG = "FireBaseService";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("PubNotification");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW));

        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }


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

        mButtonIniziale=(Button) findViewById(R.id.inizialeButton);
        mButtonIniziale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InizialeActivity.class);
                startActivity(intent);
            }
        });

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "appPub");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        ServerFacade.getInstance().setContext(this);

        if(First_Opened == false) {
            ServerFacade.getInstance().getMenuVersion();
            System.out.println("qui");
            ServerFacade.getInstance().getSchedarioVersion();
            System.out.println("Prima Apertura");
        }

        First_Opened = true;

    }



}
