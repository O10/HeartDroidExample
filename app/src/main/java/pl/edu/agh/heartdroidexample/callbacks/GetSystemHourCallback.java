package pl.edu.agh.heartdroidexample.callbacks;

import android.util.Log;

import java.util.Calendar;

import heart.Callback;
import heart.WorkingMemory;
import heart.alsvfd.SimpleNumeric;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;

/**
 * Created by O10 on 16.01.2017.
 */

public class GetSystemHourCallback implements Callback {

    @Override
    public void execute(Attribute attribute, WorkingMemory workingMemory) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        try {
            workingMemory.setAttributeValue(attribute, new SimpleNumeric((double) hour), false);
        } catch (AttributeNotRegisteredException | NotInTheDomainException e) {
            e.printStackTrace();
        }

        Log.d(getClass().getSimpleName(), "Executed GetDayOfWeekCallback for attr " + attribute.getName() + " hour set to " + hour);

    }
}
