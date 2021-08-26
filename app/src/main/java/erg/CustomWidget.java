
package erg;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class CustomWidget extends VBox {

    protected AppController parent;

    public CustomWidget(AppController parent) {
        this.parent = parent;
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
