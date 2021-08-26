
package erg;

import java.io.Serializable;

public class Device implements Serializable {
    private static final long serialversionUID = 1000L;

    String IP;
    String room_name;
    String name;

    public Device(String name, String IP, String room_name) {
        this.name = name;
        this.IP = IP;
        this.room_name = room_name;
    }

    public Device(String room_name) {
        this.name = "Undefined";
        this.IP = "000.000.0.0";
        this.room_name = room_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String iP) {
        IP = iP;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_id) {
        this.room_name = room_id;
    }

}
