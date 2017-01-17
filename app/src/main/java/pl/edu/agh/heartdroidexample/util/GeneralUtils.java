package pl.edu.agh.heartdroidexample.util;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import heart.parser.hmr.runtime.SourceString;

/**
 * Created by O10 on 16.01.2017.
 */

public class GeneralUtils {
    public static final long DEFAULT_INTERVAL = 5000;

    public static SourceString loadHmrFileIntoSourceString(Context context, String filename) throws IOException {
        InputStream inputStream = context.getAssets().open(filename);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        String rules = result.toString("UTF-8");
        inputStream.close();
        return new SourceString(rules);
    }

    public static LocationRequest createDefaultLocationRequest() {
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest
                .setInterval(DEFAULT_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    public static Location getMainSquareLocation() {
        Location targetLocation = new Location("");
        targetLocation.setLatitude(50.061585d);
        targetLocation.setLongitude(19.937420d);
        return targetLocation;
    }
}
