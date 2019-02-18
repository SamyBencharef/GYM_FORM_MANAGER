package com.gymformmanager.util;

import javafx.scene.image.Image;
import javafx.stage.Stage;

// Classe permettant de mettre l'icone au coins de l'écran
public class GymFormManagerUtil {
    public static final String IMAGE_LOC = "/icons/web_hi_res_512.png";

    public static void setStageIcon(Stage stage){
        stage.getIcons().add(new Image(IMAGE_LOC));
    }

}
