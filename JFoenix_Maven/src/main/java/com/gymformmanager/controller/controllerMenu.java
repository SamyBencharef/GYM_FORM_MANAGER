package com.gymformmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import static com.gymformmanager.util.repeat.chargerFenetre;

public class controllerMenu {

    // Classe permettant de gérer tous les bouttons du menu, ne charge que des fenetres.

    @FXML
    public void chargerAjouterMembre(ActionEvent event){
        chargerFenetre("/javafx/viewAddMembre.fxml","Ajouter un nouveau membre", getClass());
    }

    @FXML
    public void chargerAjouterSport(ActionEvent event){
        chargerFenetre("/javafx/viewAddSport.fxml","Ajouter un nouveau sport", getClass());
    }

    @FXML
    public void chargerListMembre(ActionEvent event){
        chargerFenetre("/javafx/viewListMembre.fxml","Visionner la liste des membres", getClass());
    }

    @FXML
    public void chargerListSport(ActionEvent event){
        chargerFenetre("/javafx/viewListSport.fxml","Visionner la liste des sports", getClass());
    }

    @FXML
    public void chargerParametre(ActionEvent event){
        chargerFenetre("/javafx/viewParametre.fxml","Modifier les configurations", getClass());
    }

}
