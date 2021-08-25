
package erg;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CustomWidget extends VBox {

    @FXML
    protected ImageView image;

    protected Device sensor;
    protected AppController parent;

    public CustomWidget(Device sensor) {
        this.sensor = sensor;
    }

    protected void load_fxml(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception.getCause());
            throw new RuntimeException("Could not load " + path);
        }
    }

}
