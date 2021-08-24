
package erg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TemperatureSensor extends Device {
    Number temperature;
    List<Number> temp_history;

    public TemperatureSensor(String IP, String room_id) {
        super(IP, room_id);
        temperature = 25;
        temp_history = new ArrayList<Number>();

        for (int i = 0; i < 25; i++) {
            temp_history.add(new Random().nextDouble() + 20);
        }
    }

    public List<Number> getTemp_history() {
        return temp_history;
    }

    public void setTemp_history(List<Number> temp_history) {
        this.temp_history = temp_history;
    }

    public Number getTemperature() {
        return temperature;
    }

    public void setTemperature(Number temperature) {
        this.temperature = temperature;
    }

}
