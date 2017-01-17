package pl.edu.agh.heartdroidexample.actions;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import heart.Action;
import heart.State;
import pl.edu.agh.heartdroidexample.DroidApp;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by O10 on 17.01.2017.
 */

public class ShowParkingNotification implements Action {

    @Override
    public void execute(State state) {

        final Notification notification = new NotificationCompat.Builder(DroidApp.instance)
                .setContentTitle("Strefa płatnego parkowania!")
                .setContentText("Nie zapomnij o bilecie parkingowym.").build();

        int mNotificationId = 123;
        NotificationManager mNotifyMgr =
                (NotificationManager) DroidApp.instance.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, notification);

    }

}
