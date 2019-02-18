package com.gymformmanager.controller;

import com.gymformmanager.model.Preference;
import com.gymformmanager.util.MessageAlerte;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.gymformmanager.util.repeat.fermerFenetre;

public class controllerParametre implements Initializable {
    @FXML
    private JFXTextField ancienUsername;

    @FXML
    private JFXTextField newUsername;

    @FXML
    private JFXPasswordField ancienPassword;

    @FXML
    private JFXPasswordField newPassword;

    @FXML
    private JFXPasswordField newPasswordRepeat;

    @FXML
    private Text passwordRepeatOk;

    @FXML
    private AnchorPane rootPane;


    // on initialise notre label cache qui changera si les personnes correspondent ou pas.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newPasswordRepeat.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                if (newPassword.getText().compareTo(newPasswordRepeat.getText())== 0 ){
                    passwordRepeatOk.setText("Les mots de passes correspondent");
                    passwordRepeatOk.setFill(Color.GREEN);
                }else{
                    passwordRepeatOk.setText("Les mots de passes ne correspondent pas");
                    passwordRepeatOk.setFill(Color.RED);
                }

            }
        });


    }

    // méthode permettant de modifier les parametres contenue dans notre fichier txt.
    @FXML
    void modifierParamettre(ActionEvent event) {
        if ( controleurFormulaire() ){
            Preference preference = Preference.getPreference();
            preference.setUsername(newUsername.getText());
            preference.setPassword(newPassword.getText());

            Preference.writePreferenceToFile(preference);
            MessageAlerte.actionReussi("Parametres modifié !");
        }

    }

    // Méthode permettant d'annuler, ferme la page
    @FXML
    void annulerAjoutSport(ActionEvent event) {
        fermerFenetre(rootPane);
    }

    // Méthode permettant de vérifier le formulaire
    private boolean controleurFormulaire(){
        boolean isOk = true;
        List<String> messageErreur = new ArrayList<>();
        Preference preference = Preference.getPreference();

        // Premièrement on vérifie, que l'ancier username et password correspondent, et que donc l'utilisateur est authentique
        if (ancienUsername.getText() == null || ancienUsername.getText().isEmpty()) {
            isOk = false;
            messageErreur.add("L'\"ancien username\" est obligatoire");
        }else if(DigestUtils.shaHex(ancienUsername.getText()).compareTo(preference.getUsername())!=0){
            isOk = false;
            messageErreur.add("L'\"ancien username\" est incorrect");
        }

        if (ancienPassword.getText() == null || ancienPassword.getText().isEmpty()) {
            isOk = false;
            messageErreur.add("L'\"ancien mot de passe\" est obligatoire");
        }else if(DigestUtils.shaHex(ancienPassword.getText()).compareTo(preference.getPassword())!=0){
            isOk = false;
            messageErreur.add("L'\"ancien mot de passe\" est incorrect");
        }


        // si c'est le cas alors on vérifie que tous les autres champs ne sont pas vide
        if (newUsername.getText() == null || newUsername.getText().isEmpty()) {
            isOk = false;
            messageErreur.add("Le \"nouveau username\" est obligatoire");
        }

        if (newPassword.getText() == null || newPassword.getText().isEmpty()) {
            isOk = false;
            messageErreur.add("Le\"nouveau mot de passe\" est obligatoire");
        }

        if (newPasswordRepeat.getText() == null || newPasswordRepeat.getText().isEmpty()) {
            isOk = false;
            messageErreur.add("La \"répétition du nouveau mot de passe\" est obligatoire");
        }

        // si des messages d'erreurs ont eu lieu, on fait apparaître une pop up d'erreur.
        MessageAlerte.errorPrint(isOk, messageErreur);
        return isOk;
    }

}
