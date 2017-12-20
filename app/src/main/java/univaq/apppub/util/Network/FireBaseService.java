package univaq.apppub.util.Network;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Gioele on 19/12/2017.
 */

public class FireBaseService extends FirebaseMessagingService {

    private static final String TAG = "FireBaseService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {



        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.

            } else {
                // Handle message within 10 seconds

            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            ServerFacade.getInstance().generateNOtification();
        }



    }



}
