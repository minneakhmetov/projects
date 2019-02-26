/*
 * Developed by Razil Minneakhmetov on 11/18/18 11:59 AM.
 * Last modified 11/18/18 11:59 AM.
 * Copyright © 2018. All rights reserved.
 */
import controllers.DBConnectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXAppLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/main.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("JavaFX and Maven");
        stage.setScene(new Scene(root));
        stage.show();

        String fxmlConnect = "/fxml/connect.fxml";
        FXMLLoader loaderConnect = new FXMLLoader();
        Parent rootConnect = (Parent) loaderConnect.load(getClass().getResourceAsStream(fxmlConnect));
        Stage stageConnect = new Stage();
        stageConnect.initModality(Modality.APPLICATION_MODAL);
        stageConnect.setTitle("Connect to DB");
        stageConnect.setScene(new Scene(rootConnect));
        stageConnect.show();

    }
}
