
package erg;

public class Lamp extends Device {

    Boolean powered_on;

    public Lamp(String name, String IP, String room_id) {
        super(name, IP, room_id);
    }

    public Lamp(String room_name) {
        super(room_name);
    }

}
