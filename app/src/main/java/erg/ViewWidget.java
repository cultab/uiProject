
package erg;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ViewWidget extends CustomWidget implements Initializable {

    @FXML
    protected Label name;
    @FXML
    protected Label room_name;
    protected DetailsWidget detailsWidget;

    public ViewWidget(Device sensor, AppController parent) {
        super(sensor, parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(sensor.getName());
        room_name.setText(sensor.getRoom_name());
    }

    public void update() {
        name.setText(sensor.getName());
        room_name.setText(sensor.getRoom_name());

        detailsWidget.update();
    }

    @FXML
    public void set_details() {
        parent.setCurrent_details(detailsWidget);
    }

}
