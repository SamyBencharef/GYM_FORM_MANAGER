package com.gymformmanager.controller;

import com.gymformmanager.model.Preference;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static com.gymformmanager.util.repeat.chargerFenetre;
import static com.gymformmanager.util.repeat.fermerFenetre;

public class controllerConnection implements Initializable {

    @FXML
    private Pane rootPane;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    // Fichier contenant les identifiants crypter
    Preference preference;

    // Méthode permettant de vérifier si les identfiant saisies sont correct
    @FXML
    void connect(ActionEvent event) {

        String user = DigestUtils.shaHex(username.getText());
        String pass = DigestUtils.shaHex(password.getText());

        if(user.equals(preference.getUsername()) && pass.equals(preference.getPassword())){ // s'ils sont correct, on ouvre l'application
            chargerFenetre("/javafx/viewAccueil.fxml","Gestionnaire GYM FORM", getClass());
            fermerFenetre(rootPane);
        }else{ // sinon on reste sur la page en
            username.getStyleClass().add("mauvais-indentifiants");
            password.getStyleClass().add("mauvais-indentifiants");

            System.out.println("Mauvaise pseudo ou mot de passe");
        }
    }

    // On initialise en récuperant nos informations contenu dans notre dossier
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preference = Preference.getPreference();
    }
}
