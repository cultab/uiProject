
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LampDetailsWidget extends VBox implements Initializable {

    @FXML
    ImageView image;

    Lamp sensor;

    @FXML
    TextField IP;
    @FXML
    TextField room_name;
    @FXML
    TextField status;
    @FXML
    LineChart<Number,Number> chart;

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
        image.setImage(new Image(getClass().getResource("/erg/LampOff.jpeg").toString()));

        IP.setText(sensor.getIP());
        // TODO: use room id to find room name
        room_name.setText(sensor.getRoom_id());
        status.setText(sensor.getPowered_on() ? "On": "Off");

//         XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
//         int i = 0;
//         for (var temp : sensor.getTemp_history()) {
//             series.getData().add(new XYChart.Data<Number,Number>(i++, temp));
//         }
// 
//         chart.getData().add(series);

    }

    @FXML
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
