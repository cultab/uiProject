
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;

public class AppController implements Initializable {

    @FXML
    SplitPane mainSplit;

    public AppController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainSplit.getItems().add(new TemperatureDetailsWidget(new TemperatureSensor("192.168.1.29", "0xDEADBEEF")));
        mainSplit.getItems().add(new LampDetailsWidget(new Lamp("192.168.1.29", "0xDEADBEEF")));
    }

    @FXML
    void doStuff(){}

}
