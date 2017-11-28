package Groundwater1DGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Button calculateButton;
    public Button graphButton;
    public TextArea textlength;
    public TextArea textnodes;
    public TextArea textcond;
    public TextArea textborder0;
    public TextArea textborderl;
    public TextArea textw0;
    public TextArea textw1;
    public ChoiceBox choice0;
    public ChoiceBox choicel;
    public TableView resultsTable;
    public Label warningLabel;
    public LineChart chartXY;
    TableColumn nodeCol = new TableColumn("Node");
    TableColumn xCol = new TableColumn("x");
    TableColumn wCol = new TableColumn("w");
    TableColumn hCol = new TableColumn("h");
    TableColumn qCol = new TableColumn("q");
    int n;
    double l;
    double k;
    double w0;
    double wl;
    double h0;
    double hl;
    double q0;
    double ql;
    double x;
    String choicestr0;
    String choicestrl;
    int casenum;
    List<Node> nodeList;
    final ObservableList<Node> dataList = FXCollections.observableArrayList();

    //Initializing the Choicebox options
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initializing ChoiceBoxes
        choice0.setItems(FXCollections.observableArrayList("h0", "q0"));
        choice0.getSelectionModel().selectFirst();
        choicel.setItems(FXCollections.observableArrayList("hl", "ql"));
        choicel.getSelectionModel().selectFirst();
        //Formatting table columns
        nodeCol.setStyle("-fx-alignment: CENTER;");
        xCol.setStyle("-fx-alignment: CENTER;");
        wCol.setStyle("-fx-alignment: CENTER;");
        hCol.setStyle("-fx-alignment: CENTER;");
        qCol.setStyle("-fx-alignment: CENTER;");
        nodeCol.setPrefWidth(40);

        //Adding columns to the TableView
        resultsTable.getColumns().addAll(nodeCol, xCol, wCol, hCol, qCol);
        resultsTable.setMaxWidth(nodeCol.getWidth() + xCol.getWidth() + wCol.getWidth() + hCol.getWidth() + qCol.getWidth());


    }


    public void choiceDetector() {
        if (choice0.getValue().toString().equalsIgnoreCase("q0") && choicel.getValue().toString().equalsIgnoreCase("ql")) {
            calculateButton.setDisable(true);
            graphButton.setDisable(true);
            warningLabel.setVisible(true);
        } else {
            calculateButton.setDisable(false);
            graphButton.setDisable(false);
            warningLabel.setVisible(false);
        }
    }

    //Called by clicking on "Calculate" Button
    public void calculate() {

        //Reading the input data from TextAreas
        n = Integer.parseInt(textnodes.getText());
        l = Double.parseDouble(textlength.getText());
        k = Double.parseDouble(textcond.getText());
        w0 = Double.parseDouble(textw0.getText());
        wl = Double.parseDouble(textw1.getText());
        h0 = 0;
        hl = 0;
        q0 = 0;
        ql = 0;
        x = l / (n - 1);
        choicestr0 = choice0.getValue().toString();
        choicestrl = choicel.getValue().toString();
        nodeList = new ArrayList<Node>();


        //Calling the ChoiceBox / Case identifier
        casenum = Validate.identifyCase(choicestr0, choicestrl);
        switch (casenum) {
            case (1): //h0-hl
                h0 = Double.parseDouble(textborder0.getText());
                hl = Double.parseDouble(textborderl.getText());
                break;
            case (2): //q0-hl
                q0 = Double.parseDouble(textborder0.getText());
                hl = Double.parseDouble(textborderl.getText());
                break;
            case (3): //h0-ql
                h0 = Double.parseDouble(textborder0.getText());
                ql = Double.parseDouble(textborderl.getText());
                break;
            case (4): //q0-ql
                System.out.println("System cannot be solved");
                break;
        }
        if (casenum != 4) {
            // Creating the Matrix System
            Matrix matrix = new Matrix(n, l, k, h0, hl, q0, ql, w0, wl, casenum);

            // Printing the Matrix
            matrix.MatrixPrinter();

            // Solving the analytic method
            AnaliticMethod method1 = new AnaliticMethod();
            System.out.println("Through analythic method:\t" + "h(L/2)= " + method1.calculate(l, hl, h0) + "\n");


            //Receiving the nodeList from Equsolver
            nodeList.addAll(Eqsolver.Solve(matrix.getMatrix(), matrix.getVectorLeft(), matrix.getVectorRight(), n, x, w0, wl, k, casenum));

            //Mapping the values of Nodes to columns
            nodeCol.setCellValueFactory(new PropertyValueFactory<Node, String>("N"));
            xCol.setCellValueFactory(new PropertyValueFactory<Node, String>("x"));
            wCol.setCellValueFactory(new PropertyValueFactory<Node, String>("w"));
            hCol.setCellValueFactory(new PropertyValueFactory<Node, String>("h"));
            qCol.setCellValueFactory(new PropertyValueFactory<Node, String>("q"));

            //Creating the ObsList and adding values from nodeList
            final ObservableList<Node> dataList = FXCollections.observableArrayList();
            dataList.addAll(nodeList);
            resultsTable.setItems(dataList);
            this.dataList.addAll(dataList);
        }
    }

    @FXML
    public void openGraph() {
//        chartXY=new LineChart(Axis<x> x,Axis<y> y);
//        for (int i = 0; i < n; i++) {
//            chartXY.setData(dataList);
//        }
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource("Graph.fxml"));
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Groundwater 1D");
            secondaryStage.setScene(new Scene(root1, 600, 600));
            secondaryStage.show();
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
    }

}

