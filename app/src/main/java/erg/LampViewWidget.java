
package erg;

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
        Lamp.setImage(new Image(getClass().getResource("/erg/LampOff.jpeg").toString()));

        On.setSelected(sensor.getPowered_on());
        Off.setSelected(!sensor.getPowered_on());
    }

    @FXML
    public void update() {

        Image new_image;

        if (On.isSelected()) {
            new_image = new Image(getClass().getResource("/erg/LampOn.jpeg").toString());
        } else {
            new_image = new Image(getClass().getResource("/erg/LampOff.jpeg").toString());
        }

        Lamp.setImage(new_image);

        sensor.setPowered_on(On.isSelected());

        parent.update_details();
    }

    @FXML
    public void set_details() {
        parent.setCurrent_details(sensor);
    }

    @FXML
    public void TurnLampOn(ActionEvent event)
    {
        if(On.isSelected())
            Lamp.setImage(new Image(String.valueOf(getClass().getResource("/erg/LampOn.jpeg"))));
        else if (Off.isSelected())
            Lamp.setImage(new Image (String.valueOf(getClass().getResource("/erg/LampOff.jpeg"))));
    }
}
