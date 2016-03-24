package work.payne.security;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by david.payne on 3/17/16.
 */
public class Security {
    private static final Logger log= Logger.getLogger( Security.class.getName() );

    GpioController gpioController;
    Map<String,Pin> pinMap;
    StateChangeEventHandler handler;

    Security() {
        log.info("Initializing Security Application");
        gpioController = GpioFactory.getInstance();
        pinMap = new HashMap<String, Pin>();
        handler = new StateChangeEventHandler();
        this.addPins(pinMap);
    }

    //Add configuration for all the pins.
    public void addPins(Map<String,Pin> pinMap) {
        pinMap.put("Bedroom Windows",RaspiPin.GPIO_01);
//        pinMap.put("Front Door",RaspiPin.GPIO_01);
//        pinMap.put("Back Door",RaspiPin.GPIO_01);
//        pinMap.put("Garage Door",RaspiPin.GPIO_01);
//        pinMap.put("Garage Motion Detector",RaspiPin.GPIO_01);
//        pinMap.put("Hall Motion Detector",RaspiPin.GPIO_01);
//        pinMap.put("Downstairs Bedroom Windows",RaspiPin.GPIO_01);
    }

    private void boot() throws InterruptedException {
        log.info("Booting App");

        pinMap.forEach(new BiConsumer<String,Pin>(){
            public void accept(String s, Pin gpioPin) {
                //
                log.info("Setting handler for " + s);

                // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
                GpioPinDigitalInput digitalInputPin = gpioController.provisionDigitalInputPin(gpioPin, PinPullResistance.PULL_DOWN);

                // create and register gpio pin listener
                digitalInputPin.addListener(new GpioPinListenerDigital() {
                    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                        // display pin state on console

                        handler.handleGPIOChange(event);
                    }

                });

            }
        });
    }

    private void halt() {
        log.info("Halting Security App");
        gpioController.shutdown();
    }


    public static void main(String args[]) {
        Security app = new Security();
        try {
            app.boot();
        } catch ( Exception e ) {
            log.log(Level.SEVERE,"Application Failed!",e);
        } finally {
            app.halt();
        }
    }

}
