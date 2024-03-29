
package erg;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class ViewWidget extends CustomWidget implements Initializable {
    protected Device sensor;

    @FXML
    protected ImageView image;
    @FXML
    protected Label name;
    @FXML
    protected Label room_name;
    
    protected AudioClip onSound;
    protected AudioClip offSound;
    protected Image onImg;
    protected Image offImg;

    protected DetailsWidget detailsWidget;

    public ViewWidget(Device sensor, AppController parent) {
        super(parent);
        this.sensor = sensor;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(sensor.getName());
        room_name.setText(sensor.getRoom_name());

        if (sensor.isPoweredOn()) {
            image.setImage(onImg);
        } else {
            image.setImage(offImg);
        }
    }

    public void update() {
        name.setText(sensor.getName());
        room_name.setText(sensor.getRoom_name());

        if (sensor.isPoweredOn()) {
            image.setImage(onImg);
        } else {
            image.setImage(offImg);
        }

        detailsWidget.update();
    }

    @FXML
    public void set_details() {
        parent.setCurrent_details(detailsWidget);
    }

    public void setRoom_name_visible() {
        room_name.setVisible(true);
    }

    @FXML
    public void toggle() {
        sensor.setPowerStatus(!sensor.isPoweredOn());

        if (sensor.isPoweredOn()) {
            if (!onSound.isPlaying()) {
                onSound.play();
            }
        } else {
            if (!offSound.isPlaying()) {
                offSound.play();
            }
        }
        update();
    }
}
