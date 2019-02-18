package com.gymformmanager.controller;


import com.gymformmanager.model.Sport;
import com.gymformmanager.model.SportProperty;
import com.gymformmanager.util.ActionButtonTableCell;
import com.gymformmanager.util.MessageAlerte;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

import static com.gymformmanager.model.SportDAO.deleteSportById;
import static com.gymformmanager.model.SportDAO.getAllSports;
import static com.gymformmanager.util.repeat.chargerFenetreModificationSport;

public class controllerListSport implements Initializable {

    private ObservableList<SportProperty> list = FXCollections.observableArrayList();

    @FXML
    public AnchorPane rootPane;

    @FXML
    public TableView<SportProperty> tableView;

    @FXML
    public TableColumn<SportProperty, Integer> prixAnneeCol;

    @FXML
    public TableColumn<SportProperty, String> intituleCol ;

    @FXML
    public TableColumn<SportProperty, Integer> prixTrimestreCol;

    @FXML
    public TableColumn<SportProperty, Integer> prixMoisCol;

    @FXML
    public TableColumn<SportProperty, String> entraineurCol;

    @FXML
    public TableColumn modifierCol;

    @FXML
    public TableColumn supprimerCol;

    @FXML
    JFXComboBox comboBoxFilter;

    @FXML
    JFXTextField searchBarPaiement;

    @FXML
    ImageView refreshImage;

    // Intialisation classique d'une page avec un table View
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> { // permet de rafraichir le tableau
            loadData();
        });
        initCol();
        loadData();
        searchBar();
    }

    // Méthode d'initialisation classique d'un Table View FXML
    private void initCol(){
        intituleCol.setCellValueFactory(new PropertyValueFactory<>("intituleSport"));
        entraineurCol.setCellValueFactory(new PropertyValueFactory<>("entraineur"));
        prixAnneeCol.setCellValueFactory(new PropertyValueFactory<>("prixAnnee"));
        prixTrimestreCol.setCellValueFactory(new PropertyValueFactory<>("prixTrimestre"));
        prixMoisCol.setCellValueFactory(new PropertyValueFactory<>("prixMois"));
    }



    // Méthode permettant de charger tous les sports dans les champs du Table View
    private void loadData(){
        list.clear();
        List<Sport> sports = getAllSports();


        for (Sport sport: sports) {
            SimpleStringProperty intituleRecuperer = new SimpleStringProperty(sport.getIntituleSport());
            SimpleStringProperty entraineurRecuperer = new SimpleStringProperty(sport.getEntraineur());

            Map<String, SimpleIntegerProperty> prix = new HashMap<String, SimpleIntegerProperty>();
            if (sport.getPrixAnnee() != null){
                prix.put("prixAnnee", new SimpleIntegerProperty(sport.getPrixAnnee()));
                //int prixAnneeRecuperer = sports.get(i).getPrixAnnee();
            }
            if (sport.getPrixTrimestre() != null){
                prix.put("prixTrimestre", new SimpleIntegerProperty(sport.getPrixTrimestre()));
                //int prixTrimestreRecuperer = sports.get(i).getPrixTrimestre();
            }
            if (sport.getPrixMois() != null){
                prix.put("prixMois", new SimpleIntegerProperty(sport.getPrixMois()));
                //int prixMoisRecuperer = sports.get(i).getPrixMois();
            }
            list.add(new SportProperty(intituleRecuperer, prix, entraineurRecuperer, sport.getIdSport()));

            supprimerCol.setCellFactory(ActionButtonTableCell.<SportProperty>forTableColumn("Supprimer", (SportProperty p) -> {
                supprimerSport(p); // Si le boutton supprimer à été selectionné
                return p;
            }));

            modifierCol.setCellFactory(ActionButtonTableCell.<SportProperty>forTableColumn("Modifier", (SportProperty p) -> {
                chargerFenetreModificationSport("/javafx/viewModifySport.fxml","Modification d'un sport", getClass(), p);
                return p; // si le bouton modifier à été selectionné
            }));
        }
        tableView.setItems(list);
    }

    // Méthode mettant en place une barre de recherche classique, présente à plusieurs reprise
    private void searchBar(){
        FilteredList<SportProperty> flSport = new FilteredList(tableView.getItems(), p -> true);//Pass the data to a filtered list
        tableView.setItems(flSport);//Set the table's items using the filtered list

        ObservableList<String> listFilter = FXCollections.observableArrayList();
        listFilter.addAll("Intitulé du sport", "Prix année", "Prix trimestre", "Prix mois","Entraîneur");
        comboBoxFilter.setItems(listFilter);
        comboBoxFilter.setValue("Intitulé du sport");

        searchBarPaiement.setPromptText("Rechercher ici !");
        searchBarPaiement.setOnKeyReleased(keyEvent ->
        {
            switch ((String)comboBoxFilter.getSelectionModel().getSelectedItem())//Switch on choiceBox value
            {
                case "Intitulé du sport":
                    flSport.setPredicate(p -> p.getIntituleSport().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Prix année":
                    flSport.setPredicate(p -> String.valueOf(p.getPrixAnnee()).toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Prix trimestre":
                    flSport.setPredicate(p -> String.valueOf(p.getPrixTrimestre()).toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Prix mois":
                    flSport.setPredicate(p -> String.valueOf(p.getPrixMois()).toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Entraîneur":
                    flSport.setPredicate(p -> p.getEntraineur().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
            }
        });

        comboBoxFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                searchBarPaiement.setText("");
                flSport.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
        });
    }


    // Méthode permettant de dire à l'utilisateur qu'il na pas cliqué sur un élement a supprimer (si c'est le cas)
    @FXML
    private void supprimerSportClick(ActionEvent event){
        Alert alert;
        SportProperty selectionnerPourSuppression = tableView.getSelectionModel().getSelectedItem();
        if ( selectionnerPourSuppression == null ){
            MessageAlerte.showErrorMessage("Aucun sport n'a été selectionné","S'il vous plait choisissez un sport" );
        }else{
            supprimerSport(selectionnerPourSuppression);
        }

    }
    // Méthode permettant de supprimer un sport (on vérifie aussi que l'utilisateur ne ce soit pas trompé)
    private void supprimerSport(SportProperty sportProperty){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer le sport");
        alert.setContentText("Etes vous sur de vouloir supprimer le sport "+ sportProperty.getIntituleSport()+" ?");
        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.get() == ButtonType.OK){
            deleteSportById(sportProperty.getIdSport());
            MessageAlerte.actionReussi("Sport supprimé!");
            loadData();
        }else{
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Suppression annulé");
            alert2.setContentText("Vous avez annulé la suppression de ce sport");
        }
    }

    // Méthode permettant de modifier un sport, on avertit l'utilisateur également s'il n'a rien sélectionné
    @FXML
    private void modifierSportClick(ActionEvent event){
        Alert alert;
        SportProperty selectionnerPourModification = tableView.getSelectionModel().getSelectedItem();
        if ( selectionnerPourModification == null ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Aucun sport n'a été selectionné");
            alert.setContentText("S'il vous plait choisissez un sport");
        }else{
            chargerFenetreModificationSport("/javafx/viewModifySport.fxml","Modification d'un sport", getClass(), selectionnerPourModification);
        }
    }


}



