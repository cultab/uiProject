package erg;

public class TV extends Device{

    private Boolean powered_on;
    private int volume;
    private String channel;



    public TV(String name, String IP, String room_name) {
        super(name, IP, room_name);
        powered_on = false;
        volume = 15;
        channel = "Channel 1";
    }

    public Boolean isPoweredOn() {
        return powered_on;
    }

    public void setPowerStatus(Boolean powered_on) {
        this.powered_on = powered_on;
    }

    public int getVolume() {return volume;}

    public void setVolume(int volume) {this.volume = volume;}

    public String getChannel() { return channel; }

    public void setChannel(String channel) { this.channel = channel; }

}
