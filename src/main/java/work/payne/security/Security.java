package work.payne.security;

import com.pi4j.io.gpio.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by david.payne on 3/17/16.
 */
public class Security {
    private static final Logger log= Logger.getLogger( Security.class.getName() );

    GpioController gpioController;
    Map<String,Pin> pinMap;

    Security() {
        log.info("Initializing Security Application");
        gpioController = GpioFactory.getInstance();
        pinMap = new HashMap<String, Pin>();
        this.addPins(pinMap);
    }


    //Add configuration for all the pins.
    public void addPins(Map<String,Pin> pinMap) {
        pinMap.put("Bedroom Windows",RaspiPin.GPIO_01);
        pinMap.put("Front Door",RaspiPin.GPIO_01);
        pinMap.put("Back Door",RaspiPin.GPIO_01);
        pinMap.put("Garage Door",RaspiPin.GPIO_01);
        pinMap.put("Garage Motion Detector",RaspiPin.GPIO_01);
        pinMap.put("Hall Motion Detector",RaspiPin.GPIO_01);
        pinMap.put("Downstairs Bedroom Windows",RaspiPin.GPIO_01);
    }

    private void boot() throws InterruptedException {
        log.info("Booting App");
        final GpioPinDigitalOutput pin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);

        // set shutdown state for this pin
        pin.setShutdownOptions(true, PinState.LOW);

        System.out.println("--> GPIO state should be: ON");

        Thread.sleep(5000);

        // turn off gpio pin #01
        pin.low();
        System.out.println("--> GPIO state should be: OFF");

        Thread.sleep(5000);

        // toggle the current state of gpio pin #01 (should turn on)
        pin.toggle();
        System.out.println("--> GPIO state should be: ON");

        Thread.sleep(5000);

        // toggle the current state of gpio pin #01  (should turn off)
        pin.toggle();
        System.out.println("--> GPIO state should be: OFF");

        Thread.sleep(5000);

        // turn on gpio pin #01 for 1 second and then off
        System.out.println("--> GPIO state should be: ON for only 1 second");
        pin.pulse(1000, true); // set second argument to 'true' use a blocking call

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        gpioController.shutdown();
    }



    private void handleGPIOChange() {

    }


    public static void main(String args[]) {
        Security app = new Security();
        try {
            app.boot();
        } catch ( Exception e ) {
            log.log(Level.SEVERE,"Application Failed!",e);
        }
    }



}
