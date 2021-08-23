
package erg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TemperatureSensor extends Device implements Serializable {

    private static final long serialversionUID = 1000L;

    int temperature;
    List<Integer> temp_history;

    public TemperatureSensor(String IP, String room_id) {
        super(IP, room_id);
        temperature = 25;
        temp_history = new ArrayList<Integer>();

        for (int i = 0; i < 25; i++) {
            temp_history.add(new Random().nextInt(15) + 20);
        }
    }

    public List<Integer> getTemp_history() {
        return temp_history;
    }

    public void setTemp_history(List<Integer> temp_history) {
        this.temp_history = temp_history;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

}
