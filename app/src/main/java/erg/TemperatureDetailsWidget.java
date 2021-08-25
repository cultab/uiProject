
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class TemperatureDetailsWidget extends DetailsWidget {

    TemperatureSensor thermometer;

    @FXML
    LineChart<Number, Double> chart;

    public TemperatureDetailsWidget(TemperatureSensor sensor, AppController parent) {
        super(sensor, parent);
        thermometer = (TemperatureSensor) sensor;

        load_fxml("/erg/TemperatureDetailsWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status.setText(thermometer.getTemperature().toString());

        XYChart.Series<Number, Double> series = new XYChart.Series<Number, Double>();
        int i = 0;
        for (var temp : thermometer.getTemp_history()) {
            series.getData().add(new XYChart.Data<Number, Double>(i++, temp));
        }

        chart.getData().add(series);

        super.initialize(url, rb);
        update();
    }

    @FXML
    public void update() {
        status.setText(thermometer.getTemperature().toString());

        super.update();
    }

}
