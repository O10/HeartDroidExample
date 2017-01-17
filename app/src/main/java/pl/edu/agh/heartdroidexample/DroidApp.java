package pl.edu.agh.heartdroidexample;

import android.app.Application;
import android.location.Location;

import com.google.android.gms.location.ActivityRecognitionResult;

import pl.edu.agh.heartdroidexample.util.Symbolics;

/**
 * Created by O10 on 17.01.2017.
 */

public class DroidApp extends Application {

    public static DroidApp instance;

    public Location lastKnownLocation;
    public int bluetoothDevices = 3;
    public boolean isManual = true;
    public String recActivity = Symbolics.ActivityType.inVehicle;
    public ActivityRecognitionResult lastKnownResult;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
