
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

public class RadioViewWidget extends ViewWidget {

    @FXML
    private Slider slider;
    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private ObservableList<String> list = FXCollections.observableArrayList();

    private Radio radio;

    public RadioViewWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        radio = (Radio) sensor;

        detailsWidget = new RadioDetailsWidget(sensor, parent);
        load_fxml("/erg/RadioViewWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slider.adjustValue(radio.getFrequency());

        list.add("Station 1");
        list.add("Station 2");
        list.add("Station 3");

        choice.getItems().addAll(list);

        // slider.valueProperty().addListener((observable, oldValue, newValue) -> {
        //     var new_temp = slider.getValue();
        //     thermostat.setTemperature((double) Math.round(new_temp));
        //     update();
        // });

        choice.valueProperty().addListener((observable, oldValue, newValue) -> {
            String station = choice.getValue();
            if(station.equals("Station 1"))
            {
                radio.setFrequency(88.0);
                slider.adjustValue(88.0);
            }
            else if (station.equals("Station 2"))
            {
                radio.setFrequency(102.3);
                slider.adjustValue(102.3);
            }
            else if (station.equals("Station 3"))
            {
                radio.setFrequency(96.3);
                slider.adjustValue(96.3);
            }

            radio.setChannel(station);
            update();
        });

        super.initialize(url, rb);
    }

    @Override
    public void update() {
        // var new_temp = slider.getValue();
        radio.setFrequency((double) Math.round(slider.getValue()));
        super.update();
    }

}
