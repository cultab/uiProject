
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class LampDetailsWidget extends DetailsWidget {

    Lamp lamp;

    public LampDetailsWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        lamp = (Lamp) sensor;

        onImg = new Image(getClass().getResource("/erg/LampOn.png").toString());
        offImg = new Image(getClass().getResource("/erg/LampOff.png").toString());
        load_fxml("/erg/LampDetailsWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        update();
    }

    // @FXML
    // @Override
    // public void update2() {
    // if (lamp.isPoweredOn()) {
    // status.setText("On");
    // image.setImage(on);
    // } else {
    // status.setText("Off");
    // image.setImage(off);
    // }
    //
    // super.update();
    // }
    //
    @FXML
    @Override
    public void update() {
        if (lamp.isPoweredOn()) {
            status.setText("On");
        } else {
            status.setText("Off");
        }

        super.update();
    }

}
