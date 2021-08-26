
package erg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class AppController implements Initializable {

    private List<Device> devices;
    private List<Room> rooms;
    private DetailsWidget currentDetailsWidget;
    private Boolean unsaved_details = false;
    private Alert alert;
    private HashMap<String, List<CustomWidget>> devicesViewCache;
    private HashMap<String, List<CustomWidget>> roomsViewCache;

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
        devices = new ArrayList<Device>();
        rooms = new ArrayList<Room>();

        devicesViewCache = new HashMap<String, List<CustomWidget>>();
        roomsViewCache = new HashMap<String, List<CustomWidget>>();
        // gen_rooms_and_devices();
        // 
        // save_to_cfg();

        load_from_cfg();
    }

    public void setCurrent_details(DetailsWidget widget) {
        if (currentDetailsWidget != null) {
            details.getChildren().remove(currentDetailsWidget);
        }

        currentDetailsWidget = widget;

        details.getChildren().add(currentDetailsWidget);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (var room : rooms) {
            if (!listRooms.getItems().contains(room.getRoom_id())) {
                listRooms.getItems().add(room.getRoom_id());
            }
            System.out.println("");
        }

        listDevices.getItems().add("Lamp");
        listDevices.getItems().add("Temperature Sensor");

        listRooms.setPrefHeight(listRooms.getItems().size() * ROW_HEIGHT + 2);
        listDevices.setPrefHeight(listDevices.getItems().size() * ROW_HEIGHT + 2);

    }

    public Boolean getUnsaved_details() {
        return unsaved_details;
    }

    public void setUnsaved_details(Boolean unsaved_details) {
        this.unsaved_details = unsaved_details;
    }

    @FXML
    public void check_unsaved_details() {
        if (unsaved_details) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Unsaved details");
            alert.showAndWait();
        }
    }

    @FXML
    public void quit() {
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

        var children = flow.getChildren();
        String selection = listRooms.getSelectionModel().getSelectedItem();
        var widgets_to_add = new ArrayList<CustomWidget>();

        children.removeAll(children);

        if (!roomsViewCache.containsKey(selection)) {
            for (var device : devices) {
                if (device.getRoom_name().equals(selection)) {
                    switch (device.getClass().getSimpleName()) {
                        case "Lamp":
                            widgets_to_add.add(new LampViewWidget((Lamp) device, this));
                            break;
                        case "TemperatureSensor":
                            widgets_to_add.add(new TemperatureViewWidget((TemperatureSensor) device, this));
                            break;
                        default:
                            throw new RuntimeException("Missing switch case for class.");
                    }
                }
            }
            widgets_to_add.add(new AddDeviceCustomWidget(this, null, selection));

            // add to flow
            children.addAll(widgets_to_add);
            // add to cache
            roomsViewCache.put(selection, widgets_to_add);
        } else {
            children.addAll(roomsViewCache.get(selection));
        }

    }

    public void load_widgets_by_device() {
        var children = flow.getChildren();
        String selection = listDevices.getSelectionModel().getSelectedItem();
        var widgets_to_add = new ArrayList<CustomWidget>();
        var selection_no_spaces = selection.replaceAll("\\s+", "");

        children.removeAll(children);

        if (!devicesViewCache.containsKey(selection)) {
            for (var device : devices) {
                if (device.getClass().getSimpleName().equals(selection_no_spaces)) {
                    switch (selection_no_spaces) {
                        case "Lamp":
                            widgets_to_add.add(new LampViewWidget((Lamp) device, this));
                            break;
                        case "TemperatureSensor":
                            widgets_to_add.add(new TemperatureViewWidget((TemperatureSensor) device, this));
                            break;
                        default:
                            throw new RuntimeException("Missing switch case for class.");
                    }
                }
            }
            widgets_to_add.add(new AddDeviceCustomWidget(this, selection, null));

            // add to flow
            children.addAll(widgets_to_add);
            // add to cache
            devicesViewCache.put(selection, widgets_to_add);
        } else {
            children.addAll(devicesViewCache.get(selection));
        }
    }

    public void load_from_cfg() {
        var filename = "devices.cfg";

        try (var in = new ObjectInputStream(new FileInputStream(filename))) {

            // Method for deserialization of object
            devices = (ArrayList<Device>) in.readObject();

        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new RuntimeException("Could not save devices to " + filename);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException.");
        }

        filename = "rooms.cfg";

        try (var in = new ObjectInputStream(new FileInputStream(filename))) {

            // Method for deserialization of object
            rooms = (ArrayList<Room>) in.readObject();

        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new RuntimeException("Could not save rooms to " + filename);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException.");
        }
    }

    public void newDevice(Device dev) {
        devices.add(dev);
    }

    @FXML
    public void help() {
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

    protected void gen_rooms_and_devices() {
        var l1 = new Lamp("Desk", "192.168.1.2", "Bedroom");
        devices.add(l1);
        var t1 = new TemperatureSensor("Thermostat 1", "192.168.1.22", "Bedroom");
        devices.add(t1);
        var room2_list = new ArrayList<String>();
        room2_list.add(l1.getIP());
        room2_list.add(t1.getIP());
        var room2 = new Room("Bedroom", room2_list);

        var l2 = new Lamp("Main", "192.168.1.3", "Kitchen");
        devices.add(l2);
        var t2 = new TemperatureSensor("Thermostat", "192.168.1.23", "Kitchen");
        devices.add(t2);
        var room3_list = new ArrayList<String>();
        room3_list.add(l2.getIP());
        room3_list.add(t2.getIP());
        var room3 = new Room("Kitchen", room3_list);

        var l3 = new Lamp("Main", "192.168.1.4", "Bathroom");
        devices.add(l3);
        var room4_list = new ArrayList<String>();
        room4_list.add(l3.getIP());
        var room4 = new Room("Bathroom", room4_list);

        var t4 = new TemperatureSensor("Thermostat", "192.168.1.25", "Basement");
        devices.add(t4);
        var room5_list = new ArrayList<String>();
        room5_list.add(t4.getIP());
        var room5 = new Room("Basement", room5_list);

        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
    }

}
