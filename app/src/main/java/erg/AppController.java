
package erg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
    private TilePane flow;
    @FXML
    private FlowPane details;
    @FXML
    private ListView<String> listRooms;
    @FXML
    private ListView<String> listDevices;

    // height of vertical listView item
    private final int ROW_HEIGHT = 24;

    private viewType lastView;

    private enum viewType {
        ROOMS,
        DEVICES,
        NONE
    }

    public AppController() {
        alert = new CustomAlert(Alert.AlertType.NONE);
        rooms = FXCollections.observableArrayList();

        devicesViewCache = new HashMap<String, List<CustomWidget>>();
        roomsViewCache = new HashMap<String, List<CustomWidget>>();

        lastView = viewType.NONE;

        try {
            load_from_cfg();
        } catch (RuntimeException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            System.out.println("==============================================");
            System.out.println("#           Generating rooms anew!           #");
            System.out.println("==============================================");
            gen_rooms_and_devices();
            save_to_cfg();
        }
    }

    public void setCurrent_details(DetailsWidget widget) {
        System.out.println("unsaved" + unsaved_details);

        if (!check_unsaved_details()) {
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

        Image ww = new Image(String.valueOf(getClass().getResource("/erg/Intro.jpg")));
        //Th trith false
        BackgroundSize backgroundSize = new BackgroundSize(900, 600, false, false,false, false);
        BackgroundImage backgroundImage = new BackgroundImage(ww, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        flow.setBackground(background);

        for (var room : rooms) {
            if (!listRooms.getItems().contains(room.getName())) {
                listRooms.getItems().add(room.getName());
            }
            System.out.println("");
        }

        listDevices.getItems().add("Lamp");
        listDevices.getItems().add("Thermostat");
        listDevices.getItems().add("Radio");
        listDevices.getItems().add("TV");

        listRooms.setPrefHeight(listRooms.getItems().size() * ROW_HEIGHT + 2);
        listDevices.setPrefHeight(listDevices.getItems().size() * ROW_HEIGHT + 2);

        // rooms.addListener((ListChangeListener<Room>) change -> {
        //     change.getAddedSubList();
        // });
        ListChangeListener<Device> deviceListener = change -> {
            change.next();
            for (var added_dev : change.getAddedSubList() ) {
                invalidateCache(added_dev);
            }
            for (var removed_dev : change.getRemoved() ) {
                invalidateCache(removed_dev);
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
        this.unsaved_details = unsaved_details;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void changeDeviceRoom(Device dev, String old_room_name, Room new_room) {
        for (var old_room : rooms) {
            if (old_room.getName().equals(old_room_name)) {
                System.out.println("Remove from " + old_room_name);
                old_room.removeDevice(dev);
                System.out.println("Add to " + new_room.getName());
                new_room.addDevice(dev);

                invalidateCache(old_room_name);
                invalidateCache(dev);
            }
        }
    }

    @FXML
    public Boolean check_unsaved_details() {
        if (unsaved_details) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("You have unsaved edits for a device!");
            alert.setContentText("Do you want to discard them?");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @FXML
    public void quit() {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to quit?");
        alert.setTitle("Exiting the app ...");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) flow.getScene().getWindow();
            stage.close();
        }

    }

    public void invalidateCache(Device dev) {
        devicesViewCache.remove(dev.getClass().getSimpleName());
        roomsViewCache.remove(dev.getRoom_name());
    }

    public void invalidateCache(String room_name) {
        roomsViewCache.remove(room_name);
    }

    public void load_widgets_by_room() {
        lastView = viewType.ROOMS;

        var children = flow.getChildren();
        String selection = listRooms.getSelectionModel().getSelectedItem();

        var widgets_to_add = Collections.synchronizedList(new LinkedList<CustomWidget>());

        var threads = new ArrayList<Thread>();
        Image ww = new Image(String.valueOf(getClass().getResource("/erg/Intro.jpg")));

        if(selection.equals("Bedroom"))
             ww = new Image(String.valueOf(getClass().getResource("/erg/Bedroom.jpg")));
        else if(selection.equals("Kitchen"))
            ww = new Image(String.valueOf(getClass().getResource("/erg/Kitchen.jpg")));
        else if(selection.equals("Bathroom"))
            ww = new Image(String.valueOf(getClass().getResource("/erg/Bathroom2.jpg")));
        else if(selection.equals("Garage"))
            ww = new Image(String.valueOf(getClass().getResource("/erg/Garage.jpg")));
        else if(selection.equals("Living Room"))
            ww = new Image(String.valueOf(getClass().getResource("/erg/Living Room.jpeg")));
        else if(selection.equals("Main Hall"))
            ww = new Image(String.valueOf(getClass().getResource("/erg/MainEntrance.jpg")));


        //Th trith false
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true,true, false);
        BackgroundImage backgroundImage = new BackgroundImage(ww, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        flow.setBackground(background);

        children.removeAll(children);

        if (!roomsViewCache.containsKey(selection)) {
            for (var room : rooms) {
                if (room.getName().equals(selection)) {
                    for (var device : room.getDevices()) {
                        // widgets_to_add.add(Otmac.viewWidget(device, this));
                        var t = new Thread(() -> widgets_to_add.add(Otmac.viewWidget(device, this)));
                        t.start();
                        threads.add(t);
                    }
                    threads.forEach(t -> {
                        try {
                            t.join();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    });
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
        lastView = viewType.DEVICES;
        var children = flow.getChildren();
        String selection = listDevices.getSelectionModel().getSelectedItem();
        var widgets_to_add = Collections.synchronizedList(new LinkedList<CustomWidget>());
        var threads = new ArrayList<Thread>();

        children.removeAll(children);

        if (!devicesViewCache.containsKey(selection)) {
            for (var room : rooms) {
                for (var device : room.getDevices()) {
                    if (device.getClass().getSimpleName().equals(selection)) {

                        var t = new Thread(() -> {
                            var widget = Otmac.viewWidget(device, this);
                            widget.setRoom_name_visible();
                            widgets_to_add.add(widget);
                        });
                        t.start();
                        threads.add(t);
                    }
                }
                threads.forEach(t -> {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
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

    public void forceReloadLastView() {
        switch(lastView) {
        case ROOMS:
            load_widgets_by_room();
            break;
        case DEVICES:
            load_widgets_by_device();
            break;
        case NONE:
            break;
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
            if (room.getName().equals(dev.getRoom_name())) {
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
        alert.setTitle("Please contact us");
        alert.setContentText("Contact email: cs131118@uniwa.gr");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    protected void gen_rooms_and_devices() {
        var l1 = new Lamp("Desk", "192.168.1.2", "Bedroom");
        // devices.add(l1);
        var t1 = new Thermostat("Thermostat 1", "192.168.1.22", "Bedroom");

        var tv1 = new TV("TV 1", "192.168.1.22", "Bedroom");
        var r1 = new Radio("Radio 1", "192.168.1.22", "Bedroom");
        // devices.add(t1);
        ObservableList<Device> room2_list = FXCollections.observableArrayList();

        room2_list.add(l1);
        room2_list.add(t1);
        room2_list.add(tv1);
        room2_list.add(r1);

        var room2 = new Room("Bedroom", room2_list);

        var l2 = new Lamp("Main 2", "192.168.1.3", "Kitchen");
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

        var t4 = new Thermostat("Thermostat", "192.168.1.25", "Garage");
        // devices.add(t4);
        ObservableList<Device> room5_list = FXCollections.observableArrayList();
        room5_list.add(t4);
        var room5 = new Room("Garage", room5_list);

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
