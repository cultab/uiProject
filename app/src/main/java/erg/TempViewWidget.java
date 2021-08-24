
package erg;

import java.awt.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TempViewWidget extends VBox {

    @FXML
    private Label label;
    @FXML
    private Slider slider;

    public TempViewWidget() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erg/TempViewWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

//    public void ChangeTemp() {
//
//        textField.setText(String.valueOf(slider.getValue()));
//    }

        public void initialize() {

            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                label.setText(String.valueOf(slider.getValue()));
            });

        }

}
