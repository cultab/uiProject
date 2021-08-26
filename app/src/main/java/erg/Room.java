
package erg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private static final long serialversionUID = 1000L;

    String room_name;
    List<String> device_IPs;

    public Room() {
        this.room_name = "N/A";
        this.device_IPs = new ArrayList<String>();
    }

    public Room(String room_nae, List<String> device_IPs) {
        this.room_name = room_nae;
        this.device_IPs = device_IPs;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public List<String> getDevice_ips() {
        return device_IPs;
    }

    public String toString() {
        return room_name;
    }

}
