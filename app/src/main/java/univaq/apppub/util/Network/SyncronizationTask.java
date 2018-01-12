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
        public void onTaskCompleded();
    }



    private static SyncronizationTask singletonInstance = null;

    private int runningTasks;
    private int maxNumberOfTask = 0;


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
        this.maxNumberOfTask += 1;
    }

    public void taskFinished() {
        System.out.println("rimuovoTask");
        listener.onTaskCompleded();
        if (--runningTasks == 0) {
            // sbloccco il caricamento
            System.out.println("ho finito tutto");
            if (listener != null)
                this.maxNumberOfTask = 0;
                listener.onTasksCompleted();
        }
    }

    public int getMaxNumberOfTask() {
        return maxNumberOfTask;
    }

    public void setCustomObjectListener(SyncronizationTaskInterface listener) {
        this.listener = listener;
    }


}
