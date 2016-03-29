package work.payne.security;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by david.payne on 3/18/16.
 */
public class StateChangeEventHandler {

    ArrayList<PinTriple> pins;

    StateChangeEventHandler(ArrayList<PinTriple> pins) {
        this.pins = pins;
    }

    public void handleGPIOChange(GpioPinDigitalStateChangeEvent event) {
//        this.pins.get(event.getPin());\
        PinTriple p = getPinTriple(event.getPin().getPin());

        System.out.println(" --> GPIO PIN STATE CHANGE: " + p.getFriendlyName() + " = " + event.getState());

        if(Security.armed && event.getState().isLow()) {
//            System.out.println("")
        }

    }


    private PinTriple getPinTriple(Pin p) {
        PinTriple result = null;
        for(int i = 0; i < pins.size(); i ++) {
            if (pins.get(i).getPinName() == p.getName()) {
                result = pins.get(i);
            }
        }
        return result;
    }
}
