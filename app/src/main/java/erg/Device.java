
package erg;

public class Device {
    public Device(String IP, String room_id) {
        this.IP = IP;
        this.room_id = room_id;

    }

    public String getIP() {
        return IP;
    }
    public void setIP(String iP) {
        IP = iP;
    }
    public String getRoom_id() {
        return room_id;
    }
    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    String IP;
    String room_id;
}
