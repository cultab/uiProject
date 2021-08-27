
package erg;

import java.util.ArrayList;
import java.util.List;
// import java.util.Random;

public class Thermostat extends Device {
    Double temperature;
    List<Double> temp_history;

    public Thermostat(String name, String IP, String room_id) {
        super(name, IP, room_id);
        temperature = 25.0;
        temp_history = new ArrayList<Double>();
    }

    public Thermostat(String room_name) {
        super(room_name);
        temperature = 25.0;
        temp_history = new ArrayList<Double>();
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
