/*
 * Developed by Razil Minneakhmetov on 11/18/18 11:59 AM.
 * Last modified 11/18/18 11:59 AM.
 * Copyright © 2018. All rights reserved.
 */
package controllers;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import services.DBConnector;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;


public class DBConnectController implements Initializable {
    private Properties properties;
    public DBConnector connector;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button connect;

    @FXML
    private Label status;

    @FXML
    private ImageView load;

    private String path = "\\src\\main\\resources\\properties\\connect.properties";

    String dir = "";


    @Override
    @SneakyThrows
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dir = System.getProperty("user.dir");
        load.setVisible(false);
        properties = new Properties();
        properties.load(new FileInputStream(dir + path));
        ip.setText(properties.getProperty("database.ip"));
        port.setText(properties.getProperty("database.port"));
        name.setText(properties.getProperty("database.name"));
        username.setText(properties.getProperty("database.username"));
        password.setText(properties.getProperty("database.password"));
    }

    @SneakyThrows
    public void connectDB(){
        load.setVisible(true);
        status.setText("Connecting...");
        status.setTextFill(Color.BLACK);

        String ipValue = ip.getText();
        String portValue = port.getText();
        String nameValue = name.getText();
        String usernameValue = username.getText();
        String passwordValue = password.getText();
        properties.setProperty("database.ip", ipValue);
        properties.setProperty("database.port", portValue);
        properties.setProperty("database.name", nameValue);
        properties.setProperty("database.username", usernameValue);
        properties.setProperty("database.password", passwordValue);
        properties.store(new FileOutputStream(dir + path), null);

        connector = DBConnector.getInstance();
        connector.setIp(ipValue);
        connector.setPort(portValue);
        connector.setDatabaseName(nameValue);
        connector.setUsername(usernameValue);
        connector.setPassword(passwordValue);
        connector.setProperties();

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                if(connector.connectionStatus()){
                    load.setVisible(false);
                    status.setText("Connected");
                    status.setTextFill(Color.GREEN);
                    Task<Void> sleeperClose = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                            }
                            return null;
                        }
                    };
                    sleeperClose.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            Stage stage = (Stage) connect.getScene().getWindow();
                            stage.close();

                        }
                    });
                    new Thread(sleeperClose).start();
                } else {
                    load.setVisible(false);
                    status.setText("Connection Failed");
                    status.setTextFill(Color.RED);
                }
            }
        });
        new Thread(sleeper).start();






    }
}