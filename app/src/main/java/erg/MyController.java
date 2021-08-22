package erg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MyController {
    @FXML
    public void TurnLampOn(ActionEvent event)
    {
        if(On.isSelected())
        {
            Lamp.setImage("LampOn.jpeg");
        }
    }
}
