
package erg;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class CustomAlert extends Alert {

    public CustomAlert(AlertType type) {
        super(type);
        Platform.runLater(() -> {
            if (getWidth() < 50 || getHeight() < 50) {
                setResizable(true);
                setWidth(350);
                setHeight(200);
            }
        });
    }
}
