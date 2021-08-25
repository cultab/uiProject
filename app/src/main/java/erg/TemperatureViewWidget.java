
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class TemperatureViewWidget extends ViewWidget {

    @FXML
    private Slider slider;

    private TemperatureSensor thermostat;

    public TemperatureViewWidget(TemperatureSensor sensor, AppController parent) {
        super(sensor, parent);
        thermostat = (TemperatureSensor)sensor;

        load_fxml("/erg/TempViewWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slider.adjustValue(thermostat.getTemperature());

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            var new_temp = slider.getValue();
            thermostat.setTemperature((double)Math.round(new_temp));
            super.update();
        });

        super.initialize(url, rb);
    }
}
