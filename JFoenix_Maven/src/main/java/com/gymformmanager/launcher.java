package com.gymformmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.gymformmanager.util.GymFormManagerUtil.setStageIcon;

public class launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/javafx/viewConnection.fxml"));

        //JFXDecorator decorator = new JFXDecorator(stage, root);
        //decorator.setCustomMaximize(true);

        // Scene scene = new Scene(decorator);
        Scene scene = new Scene(root);
        //String uri = getClass().getResource("/style/style.css").toExternalForm();
        //scene.getStylesheets().add(uri);

        stage.setScene(scene);
        stage.setTitle("Connexion au gestionnaire");

        setStageIcon(stage);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
