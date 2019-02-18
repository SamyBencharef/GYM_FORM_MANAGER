package com.gymformmanager.controller;

import com.gymformmanager.model.Membre;
import com.gymformmanager.model.MembreSport;
import com.gymformmanager.model.Sport;
import com.gymformmanager.util.MessageAlerte;
import com.gymformmanager.util.repeat;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;


import java.net.URL;
import java.sql.Date;
import java.util.*;

import static com.gymformmanager.model.MembreDAO.getMembreWithAllAttributs;
import static com.gymformmanager.model.SportDAO.*;
import static com.gymformmanager.util.repeat.controlerFormulaireMembre;
import static com.gymformmanager.util.repeat.fermerFenetre;

public class controllerAddMembre  implements Initializable{

    @FXML
    private JFXTextField nomMembre;

    @FXML
    private JFXTextField prenomMembre;

    @FXML
    private JFXDatePicker dtnMembre;

    @FXML
    private JFXTextField emailMembre;

    @FXML
    private JFXTextField numPortableMembre;

    @FXML
    private JFXTextField poidMembre;

    @FXML
    private JFXTextField tailleMembre;

    @FXML
    private CheckComboBox<String> sportMembre;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXCheckBox hommeMembre;

    @FXML
    private JFXCheckBox femmeMembre;

    // fonction permettant d'ajouter un nouveau membre dans la base de donnée
    @FXML
    void ajouterMembre(ActionEvent event){

        // on verifie si le formulaire a été saisie correctement, si c'est le cas on procède à l'insertion
        if(controlerFormulaireMembre(nomMembre.getText(), prenomMembre.getText(),
                !hommeMembre.isSelected() && !femmeMembre.isSelected(),
                emailMembre.getText(), numPortableMembre.getText(),
                poidMembre.getText(), tailleMembre.getText(),
                (dtnMembre.getValue()!= null ? dtnMembre.getValue().toString() :""),
                !sportMembre.getCheckModel().isEmpty())) {


            Map<String, Float> infos = new HashMap<String,Float>(); // Sert a ne pas passer de nul dans notre objet java
            if(poidMembre.getText() != null && !poidMembre.getText().isEmpty()) {
                infos.put("poidMembre", Float.parseFloat(poidMembre.getText()));
            }
            if(tailleMembre.getText() != null && !tailleMembre.getText().isEmpty()) {
                infos.put("tailleMembre", Float.parseFloat(tailleMembre.getText()));
            }

            // sert à savoir si c'est un homme ou une femme
            String genre;
            if(hommeMembre.isSelected())
                genre="H";
            else
                genre="F";

            // on crée notre objet membre
            Membre nouveauMembre = new Membre(nomMembre.getText(), prenomMembre.getText(),
                    emailMembre.getText(),numPortableMembre.getText(),
                    Date.valueOf(dtnMembre.getValue()),infos,genre);

            // Très rare dans ce programme, devrait en avoir plus, un suivi en SOP
            System.out.println(nouveauMembre.getIdMembre()+" "+nouveauMembre.getNomMembre()
                    +" "+nouveauMembre.getPrenomMembre() +" "+nouveauMembre.getEmailMembre()
                    +" "+nouveauMembre.getDtnMembre()+" "+nouveauMembre.getPoidMembre()
                    +" "+nouveauMembre.getTailleMembre()+" "+nouveauMembre.getNumPortableMembre()
                    +" "+ nouveauMembre.getGenre());

            // on ajoute notre nouveau membre
            repeat.insertData(nouveauMembre);

            // et on fait le liens de notre nouveau membre avec ses sports
            for (String name : sportMembre.getCheckModel().getCheckedItems()) {

                Sport resultSport = getSportByNameSport(name);

                // Comme l'id ne se génére qu'une fois l'objet inséré dans la database on doit récuperer l'objet une
                // fois celui-ci inséré pour obtenir son id.... Ici on ce dira qu'il n'y a qu'un seul membre
                // avec le même prénom le même nom et la même date de naissance..
                // Méthode assez barbare à améliorer dans le futur...
                Membre resultMembre = getMembreWithAllAttributs(nouveauMembre.getNomMembre(),
                        nouveauMembre.getPrenomMembre(), nouveauMembre.getEmailMembre(),
                        nouveauMembre.getNumPortableMembre(),nouveauMembre.getDtnMembre()

                );

                // et on les ajoute dans notre base également.
                MembreSport nouveauMembreSport = new MembreSport(resultSport.getIdSport(),resultMembre.getIdMembre());
                repeat.insertData(nouveauMembreSport);
            }

            // tout c'est bien passé, on en informe l'utilisateur.
            MessageAlerte.actionReussi("Nouveau membre inséré !");
            fermerFenetre(rootPane);
        }
    }

    // méthode permettant d'annuler le processus (ferme la fenetre)
    @FXML
    void annulerAjoutMembre(ActionEvent event) {
        fermerFenetre(rootPane);
    }



    // on ce sert de la fonction d'initialiation pour ajouter la liste des sports dans notre checkComboBox
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listSport = FXCollections.observableArrayList();
        List<Sport> sports = getAllSports();
        for (int i=0;i<sports.size();i++) {
            listSport.add(sports.get(i).getIntituleSport());
            System.out.println(sports.get(i).getIntituleSport());
        }
        sportMembre.getItems().addAll(listSport);
    }


    // ces deux fonctions permettent de n'avoir qu'une seule checkbox GENRE selectionné.
    @FXML
    private void handleHommeBox(){
        if (hommeMembre.isSelected())
            femmeMembre.setSelected(false);
    }

    @FXML
    private void handleFemmeBox(){
        if (femmeMembre.isSelected())
            hommeMembre.setSelected(false);
    }
}

