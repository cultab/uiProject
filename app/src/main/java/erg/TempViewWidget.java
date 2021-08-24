
package erg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class TempViewWidget extends VBox implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Slider slider;
    private TemperatureSensor sensor;
    private AppController parent;

    public TempViewWidget(TemperatureSensor sensor, AppController parent) {
        this.sensor = sensor;
        this.parent = parent;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erg/TempViewWidget.fxml"));
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
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            var new_temp = slider.getValue();
            label.setText(String.valueOf(new_temp));
            sensor.setTemperature(new_temp);
            parent.update_details();
        });
    }

    @FXML
    public void set_details() {
        parent.setCurrent_details(sensor);
    }
}
