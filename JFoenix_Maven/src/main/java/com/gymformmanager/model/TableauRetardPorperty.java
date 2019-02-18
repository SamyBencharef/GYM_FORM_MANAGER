package com.gymformmanager.model;

import javafx.beans.property.SimpleStringProperty;

// Classe permettant de gérer les Retard contenu dans les TablesViews FXML
public class TableauRetardPorperty {

    private SimpleStringProperty nomMembre;

    private SimpleStringProperty prenomMembre;

    private SimpleStringProperty sportMembre;

    private SimpleStringProperty retardDepuis;

    private SimpleStringProperty datePaiement;

    private SimpleStringProperty sommePaiement;

    private int idPaiement;

    public TableauRetardPorperty(SimpleStringProperty nomMembre,
                                 SimpleStringProperty prenomMembre,
                                 SimpleStringProperty sportMembre,
                                 SimpleStringProperty retardDepuis,
                                 SimpleStringProperty datePaiement,
                                 SimpleStringProperty sommePaiement,
                                 int idPaiement) {
        this.nomMembre = nomMembre;
        this.prenomMembre = prenomMembre;
        this.sportMembre = sportMembre;
        this.retardDepuis = retardDepuis;
        this.datePaiement = datePaiement;
        this.sommePaiement = sommePaiement;
        this.idPaiement = idPaiement;
    }

    public String getNomMembre() { return nomMembre.get(); }

    public SimpleStringProperty nomMembreProperty() { return nomMembre; }

    public String getPrenomMembre() { return prenomMembre.get(); }

    public SimpleStringProperty prenomMembreProperty() { return prenomMembre; }

    public String getSportMembre() { return sportMembre.get(); }

    public SimpleStringProperty sportMembreProperty() { return sportMembre; }

    public String getRetardDepuis() { return retardDepuis.get(); }

    public SimpleStringProperty retardDepuisProperty() { return retardDepuis; }

    public String getDatePaiement() { return datePaiement.get(); }

    public SimpleStringProperty datePaiementProperty() { return datePaiement; }

    public String getSommePaiement() { return sommePaiement.get(); }

    public SimpleStringProperty sommePaiementProperty() { return sommePaiement; }

    public int getIdPaiement() {
        return idPaiement;
    }
}
