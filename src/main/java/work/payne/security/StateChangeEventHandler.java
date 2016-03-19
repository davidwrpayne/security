package work.payne.security;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;

/**
 * Created by david.payne on 3/18/16.
 */
public class StateChangeEventHandler {


    public void handleGPIOChange(GpioPinDigitalStateChangeEvent event) {
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
    }

}
