package pl.edu.agh.heartdroidexample.callbacks;

import android.util.Log;

import heart.Callback;
import heart.WorkingMemory;
import heart.alsvfd.SimpleNumeric;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.NotInTheDomainException;
import heart.xtt.Attribute;
import pl.edu.agh.heartdroidexample.DroidApp;

/**
 * Created by O10 on 17.01.2017.
 */

public class GetBluetoothCountCallback implements Callback {

    @Override
    public void execute(Attribute attribute, WorkingMemory workingMemory) {
        int devicesNear = DroidApp.instance.bluetoothDevices;
        try {
            workingMemory.setAttributeValue(attribute, new SimpleNumeric((double) devicesNear), false);
        } catch (NotInTheDomainException | AttributeNotRegisteredException e) {
            e.printStackTrace();
        }
        Log.d(getClass().getSimpleName(), "Executed GetBluetoothCountCallback for " + attribute.getName() + " count set to " + devicesNear);
    }
}
