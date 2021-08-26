
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class TVDetailsWidget extends DetailsWidget {

    TV tv;

    @FXML
    LineChart<Number, Double> chart;

    public TVDetailsWidget(TV sensor, AppController parent) {
        super(sensor, parent);
        tv = (TV) sensor;
        load_fxml("/erg/TVDetailsWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        update();
    }

    @FXML
    public void update() {
        status.setText("Channel 1");

        super.update();
    }

}
