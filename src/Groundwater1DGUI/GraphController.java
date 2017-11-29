package Groundwater1DGUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphController implements Initializable{
@FXML
public LineChart chart1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chart1.getXAxis().setLabel("Node");
        chart1.getYAxis().setLabel("h[m]");
        chart1.getData().add(Controller.series);
    }
}
