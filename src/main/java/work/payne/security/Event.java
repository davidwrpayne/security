package work.payne.security;

import javax.print.DocFlavor;
import java.util.ArrayList;

/**
 * Created by david.payne on 3/28/2016.
 */
public class Event {


    public enum MsgType { AliveMsg, Armed, Disarmed }

    MsgType type;
    String user;
    ArrayList<String> zones;

    public Event(MsgType type, ArrayList<String> zones, String user) {
        this.type = type;
        this.zones = zones;
        this.user = user;
    }

}
