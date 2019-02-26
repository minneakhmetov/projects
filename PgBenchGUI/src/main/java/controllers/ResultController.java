/*
 * Developed by Razil Minneakhmetov on 11/19/18 11:43 PM.
 * Last modified 11/19/18 11:43 PM.
 * Copyright © 2018. All rights reserved.
 */

package controllers;

import com.google.common.io.Files;
import constants.Constants;
import context.Context;
import forms.GrafanaSetup;
import forms.LogResult;
import forms.PgbenchResultForm;
import forms.ResultForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Sheet;
import services.DBConnector;
import services.Executor;
import services.LogParser;


import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

    @FXML
    private Label time;
    @FXML
    private Label latency;
    @FXML
    private Label iterations;
    @FXML
    private Label tpsIn;
    @FXML
    private Label tpsEx;
    @FXML
    private Button save;

    @FXML
    private Pane pane;

    @FXML private AnchorPane anchorPane;

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();


    private LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);

    private Context context;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = Context.getInstance();
        PgbenchResultForm form = (PgbenchResultForm) context.getObject("pgbenchResult");
        time.setText(context.getObject("time").toString() + " seconds");
        latency.setText(form.getLatencyAverage() + " ms");
        iterations.setText(form.getTransactions());
        tpsIn.setText(form.getTpsIn());
        tpsEx.setText(form.getTpsEx());


        ObservableList<XYChart.Series<Number, Number>> lineChartData = FXCollections.observableArrayList();

        LineChart.Series<Number, Number> series = new LineChart.Series<Number, Number>();

        final LogParser parser = new LogParser();

        Task<Void> sleeperClose = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    parser.start();
                    parser.join();
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeperClose.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                for(int i = 0; i < parser.getxAxis().size(); i++){
                    series.getData().add(new XYChart.Data(parser.getxAxis().get(i), parser.getyAxis().get(i)));
                }

                lineChartData.add(series);

                chart.setData(lineChartData);
                //chart.createSymbolsProperty();
                pane.getChildren().add(chart);

                new Executor().deleteTempDir();

            }
        });
        new Thread(sleeperClose).start();


        //chart.getData().add(series);

    }
    @FXML

    public void save(){
        FileChooser chooser = new FileChooser();
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLSX files (*.xls)", "*.xlsx");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Save a report");
        chooser.setInitialFileName("report");
        File query = chooser.showSaveDialog(stage);
        if (query != null) {
            File file = new File(Constants.TEMP_PATH_XLS);
            try {
                Files.copy(file, query);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createXLS(){

    }
}