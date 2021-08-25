
package erg;

import javafx.fxml.FXML;
import javafx.scene.image.Image;


public class LampViewWidget extends ViewWidget {

    private Image onImg;
    private Image offImg;

    private Lamp lamp;

    public LampViewWidget(Lamp sensor, AppController parent) {
        super(sensor, parent);
        lamp = (Lamp)sensor;

        onImg  = new Image(getClass().getResource("/erg/LampOn.png").toString());
        offImg = new Image(getClass().getResource("/erg/LampOff.png").toString());

        load_fxml("/erg/LampViewWidget.fxml");
    }

    // @Override
    // public void initialize(URL url, ResourceBundle rb) {
    //     super.initialize(url, rb);
    // }

    @FXML
    @Override
    public void update() {
        if(lamp.isPoweredOn()) {
            image.setImage(onImg);
        } else {
            image.setImage(offImg);
        }

        super.update();
    }

    @FXML
    public void toggle() {
        lamp.setPowerStatus(!lamp.isPoweredOn());
        update();
    }

}
