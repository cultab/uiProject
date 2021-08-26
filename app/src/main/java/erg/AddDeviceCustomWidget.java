
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class AddDeviceCustomWidget extends CustomWidget implements Initializable {

    @FXML
    ChoiceBox<String> dropdown;
    @FXML
    Button add;

    private final String DEFAULT_SELECTION_TEXT = "Select a device type..";
    private String type;

    public AddDeviceCustomWidget(Device dev, AppController parent) {
        super(dev, parent);
        type = null;

        load_fxml("/erg/AddDeviceViewWidget.fxml");

    }

    public AddDeviceCustomWidget(Device dev, AppController parent, String new_type) {
        super(dev, parent);
        type = new_type;

        load_fxml("/erg/AddDeviceViewWidget.fxml");
    }
    
    public static AddDeviceCustomWidget newWithRoom(AppController parent, String room_name){
        var dev = new Device("New Device", "Undefined", room_name);

        return new AddDeviceCustomWidget(dev, parent);
    }

    public static AddDeviceCustomWidget newWithType(AppController parent, String type){
        var dev = new Device("New Device", "Undefined", "Undefined");

        return new AddDeviceCustomWidget(dev, parent, type);
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // HACK: ?

        if (type == null) {
            dropdown.setItems(FXCollections.observableArrayList(DEFAULT_SELECTION_TEXT, "Lamp", "Temperature Sensor"));
            dropdown.setDisable(false);
            dropdown.getSelectionModel().select(0);
            dropdown.getSelectionModel().selectedItemProperty().addListener((obs, old_value, new_value) -> {
                if (!new_value.equals(DEFAULT_SELECTION_TEXT)) {
                    add.setDisable(false);
                }
            });
        } else {
            dropdown.setItems(FXCollections.observableArrayList(type));
            dropdown.getSelectionModel().select(0);
            dropdown.setDisable(true);
            add.setDisable(false);
        }
    }

    public void add() {
        Device dev;

        switch(dropdown.getSelectionModel().getSelectedItem()) {
        case "Lamp":
            dev = new Lamp("New Lamp", "000.000.0.0", "Room");
            break;
        case "Temperature Sensor":
            dev = new TemperatureSensor("New Temperature Sensor", "000.000.0.0", "Room");
            break;
        default:
            throw new RuntimeException("No such class name.");
        }
        
        parent.newDevice(dev);
        parent.setCurrent_details(dev);

        if (type == null) {
            add.setDisable(true);
            dropdown.getSelectionModel().select(0);
        }
    }
}
