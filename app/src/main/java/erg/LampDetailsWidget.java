
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class LampDetailsWidget extends DetailsWidget {

    Lamp lamp;

    public LampDetailsWidget(Lamp sensor) {
        super(sensor);
        lamp = (Lamp) sensor;
        load_fxml("/erg/LampDetailsWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update();
    }

    @FXML
    @Override
    public void update() {
        String status_text;
        Image new_image;

        if (lamp.isPoweredOn()) {
            status_text = "On";
            new_image = new Image(getClass().getResource("/erg/LampOn.png").toString());
        } else {
            status_text = "Off";
            new_image = new Image(getClass().getResource("/erg/LampOff.png").toString());
        }

        status.setText(status_text);
        image.setImage(new_image);

        super.update();
    }

}
