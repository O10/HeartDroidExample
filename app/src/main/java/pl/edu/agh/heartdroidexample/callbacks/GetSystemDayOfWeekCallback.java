package pl.edu.agh.heartdroidexample.callbacks;

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

public class GetSystemDayOfWeekCallback implements Callback {
    @Override
    public void execute(Attribute attribute, WorkingMemory workingMemory) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        day = (day == 0 ? 7 : day);
        try {
            workingMemory.setAttributeValue(attribute, new SimpleNumeric((double) day), false);
        } catch (NotInTheDomainException | AttributeNotRegisteredException e) {
            e.printStackTrace();
        }
        System.out.println("Executed GetDayOfAWeekCallback for " + attribute.getName() + " day set to " + day);

    }
}
