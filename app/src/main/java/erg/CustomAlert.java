
package erg;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class CustomAlert extends Alert {

    public CustomAlert(AlertType type) {
        super(type);
        Platform.runLater(() -> setWidth(-1));
        Platform.runLater(() -> setHeight(-1));
        Platform.runLater(() -> setResizable(true));

    }

}
