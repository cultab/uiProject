
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class LampViewWidget extends ViewWidget {

    private Image onImg;
    private Image offImg;
    private AudioClip click;

    private Lamp lamp;

    public LampViewWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        lamp = (Lamp) sensor;

        detailsWidget = new LampDetailsWidget(lamp, parent);
        onImg = new Image(getClass().getResource("/erg/LampOn.png").toString());
        offImg = new Image(getClass().getResource("/erg/LampOff.png").toString());
        click = new AudioClip(
                getClass().getResource("/erg/Light Pull Chain- Sound Effect [HQ]-xpiz-39Q3mk.wav").toString());

        load_fxml("/erg/LampViewWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (lamp.isPoweredOn()) {
            image.setImage(onImg);
        } else {
            image.setImage(offImg);
        }

        super.initialize(url, rb);
    }

    @FXML
    @Override
    public void update() {

        if (lamp.isPoweredOn()) {
            image.setImage(onImg);
        } else {
            image.setImage(offImg);
        }

        super.update();
    }

    @FXML
    public void toggle() {

        click.play();
        lamp.setPowerStatus(!lamp.isPoweredOn());
        update();
    }

}
