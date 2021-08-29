
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class DetailsWidget extends CustomWidget implements Initializable {

    protected Boolean saved;
    protected Device sensor;

    @FXML
    protected ImageView image;
    @FXML
    protected TextField IP;
    @FXML
    protected ComboBox<Room> room_name;
    @FXML
    protected TextField name;
    @FXML
    protected TextField status;
    @FXML
    protected Button save;

    public DetailsWidget(Device sensor, AppController parent) {
        super(parent);
        this.sensor = sensor;
        saved = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        var rooms = parent.getRooms();
        room_name.setItems(FXCollections.observableArrayList(rooms));
        room_name.setPromptText("Select a room");

        ChangeListener<String> set_unsaved = (obs, oldText, newText) -> {
            setSaved(false);
        };
        ChangeListener<Room> set_unsaved_r = (obs, oldText, newText) -> {
            setSaved(false);
        };

        update();

        if (room_name.getSelectionModel().getSelectedIndex() == -1) {
            room_name.getSelectionModel().select(0);
        }

        room_name.getSelectionModel().selectedItemProperty().addListener(set_unsaved_r);
        name.textProperty().addListener(set_unsaved);
        IP.textProperty().addListener(set_unsaved);

    }

    public void update() {
        name.setText(sensor.getName());
        IP.setText(sensor.getIP());

        for (var room : room_name.getItems()) {
            if (room.getName().equals(sensor.getRoom_name())) {
                room_name.getSelectionModel().select(room);
            }
        }
    }

    @FXML
    public void save() {
        sensor.setIP(IP.getText());
        sensor.setName(name.getText());

        var new_room = room_name.getSelectionModel().getSelectedItem();

        if (!sensor.getRoom_name().equals(new_room.getName())) {
            parent.changeDeviceRoom(sensor, sensor.getRoom_name(), new_room);
        }

        sensor.setRoom_name(new_room.getName());

        for (var room : parent.getRooms()) {
            if (room.getName().equals(sensor.getRoom_name())) {
                if (!room.getDevices().contains(sensor)) {
                    parent.newDevice(sensor);
                }
            }
        }

        setSaved(true);
        update();
        parent.invalidateCache(sensor);
        parent.forceReloadLastView();
    }

    public void setSaved(Boolean s) {
        saved = s;
        save.setDisable(s);
        parent.setUnsaved_details(!s);
    }

    @FXML
    public void delete(){
        parent.delete(sensor);
    }

}
