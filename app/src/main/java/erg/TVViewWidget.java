
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

public class TVViewWidget extends ViewWidget {

    @FXML
    private Slider slider;
    @FXML
    private ChoiceBox<String> choice;
    @FXML
    private ObservableList<String> list = FXCollections.observableArrayList();

    private TV tv;

    public TVViewWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        tv = (TV) sensor;

        detailsWidget = new TVDetailsWidget(sensor, parent);
        load_fxml("/erg/TVViewWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        slider.adjustValue(tv.getVolume());

        list.add("Channel 1");
        list.add("Channel 2");
        list.add("Channel 3");

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
        tv.setVolume((int) Math.round(slider.getValue()));
        super.update();
    }

}
