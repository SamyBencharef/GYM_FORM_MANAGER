package com.gymformmanager.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Classe permettant de gérer les Paiement contenu dans les TablesViews FXML
public class TableauPaiementProperty {

    private int idPaiement;

    private SimpleStringProperty nomMembre;

    private SimpleStringProperty prenomMembre;

    private SimpleStringProperty dtnMembre;

    private SimpleIntegerProperty sommePaye;

    private SimpleStringProperty datePaiement;

    private SimpleStringProperty intituleSport;

    private SimpleStringProperty typePaiement;

    private SimpleStringProperty dureePaiement;


    public TableauPaiementProperty(SimpleStringProperty nomMembre, SimpleStringProperty prenomMembre,
                                   SimpleStringProperty dtnMembre, SimpleIntegerProperty sommePaye,
                                   SimpleStringProperty datePaiement, SimpleStringProperty intituleSport,
                                   SimpleStringProperty typePaiement, SimpleStringProperty dureePaiement,
                                   int idPaiement) {
        this.nomMembre = nomMembre;
        this.prenomMembre = prenomMembre;
        this.dtnMembre = dtnMembre;
        this.sommePaye = sommePaye;
        this.datePaiement = datePaiement;
        this.intituleSport = intituleSport;
        this.typePaiement = typePaiement;
        this.dureePaiement = dureePaiement;
        this.idPaiement = idPaiement;
    }

    public int getIdPaiement() { return idPaiement; }

    public String getNomMembre() { return nomMembre.get(); }

    public SimpleStringProperty nomMembreProperty() { return nomMembre; }

    public String getPrenomMembre() { return prenomMembre.get(); }

    public SimpleStringProperty prenomMembreProperty() { return prenomMembre; }

    public String getDtnMembre() { return dtnMembre.get(); }

    public SimpleStringProperty dtnMembreProperty() { return dtnMembre; }

    public int getSommePaye() { return sommePaye.get(); }

    public SimpleIntegerProperty sommePayeProperty() { return sommePaye; }

    public String getDatePaiement() { return datePaiement.get(); }

    public SimpleStringProperty datePaiementProperty() { return datePaiement; }

    public String getIntituleSport() { return intituleSport.get(); }

    public SimpleStringProperty intituleSportProperty() { return intituleSport; }

    public String getTypePaiement() { return typePaiement.get(); }

    public SimpleStringProperty typePaiementProperty() { return typePaiement; }

    public String getDureePaiement() { return dureePaiement.get(); }

    public SimpleStringProperty dureePaiementProperty() { return dureePaiement; }
}
