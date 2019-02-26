/*
 * Developed by Razil Minneakhmetov on 11/18/18 11:59 AM.
 * Last modified 11/18/18 11:59 AM.
 * Copyright © 2018. All rights reserved.
 */
package controllers;

import benchs.methodTypes.MethodType;
import benchs.methodTypes.Pgbench;
import context.Context;
import forms.GrafanaSetup;
import forms.ResultForm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;
import services.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class FXApp implements Initializable {

    private String methodType = "pgbench";
    private DBConnector connector;
    @FXML
    private RadioButton pgbench;
    @FXML
    private RadioButton manual;
    @FXML
    private CheckBox grafana;
    @FXML
    private TextField path;
    @FXML
    private Button browse;
    @FXML
    private Spinner<Integer> time;
    @FXML
    private Button start;
    @FXML
    private ProgressBar progress;
    @FXML
    private ImageView load;
    @FXML
    private Label querying;
    @FXML
    private AnchorPane anchorPane;

    private Context context;


    @FXML
    @SneakyThrows
    public void browseButton(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        File query = chooser.showOpenDialog(stage);
        if (query != null)
            path.setText(query.getPath());

    }

    @Override
    @SneakyThrows
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context = Context.getInstance();
        load.setVisible(false);
        querying.setVisible(false);
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 1);
        time.setValueFactory(spinnerValueFactory);
        connector = DBConnector.getInstance();
        context.setObject("grafana", new GrafanaSetup());
//        if (connector.isConnectionStatus()) {
//            status.setText("Connected");
//            status.setTextFill(Color.GREEN);
//        } else {
//            status.setText("Not Connected");
//            status.setTextFill(Color.RED);
//        }
    }

    @FXML
    public void methodType(ActionEvent event) {
        if (pgbench.isSelected())
            methodType = "pgbench";
        if (manual.isSelected())
            methodType = "manual";
    }

    @FXML
    public void grafanaSetup(ActionEvent event){
        if(grafana.isSelected()){
            GrafanaSetup setup = new GrafanaSetup();
            setup.setAddSupport(true);
            context.setObject("grafana",setup);
        } else {
            context.setObject("grafana", new GrafanaSetup());
        }
    }

    @FXML
    private void start(ActionEvent event) {

        File script = new File(path.getText());

        try{
            new Scanner(script);
            load.setVisible(true);
            querying.setText("Querying...");
            querying.setTextFill(Color.BLACK);
            querying.setVisible(true);
            int timeVal = time.getValue() * 60;
            progressIndicator(timeVal);
            path.setDisable(true);
            browse.setDisable(true);
            time.setDisable(true);
            start.setDisable(true);
            pgbench.setDisable(true);
            manual.setDisable(true);
            grafana.setDisable(true);

            MethodType pgbenchMethod = new Pgbench();
            pgbenchMethod.bench(timeVal, script);
            Task<Void> sleeperClose = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep((timeVal + 2) * 1000);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeperClose.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override

                public void handle(WorkerStateEvent event) {
                    path.setDisable(false);
                    browse.setDisable(false);
                    time.setDisable(false);
                    start.setDisable(false);
                    pgbench.setDisable(false);
                    manual.setDisable(false);
                    grafana.setDisable(false);
                    //load.setVisible(true);
                    querying.setText("Resulting...");
                    querying.setTextFill(Color.BLACK);
                    //querying.setVisible(true);
                    //progress.setProgress(0.0);



                    Task<Void> sleeperClose = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            return null;
                        }
                    };
                    sleeperClose.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            load.setVisible(false);
                            querying.setText("Querying...");
                            querying.setTextFill(Color.BLACK);
                            querying.setVisible(false);

                        }
                    });
                    new Thread(sleeperClose).start();

                    ResultForm form = pgbenchMethod.getResult();
                    context.setObject("pgbenchResult", form);
                    context.setObject("time", timeVal);


                    String fxmlConnect = "/fxml/result.fxml";
                    FXMLLoader loaderConnect = new FXMLLoader();

                    Parent rootConnect = null;
                    try {
                        rootConnect = (Parent) loaderConnect.load(getClass().getResourceAsStream(fxmlConnect));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stageConnect = new Stage();
                    //stageConnect.initModality(Modality.APPLICATION_MODAL);
                    stageConnect.setTitle("Result");
                    stageConnect.setScene(new Scene(rootConnect));
                    stageConnect.show();

                }
            });
            new Thread(sleeperClose).start();

        } catch (IOException e){
            querying.setVisible(true);
            querying.setText("Set up a script file");
            querying.setTextFill(Color.RED);
        }







        //System.out.println(((Pgbench) pgbench).getResult());


    }


    private Timeline timeline;
    private Label timerLabel = new Label();

    private void progressIndicator(int time) {

        IntegerProperty timeSeconds = new SimpleIntegerProperty(time*100);

        timerLabel.textProperty().bind(timeSeconds.divide(100).asString());
        timerLabel.setTextFill(Color.RED);
        timerLabel.setStyle("-fx-font-size: 4em;");


        progress.progressProperty().bind(
                timeSeconds.divide(time*100.0).subtract(1).multiply(-1));

        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set((time+1)*100);
        timeline = new Timeline();
        timeline.getKeyFrames().add(

                new KeyFrame(Duration.seconds(time+1),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();

    }
}
