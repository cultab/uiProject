
package erg;

import java.util.ArrayList;
import java.util.List;
// import java.util.Random;

public class TemperatureSensor extends Device {
    Double temperature;
    List<Double> temp_history;

    public TemperatureSensor(String name, String IP, String room_id) {
        super(name, IP, room_id);
        temperature = 25.0;
        temp_history = new ArrayList<Double>();

        // for (int i = 0; i < 25; i++) {
        //     temp_history.add(new Random().nextDouble() + 20);
        // }
    }

    public List<Double> getTemp_history() {
        return temp_history;
    }

    public void setTemp_history(List<Double> temp_history) {
        this.temp_history = temp_history;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
        temp_history.add(temperature);
    }

}
