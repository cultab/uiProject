
package erg;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TempWidget extends VBox {

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

    @FXML
    protected void doSomething() {
        System.out.println("The button was clicked!");
    }
}
