
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LampDetailsWidget extends DetailsWidget implements Initializable {

    @FXML
    ImageView image;

    Lamp sensor;

    @FXML
    TextField IP;
    @FXML
    TextField room_name;
    @FXML
    TextField status;

    public LampDetailsWidget(Lamp sensor) {
        this.sensor = sensor;

        // load fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erg/LampDetailsWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception exception) {
            System.out.println("Could not load LampDetailsWidget.fxml");
            System.out.println(exception.getCause());
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        IP.setText(sensor.getIP());
        room_name.setText(sensor.getRoom_id());

        update();

    }

    @FXML
    @Override
    public void update() {
        String status_text;
        Image new_image;

        if (sensor.getPowered_on()) {
            status_text = "On";
            new_image = new Image(getClass().getResource("/erg/LampOn.jpeg").toString());
        } else {
            status_text = "Off";
            new_image = new Image(getClass().getResource("/erg/LampOff.jpeg").toString());
        }

        status.setText(status_text);
        image.setImage(new_image);
    }

}
