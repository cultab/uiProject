
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class TemperatureDetailsWidget extends DetailsWidget {

    TemperatureSensor thermometer;
    XYChart.Series<Number, Double> series;

    @FXML
    LineChart<Number, Double> chart;

    public TemperatureDetailsWidget(TemperatureSensor sensor, AppController parent) {
        super(sensor, parent);
        thermometer = (TemperatureSensor) sensor;
        series = new XYChart.Series<Number, Double>();
        load_fxml("/erg/TemperatureDetailsWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        chart.getData().add(series);
        int i = 0;
        System.out.println("here");
        for (var temp : thermometer.getTemp_history()) {
        System.out.println("here1");
            series.getData().add(new XYChart.Data<Number, Double>(i++, temp));
        System.out.println("here2:W");
        }
        update();
    }

    @FXML
    public void update() {
        status.setText(thermometer.getTemperature().toString());

        var data = series.getData();

        if (data.isEmpty()) {
            data.add(new XYChart.Data<Number, Double>((Integer)1 , thermometer.getTemperature()));
        } else {
            data.add(new XYChart.Data<Number, Double>((Integer)data.get(data.size() - 1).getXValue() + 1 , thermometer.getTemperature()));
        }

        super.update();
    }

}
