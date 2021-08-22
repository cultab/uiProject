package erg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MyController {
    @FXML
    private ImageView Lamp;
    @FXML
    private RadioButton On;
    @FXML
    private RadioButton Off;

    @FXML
    public void TurnLampOn(ActionEvent event)
    {
        if(On.isSelected())
        {
            File file = new File("/home/asimakis/Code/uiProject2/app/src/main/resources/LampOn.jpeg");
            Lamp.setImage(new Image(file.toURI().toString()));

        }
        else if (Off.isSelected())
        {
            File file = new File("/home/asimakis/Code/uiProject2/app/src/main/resources/LampOff.jpeg");
            Lamp.setImage(new Image(file.toURI().toString()));

        }
    }
}
