package univaq.apppub.util.Network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.test.SyncBaseInstrumentation;

/**
 * Created by Gioele on 26/12/2017.
 */

public class SyncronizationTask {

    public interface SyncronizationTaskInterface {

        public void onTasksCompleted();

    }


    private static SyncronizationTask singletonInstance = null;

    private int runningTasks;

    private SyncronizationTaskInterface listener;

    private SyncronizationTask() {

    }

    public static SyncronizationTask getSingletonInstance() {
        if (singletonInstance == null) {
            singletonInstance = new SyncronizationTask();
        }
        return singletonInstance;
    }

    public void addTask(){
        this.runningTasks++;
        System.out.println("aggiungoTask");
    }

    public void taskFinished() {
        System.out.println("rimuovoTask");
        if (--runningTasks == 0) {
            // sbloccco il caricamento
            System.out.println("ho finito tutto");
            if (listener != null)
                listener.onTasksCompleted();
        }
    }


    public void setCustomObjectListener(SyncronizationTaskInterface listener) {
        this.listener = listener;
    }


}
