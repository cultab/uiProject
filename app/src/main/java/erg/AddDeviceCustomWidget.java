
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;

public class AddDeviceCustomWidget extends CustomWidget implements Initializable {

    @FXML
    ImageView image;
    @FXML
    ChoiceBox<String> dropdown;
    @FXML
    Button add;

    private final String DEFAULT_SELECTION_TEXT = "Select a device type..";
    private String type;
    private String room_name;

//     public AddDeviceCustomWidget(Device sensor, AppController parent) {
//         super(new Device("rip"), parent);
//         type = null;
// 
//         load_fxml("/erg/AddDeviceViewWidget.fxml");
// 
//     }

    public AddDeviceCustomWidget(AppController parent, String new_type, String new_room_name) {
        super(parent);
        type = new_type;
        room_name = new_room_name;
        if (room_name == null) {
            room_name = "Undefined";
        }

        load_fxml("/erg/AddDeviceViewWidget.fxml");
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
        Device sensor;
        DetailsWidget widget;

        switch(dropdown.getSelectionModel().getSelectedItem()) {
        case "Lamp":
            sensor = new Lamp(room_name);
            widget = new LampDetailsWidget(sensor, parent);
            break;
        case "Temperature Sensor":
            sensor = new TemperatureSensor(room_name);
            widget = new TemperatureDetailsWidget(sensor, parent);
            break;
        default:
            throw new RuntimeException("No such class name.");
        }
        
        parent.newDevice(sensor);
        parent.setCurrent_details(widget);

        if (type == null) {
            add.setDisable(true);
            dropdown.getSelectionModel().select(0);
        }
    }
}
