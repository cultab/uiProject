
package erg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class TVDetailsWidget extends DetailsWidget {

    TV tv;

    @FXML
    protected TextField volume;


    public TVDetailsWidget(Device sensor, AppController parent) {
        super(sensor, parent);
        tv = (TV) sensor;
        onImg = new Image(getClass().getResource("/erg/TVOn.png").toString());
        offImg = new Image(getClass().getResource("/erg/TVOff.png").toString());
        load_fxml("/erg/TVDetailsWidget.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        update();
    }

    @FXML
    public void update() {
        status.setText(tv.getChannel());
        volume.setText(String.valueOf(tv.getVolume()));

        super.update();
    }

}
