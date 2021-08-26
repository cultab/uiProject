
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    protected TextField room_name;
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
        name.setText(sensor.getName());
        room_name.setText(sensor.getRoom_name());
        IP.setText(sensor.getIP());

        ChangeListener<String> set_unsaved = (obs, oldText, newText) -> {
            setSaved(false);
        };

        name.textProperty().addListener(set_unsaved);
        room_name.textProperty().addListener(set_unsaved);
        IP.textProperty().addListener(set_unsaved);
    }

    public void update() {
        name.setText(sensor.getName());
        room_name.setText(sensor.getRoom_name());
        IP.setText(sensor.getIP());
    }

    @FXML
    public void save() {
        sensor.setIP(IP.getText());
        sensor.setName(name.getText());
        sensor.setRoom_name(room_name.getText());
        setSaved(true);
        update();
    }

    public void setSaved(Boolean s) {
        saved = s;
        save.setDisable(s);
        parent.setUnsaved_details(!s);
    }

}
