
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

        detailsWidget = new TVDetailsWidget(sensor, parent);
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

        super.initialize(url, rb);
    }

    @Override
    public void update() {
        // var new_temp = slider.getValue();
        radio.setFrequency((double) Math.round(slider.getValue()));
        super.update();
    }

}
