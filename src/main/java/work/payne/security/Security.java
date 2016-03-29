package work.payne.security;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import work.payne.security.examples.ControlGpioExample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    ArrayList<PinTriple> pins;
    StateChangeEventHandler handler;
    Boolean running;

    static Boolean armed;

    Security() {
        log.info("Initializing Security Application");
        gpioController = GpioFactory.getInstance();
        pins = new ArrayList<>();
        this.addPins(pins);

        handler = new StateChangeEventHandler(pins);

        this.running = false;
        this.armed = false;
    }

    //Add configuration for all the pins.
    public void addPins(ArrayList<PinTriple> pins) {
        pins.add(new PinTriple(RaspiPin.GPIO_01,1,"Bedroom Windows"));
        pins.add(new PinTriple(RaspiPin.GPIO_04,4,"Front Door"));
        pins.add(new PinTriple(RaspiPin.GPIO_05,5,"Back Door"));
        pins.add(new PinTriple(RaspiPin.GPIO_06,6,"Garage Door"));
        pins.add(new PinTriple(RaspiPin.GPIO_10,10,"Hall Motion Detector"));
        pins.add(new PinTriple(RaspiPin.GPIO_11,11,"Garage Motion Detector"));
//        pins.add(new PinTriple(RaspiPin.GPIO_01,1,"Bedroom Windows"));

    }

    private void boot() throws InterruptedException {
        log.info("Booting App");
        this.running = true;
        for (Iterator<PinTriple> i = pins.iterator(); i.hasNext(); ) {
            PinTriple pin = i.next();
            //
            log.info("Setting handler for " + pin.getFriendlyName());

            // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
            GpioPinDigitalInput digitalInputPin = gpioController.provisionDigitalInputPin(pin.getPin(), PinPullResistance.PULL_DOWN);

            // create and register gpio pin listener
            digitalInputPin.addListener(new GpioPinListenerDigital() {
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    // display pin state on console
                    handler.handleGPIOChange(event);
                }
            });
        }

        while(this.running){
            Thread.sleep(10);
        }

    }

    private void halt() {
        log.info("Halting Security App");
        gpioController.shutdown();
    }


    public static void main(String args[]) {
        Security app = new Security();
        try {
            app.boot();
//            ControlGpioExample.start();
        } catch ( Exception e ) {
            log.log(Level.SEVERE,"Application Failed!",e);
        } finally {
            app.halt();
        }
    }

}
