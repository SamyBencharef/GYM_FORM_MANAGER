package com.gymformmanager.controller;

import com.gymformmanager.model.*;
import com.gymformmanager.util.ActionButtonTableCell;
import com.gymformmanager.util.MessageAlerte;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleFloatProperty;
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
import java.text.SimpleDateFormat;
import java.util.*;

import static com.gymformmanager.model.MembreDAO.deleteMembreWithIdMembre;
import static com.gymformmanager.model.MembreDAO.getAllMembres;
import static com.gymformmanager.model.SportDAO.getIdSportsByIdMembre;
import static com.gymformmanager.model.SportDAO.getSportByIdSport;
import static com.gymformmanager.util.repeat.chargerFenetreModificationMembre;

public class controllerListMembre implements Initializable {

    @FXML
    public AnchorPane rootPane;

    @FXML
    public TableView<MembreProperty> tableView;

    @FXML
    public TableColumn<MembreProperty, SimpleStringProperty> nomCol ;

    @FXML
    public TableColumn<MembreProperty, SimpleStringProperty> prenomCol ;

    @FXML
    public TableColumn<MembreProperty, SimpleStringProperty> emailCol ;

    @FXML
    public TableColumn<MembreProperty, SimpleStringProperty> telCol;

    @FXML
    public TableColumn<MembreProperty, SimpleStringProperty> dtnCol;

    @FXML
    public TableColumn<MembreProperty, SimpleFloatProperty> poidCol;

    @FXML
    public TableColumn<MembreProperty, SimpleFloatProperty> tailleCol;

    @FXML
    public TableColumn<MembreProperty, SimpleStringProperty> sportCol;

    @FXML
    public TableColumn<MembreProperty, SimpleStringProperty> genreCol;

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

