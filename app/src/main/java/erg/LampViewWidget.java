
package erg;



import javafx.scene.media.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class LampViewWidget extends VBox implements Initializable {

    @FXML
    private ImageView Lamp;
    @FXML
    private RadioButton On;
    @FXML
    private RadioButton Off;
    @FXML
    private Button details_button;

    private Lamp sensor;
    private AppController parent;

    public LampViewWidget(Lamp sensor, AppController parent) {
        this.sensor = sensor;
        this.parent = parent;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erg/LampViewWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image new_image;

        System.out.println("Initialization");
        if(sensor.getPowered_on())
        {
            On.setSelected(true);
            Off.setSelected(false);
            new_image = new Image(getClass().getResource("/erg/LampOn.jpeg").toString());
            Lamp.setImage(new_image);
        }
        else
        {
            On.setSelected(false);
            Off.setSelected(true);
            new_image = new Image(getClass().getResource("/erg/LampOff.jpeg").toString());
            Lamp.setImage(new_image);
        }
        System.out.println("Initialized");

    }

    @FXML
    public void update() {
        System.out.println("Here");
        Image new_image;
        AudioClip click = new AudioClip(getClass().getResource("/erg/Light Pull Chain- Sound Effect [HQ]-xpiz-39Q3mk.wav").toString());
        System.out.println("Built");
        if(sensor.getPowered_on())
        {
            click.play();
            System.out.println("Played");
            On.setSelected(false);
            Off.setSelected(true);
            sensor.setPowered_on(false);
            new_image = new Image(getClass().getResource("/erg/LampOff.jpeg").toString());
            Lamp.setImage(new_image);
        }
        else
        {
            click.play();
            On.setSelected(true);
            Off.setSelected(false);
            sensor.setPowered_on(true);
            new_image = new Image(getClass().getResource("/erg/LampOn.jpeg").toString());
            Lamp.setImage(new_image);
        }

        parent.update_details();
    }

    @FXML
    public void set_details() {
        parent.setCurrent_details(sensor);
    }
}
