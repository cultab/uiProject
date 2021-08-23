
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AppController implements Initializable {

    @FXML
    SplitPane mainSplit;
    @FXML
    Label fucking_label;

    @FXML
    private ImageView Lamp;
    @FXML
    private RadioButton On;
    @FXML
    private RadioButton Off;

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

    @FXML
    public void TurnLampOn(ActionEvent event)
    {
        if(On.isSelected())
            Lamp.setImage(new Image (String.valueOf(getClass().getResource("/erg/LampOn.jpeg"))));
        else if (Off.isSelected())
            Lamp.setImage(new Image (String.valueOf(getClass().getResource("/erg/LampOff.jpeg"))));
    }
}
