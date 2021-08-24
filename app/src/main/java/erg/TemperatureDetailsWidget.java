
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

public class TemperatureDetailsWidget extends DetailsWidget implements Initializable {

    @FXML
    ImageView image;

    TemperatureSensor sensor;

    @FXML
    TextField IP;
    @FXML
    TextField room_name;
    @FXML
    Slider temperature;
    @FXML
    LineChart<Number,Number> chart;

    public TemperatureDetailsWidget(TemperatureSensor sensor) {
        this.sensor = sensor;

        // load fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erg/TemperatureDetailsWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception exception) {
            System.out.println("Could not load TemperatureDetailsWidget.fxml");
            System.out.println(exception.getCause());
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        image.setImage(new Image(getClass().getResource("/erg/thermometer.png").toString()));

        update();

        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
        int i = 0;
        for (var temp : sensor.getTemp_history()) {
            series.getData().add(new XYChart.Data<Number,Number>(i++, temp));
        }

        chart.getData().add(series);


    }

    @FXML
    public void update() {
        IP.setText(sensor.getIP());
        // TODO: use room id to find room name
        room_name.setText(sensor.getRoom_id());
        temperature.adjustValue(sensor.getTemperature());
    }
}
