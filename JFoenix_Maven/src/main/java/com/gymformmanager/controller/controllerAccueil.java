package com.gymformmanager.controller;

import com.gymformmanager.model.*;
import com.gymformmanager.util.ActionButtonTableCell;
import com.gymformmanager.util.AutoCompleteComboBoxListener;
import com.gymformmanager.util.MessageAlerte;
import com.gymformmanager.util.repeat;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.gymformmanager.model.MembreDAO.*;
import static com.gymformmanager.model.MembreSportDAO.getIdMembreSportByIdSportAndIdMembre;
import static com.gymformmanager.model.MembreSportDAO.getMembreSportByIdMembreSport;
import static com.gymformmanager.model.PaiementDAO.*;
import static com.gymformmanager.model.SportDAO.getIdSportsByIdMembre;
import static com.gymformmanager.model.SportDAO.getSportByIdSport;
import static com.gymformmanager.util.repeat.*;


public class controllerAccueil implements Initializable{


    @FXML
    private StackPane rootPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Text sportLabel;

    @FXML
    private Text sommeLabel;

    @FXML
    private Text typePaiementLabel;

    @FXML
    private Text dateLabel;


    // VARIABLE AJOUTER PAIEMENT
    @FXML
    JFXComboBox membreField;

    @FXML
    JFXComboBox sportField;

    @FXML
    JFXComboBox sommeField;

    @FXML
    JFXComboBox typePaiementField;

    @FXML
    JFXDatePicker dateField;

    @FXML
    JFXButton boutonPaiement;

    private int idMembre;
    private int idSport;
    private int paiement;

    private int paiementAnnee;
    private int paiementTrimestre;

    private String dureePaiement;
    // FIN VARIABLE AJOUTER PAIEMENT


    // VARIABLE RETARD PAIEMENT
    @FXML
    JFXComboBox comboBoxFilterRetard;

    @FXML
    JFXTextField searchBarPaiementRetard;

    @FXML
    public TableView<TableauRetardPorperty> tableViewRetard;

    @FXML
    public TableColumn<TableauRetardPorperty, SimpleStringProperty> nomColRetard ;

    @FXML
    public TableColumn<TableauRetardPorperty, SimpleStringProperty> prenomColRetard ;

    @FXML
    public TableColumn<TableauRetardPorperty, SimpleStringProperty> sportColRetard ;

    @FXML
    public TableColumn<TableauRetardPorperty, SimpleDateFormat> retardDepuisCol ;

    @FXML
    public TableColumn<TableauRetardPorperty, SimpleDateFormat> datePaiementColRetard ;

    @FXML
    public TableColumn<TableauRetardPorperty, SimpleStringProperty> sommeColRetard ;

    @FXML
    public TableColumn envoyerRappelCol;

    @FXML
    public TableColumn retirerRetardCol;

    // FIN VARIABLE RETARD PAIEMENT



    // VARIABLE HISTORIQUE DE PAIEMENT
    @FXML
    JFXComboBox comboBoxFilter;

    @FXML
    JFXTextField searchBarPaiement;

    @FXML
    public TableView<TableauPaiementProperty> tableViewPaiement;

    @FXML
    public TableColumn<TableauPaiementProperty, SimpleStringProperty> nomCol ;

    @FXML
    public TableColumn<TableauPaiementProperty, SimpleStringProperty> prenomCol ;

    @FXML
    public TableColumn<TableauPaiementProperty, SimpleStringProperty> dtnCol ;

    @FXML
    public TableColumn<TableauPaiementProperty, SimpleStringProperty> sportCol ;

    @FXML
    public TableColumn<TableauPaiementProperty, SimpleIntegerProperty> paiementCol ;

    @FXML
    public TableColumn<TableauPaiementProperty, SimpleStringProperty> datePaiementCol ;

    @FXML
    public TableColumn<TableauPaiementProperty, SimpleStringProperty> typePaiementCol;

    @FXML
    public TableColumn<TableauPaiementProperty, SimpleStringProperty> dureeCol;

