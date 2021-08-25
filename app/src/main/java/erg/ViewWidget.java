
package erg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ViewWidget extends VBox implements Initializable {

    @FXML
    protected ImageView image;
    @FXML
    protected Label name;
    @FXML
    protected Label room_name;

    protected Device sensor;
    protected AppController parent;

    public ViewWidget(Device sensor, AppController parent) {
        this.sensor = sensor;
        this.parent = parent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(sensor.getName());
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
        name.setText(sensor.getName());
        room_name.setText(sensor.getRoom_name());

        parent.update_details();
    }

    @FXML
    public void set_details() {
        parent.setCurrent_details(sensor);
    }

}