    // Initialisation classique d'un fenetre avec un table view ..
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> { // permet de rafraichir le tableau
            loadData();
        });

        initCol();
        loadData();
        searchBar();
    }

    // Initialisation classique d'un Table View
    private void initCol(){
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nomMembre"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenomMembre"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("emailMembre"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("numPortableMembre"));
        dtnCol.setCellValueFactory(new PropertyValueFactory<>("dtnMembre"));
        poidCol.setCellValueFactory(new PropertyValueFactory<>("poidMembre"));
        tailleCol.setCellValueFactory(new PropertyValueFactory<>("tailleMembre"));
        sportCol.setCellValueFactory(new PropertyValueFactory<>("sportsMembre"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genreMembre"));
    }


    private  ObservableList<MembreProperty> membresEnvoye = FXCollections.observableArrayList();


    // Méthode insérant tous les membres dans les champs de table view
    private void loadData(){
        membresEnvoye.clear();
        List<Membre>  membres = getAllMembres();


        for (Membre membre: membres) {
            // on met en forme les dates de naissance
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            String dateString = formatter.format(membre.getDtnMembre());

            Map<String, SimpleFloatProperty> infos = new HashMap<String, SimpleFloatProperty>();
            if (membre.getPoidMembre() != 0.0f){
                infos.put("poidMembre", new SimpleFloatProperty(membre.getPoidMembre()));
            }
            if (membre.getTailleMembre() != 0.0f){
                System.out.println(membre.getTailleMembre());
                infos.put("tailleMembre", new SimpleFloatProperty(membre.getTailleMembre()));

            }

            List<Integer> sports = getIdSportsByIdMembre(membre.getIdMembre());

            List<String> sportName = new ArrayList<String>();
            for(Integer idSport : sports){
                Sport sportSelected = getSportByIdSport(idSport);
                sportName.add(sportSelected.getIntituleSport());
            }

            String sportsPrint = "";
            for(String sportNamePrint: sportName) {
                // on mets en forme les sport
                sportsPrint = sportsPrint + sportNamePrint + ",";
                System.out.println(sportNamePrint);
            }

            membresEnvoye.add(new MembreProperty(new SimpleStringProperty(membre.getNomMembre()),
                    new SimpleStringProperty(membre.getPrenomMembre()), new SimpleStringProperty(dateString),
                    new SimpleStringProperty(membre.getEmailMembre()),new SimpleStringProperty(membre.getNumPortableMembre()),
                    infos, new SimpleStringProperty(sportsPrint), new SimpleStringProperty(membre.getGenre()), membre.getIdMembre()));


            supprimerCol.setCellFactory(ActionButtonTableCell.<MembreProperty>forTableColumn("Supprimer", (MembreProperty p) -> {
                supprimerMembre(p); // si le bouton supprimer et selectionné, on supprime le membre
                return p;
            }));

            modifierCol.setCellFactory(ActionButtonTableCell.<MembreProperty>forTableColumn("Modifier", (MembreProperty p) -> {
                chargerFenetreModificationMembre("/javafx/viewModifyMembre.fxml","Modification d'un membre", getClass(), p);
                return p; // si on veut le modifier on ouvre la fenetre modifier
            }));
        }

        tableView.setItems(membresEnvoye);
    }

    // méthode permettant d'ajouter un barre de recherche (utile quand plusieurs membres) -- Redondant entre les codes (améliorer)
    private void searchBar(){
        FilteredList<MembreProperty> flMember = new FilteredList(tableView.getItems(), p -> true);//Pass the data to a filtered list
        tableView.setItems(flMember);//Set the table's items using the filtered list

        ObservableList<String> listFilter = FXCollections.observableArrayList();
        listFilter.addAll("Nom", "Prénom","Genre","Date de naissance", "Email","Téléphone Portable", "Poid", "Taille", "Sport");
        comboBoxFilter.setItems(listFilter);
        comboBoxFilter.setValue("Nom");

        searchBarPaiement.setPromptText("Rechercher ici !");
        searchBarPaiement.setOnKeyReleased(keyEvent ->
        {
            switch ((String)comboBoxFilter.getSelectionModel().getSelectedItem())//Switch on choiceBox value
            {
                case "Nom":
                    flMember.setPredicate(p -> p.getNomMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Prénom":
                    flMember.setPredicate(p -> p.getPrenomMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Genre":
                    flMember.setPredicate(p -> p.getGenreMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Date de naissance":
                    flMember.setPredicate(p -> p.getDtnMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Email":
                    flMember.setPredicate(p -> p.getEmailMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Téléphone Portable":
                    flMember.setPredicate(p -> p.getNumPortableMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Poid":
                    flMember.setPredicate(p -> String.valueOf(p.getPoidMembre()).toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name

                    break;
                case "Taille":
                    flMember.setPredicate(p -> String.valueOf(p.getTailleMembre()).toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Sport":
                    flMember.setPredicate(p -> p.getSportsMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
            }
        });

        comboBoxFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                searchBarPaiement.setText("");
                flMember.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
        });
    }


    // Méthode permettant de dire à l'utilisateur qu'il na pas cliqué sur un élement a suppriemer (si c'est le cas)
    @FXML
    private void supprimerMembreClick(ActionEvent event){
        Alert alert;
        MembreProperty selectionnerPourSuppression = tableView.getSelectionModel().getSelectedItem();
        if ( selectionnerPourSuppression == null ){
            MessageAlerte.showErrorMessage("Aucun membre n'a été selectionné","S'il vous plait choisissez un membre" );
        }else{
            supprimerMembre(selectionnerPourSuppression);
        }

    }

    // Méthode permettant de supprimer un membre (on vérifie aussi que l'utilisateur ne ce soit pas trompé)
    private void supprimerMembre(MembreProperty membreProperty){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer le membre");
        alert.setContentText("Etes vous sur de vouloir supprimer le membre "+ membreProperty.getNomMembre()+" "+ membreProperty.getPrenomMembre()+" ?");
        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.get() == ButtonType.OK){
            deleteMembreWithIdMembre(membreProperty.getIdMembre());
            MessageAlerte.actionReussi("Membre supprimé!");
            loadData();
        }else{
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Suppression annulé");
            alert2.setContentText("Vous avez annulé la suppression de ce membre");
        }
    }

    // Méthode permettant de modifier un membre, on avertit l'utilisateur également s'il n'a rien sélectionné
    @FXML
    private void modifierMembreClick(ActionEvent event){
        Alert alert;
        MembreProperty selectionnerPourModification = tableView.getSelectionModel().getSelectedItem();
        if ( selectionnerPourModification == null ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Aucun membre n'a été selectionné");
            alert.setContentText("S'il vous plait choisissez un membre");
        }else{
            chargerFenetreModificationMembre("/javafx/viewModifyMembre.fxml","Modification d'un membre", getClass(), selectionnerPourModification);
        }

    }

}
