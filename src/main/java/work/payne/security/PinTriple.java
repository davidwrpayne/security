package work.payne.security;

import com.pi4j.io.gpio.Pin;

/**
 * Created by david.payne on 3/28/2016.
 */
public class PinTriple {
    Pin rPin;
    int pinNum;
    String name;

    PinTriple(Pin p, int n, String s) {
        this.rPin = p;
        this.pinNum = n;
        this.name = s;
    }

    public int getNum() {
        return pinNum;
    }

    public String getFriendlyName() {
        return name;
    }

    public Pin getPin() {
        return rPin;
    }

    public String getPinName() {
        return this.rPin.getName();
    }
}
