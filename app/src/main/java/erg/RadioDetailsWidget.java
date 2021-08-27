
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class RadioDetailsWidget extends DetailsWidget {

    Radio radio;

    @FXML
    LineChart<Number, Double> chart;

    public RadioDetailsWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        radio = (Radio) sensor;
        load_fxml("/erg/RadioDetailsWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        update();
    }

    @FXML
    public void update() {
        status.setText("Statiom 1");

        super.update();
    }

}
