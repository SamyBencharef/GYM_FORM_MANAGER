package com.gymformmanager.controller;

import com.gymformmanager.model.Sport;
import com.gymformmanager.util.MessageAlerte;
import com.gymformmanager.util.repeat;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gymformmanager.util.repeat.controlerFormulaireSport;
import static com.gymformmanager.util.repeat.fermerFenetre;

public class controllerAddSport {

    @FXML
    private JFXTextField sportIntitule;

    @FXML
    private JFXTextField sportEntraineur;

    @FXML
    private JFXTextField sportPrixAnnee;

    @FXML
    private JFXTextField sportPrixMois;

    @FXML
    private JFXTextField sportPrixTrimestre;

    @FXML
    private AnchorPane rootPane;

    // Méthode permettant d'ajouter un nouveau sport
    @FXML
    void ajouterSport(ActionEvent event) {

        // on verifie si le formulaire a été saisie correctement, si c'est le cas on procède à l'insertion
        if(controlerFormulaireSport(sportIntitule.getText(), sportEntraineur.getText(),
                sportPrixAnnee.getText(), sportPrixTrimestre.getText(), sportPrixMois.getText())) {

            Map<String, Integer> prix = new HashMap<String,Integer>(); // On envoie pas d'élement nul dans notre sport
            if(sportPrixAnnee.getText() != null && !sportPrixAnnee.getText().isEmpty()) {
                prix.put("prixAnnee", Integer.parseInt(sportPrixAnnee.getText()));
            }
            if(sportPrixTrimestre.getText() != null && !sportPrixTrimestre.getText().isEmpty()) {
                prix.put("prixTrimestre", Integer.parseInt(sportPrixTrimestre.getText()));
            }
            if(sportPrixMois.getText() != null && !sportPrixMois.getText().isEmpty()) {
                prix.put("prixMois", Integer.parseInt(sportPrixMois.getText()));
            }

            // on créer notre objet sport
            Sport nouveauSport = new Sport(sportIntitule.getText(), prix, sportEntraineur.getText());

            // Suivi en SOP, en mettre un peu plus.. (amélioration)
            System.out.println(nouveauSport.getIdSport()+" "+nouveauSport.getEntraineur()
                    +" "+nouveauSport.getIntituleSport() +" "+nouveauSport.getPrixAnnee()
                    +" "+nouveauSport.getPrixTrimestre()+" "+nouveauSport.getPrixMois());

            repeat.insertData(nouveauSport); // on ajoute le nouveau sport

            // on en informe l'utilisateur
            MessageAlerte.actionReussi("Nouveau sport inséré !");
            fermerFenetre(rootPane);
        }
    }

    // Méthode fermant la fentre en cas d'annulation
    @FXML
    void annulerAjoutSport(ActionEvent event) {
        fermerFenetre(rootPane);
    }

}