    @FXML
    public TableColumn supprimerCol;
    // Fin variable historique de paiement


    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;


    @FXML
    ImageView refreshImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> { // permet de rafraichir le tableau
            ajouterPaiementPart();
            initialize();
            loadDataRetardPaiement();
            loadDataHistoriquePaiement();
        });


        // Initialise le drawer (Correspond au menu Hamburger)
        initDrawer();


        // PARTIE AJOUTER UN PAIEMENT ------------------------------------------
        ajouterPaiementPart();
        // FIN PARTIE AJOUTER UN PAIEMENT ---------------------------------------


        // PARTIE RETARD PAIEMENT ------------------------------------------
        initColRetardPaiement();
        loadDataRetardPaiement();
        searchBarRetardPaiement();
        // FIN PARTIE RETARD PAIEMENT ---------------------------------------



        // PARTIE HISTORIQUE PAIEMENT ------------------------------------------
        initColHistoriquePaiement();
        loadDataHistoriquePaiement();
        searchBarHistoriquePaiement();
        // FIN PARTIE HISTORIQUE PAIEMENT ---------------------------------------
    }


    // Méthode permettant de fermer l'application (à partir du menubar)
    @FXML
    public void closeApplication(ActionEvent event){
        fermerFenetre(rootPane);
    }

    // Méthode permettant de faire passer l'application en plein écran.
    @FXML
    public void fullScreen(ActionEvent event){
        goFullScreen(rootPane);
    }

    // Méthode permettant de gérer le menu hamburger
    private void initDrawer(){
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource("/javafx/viewMenu.fxml"));
            drawer.setSidePane(toolbar);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
                task.setRate(task.getRate() * -1);
                task.play();
                if(drawer.isOpened()) {
                    drawer.close();
                }else {
                    drawer.open();
                }
        });
    }


    // -------------------------- Partie du chargements des différentes fenêtres. ------------------------------\\
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
    public void chargerSamy(ActionEvent event){
        chargerFenetre("/javafx/viewSamy.fxml","Contactez moi !", getClass());
    }
    // ------------------------------------- FIN PARTIE CHANGEMENT FENETRE -------------------------------------\\



    // ----------------------------------------- Partie du de l'ajout de paiement. -------------------------------\\
    private final Color colorRouge = Color.rgb(246,10,32);
    private final Color colorGris = Color.rgb(64,64,64);

    //Permet d'ajouter un paiement
    @FXML
    public void ajouterPaiement(ActionEvent event){
        // On vérifie qu'il y a bien une date de saisie
        String value = (dateField.getValue()!= null ? dateField.getValue().toString() :"");
        if(value.equals("")) { // Si c'est vide, on affiche une erreur
            boolean isOk = false;
            List<String> messageErreur = new ArrayList<>();
            messageErreur.add("Le champ \"date de paiement\" est obligatoire");
            MessageAlerte.errorPrint(isOk, messageErreur);
        }else{ // si ce n'est pas le cas on insére le nouveau paiement
                int idMembreSport = getIdMembreSportByIdSportAndIdMembre(idSport, idMembre);
                Paiement nouveauPaiement = new Paiement(idMembreSport, Date.valueOf(dateField.getValue()),paiement, (String)typePaiementField.getValue(), dureePaiement);
                repeat.insertData(nouveauPaiement);

                // on notifie l'utilisateur que le paiement est un succès
                JFXButton button = new JFXButton("D'accord !");
                MessageAlerte.showMaterialDialog(rootPane, borderPane, Arrays.asList(button), "Nouveau paiement inséré !", null);

                // on n'oublie pas de remettre vide tous les combobox.
                initialize();
                membreField.setValue(null);
                sportLabel.setFill(colorGris);

                // et on mets à jour les tables.
                loadDataHistoriquePaiement();
                loadDataRetardPaiement();
        }
    }

    // Méthode permmettant de mettre à vide tous les champs, de les desactiver et de mettre les labels en gris. (Excepté Membre)
    private void initialize(){
        sportField.setDisable(true);
        sportField.getSelectionModel().clearSelection();
        sommeField.setDisable(true);
        sommeField.getSelectionModel().clearSelection();
        typePaiementField.setDisable(true);
        typePaiementField.getSelectionModel().clearSelection();
        dateField.setDisable(true);
        dateField.getEditor().clear();
        boutonPaiement.setDisable(true);
        sommeLabel.setFill(colorGris);
        typePaiementLabel.setFill(colorGris);
        dateLabel.setFill(colorGris);
    }

    // Méthode qui met en forme la partie paiement
    // I mean: qui initialise et active les comboBox, met en rouge les labels au fur et a mesure des selection
    public void ajouterPaiementPart(){
        // premièrement on remplie la première combobox de tous les membres contenue dans la base de donnée
        List<Membre> membres = getAllMembres();
        ObservableList<String> listMembre = FXCollections.observableArrayList();
        HashMap<String, Integer> joindIdMembreWithStringBox = new HashMap<>(); // Cette variable sera utile à retrouver facilement l'id du membre

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        for(Membre membre: membres) {
            String dateString = formatter.format(membre.getDtnMembre());
            String keyPrint = membre.getNomMembre() + " " + membre.getPrenomMembre() + " " +dateString;
            joindIdMembreWithStringBox.put(keyPrint, membre.getIdMembre());
            listMembre.add(keyPrint);
        }
        membreField.getItems().setAll();
        membreField.getItems().addAll(listMembre);
        new AutoCompleteComboBoxListener<>(membreField); // Permet de pouvoir écrire dans cette combobox (utile pour une grande liste de membre)


        // on remplie également les moyens de paiements qui sont les mêmes quel que soit les choix précedent
        ObservableList<String> listTypePaiement = FXCollections.observableArrayList();
        listTypePaiement.setAll("Carte Bancaire", "Chèques", "Liquide", "Chèque sport", "Chèque jeune");
        typePaiementField.getItems().setAll(listTypePaiement);

        // on désactive tous les champs qui ne doivent pas être accessible au début.
        initialize();


        // on créer un Map qui sera utile pour trouver le sport selectionné avec son id
        HashMap<String, Integer> joindIdSportWithStringBox = new HashMap<>();

        // au moment de la selection du sportif
        membreField.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            // on rénitialise tous les champs une fois de plus, au cas ou la personne qui crée le paiment décide de revenir en arrière
            // et choisir un autre membre, dans ce cas il faudra recommencer le processus
            initialize();
            sportField.getItems().setAll(); // Rajouter cet isntructions permet d'éviter les doublons en cas de retour en arrière !

            // sinon on active la combobox de sport
            sportField.setDisable(false);
            // et on mets la couleur du label en rouge pour ajouter l'indication qu'on peut interrargir avec la combobox
            sportLabel.setFill(colorRouge);


            if(joindIdMembreWithStringBox.containsKey(newValue)){ // On regarde l'ID du membre qui est en rapport avec la cas selectionné
                idMembre = joindIdMembreWithStringBox.get(newValue); // on récupere cet id
                List<Integer> sports = getIdSportsByIdMembre(joindIdMembreWithStringBox.get(newValue)); // on récupere tous les id de ses sport
                ObservableList<String> listSport = FXCollections.observableArrayList(); // on crée la list qui contiendra le nom des sports
                for(Integer idSport : sports){ // et pour chaque id de sport récuperer, on ajoute les sports.
                    Sport sportSelected = getSportByIdSport(idSport);
                    listSport.add(sportSelected.getIntituleSport());
                    joindIdSportWithStringBox.put(sportSelected.getIntituleSport(), sportSelected.getIdSport());
                }
                sportField.getItems().addAll(listSport); // et on remplie la prochaine comboBox
            }
        });

        // on répete le même processus pour tous les autres combo box
        sportField.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            // activer la prochaine combobox
            sommeField.setDisable(false);
            sommeLabel.setFill(colorRouge);

            // mais également la vider
            sommeField.getSelectionModel().clearSelection();
            sommeField.getItems().setAll();

            // Désactiver toutes les suivantes également et changer le couleur des labels
            typePaiementField.setDisable(true);
            typePaiementField.getSelectionModel().clearSelection();
            dateField.setDisable(true);
            dateField.getEditor().clear();
            boutonPaiement.setDisable(true);
            typePaiementLabel.setFill(colorGris);
            dateLabel.setFill(colorGris);

            // Même processus que le précedents
            if(joindIdSportWithStringBox.containsKey(newValue)){
                idSport = joindIdSportWithStringBox.get(newValue);
                Sport sport = getSportByIdSport(joindIdSportWithStringBox.get(newValue));
                ObservableList<String> listPaiement = FXCollections.observableArrayList();

                // A l'exception qu'il faut différencier avec les prix cette fois-ci
                if (sport.getPrixAnnee() != null ){
                    listPaiement.add(String.valueOf(sport.getPrixAnnee()));
                    paiementAnnee = sport.getPrixAnnee();
                }
                if (sport.getPrixTrimestre() != null ){
                    listPaiement.add(String.valueOf(sport.getPrixTrimestre()));
                    paiementTrimestre = sport.getPrixTrimestre();
                }
                if (sport.getPrixMois() != null ){
                    listPaiement.add(String.valueOf(sport.getPrixMois()));
                }
                sommeField.getItems().addAll(listPaiement);
            }
        });


        sommeField.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            // active prochaine
            typePaiementField.setDisable(false);
            typePaiementField.getSelectionModel().clearSelection();

            // desactive les trop éloignés en cas de retour
            dateField.setDisable(true);
            dateField.getEditor().clear();
            boutonPaiement.setDisable(true);
            typePaiementLabel.setFill(colorRouge);
            dateLabel.setFill(colorGris);

            // mettre la liste qui a déjà été préparé
            typePaiementField.getItems().setAll(listTypePaiement);

            // récupéré la somme
            paiement = Integer.parseInt((String)newValue);

            if (paiement == paiementAnnee ){
                dureePaiement ="Année";
            }else if(paiement == paiementTrimestre){
                dureePaiement ="Trimestre";
            }else{
                dureePaiement ="Mois";
            }
        });


        typePaiementField.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            dateField.setDisable(false);
            dateField.getEditor().clear();

            dateLabel.setFill(colorRouge);
        });

        dateField.valueProperty().addListener( (options, oldValue, newValue) -> {
            boutonPaiement.setDisable(false);
        });
    }


    // ------------------------------------- PARTIE RETARD PAIEMENT -------------------------------------\\p;:
    // Initialisation basic d'un TableView JFX
    private void initColRetardPaiement(){
        nomColRetard.setCellValueFactory(new PropertyValueFactory<>("nomMembre"));
        prenomColRetard.setCellValueFactory(new PropertyValueFactory<>("prenomMembre"));
        sportColRetard.setCellValueFactory(new PropertyValueFactory<>("sportMembre"));
        retardDepuisCol.setCellValueFactory(new PropertyValueFactory<>("retardDepuis"));
        datePaiementColRetard.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
        sommeColRetard.setCellValueFactory(new PropertyValueFactory<>("sommePaiement"));
    }

    // Variable qui contiendra la liste des paiements affichés
    private  ObservableList<TableauRetardPorperty> myPaiementsRetardPrint = FXCollections.observableArrayList();

    // Fonction importante qui permet d'afficher les lignes de paiement en retard (Fait grâce à un algorithme)
    public void loadDataRetardPaiement(){

        // On vide le tableau (au cas ou un nouveau paimenet est ajouté, modifier, supprimer.
        // Pour qu'aucun doublou ne soit affiché.
        myPaiementsRetardPrint.clear();

        // ensuite on récupere tous les paiements
        List<Paiement> paiements = getAllPaiement();

        // et on les aprses
        for (Paiement paiement : paiements) {
            // on vérifie si les paiements ont été considéré comme non en retard (possible en cliquant sur supprimer retard)
            if (!paiement.isPlusRecent()){ // si ce n'est pas le cas, alors on vérifié s'ils sont en retard
                // on récupere la date du paiement dans des variables
                Calendar calendar = new GregorianCalendar(); // on crée un calendrier qui contiendra la date du paiement sous forme gregorian
                calendar.setTime(paiement.getDatePaiement()); // voici le calendrier

                // on récupere les valeurs de notre date, grâce à ce dernier
                int year = calendar.get(Calendar.YEAR);
                //Add one to month {0 - 11}
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // On récupere la date courante
                Calendar cal = Calendar.getInstance();
                java.util.Date date=cal.getTime();

                boolean add=false; // On crée un boolean qui nous dira s'il faut ajouter le paiement ou pas (par défaut non)

                java.util.Date dateMax; // on crée un variable qui contiendra
                // // la date maximum du paiement (cad à quel moment il devient un retard)

                // et l'on on commence, si c'est un paiement d'une année, il faut regarder que la date courante n'ampiete pas sur la prochaine
                // année scolaire (GYM FORM) fonctionne seulement en paiement d'année scolaire.
                if(paiement.getDuree().compareTo("Année")==0){
                    // si vous voulez une année complete, remplacé 8 par payMonth-1 et 3 par day
                    dateMax = new GregorianCalendar((year+1), 8, 3).getTime();
                    if(date.after(dateMax)) // si c'est le cas alors on a un retard
                        add=true;

                }else if(paiement.getDuree().compareTo("Trimestre")==0){ // pour les trimestres, il y en a 3
                    // Debut de trimestre : Septembre(09), Janvier(01), Avril(04), il faut donc regarder que la date du paiement de trimestre
                    // n'ampiete pas sur un autre trimestre
                    int payMonth=0;
                    int payYear=year;
                    if (9<=month){
                        payMonth=1;
                        payYear = payYear+1;
                    }else if(1<=month && month<4)
                        payMonth=4;
                    else
                        payMonth=9;

                    dateMax = new GregorianCalendar(payYear, payMonth-1, day).getTime();
                    if(date.after(dateMax))
                        add=true;

                }else{  // et enfin pour les paiements au moins, il faut juste ajouter un mois
                    if (month == 12)
                        dateMax = new GregorianCalendar((year+1), 0, day).getTime(); // attention à décembre qui renitiallise
                    else
                        dateMax = new GregorianCalendar(year, month, day).getTime();
                    if(date.after(dateMax))
                        add=true;

                }
                if (add){ // si on doit ajouter, alors on ajoute le paiement (traditionnel ajout dans un TableView FX)
                    MembreSport membreSportRecuperer = getMembreSportByIdMembreSport(paiement.getIdMembre_Sport());
                    Sport sportRecuperer = getSportByIdSport(membreSportRecuperer.getIdSport());
                    Membre membreRecuperer = getMembreByIdMembre(membreSportRecuperer.getIdMembre());

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

                    String datePaiementString = formatter.format(paiement.getDatePaiement());
                    SimpleStringProperty datePaiementRecuperer = new SimpleStringProperty(datePaiementString);

                    String datePaiementMaxString = formatter.format(dateMax);
                    SimpleStringProperty datePaiementMaxRecuperer = new SimpleStringProperty(datePaiementMaxString);

                    myPaiementsRetardPrint.add(new TableauRetardPorperty(new SimpleStringProperty(membreRecuperer.getNomMembre()),
                            new SimpleStringProperty(membreRecuperer.getPrenomMembre()),
                            new SimpleStringProperty(sportRecuperer.getIntituleSport()),
                            datePaiementRecuperer, datePaiementMaxRecuperer,
                            new SimpleStringProperty(paiement.getDuree()+" : "+paiement.getSommePaye()),
                            paiement.getIdPaiement()    ));

                    retirerRetardCol.setCellFactory(ActionButtonTableCell.<TableauRetardPorperty>forTableColumn("Supprimer", (TableauRetardPorperty p) -> {
                        supprimerRetard(p); // Si la personne clique sur le bouton retard, alors on supprime le retard :p
                        return p;
                    }));

                    envoyerRappelCol.setCellFactory(ActionButtonTableCell.<TableauRetardPorperty>forTableColumn("Envoyer Rappel", (TableauRetardPorperty p) -> {
                        // si le bouton envoie d'email à été selectionné alors on envoie un email

                        // on récupere l'email du membre
                        String email = getMembreByIdMembre(getMembreSportByIdMembreSport(getPaiementWithIdPaiement(p.getIdPaiement()).getIdMembre_Sport()).getIdMembre()).getEmailMembre();

                        try {
                            // on crée l'entête
                            String entete = "Retard de paiement pour le sport "+p.getSportMembre()+" depuis le "+p.getRetardDepuis() +" - GYM FORM (Albi)";

                            // et on l'envoie ----- Processus long.. trouvé une solution
                            sendEmail(email, entete);

                            // et on affiche à l'utilisateur qu'il a bien été envoyé
                            JFXButton button = new JFXButton("D'accord !");
                            MessageAlerte.showMaterialDialog(rootPane, borderPane, Arrays.asList(button), "Rappel envoyé !", null);

                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                        return p;
                    }));
                }
            }
        }
        tableViewRetard.setItems(myPaiementsRetardPrint); // On ajoute tous les paiements
    }


    // Permet de supprimer un retard à l'aide d'un click droit, attention, s'il n'y a pas d'élement il faut prévenir l'utilisateur de
    // ne pas faire ça, ahah.
    @FXML
    private void supprimerRetardClick(ActionEvent event){
        Alert alert;
        TableauRetardPorperty selectionnerPourSuppression = tableViewRetard.getSelectionModel().getSelectedItem();
        if ( selectionnerPourSuppression == null ){
            alert = new Alert(Alert.AlertType.ERROR);
            MessageAlerte.showErrorMessage("Aucun retard n'a été selectionné","S'il vous plait choisissez un retard" );
        }else{
            supprimerRetard(selectionnerPourSuppression);
        }
    }

    // Fonction permettant de supprimer le retard
    private void supprimerRetard(TableauRetardPorperty tableauRetardPorperty){
        // on vérifie que c'est bien l'action voulu par notre utilisateur
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer le retard");
        alert.setContentText("Etes vous sur de vouloir supprimer le retard de "+ tableauRetardPorperty.getNomMembre()+" " +
                " "+ tableauRetardPorperty.getPrenomMembre()+" pour le sport "+  tableauRetardPorperty.getSportMembre()+ " " +
                "d'une valeur de "+tableauRetardPorperty.getSommePaiement()+" et datant du "+ tableauRetardPorperty.getDatePaiement()+"" +
                "et en retard depuis le "+ tableauRetardPorperty.getRetardDepuis());
        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.get() == ButtonType.OK){ // et si c'est le cas, on le supprimer en mettant le boolean sur true, ce qui ne le fera plus
            // apparaitre dans le tableau retard
            updatePaiementPlusRecent(tableauRetardPorperty.getIdPaiement(), true);
            MessageAlerte.actionReussi("Retard retiré !");
            loadDataRetardPaiement();
        }else{ // sinon, on annule.
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Suppression annulé");
            alert2.setContentText("Vous avez annulé la suppression de ce paiement");
        }
    }

    // On ajoute une bar de recherche. -- Redondant dans les codes, amélioration possible
    private void searchBarRetardPaiement(){
        FilteredList<TableauRetardPorperty> flRetardPaiement = new FilteredList(tableViewRetard.getItems(), p -> true);//Pass the data to a filtered list
        tableViewRetard.setItems(flRetardPaiement);//Set the table's items using the filtered list

        ObservableList<String> listFilter = FXCollections.observableArrayList();
        listFilter.addAll("Nom", "Prénom", "Sport","Payé le", "En retard depuis", "Somme payé");
        comboBoxFilterRetard.setItems(listFilter);
        comboBoxFilterRetard.setValue("Nom");

        searchBarPaiementRetard.setPromptText("Rechercher ici !");
        searchBarPaiementRetard.setOnKeyReleased(keyEvent ->
        {
            switch ((String)comboBoxFilterRetard.getSelectionModel().getSelectedItem())//Switch on choiceBox value
            {
                case "Nom":
                    flRetardPaiement.setPredicate(p -> p.getNomMembre().toLowerCase().contains(searchBarPaiementRetard.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Prénom":
                    flRetardPaiement.setPredicate(p -> p.getPrenomMembre().toLowerCase().contains(searchBarPaiementRetard.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Sport":
                    flRetardPaiement.setPredicate(p -> p.getSportMembre().toLowerCase().contains(searchBarPaiementRetard.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Payé le":
                    flRetardPaiement.setPredicate(p -> p.getDatePaiement().toLowerCase().contains(searchBarPaiementRetard.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "En retard depuis":
                    flRetardPaiement.setPredicate(p -> String.valueOf(p.getRetardDepuis()).toLowerCase().contains(searchBarPaiementRetard.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Somme payé":
                    flRetardPaiement.setPredicate(p -> p.getSommePaiement().toLowerCase().contains(searchBarPaiementRetard.getText().toLowerCase().trim()));//filter table by first name
                    break;
            }
        });

        comboBoxFilterRetard.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                searchBarPaiementRetard.setText("");
                flRetardPaiement.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
        });
    }

    // Méthode permettant d'envoyer un email, grâce au service stmp de gmail..
    public void sendEmail(String email, String entete) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("votre-adresse-email@gmail.com", "votre-mot-de-passe");
                // TROUVER UNE SOLUTION SECURITE 00 DAVOIR LE PASSWORD EN PLAINTEXT OMG
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("votre-adresse-email@gmail.com"));


        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(entete);


        String filename="src/main/resources/style/emailTemplate.html";

        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(content);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent( content , "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

    }
    // ------------------------------------- FIN PARTIE RETARD PAIEMENT -------------------------------------\\



    // ------------------------------------- PARTIE HISTORIQUE PAIEMENT -------------------------------------\\
    // Initialisation clasique d'un TableView FX
    private void initColHistoriquePaiement(){
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nomMembre"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenomMembre"));
        dtnCol.setCellValueFactory(new PropertyValueFactory<>("dtnMembre"));
        paiementCol.setCellValueFactory(new PropertyValueFactory<>("sommePaye"));
        datePaiementCol.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
        sportCol.setCellValueFactory(new PropertyValueFactory<>("intituleSport"));
        typePaiementCol.setCellValueFactory(new PropertyValueFactory<>("typePaiement"));
        dureeCol.setCellValueFactory(new PropertyValueFactory<>("dureePaiement"));
    }

    // Pareil que retard sans l'algorithm, donc on rajoute tous les paiements..
    private  ObservableList<TableauPaiementProperty> myPaiementsPrint = FXCollections.observableArrayList();

    // Fonction rajoutant les paiements
    private void loadDataHistoriquePaiement(){
        myPaiementsPrint.clear();
        List<Paiement> paiements = getAllPaiement();

        for (Paiement paiement : paiements) {
            SimpleIntegerProperty paiementRecuperer = new SimpleIntegerProperty(paiement.getSommePaye());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
            String datePaiementString = formatter.format(paiement.getDatePaiement());
            SimpleStringProperty datePaiementRecuperer = new SimpleStringProperty(datePaiementString);

            MembreSport membreSportRecuperer = getMembreSportByIdMembreSport(paiement.getIdMembre_Sport());
            Sport sportRecuperer = getSportByIdSport(membreSportRecuperer.getIdSport());
            Membre membreRecuperer = getMembreByIdMembre(membreSportRecuperer.getIdMembre());

            String dtnString = formatter.format(membreRecuperer.getDtnMembre());
            SimpleStringProperty dtnRecuperer = new SimpleStringProperty(dtnString);


            myPaiementsPrint.add(new TableauPaiementProperty(new SimpleStringProperty(membreRecuperer.getNomMembre()),
                    new SimpleStringProperty(membreRecuperer.getPrenomMembre()),
                    dtnRecuperer, paiementRecuperer, datePaiementRecuperer,
                    new SimpleStringProperty(sportRecuperer.getIntituleSport()),
                    new SimpleStringProperty(paiement.getTypePaiement()),
                    new SimpleStringProperty(paiement.getDuree()), paiement.getIdPaiement()));

            supprimerCol.setCellFactory(ActionButtonTableCell.<TableauPaiementProperty>forTableColumn("Supprimer", (TableauPaiementProperty p) -> {
                supprimerPaiement(p);
                return p;
            }));

        }
        tableViewPaiement.setItems(myPaiementsPrint);
    }

    // Pareil que pour les retard, on verifie que ce n'est pas dans le vide
    @FXML
    private void supprimerPaiementClick(ActionEvent event){
        Alert alert;
        TableauPaiementProperty selectionnerPourSuppression = tableViewPaiement.getSelectionModel().getSelectedItem();
        if ( selectionnerPourSuppression == null ){
            MessageAlerte.showErrorMessage("Aucun paiement n'a été selectionné","S'il vous plait choisissez un paiement" );
        }else{
            supprimerPaiement(selectionnerPourSuppression);
        }

    }

    // Fonction qui supprime le paiement.. pareil que pour retard..
    private void supprimerPaiement(TableauPaiementProperty tableauPaiementProperty){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer le paiement");
        alert.setContentText("Etes vous sur de vouloir supprimer le paiement de "+ tableauPaiementProperty.getNomMembre()+" " +
                " "+ tableauPaiementProperty.getPrenomMembre()+" pour le sport "+  tableauPaiementProperty.getIntituleSport()+ " " +
                "d'une valeur de "+tableauPaiementProperty.getSommePaye()+" et datant du "+ tableauPaiementProperty.getDatePaiement());
        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.get() == ButtonType.OK){
            deletePaiementWithIdPaiement(tableauPaiementProperty.getIdPaiement()); // mais on le supprime vraiment cette fois .
            MessageAlerte.actionReussi("Paiement supprimé!");
            loadDataHistoriquePaiement();
        }else{
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Suppression annulé");
            alert2.setContentText("Vous avez annulé la suppression de ce paiement");
        }
    }

    // Bar de recherche - redondant.. amélioration needed
    private void searchBarHistoriquePaiement(){
        FilteredList<TableauPaiementProperty> flPaiement = new FilteredList(tableViewPaiement.getItems(), p -> true);//Pass the data to a filtered list
        tableViewPaiement.setItems(flPaiement);//Set the table's items using the filtered list

        ObservableList<String> listFilter = FXCollections.observableArrayList();
        listFilter.addAll("Nom", "Prénom", "Date de naissance", "Sport","Paiement", "Date du paiement");
        comboBoxFilter.setItems(listFilter);
        comboBoxFilter.setValue("Nom");

        searchBarPaiement.setPromptText("Rechercher ici !");
        searchBarPaiement.setOnKeyReleased(keyEvent ->
        {
            switch ((String)comboBoxFilter.getSelectionModel().getSelectedItem())//Switch on choiceBox value
            {
                case "Nom":
                    flPaiement.setPredicate(p -> p.getNomMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Prénom":
                    flPaiement.setPredicate(p -> p.getPrenomMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Date de naissance":
                    flPaiement.setPredicate(p -> p.getDtnMembre().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Sport":
                    flPaiement.setPredicate(p -> p.getIntituleSport().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Paiement":
                    flPaiement.setPredicate(p -> String.valueOf(p.getSommePaye()).toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
                case "Date du paiement":
                    flPaiement.setPredicate(p -> p.getDatePaiement().toLowerCase().contains(searchBarPaiement.getText().toLowerCase().trim()));//filter table by first name
                    break;
            }
        });

        comboBoxFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                searchBarPaiement.setText("");
                flPaiement.setPredicate(null);//This is same as saying flPerson.setPredicate(p->true);
            }
        });
    }
    // ------------------------------------- FIN PARTIE HISTORIQUE PAIEMENT -------------------------------------\\

}
