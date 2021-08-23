
package erg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private static final long serialversionUID = 1000L;

    String room_id;
    List<String> device_IPs;

    public Room() {
        this.room_id = "N/A";
        this.device_IPs = new ArrayList<String>();
    }

    public Room(String room_id, List<String> device_IPs) {
        this.room_id = room_id;
        this.device_IPs = device_IPs;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public List<String> getDevice_ips() {
        return device_IPs;
    }

}
