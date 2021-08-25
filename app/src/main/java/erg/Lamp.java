
package erg;

public class Lamp extends Device {

    Boolean powered_on;

    public Lamp(String name, String IP, String room_id) {
        super(name, IP, room_id);
        powered_on = false;
    }

    public Boolean isPoweredOn() {
        return powered_on;
    }

    public void setPowerStatus(Boolean powered_on) {
        this.powered_on = powered_on;
    }

}
