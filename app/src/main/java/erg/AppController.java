
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

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

    private ObservableList<Room> rooms;
    private DetailsWidget currentDetailsWidget;
    private Boolean unsaved_details = false;
    private CustomAlert alert;
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
        alert = new CustomAlert(Alert.AlertType.NONE);
        // devices = new ArrayList<Device>();
        rooms = FXCollections.observableArrayList();

        devicesViewCache = new HashMap<String, List<CustomWidget>>();
        roomsViewCache = new HashMap<String, List<CustomWidget>>();
        gen_rooms_and_devices();
        save_to_cfg();
        load_from_cfg();
    }

    public void setCurrent_details(DetailsWidget widget) {
        System.out.println("unsaved" + unsaved_details);
        if (unsaved_details) {
            return;
        }
        if (currentDetailsWidget != null) {
            details.getChildren().remove(currentDetailsWidget);
        }

        currentDetailsWidget = widget;

        details.getChildren().add(currentDetailsWidget);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (var room : rooms) {
            if (!listRooms.getItems().contains(room.getRoom_name())) {
                listRooms.getItems().add(room.getRoom_name());
            }
            System.out.println("");
        }

        listDevices.getItems().add("Lamp");
        listDevices.getItems().add("Thermostat");

        listRooms.setPrefHeight(listRooms.getItems().size() * ROW_HEIGHT + 2);
        listDevices.setPrefHeight(listDevices.getItems().size() * ROW_HEIGHT + 2);

        // rooms.addListener((ListChangeListener<Room>) change -> {
        //     change.getAddedSubList();
        // });
        ListChangeListener<Device> deviceListener = change -> {
            change.next();
            for (var added_dev : change.getAddedSubList() ) {
                roomsViewCache.remove(added_dev.getRoom_name());
                devicesViewCache.remove(added_dev.getClass().getSimpleName());
            }
            for (var removed_dev : change.getRemoved() ) {
                roomsViewCache.remove(removed_dev.getRoom_name());
                devicesViewCache.remove(removed_dev.getClass().getSimpleName());
            }
        };

        for (var room : rooms) {
            // TODO: lookup InvalidationListener
            room.getDevices().addListener(deviceListener);
        }

    }

    public Boolean getUnsaved_details() {
        return unsaved_details;
    }

    public void setUnsaved_details(Boolean unsaved_details) {
        System.out.println("SET unsaved -> " + unsaved_details);
        this.unsaved_details = unsaved_details;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void changeDeviceRoom(Device dev, String old_room_name, Room new_room) {
        for (var old_room : rooms) {
            if (old_room.getRoom_name().equals(old_room_name)) {
                old_room.removeDevice(dev);
                new_room.addDevice(dev);
            }
        }

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
            for (var room : rooms) {
                if (room.getRoom_name().equals(selection)) {
                    for (var device : room.getDevices()) {
                        widgets_to_add.add(Otmac.viewWidget(device, this));
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

        children.removeAll(children);

        if (!devicesViewCache.containsKey(selection)) {
            for (var room : rooms) {
                for (var device : room.getDevices()) {
                    if (device.getClass().getSimpleName().equals(selection)) {
                        var widget = Otmac.viewWidget(device, this);
                        widget.setRoom_name_visible();
                        widgets_to_add.add(widget);
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
        var filename = "rooms.cfg";

        try (var in = new ObjectInputStream(new FileInputStream(filename))) {

            // Method for deserialization of object
            rooms.addAll((ArrayList<Room>) in.readObject());
            // rooms =

        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new RuntimeException("Could load from " + filename);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new RuntimeException("Could load from " + filename);
        }
    }

    public void newDevice(Device dev) {
        for (var room : rooms) {
            if (room.getRoom_name().equals(dev.getRoom_name())) {
                room.addDevice(dev);
            }
        }
    }

    @FXML
    public void save_to_cfg() {
        var filename = "rooms.cfg";

        // Serialization
        try (var out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(new ArrayList<Room>(rooms));
        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println(e);
            throw new RuntimeException("Could not save to " + filename);
        }

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Save Succesfull");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    public void help() {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText("Help Needed");
        alert.showAndWait();
    }

    protected void gen_rooms_and_devices() {
        var l1 = new Lamp("Desk", "192.168.1.2", "Bedroom");
        // devices.add(l1);
        var t1 = new Thermostat("Thermostat 1", "192.168.1.22", "Bedroom");
        // devices.add(t1);
        ObservableList<Device> room2_list = FXCollections.observableArrayList();
        room2_list.add(l1);
        room2_list.add(t1);
        var room2 = new Room("Bedroom", room2_list);

        var l2 = new Lamp("Main", "192.168.1.3", "Kitchen");
        // devices.add(l2);
        var t2 = new Thermostat("Thermostat", "192.168.1.23", "Kitchen");
        // devices.add(t2);
        ObservableList<Device> room3_list = FXCollections.observableArrayList();
        room3_list.add(l2);
        room3_list.add(t2);
        var room3 = new Room("Kitchen", room3_list);

        var l3 = new Lamp("Main", "192.168.1.4", "Bathroom");
        // devices.add(l3);
        ObservableList<Device> room4_list = FXCollections.observableArrayList();
        room4_list.add(l3);
        var room4 = new Room("Bathroom", room4_list);

        var t4 = new Thermostat("Thermostat", "192.168.1.25", "Basement");
        // devices.add(t4);
        ObservableList<Device> room5_list = FXCollections.observableArrayList();
        room5_list.add(t4);
        var room5 = new Room("Basement", room5_list);

        var room6 = new Room("Living Room", FXCollections.observableArrayList());
        var room7 = new Room("Main Hall", FXCollections.observableArrayList());

        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);
        rooms.add(room7);
    }

}
