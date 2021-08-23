
package erg;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class LampViewWidget extends VBox {

    @FXML
    private ImageView Lamp;
    @FXML
    private RadioButton On;
    @FXML
    private RadioButton Off;

    public LampViewWidget() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erg/LampViewWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void TurnLampOn(ActionEvent event)
    {
        if(On.isSelected())
            Lamp.setImage(new Image(String.valueOf(getClass().getResource("/erg/LampOn.jpeg"))));
        else if (Off.isSelected())
            Lamp.setImage(new Image (String.valueOf(getClass().getResource("/erg/LampOff.jpeg"))));
    }
}
