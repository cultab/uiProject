package erg;

public class Radio extends Device{

    private Boolean powered_on;
    private Double frequency;
    private String channel;



    public Radio(String name, String IP, String room_name) {
        super(name, IP, room_name);
        powered_on = false;
        frequency = 96.3;
        channel = "Station 1";
    }

    public Boolean isPoweredOn() {
        return powered_on;
    }

    public void setPowerStatus(Boolean powered_on) {
        this.powered_on = powered_on;
    }

    public Double getFrequency() {return frequency;}

    public void setFrequency(Double frequency) {this.frequency = frequency;}

    public String getChannel() { return channel; }

    public void setChannel(String channel) { this.channel = channel; }

}
