package com.gymformmanager.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

// Classe permettant de gérer les Sports contenu dans les TablesViews FXML
public class SportProperty {

    private int idSport;

    private SimpleStringProperty intituleSport;

    private SimpleIntegerProperty prixAnnee;

    private SimpleIntegerProperty prixTrimestre;

    private SimpleIntegerProperty prixMois;

    private SimpleStringProperty entraineur;

    public SportProperty(SimpleStringProperty intituleSport,
                         Map<String, SimpleIntegerProperty> prix,
                         SimpleStringProperty entraineur, int idSport) {
        this.intituleSport = intituleSport;

        if(prix.containsKey("prixAnnee")){
            this.prixAnnee=prix.get("prixAnnee");
        }
        if(prix.containsKey("prixTrimestre")){
            this.prixTrimestre=prix.get("prixTrimestre");
        }
        if(prix.containsKey("prixMois")){
            this.prixMois=prix.get("prixMois");
        }

        this.entraineur = entraineur;

        this.idSport = idSport;
    }

    public int getIdSport() { return idSport; }

    public String getIntituleSport() { return intituleSport.get(); }

    public SimpleStringProperty intituleSportProperty() { return intituleSport; }

    public int getPrixAnnee() { return prixAnnee.get(); }

    public SimpleIntegerProperty prixAnneeProperty() { return prixAnnee; }

    public int getPrixTrimestre() { return prixTrimestre.get(); }

    public SimpleIntegerProperty prixTrimestreProperty() { return prixTrimestre; }

    public int getPrixMois() { return prixMois.get(); }

    public SimpleIntegerProperty prixMoisProperty() { return prixMois; }

    public String getEntraineur() { return entraineur.get(); }

    public SimpleStringProperty entraineurProperty() { return entraineur; }
}
