
package erg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class AppController implements Initializable {

    private HashMap<String, Device> devices;
    private HashMap<String, Room> rooms;
    private DetailsWidget currentDetailsWidget;
    private Boolean unsaved_details = false;
    private Alert alert;

    @FXML
    private SplitPane mainSplit;
    @FXML
    private FlowPane flow;
    @FXML
    private TilePane details;
    @FXML
    private ListView<String> listRooms;
    @FXML
    private ListView<String> listDevices;

    // height of vertical listView item
    private final int ROW_HEIGHT = 24;

    public AppController() {
        alert = new Alert(Alert.AlertType.NONE);
        devices = new HashMap<String, Device>();
        rooms = new HashMap<String, Room>();
//         gen_rooms_and_devices();
// 
//         save_to_cfg();

        load_devices();
        // for (var a : devices.entrySet()) {
        // System.out.println(a.getValue().getIP());
        // }
        load_rooms();
        // for (var a : rooms.entrySet()) {
        // System.out.println(a.getValue().getRoom_id());
        // }
    }

    public void setCurrent_details(Device device) {
        System.out.println("setting new details");
        if (currentDetailsWidget != null) {
            details.getChildren().remove(currentDetailsWidget);
        }

        switch (device.getClass().getSimpleName()) {
            case "TemperatureSensor":
                currentDetailsWidget = new TemperatureDetailsWidget((TemperatureSensor) device, this);
                break;
            case "Lamp":
                currentDetailsWidget = new LampDetailsWidget((Lamp) device, this);
                break;
            default:
                throw new RuntimeException("No such Device class like" + device.getClass().getSimpleName());
        }

        details.getChildren().add(currentDetailsWidget);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (String s : rooms.keySet()) {
            listRooms.getItems().add(s);
            listRooms.setPrefHeight(listRooms.getItems().size() * ROW_HEIGHT + 2);
        }

        listDevices.getItems().add("Lamp");
        listDevices.getItems().add("Temperature Sensor");
        listDevices.setPrefHeight(listDevices.getItems().size() * ROW_HEIGHT + 2);

    }

    public Boolean getUnsaved_details() {
        return unsaved_details;
    }

    public void setUnsaved_details(Boolean unsaved_details) {
        System.out.println(unsaved_details);
        this.unsaved_details = unsaved_details;
    }

    @FXML
    public void check_unsaved_details() {
        if (unsaved_details) {
            // TODO: create Alert
            System.out.println("Unsaved details Alert!");
        } else {
            System.out.println("Unsaved details are FINE!");
        }
    }

    public void update_details() {
        if (currentDetailsWidget != null) {
            currentDetailsWidget.update();
        }
    }

    @FXML
    public void quit()
    {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        Platform.runLater(() -> alert.setWidth(200));
        Platform.runLater(() -> alert.setHeight(150));
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) flow.getScene().getWindow();
            stage.close();
        }


    }

    public void load_widgets_by_room() {
        flow.getChildren().removeAll(flow.getChildren());
        String selection = listRooms.getSelectionModel().getSelectedItem();

        for (var device : devices.values()) {
            if (device.getRoom_name().equals(selection)) {

                if (device instanceof Lamp)
                    flow.getChildren().add(new LampViewWidget((Lamp) device, this));
                if (device instanceof TemperatureSensor)
                    flow.getChildren().add(new TemperatureViewWidget((TemperatureSensor) device, this));
            }
        }
        System.out.println("room selection" + selection);
        flow.getChildren().add(AddDeviceCustomWidget.newWithRoom(this, selection));
    }

    public void load_widgets_by_device() {
        flow.getChildren().removeAll(flow.getChildren());
        String selection = listDevices.getSelectionModel().getSelectedItem();
        var selection_no_spaces = selection.replaceAll("\\s+", "");

        for (var device : devices.values()) {
            if (device.getClass().getSimpleName().equals(selection_no_spaces)) {
                switch (selection_no_spaces) {
                    case "Lamp":
                        flow.getChildren().add(new LampViewWidget((Lamp) device, this));
                        break;
                    case "TemperatureSensor":
                        flow.getChildren().add(new TemperatureViewWidget((TemperatureSensor) device, this));
                        break;
                }
            }
        }
        flow.getChildren().add(AddDeviceCustomWidget.newWithType(this, selection));
    }

    public void load_devices() {
        var filename = "devices.cfg";

        try (var in = new ObjectInputStream(new FileInputStream(filename))) {

            // Method for deserialization of object
            devices = (HashMap<String, Device>) in.readObject();

        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new RuntimeException("Could not save devices to " + filename);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException.");
        }
    }

    public void newDevice(Device dev) {
        // HACK: randomized key until we use a list
        devices.put("JUST USE A LIST FUCK " + Math.random(), dev);
    }

    @FXML
    public void help(){
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Help Needed");
        alert.showAndWait();
    }

    @FXML
    public void save_to_cfg() {
        String filename = "devices.cfg";

        // Serialization
        try (var out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(devices);
        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            // throw new RuntimeException("Could not save devices to " + filename);
        }

        filename = "rooms.cfg";

        // Serialization
        try (var out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(rooms);
        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new RuntimeException("Could not save rooms to " + filename);
        }

        // Platform.runLater(() -> alert.setResizable(true));
        Platform.runLater(() -> alert.setWidth(200));
        Platform.runLater(() -> alert.setHeight(150));
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Save Succesfull");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void load_rooms() {
        var filename = "rooms.cfg";

        try (var in = new ObjectInputStream(new FileInputStream(filename))) {

            // Method for deserialization of object
            rooms = (HashMap<String, Room>) in.readObject();

        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new RuntimeException("Could not save rooms to " + filename);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException.");
        }
    }

    protected void gen_rooms_and_devices() {
        var l1 = new Lamp("Desk", "192.168.1.2", "Bedroom");
        devices.put(l1.getIP(), l1);
        var t1 = new TemperatureSensor("Thermostat 1", "192.168.1.22", "Bedroom");
        devices.put(t1.getIP(), t1);
        var room2_list = new ArrayList<String>();
        room2_list.add(l1.getIP());
        room2_list.add(t1.getIP());
        var room2 = new Room("Bedroom", room2_list);

        var l2 = new Lamp("Main", "192.168.1.3", "Kitchen");
        devices.put(l2.getIP(), l2);
        var t2 = new TemperatureSensor("Thermostat", "192.168.1.23", "Kitchen");
        devices.put(t2.getIP(), t2);
        var room3_list = new ArrayList<String>();
        room3_list.add(l2.getIP());
        room3_list.add(t2.getIP());
        var room3 = new Room("Kitchen", room3_list);

        var l3 = new Lamp("Main", "192.168.1.4", "Bathroom");
        devices.put(l3.getIP(), l3);
        var room4_list = new ArrayList<String>();
        room4_list.add(l3.getIP());
        var room4 = new Room("Bathroom", room4_list);

        var t4 = new TemperatureSensor("Thermostat", "192.168.1.25", "Basement");
        devices.put(t4.getIP(), t4);
        var room5_list = new ArrayList<String>();
        room5_list.add(t4.getIP());
        var room5 = new Room("Basement", room5_list);

        rooms.put(room2.getRoom_id(), room2);
        rooms.put(room3.getRoom_id(), room2);
        rooms.put(room4.getRoom_id(), room2);
        rooms.put(room5.getRoom_id(), room2);
    }

}
