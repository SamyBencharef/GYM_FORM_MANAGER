package com.gymformmanager.controller;

import com.gymformmanager.model.SportProperty;
import com.gymformmanager.util.MessageAlerte;
import com.gymformmanager.util.repeat;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import static com.gymformmanager.model.SportDAO.updateSportByIdSport;
import static com.gymformmanager.util.repeat.controlerFormulaireSport;
import static com.gymformmanager.util.repeat.fermerFenetre;

public class controllerModifySport {

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

    private SportProperty theSport;

    // Méthode permettant de pré-remplire les champs par les données  du sport que l'on souhaite modifier.
    public void initData(SportProperty sportProperty) {
        sportIntitule.setText(sportProperty.getIntituleSport());
        sportEntraineur.setText(sportProperty.getEntraineur());
        if (sportProperty.prixAnneeProperty()!=null)
            sportPrixAnnee.setText(String.valueOf(sportProperty.getPrixAnnee()));
        if (sportProperty.prixTrimestreProperty()!=null)
            sportPrixTrimestre.setText(String.valueOf(sportProperty.getPrixTrimestre()));
        if (sportProperty.prixMoisProperty()!=null)
            sportPrixMois.setText(String.valueOf(sportProperty.getPrixMois()));
        theSport = sportProperty;
    }


    // Méthode permettant de vérifier et modifier le sport
    @FXML
    void modifierSport(ActionEvent event) {


        // on verifie si le formulaire a été saisie correctement, si c'est le cas on procède à l'insertion
        if (controlerFormulaireSport(sportIntitule.getText(), sportEntraineur.getText(),
                sportPrixAnnee.getText(), sportPrixTrimestre.getText(), sportPrixMois.getText())) {

            Integer prixAnnee = null ;
            Integer prixTrimestre = null ;
            Integer prixMois = null ;

            if (sportPrixAnnee.getText()!=null && !sportPrixAnnee.getText().isEmpty())
                prixAnnee = Integer.parseInt(sportPrixAnnee.getText());
            if (sportPrixTrimestre.getText()!=null && !sportPrixTrimestre.getText().isEmpty())
                prixTrimestre = Integer.parseInt(sportPrixTrimestre.getText());
            if (sportPrixMois.getText()!=null && !sportPrixMois.getText().isEmpty())
                prixMois = Integer.parseInt(sportPrixMois.getText());

            System.out.println(theSport.getIdSport()+" "+sportIntitule.getText()+" "+sportEntraineur.getText()
                            +" "+sportPrixAnnee.getText()+" "+ sportPrixTrimestre.getText()+" "+
                    sportPrixMois.getText());

            updateSportByIdSport(theSport.getIdSport(), sportIntitule.getText(), sportEntraineur.getText(),
                    prixAnnee,
                    prixTrimestre,
                    prixMois);

            MessageAlerte.actionReussi("Modification du sport effectué !");
            fermerFenetre(rootPane);
        }
    }

    @FXML
    void annulerModificationSport(ActionEvent event) {
        fermerFenetre(rootPane);
    }

}
