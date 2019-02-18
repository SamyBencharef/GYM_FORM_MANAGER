package com.gymformmanager.model;

import javax.persistence.*;
import java.util.Map;

// Classe permettant de gérer les Membres (Et la base de donnée - annotation Hibernate)
@Entity
@Table(name = "SPORT")
public class Sport {

    @Id
    @Column(name="idSport", nullable = false, unique=true)
    @GeneratedValue (strategy= GenerationType.SEQUENCE, generator="sportSeqGen")
    @SequenceGenerator(name = "sportSeqGen", sequenceName = "SportSequence", allocationSize = 1, initialValue= 1)
    private int idSport;

    @Column(name = "intituleSport")
    private String intituleSport;

    @Column(name = "prixAnnee")
    private Integer prixAnnee;

    @Column(name = "prixTrimestre")
    private Integer prixTrimestre;

    @Column(name = "prixMois")
    private Integer prixMois;

    @Column(name = "entraineur")
    private String entraineur;


    public Sport() {}
    public Sport(String intituleSportP, Map<String, Integer> prix, String entraineurP) {
        this.intituleSport=intituleSportP;

        if(prix.containsKey("prixAnnee")){ // On accepte une map pour accepter d'envoyer des nul à notre base de donnée
            this.prixAnnee=prix.get("prixAnnee");
        }
        if(prix.containsKey("prixTrimestre")){
            this.prixTrimestre=prix.get("prixTrimestre");
        }
        if(prix.containsKey("prixMois")){
            this.prixMois=prix.get("prixMois");
        }
        this.entraineur=entraineurP;
    }

    public int getIdSport() { return idSport; }
    public void setIdSport(int idSport) { this.idSport = idSport; }

    public String getIntituleSport() { return intituleSport; }
    public void setIntituleSport(String intituleSport) { this.intituleSport = intituleSport; }

    public Integer getPrixAnnee() { return prixAnnee; }
    public void setPrixAnnee(Integer prixAnnee) { this.prixAnnee = prixAnnee; }

    public Integer getPrixTrimestre() { return prixTrimestre; }
    public void setPrixTrimestre(Integer prixTrimestre) { this.prixTrimestre = prixTrimestre; }

    public Integer getPrixMois() { return prixMois; }
    public void setPrixMois(Integer prixMois) { this.prixMois = prixMois; }

    public String getEntraineur() { return entraineur; }
    public void setEntraineur(String entraineur) { this.entraineur = entraineur; }


}
