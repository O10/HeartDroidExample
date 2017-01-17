package pl.edu.agh.heartdroidexample.actions;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import heart.Action;
import heart.State;
import pl.edu.agh.heartdroidexample.DroidApp;
import pl.edu.agh.heartdroidexample.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by O10 on 17.01.2017.
 */

public class ShowParkingNotification implements Action {

    private final static int notId = 123;

    @Override
    public void execute(State state) {

        final Notification notification = new NotificationCompat.Builder(DroidApp.instance)
                .setContentTitle(DroidApp.instance.getString(R.string.not_title))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(DroidApp.instance.getString(R.string.not_content)).build();

        NotificationManager mNotifyMgr =
                (NotificationManager) DroidApp.instance.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notId, notification);

    }

}
