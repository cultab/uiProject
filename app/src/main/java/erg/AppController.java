
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

public class AppController implements Initializable {

    @FXML
    SplitPane mainSplit;
    @FXML
    Label fucking_label;

    public AppController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (mainSplit == null) {
            System.out.print("FUCKING AAAAAAAAA");
            System.exit(0);
        }
        mainSplit.getItems().add(new TempWidget());
    }

    @FXML
    void doStuff(){}
}
