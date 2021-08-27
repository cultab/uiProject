
package erg;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Room implements Serializable {
    private static final long serialversionUID = 1000L;

    String room_name;
    ObservableList<Device> devices;

    public Room() {
        this.room_name = "N/A";
    }

    public Room(String room_name, ObservableList<Device> devs) {
        this.room_name = room_name;
        this.devices = FXCollections.observableArrayList(devs);
    }

    public String getName() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public ObservableList<Device> getDevices() {
        return devices;
    }

    public String toString() {
        return room_name;
    }

    public void removeDevice(Device dev) {
        devices.remove(dev);
    }

    public void addDevice(Device dev) {
        devices.add(dev);
    }

    /*
     *                        ####################
     *                        # ONE FUCKING HOUR #
     *                        ####################
     *
     * ObservableLists are not Serializable so we need to convert them
     * to ArrayLists to write them to disk.
     *
     * Also the object's constructor is not called before the methods below
     * as such we need to initialize the devices list appropriately.
     *
     *  - fuck
     */

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(room_name);
        out.writeObject(new ArrayList<Device>(devices));
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        room_name = (String)in.readObject();
        var raw = (ArrayList<Device>) in.readObject();
        devices = FXCollections.observableArrayList();
        devices.addAll(raw);
    }
    private void readObjectNoData() throws ObjectStreamException {
        throw new RuntimeException("readObjectNoData not implemented for class Room");
    }
}
