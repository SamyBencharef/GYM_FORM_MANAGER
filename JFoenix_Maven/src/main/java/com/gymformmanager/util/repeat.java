package com.gymformmanager.util;


import com.gymformmanager.controller.controllerAccueil;
import com.gymformmanager.controller.controllerModifyMembre;
import com.gymformmanager.controller.controllerModifySport;
import com.gymformmanager.model.MembreProperty;
import com.gymformmanager.model.SportProperty;
import com.jfoenix.controls.JFXDecorator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static com.gymformmanager.util.GymFormManagerUtil.setStageIcon;

public class repeat {
    private static Transaction tx;
    private static Session session;

    // Méthode qui permet de lancer une nouvelle session et de la récuperer
    public static Session getNewSession(){
        tx = null;
        // on récupere notre session
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        return session;
    }

    // Permet de récuperer la session courrante
    public static Session getCurrentSession(){
        return session;
    }

    // Méthode qui permet d'effectuer un commit et de fermer une session
    public static void finishSession(){
        tx.commit();
        session.close();
    }

    // Méthode permettant de charger une fenetre
    public static void chargerFenetre(String loc, String titre, Class theClass){
        try{
            Parent parent = FXMLLoader.load(theClass.getResource(loc));

            Stage stage = new Stage(StageStyle.DECORATED);
            setStageIcon(stage);
            // Les commentaires bloque l'option de mettre les bords des fenetres en noir (Drakula Theme)
            //JFXDecorator decorator = new JFXDecorator(stage, parent);
            //decorator.setCustomMaximize(true);


            //Scene scene = new Scene(decorator);
            Scene scene = new Scene(parent);
            stage.setTitle(titre);

            stage.setScene(scene);

            //String uri = theClass.getResource("/style/style.css").toExternalForm();
            //scene.getStylesheets().add(uri);
            //stage.setScene(scene);


            stage.show();
        } catch (IOException ex){
            Logger.getLogger(controllerAccueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Méthode permettant de vérifier si un email est valide
    public static boolean validateEmailAddress(String emailID) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(emailID).matches();
    }

    // Méthode permettant de charger la fenetre de modification d'un sport
    public static void chargerFenetreModificationSport(String loc, String titre, Class theClass, SportProperty values){
        try{
            FXMLLoader loader = new FXMLLoader(theClass.getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene((AnchorPane) loader.load()));
            stage.setTitle(titre);

            setStageIcon(stage);

            controllerModifySport controller = loader.<controllerModifySport>getController();
            controller.initData(values);

            stage.show();

        } catch (IOException ex){
            Logger.getLogger(controllerAccueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Méthode permettant de charger la fenetre de modification d'un membre
    public static void chargerFenetreModificationMembre(String loc, String titre, Class theClass, MembreProperty values){
        try{
            FXMLLoader loader = new FXMLLoader(theClass.getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene((AnchorPane) loader.load()));
            stage.setTitle(titre);

            setStageIcon(stage);

            controllerModifyMembre controller = loader.<controllerModifyMembre>getController();
            controller.initData(values);

            stage.show();

        } catch (IOException ex){
            Logger.getLogger(controllerAccueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Méthode qui permet d'insérer une nouvelle ligne dans la database à l'aide des fonctionnalités d'Hibernate
    public static void insertData(Object object){
        // on commence la transaction qui correspond à l'insertion
        Transaction tx = null;
        // on récupere notre session
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            // et on insert
            session.save(object);
            tx.commit();
        }
        catch(HibernateException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
        finally{
            session.close();
        }
    }


    // Fonction permettant de voir un la chaine passée en paramètre est bien une variable de type int
    public static boolean estUnInt(String chaine) {
        try {
            Integer.valueOf(chaine);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }


    // Fonction permettant de voir un la chaine passée en paramètre est bien une variable de type float
    public static boolean estUnFloat(String chaine) {
        try {
            Float.valueOf(chaine);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }


    // Fonction pour fermer une fenetre
    public static void fermerFenetre(Pane rootPane){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    // Fonction pour fermer une fenetre
    public static void goFullScreen(Pane rootPane){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    //Méthode de contrôle de la validité des données saisies
    public static boolean controlerFormulaireSport(String sportIntitule, String sportEntraineur,
                                                    String sportPrixAnnee, String sportPrixTrimestre,
                                                    String sportPrixMois) {
        boolean isOk = true;
        List<String> messageErreur = new ArrayList<>();
        // Intitulé du sport non vide ?
        if (sportIntitule == null || sportIntitule.isEmpty()) {
            isOk = false;
            messageErreur.add("Le champ \"Intitulé du sport\" est obligatoire");
        }
        // Entraîneur non vide ?
        if (sportEntraineur == null || sportEntraineur.isEmpty()) {
            isOk = false;
            messageErreur.add("Le champ \"Entraîneur\" est obligatoire");
        }

        // Un des prix n'est pas vide ?
        if ( (sportPrixAnnee == null || sportPrixAnnee.isEmpty()) &&
                (sportPrixTrimestre == null || sportPrixTrimestre.isEmpty())  &&
                (sportPrixMois == null || sportPrixMois.isEmpty()) ) {

            isOk = false;
            messageErreur.add("Au moins un champ de \"Prix\" est obligatoire");
            // le prix est-il bien du format int s'il n'est pas vide?

        }else if( (!repeat.estUnInt(sportPrixAnnee) && !sportPrixAnnee.isEmpty())
                || (!repeat.estUnInt(sportPrixTrimestre) && !sportPrixTrimestre.isEmpty())
                || (!repeat.estUnInt(sportPrixMois) && !sportPrixMois.isEmpty())         ){
            isOk = false;
            messageErreur.add("Les champs \"Prix\" doivent être des chiffres");
        }

        MessageAlerte.errorPrint(isOk, messageErreur);
        return isOk;
    }

    //Méthode de contrôle de la validité des données saisies
    public static boolean controlerFormulaireMembre(String nomMembre, String prenomMembre,
                                                     boolean genreSelected, String emailMembre,
                                                     String numPortableMembre, String poidMembre,
                                                     String tailleMembre, String dtnValue, boolean sportSelected) {
        boolean isOk = true;
        List<String> messageErreur = new ArrayList<>();
        // Nom du membre non vide ?
        if (nomMembre == null || nomMembre.isEmpty()) {
            isOk = false;
            messageErreur.add("Le champ \"nom du membre\" est obligatoire");
        }
        // Prénom du membre non vide ?
        if (prenomMembre == null || prenomMembre.isEmpty()) {
            isOk = false;
            messageErreur.add("Le champ \"prénom du membre\" est obligatoire");
        }


        if(/*!hommeMembre.isSelected() && !femmeMembre.isSelected()*/ genreSelected){
            isOk = false;
            messageErreur.add("Le champ \"Homme ou Femme\" est obligatoire");
        }

        // Email du membre non vide ?
        if (emailMembre == null || emailMembre.isEmpty()) {
            isOk = false;
            messageErreur.add("Le champ \"email du membre\" est obligatoire");
        }else if(!validateEmailAddress(emailMembre)){
            isOk = false;
            messageErreur.add("Fournir \"un email valide\" est obligatoire");
        }
        // Numéro de téléphone du membre non vide ?
        if (numPortableMembre == null || numPortableMembre.isEmpty()) {
            isOk = false;
            messageErreur.add("Le champ \"téléphone portable\" est obligatoire");
        } else if (!repeat.estUnInt(numPortableMembre)){
            isOk = false;
            messageErreur.add("Le champ \"téléphone portable\" doit être des chiffres");
        }

        if( (!repeat.estUnFloat(poidMembre) && !poidMembre.isEmpty())
                || (!repeat.estUnFloat(tailleMembre) && !tailleMembre.isEmpty())){
            isOk = false;
            messageErreur.add("Les champs \"poid et taille du membre\" doivent être des chiffres");
        }

        //String value = (dtnMembre.getValue()!= null ? dtnMembre.getValue().toString() :"");

        if(dtnValue.equals("")) {
            isOk = false;
            messageErreur.add("Le champ \"date de naissance\" est obligatoire");
        }

        if ( /*sportMembre.getCheckModel().isEmpty() */ !sportSelected){
            isOk = false;
            messageErreur.add("Le champ \"Sport\" doit contenir au minimm un sport");
        }

        // si des messages d'erreurs ont eu lieu, on fait apparaître une pop up d'erreur.
        MessageAlerte.errorPrint(isOk, messageErreur);
        return isOk;
    }
}
