package univaq.apppub.controller;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;

import univaq.apppub.R;
import univaq.apppub.util.Network.ServerFacade;
import univaq.apppub.util.Network.SyncronizationTask;

public class LoadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView messaggioCaricamento;

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
        setContentView(R.layout.activity_load);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        messaggioCaricamento = (TextView) findViewById(R.id.testoCaricamento);
        progressBar.setVisibility(View.VISIBLE);

        messaggioCaricamento.setText("Stiamo controllando gli aggiornamenti");
        ServerFacade.getInstance().setContext(this);

        FirebaseMessaging.getInstance().subscribeToTopic("PubNotification");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
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


        SyncronizationTask.getSingletonInstance().setCustomObjectListener(new SyncronizationTask.SyncronizationTaskInterface() {
            private int numberOfTaskCompleted = 0;
            private int maxNumberTask = 0;

            @Override
            public void onTasksCompleted() {
                messaggioCaricamento.setText("Tutto Aggiornato! Grazie della pazienza");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);

            }

            @Override
            public void onTaskCompleded() {
                int percentage = 0;
                maxNumberTask = SyncronizationTask.getSingletonInstance().getMaxNumberOfTask();
                numberOfTaskCompleted +=1;
                if(numberOfTaskCompleted != 0 && maxNumberTask != 0) {
                    percentage = (numberOfTaskCompleted * 100) / maxNumberTask;
                }

                messaggioCaricamento.setText("Aggiornamento in corso: "+ String.valueOf(percentage)+" %");

            }
        });



        if (shouldAskPermissions()) {
            askPermissions();
        } else {
            ServerFacade.getInstance().getMenuVersion();
            ServerFacade.getInstance().getSchedarioVersion();
            Toast.makeText(this, "Scaricamento", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 200: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permessi Concessi");


                    File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "appPub");
                    if (!mediaStorageDir.exists()) {
                        if (!mediaStorageDir.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        }
                    }

                    ServerFacade.getInstance().getMenuVersion();
                    ServerFacade.getInstance().getSchedarioVersion();

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    } else {
                        finish();
                    }
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
