
package erg;

import java.io.Serializable;
import java.util.Random;

public class Device implements Serializable {
    private static final long serialversionUID = 1000L;

    String IP;
    String room_name;
    String name;
    Boolean powered_on;

    public Device(String name, String IP, String room_name) {
        this.name = name;
        this.IP = IP;
        this.room_name = room_name;
        this.powered_on = false;
    }

    public Device(String room_name) {
        this.name = "Enter a Name";
        var ip_todo = "192.168.%.%";
        ip_todo = ip_todo.replaceFirst("%", Integer.toString(new Random().nextInt(254) + 1));
        this.IP = ip_todo.replaceFirst("%", Integer.toString(new Random().nextInt(254) + 1));
        this.room_name = room_name;
        this.powered_on = false;
    }

    public Boolean isPoweredOn() {
        return powered_on;
    }

    public void setPowerStatus(Boolean powered_on) {
        this.powered_on = powered_on;
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

    static public long getID() {
        return serialversionUID;
    }

}
