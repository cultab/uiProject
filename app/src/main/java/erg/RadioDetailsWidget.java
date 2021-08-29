
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;

public class RadioDetailsWidget extends DetailsWidget {

    Radio radio;

    @FXML
    LineChart<Number, Double> chart;

    public RadioDetailsWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        radio = (Radio) sensor;

        onImg = new Image(getClass().getResource("/erg/RadioOn.png").toString());
        offImg = new Image(getClass().getResource("/erg/RadioOff.png").toString());
        load_fxml("/erg/RadioDetailsWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        update();
    }

    @FXML
    public void update() {
        status.setText(String.valueOf(radio.getFrequency()));


        super.update();
    }

}
