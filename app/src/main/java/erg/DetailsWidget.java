
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DetailsWidget extends CustomWidget implements Initializable {

    @FXML
    protected TextField room_name;
    @FXML
    private TextField IP;
    @FXML
    protected TextField status;

    public DetailsWidget(Device sensor) {
        super(sensor);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        room_name.setText(sensor.getRoom_name());
        IP.setText(sensor.getIP());
    }

    public void update() {
        IP.setText(sensor.getIP());
    }
}
