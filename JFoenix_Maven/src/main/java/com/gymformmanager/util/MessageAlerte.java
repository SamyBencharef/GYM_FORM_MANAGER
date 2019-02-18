package com.gymformmanager.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static com.gymformmanager.util.GymFormManagerUtil.setStageIcon;

public class MessageAlerte {


    // Méthode permettant d'afficher une réussite en pop-up
    public static void actionReussi(String message) {
        Alert reussi = new Alert(Alert.AlertType.INFORMATION);
        reussi.setHeaderText(null);
        reussi.setContentText(message);
        styleAlert(reussi);
        reussi.showAndWait();
    }

    // Méthode permettant d'afficher une erreur en pop-up
    public static void showErrorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(title);
        alert.setContentText(content);
        styleAlert(alert);
        alert.showAndWait();
    }


    // Méthode permettant d'afficher des erreurs en pop-up
    public static void errorPrint(boolean isOk, List<String> messageErreur) {
        // si des messages d'erreurs ont eu lieu, on fait apparaître une pop up d'erreur.
        if (!isOk) {
            Alert erreur = new Alert(Alert.AlertType.ERROR);
            erreur.setTitle("Erreur ! ");
            StringBuilder sb = new StringBuilder();
            messageErreur.stream().forEach((x) -> sb.append("\n" + x));
            erreur.setHeaderText(sb.toString());
            styleAlert(erreur);
            erreur.showAndWait();
        }
    }

    // Méthode permettant d'afficher une alert en pop-up
    private static void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        setStageIcon(stage);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(MessageAlerte.class.getResource("/style/style.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }

    // Méthode permettant d'afficher la pop-up sur la page principal en flou
    public static void showMaterialDialog(StackPane root, Node nodeToBeBlurred, List<JFXButton> controls, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        if (controls.isEmpty()) {
            controls.add(new JFXButton("Okay"));
        }
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);

        controls.forEach(controlButton -> {
            controlButton.getStyleClass().add("message-button");
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
        });

        dialogLayout.setHeading(new Label(header));
        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(controls);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
            nodeToBeBlurred.setEffect(null);
        });
        nodeToBeBlurred.setEffect(blur);
    }


    public static final String ICON_IMAGE_LOC = "/icons/web_hi_res_512.png";

    // Méthode permettant de déclencher une notification sur l'ordinateur de l'utilisateur
    public static void showTrayMessage(String title, String message) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            BufferedImage image = ImageIO.read(MessageAlerte.class.getResource(ICON_IMAGE_LOC));
            TrayIcon trayIcon = new TrayIcon(image, "GYM FORM Manager");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("GYM FORM Manager");
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            tray.remove(trayIcon);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}

