
package erg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TempWidget extends VBox /*implements Initializable*/ {

    @FXML
    ImageView image;


    public TempWidget() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/erg/TempWidget.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

//     @Override
//     public void initialize(URL url, ResourceBundle rb) {
//         // image = new ImageView(new Image(getClass().getResource("/erg/thermometer.png").toString()));
// 
//     }

    @FXML
    protected void doSomething() {
        System.out.println("The button was clicked!");
    }

}
