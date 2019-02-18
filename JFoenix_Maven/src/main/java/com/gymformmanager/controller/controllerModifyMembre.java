package com.gymformmanager.controller;

import com.gymformmanager.model.MembreProperty;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.gymformmanager.model.MembreDAO.updateMembreByIdMembre;
import static com.gymformmanager.model.MembreSportDAO.*;
import static com.gymformmanager.model.SportDAO.*;
import static com.gymformmanager.model.SportDAO.getSportByIdSport;
import static com.gymformmanager.util.repeat.controlerFormulaireMembre;
import static com.gymformmanager.util.repeat.fermerFenetre;

public class controllerModifyMembre implements Initializable {

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

    private MembreProperty theMembreProperty;

    // Méthode permettant de pré-remplire les champs par les données  du membre que l'on souhaite modifier.
    public void initData(MembreProperty membreProperty) {
        nomMembre.setText(membreProperty.getNomMembre());
        prenomMembre.setText(membreProperty.getPrenomMembre());
        //dtn
        /*
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String dateString = formatter.format(membre.getDtnMembre());
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDate theDtn = LocalDate.parse(membreProperty.getDtnMembre(), formatter);
        dtnMembre.setValue(theDtn);
        emailMembre.setText(membreProperty.getEmailMembre());
        numPortableMembre.setText(membreProperty.getNumPortableMembre());
        poidMembre.setText(String.valueOf(membreProperty.getPoidMembre()));
        tailleMembre.setText(String.valueOf(membreProperty.getTailleMembre()));
        // sport
        List<Integer> sports = getIdSportsByIdMembre(membreProperty.getIdMembre());
        List<String> sportName = new ArrayList<String>();
        for(Integer idSport : sports){
            Sport sportSelected = getSportByIdSport(idSport);
            sportName.add(sportSelected.getIntituleSport());
        }

        for (String nomSport : sportMembre.getItems()){
            if (sportName.contains(nomSport))
                sportMembre.getCheckModel().check(nomSport);
        }

        if(membreProperty.getGenreMembre().compareTo("H")==0)
            hommeMembre.setSelected(true);
        else
            femmeMembre.setSelected(true);

        theMembreProperty = membreProperty;
    }

    // Méthode permettant de vérifier et modifier le membre
    @FXML
    void modifierMembre(ActionEvent event){
        if(controlerFormulaireMembre(nomMembre.getText(), prenomMembre.getText(),
                !hommeMembre.isSelected() && !femmeMembre.isSelected(),
                emailMembre.getText(), numPortableMembre.getText(),
                poidMembre.getText(), tailleMembre.getText(),
                (dtnMembre.getValue()!= null ? dtnMembre.getValue().toString() :""),
                !sportMembre.getCheckModel().isEmpty())) {

            String genre;
            if(hommeMembre.isSelected())
                genre="H";
            else
                genre="F";

            Float poid = null ;
            Float taille = null ;

            if (poidMembre.getText()!=null && !poidMembre.getText().isEmpty())
                poid = Float.parseFloat(poidMembre.getText());
            if (tailleMembre.getText()!=null && !tailleMembre.getText().isEmpty())
                taille = Float.parseFloat(tailleMembre.getText());

            List<MembreSport> sportsOfMember = getListMembreSportByIdMembre(theMembreProperty.getIdMembre());
            List<Integer> listIntSportOfMember = getIdSportsByIdMembre(theMembreProperty.getIdMembre());

            for (String name : sportMembre.getCheckModel().getCheckedItems()) {
                boolean isIt = false;  // Pour acceder à tous les sports du membre, on doit passer par membre sport,
                // d'ou ce processus de double for, il faut regarder si des sports ont changer pour les supprimer/ajouter dans MembreSport
                System.out.println("1st syso, sport check : "+ name);

                for (MembreSport memberSport : sportsOfMember){

                    Sport sportFromMember = getSportByIdSport(memberSport.getIdSport());
                    System.out.println("2eme syso, les sports du membre : "+ sportFromMember.getIntituleSport());

                    if (sportFromMember.getIntituleSport().compareTo(name)==0){
                        listIntSportOfMember.remove(new Integer(memberSport.getIdSport()));
                        System.out.println("3rd syso, les sport du membres et les sport qui sont checked qui sont identiques : "+ sportFromMember.getIntituleSport()+" "+name);
                        isIt = true;
                    }
                }
                if ( !isIt ){
                    MembreSport nouveauMembreSport = new MembreSport(getSportByNameSport(name).getIdSport(), theMembreProperty.getIdMembre());
                    repeat.insertData(nouveauMembreSport);
                }
            }

            for ( Integer idSportAtDelete : listIntSportOfMember)
                deleteMembreSportByIdSport(idSportAtDelete); // s'il y en a on les supprime par exemple

            updateMembreByIdMembre(theMembreProperty.getIdMembre(),nomMembre.getText(), prenomMembre.getText(),
                    emailMembre.getText(),numPortableMembre.getText(),
                    Date.valueOf(dtnMembre.getValue()), poid,  taille,  genre);

            MessageAlerte.actionReussi("Modification du membre effectué !");
            fermerFenetre(rootPane);
        }
    }

    // Méthode permettant d'annuler (ferme la page)
    @FXML
    void annulerModifierMembre(ActionEvent event) {
        fermerFenetre(rootPane);
    }


    // Intitalisation dans laquel on préremplie la checkbox des sports
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


    // Permet de ne selectionné qu'une checkbox à la fois.
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
