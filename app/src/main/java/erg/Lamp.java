
package erg;

public class Lamp extends Device {

    Boolean powered_on;

    public Lamp(String IP, String room_id) {
        super(IP, room_id);
        powered_on = false;
    }

    public Boolean getPowered_on() {
        return powered_on;
    }

    public void setPowered_on(Boolean powered_on) {
        this.powered_on = powered_on;
    }

}
