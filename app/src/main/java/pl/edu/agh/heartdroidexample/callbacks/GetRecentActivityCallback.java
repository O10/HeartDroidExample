package pl.edu.agh.heartdroidexample.callbacks;

import android.util.Log;

import com.google.android.gms.location.DetectedActivity;

import heart.Callback;
import heart.WorkingMemory;
import heart.alsvfd.SimpleSymbolic;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;
import pl.edu.agh.heartdroidexample.DroidApp;
import pl.edu.agh.heartdroidexample.util.Symbolics;

/**
 * Created by O10 on 17.01.2017.
 */

public class GetRecentActivityCallback implements Callback {

    @Override
    public void execute(Attribute attribute, WorkingMemory workingMemory) {

        String value;
        float certainFactor = 0.3f;

        if (DroidApp.instance.isManual) {
            value = DroidApp.instance.recActivity;
        } else {
            final DetectedActivity mostProbableActivity = DroidApp.instance.lastKnownResult.getMostProbableActivity();

            switch (mostProbableActivity.getType()) {
                case DetectedActivity.IN_VEHICLE:
                    value = Symbolics.ActivityType.inVehicle;
                    certainFactor = mostProbableActivity.getConfidence() / 100.0f;
                    break;
                default:
                    value = Symbolics.ActivityType.onFoot;
                    certainFactor = 0.7f;
                    break;
            }
        }

        try {
            final SimpleSymbolic simpleSymbolic = new SimpleSymbolic(value);
            simpleSymbolic.setCertaintyFactor(certainFactor);
            workingMemory.setAttributeValue(attribute, simpleSymbolic, false);
        } catch (AttributeNotRegisteredException | NotInTheDomainException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), "Executed GetRecentActivityCallback for attr " + attribute.getName() + " value set to " + value);
    }
}
