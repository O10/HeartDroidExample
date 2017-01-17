package pl.edu.agh.heartdroidexample.callbacks;

import android.location.Location;

import java.util.Calendar;

import heart.Callback;
import heart.WorkingMemory;
import heart.alsvfd.SimpleSymbolic;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;
import pl.edu.agh.heartdroidexample.DroidApp;
import pl.edu.agh.heartdroidexample.util.GeneralUtils;
import pl.edu.agh.heartdroidexample.util.Symbolics;

/**
 * Created by O10 on 17.01.2017.
 */

public class GetParkingZoneCallback implements Callback {

    private final float PARKING_ZONE_RADIUS = 2000f;
    private final long TIME_OFFSET = 300000;

    @Override
    public void execute(Attribute attribute, WorkingMemory workingMemory) {
        Location lastKnownLocation = DroidApp.instance.lastKnownLocation;
        Location mainSquareLocation = GeneralUtils.getMainSquareLocation();

        float certainFactor;
        SimpleSymbolic symbolicValue;

        if (lastKnownLocation.distanceTo(mainSquareLocation) > PARKING_ZONE_RADIUS) {
            //PROBABLY NOT IN PARKING
            certainFactor = calculateCertainFactor(lastKnownLocation, 0.9f, 0.8f);
            symbolicValue = new SimpleSymbolic(Symbolics.ParkingZoneType.freeZone);
        } else {
            //PROBABLY IN PARKING ZONE
            certainFactor = calculateCertainFactor(lastKnownLocation, 0.8f, 0.7f);
            symbolicValue = new SimpleSymbolic(Symbolics.ParkingZoneType.payZone);
        }

        symbolicValue.setCertaintyFactor(certainFactor);

        try {
            workingMemory.setAttributeValue(attribute, symbolicValue, false);
        } catch (AttributeNotRegisteredException | NotInTheDomainException e) {
            e.printStackTrace();
        }

    }

    private float calculateCertainFactor(Location lastKnownLocation, float thresh1, float thresh2) {
        long currentTime = Calendar.getInstance().getTime().getTime();

        float certainFactor = 1f;

        if (currentTime - lastKnownLocation.getTime() > TIME_OFFSET) {
            certainFactor = thresh1;
        }
        if (currentTime - lastKnownLocation.getTime() > 2 * TIME_OFFSET) {
            certainFactor = thresh2;
        }

        return certainFactor;
    }
}
