package com.gymformmanager.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

// Classe permettant de gérer les Membres contenu dans les TablesViews FXML
public class MembreProperty {


    private int idMembre;

    private SimpleStringProperty nomMembre;

    private SimpleStringProperty prenomMembre;

    private SimpleStringProperty dtnMembre;

    private SimpleStringProperty emailMembre;

    private SimpleStringProperty numPortableMembre;

    private SimpleFloatProperty poidMembre;

    private SimpleFloatProperty tailleMembre;

    private SimpleStringProperty sportsMembre;

    private SimpleStringProperty genreMembre;


    public String getGenreMembre() {
        return genreMembre.get();
    }

    public SimpleStringProperty genreMembreProperty() {
        return genreMembre;
    }

    public MembreProperty(SimpleStringProperty nomMembre,
                          SimpleStringProperty prenomMembre, SimpleStringProperty dtnMembre,
                          SimpleStringProperty emailMembre, SimpleStringProperty numPortableMembre,
                          Map<String, SimpleFloatProperty> infos, SimpleStringProperty sportsMembre,
                          SimpleStringProperty genre, int idMembre) {
        this.nomMembre = nomMembre;
        this.prenomMembre = prenomMembre;
        this.dtnMembre = dtnMembre;
        this.emailMembre = emailMembre;
        this.numPortableMembre = numPortableMembre;
        if(infos.containsKey("poidMembre")){
            this.poidMembre=infos.get("poidMembre");
        }
        if(infos.containsKey("tailleMembre")){
            this.tailleMembre=infos.get("tailleMembre");
        }
        this.sportsMembre=sportsMembre;
        this.genreMembre=genre;
        this.idMembre=idMembre;
    }

    public int getIdMembre() { return idMembre; }

    public String getNomMembre() { return nomMembre.get(); }

    public SimpleStringProperty nomMembreProperty() { return nomMembre; }

    public String getPrenomMembre() { return prenomMembre.get(); }

    public SimpleStringProperty prenomMembreProperty() { return prenomMembre; }

    public String getDtnMembre() { return dtnMembre.get(); }

    public SimpleStringProperty dtnMembreProperty() { return dtnMembre; }

    public String getEmailMembre() { return emailMembre.get(); }

    public SimpleStringProperty emailMembreProperty() { return emailMembre; }

    public String getNumPortableMembre() { return numPortableMembre.get(); }

    public SimpleStringProperty numPortableMembreProperty() { return numPortableMembre; }

    public float getPoidMembre() { return poidMembre.get(); }

    public SimpleFloatProperty poidMembreProperty() { return poidMembre; }

    public float getTailleMembre() { return tailleMembre.get(); }

    public SimpleFloatProperty tailleMembreProperty() { return tailleMembre; }

    public String getSportsMembre() { return sportsMembre.get(); }

    public SimpleStringProperty sportsMembreProperty() { return sportsMembre; }
}
