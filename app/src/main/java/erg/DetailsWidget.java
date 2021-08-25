
package erg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class DetailsWidget extends VBox implements Initializable {
    protected Device sensor;

    @FXML
    protected ImageView image;
    @FXML
    private TextField IP;
    @FXML
    private TextField room_name;
    @FXML
    protected TextField status;

    public DetailsWidget(Device sensor) {
        this.sensor = sensor;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IP.setText(sensor.getIP());
        room_name.setText(sensor.getRoom_name());
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

    public void update() {
        IP.setText(sensor.getIP());
        room_name.setText(sensor.getRoom_name());
    }
}
