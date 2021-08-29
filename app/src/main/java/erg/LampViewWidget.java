
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class LampViewWidget extends ViewWidget {

    private Lamp lamp;

    public LampViewWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        lamp = (Lamp) sensor;

        detailsWidget = new LampDetailsWidget(lamp, parent);
        onImg = new Image(getClass().getResource("/erg/LampOn.png").toString());
        offImg = new Image(getClass().getResource("/erg/LampOff.png").toString());
        onSound = new AudioClip(getClass().getResource("/erg/Lamp_Chain.wav").toString());
        offSound = new AudioClip(getClass().getResource("/erg/Lamp_Chain.wav").toString());

        load_fxml("/erg/LampViewWidget.fxml");
    }

}
