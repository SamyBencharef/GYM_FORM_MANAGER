package com.gymformmanager.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

// Classe permettant de gérer les Membres (Et la base de donnée - annotation Hibernate)
@Entity
@Table(name = "MEMBRE")
public class Membre {

    @Id
    @Column(name="idMembre", nullable = false, unique=true)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="membreSeqGen")
    @SequenceGenerator(name = "membreSeqGen", sequenceName = "MembreSequence", allocationSize = 1, initialValue= 1)
    private int idMembre;

    @Column(name = "nomMembre")
    private String nomMembre;

    @Column(name = "prenomMembre")
    private String prenomMembre;

    @Column(name = "emailMembre")
    private String emailMembre;

    @Column(name = "numPortableMembre")
    private String numPortableMembre;

    @Column(name = "dtnMembre")
    private java.sql.Date dtnMembre;

    @Column(name = "poidMembre")
    private float poidMembre;

    @Column(name = "tailleMembre")
    private float tailleMembre;

    @Column(name = "Genre")
    private String genre;
    //private ArrayList<Sport> sportsMembre;

    public Membre() {}
    public Membre(String nomMembre, String prenomMembre, String emailMembre, String numPortableMembre,
                  java.sql.Date dtnMembre, Map<String, Float> infos, String genre) {
        this.nomMembre = nomMembre;
        this.prenomMembre = prenomMembre;
        this.emailMembre = emailMembre;
        this.dtnMembre = dtnMembre;
        this.numPortableMembre = numPortableMembre;
        if(infos.containsKey("poidMembre")) // On accepte une map pour accepter d'envoyer des nul à notre base de donnée
            this.poidMembre=infos.get("poidMembre");

        if(infos.containsKey("tailleMembre"))
            this.tailleMembre=infos.get("tailleMembre");
        this.genre= genre;

    }


    public int getIdMembre() { return idMembre; }

    public String getNomMembre() { return nomMembre; }

    public String getPrenomMembre() { return prenomMembre; }

    public String getEmailMembre() { return emailMembre; }

    public Date getDtnMembre() { return dtnMembre; }

    public float getPoidMembre() { return poidMembre; }

    public float getTailleMembre() { return tailleMembre; }

    public String getNumPortableMembre() { return numPortableMembre; }

    public String getGenre() {
        return genre;
    }
}
