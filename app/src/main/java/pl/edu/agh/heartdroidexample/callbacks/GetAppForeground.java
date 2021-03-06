package pl.edu.agh.heartdroidexample.callbacks;

import heart.Callback;
import heart.WorkingMemory;
import heart.alsvfd.SimpleSymbolic;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;
import pl.edu.agh.heartdroidexample.util.Symbolics;

/**
 * Created by O10 on 17.01.2017.
 */

public class GetAppForeground implements Callback {
    @Override
    public void execute(Attribute attribute, WorkingMemory workingMemory) {
        String value = Symbolics.ApplicationForeground.none;

        try {
            workingMemory.setAttributeValue(attribute, new SimpleSymbolic(value), false);
        } catch (AttributeNotRegisteredException | NotInTheDomainException e) {
            e.printStackTrace();
        }
    }
}
