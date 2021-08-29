
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class TemperatureViewWidget extends ViewWidget {

    @FXML
    private Slider slider;

    private Thermostat thermostat;

    public TemperatureViewWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        thermostat = (Thermostat) sensor;

        detailsWidget = new TemperatureDetailsWidget(thermostat, parent);
        onImg = new Image(getClass().getResource("/erg/ThermostatOn.png").toString());
        offImg = new Image(getClass().getResource("/erg/ThermostatOff.png").toString());
        onSound = new AudioClip(getClass().getResource("/erg/TempOn.wav").toString());
        offSound = new AudioClip(getClass().getResource("/erg/TempOff.wav").toString());
        load_fxml("/erg/TempViewWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slider.adjustValue(thermostat.getTemperature());

        // slider.valueProperty().addListener((observable, oldValue, newValue) -> {
        //     var new_temp = slider.getValue();
        //     thermostat.setTemperature((double) Math.round(new_temp));
        //     update();
        // });

        super.initialize(url, rb);
    }

    @FXML
    public void setTemperature(Double temp) {
        if (temp != thermostat.getTemperature()) {
            thermostat.setTemperature(temp);
        }
        update();
    }

    @Override
    public void update() {
        // var new_temp = slider.getValue();
        thermostat.setTemperature((double) Math.round(slider.getValue()));
        super.update();
    }

}
