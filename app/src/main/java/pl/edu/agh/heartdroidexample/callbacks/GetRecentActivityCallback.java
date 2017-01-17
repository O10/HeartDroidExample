package pl.edu.agh.heartdroidexample.callbacks;

import android.util.Log;

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

        if (DroidApp.instance.isManual) {
            value = DroidApp.instance.recActivity;
        } else {
            value = Symbolics.ActivityType.inVehicle;
        }

        try {
            final SimpleSymbolic simpleSymbolic = new SimpleSymbolic("in_vehicle");
            simpleSymbolic.setCertaintyFactor(0.3f);
            workingMemory.setAttributeValue(attribute, simpleSymbolic, false);
        } catch (AttributeNotRegisteredException | NotInTheDomainException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), "Executed GetRecentActivityCallback for attr " + attribute.getName() + " value set to " + value);
    }
}
