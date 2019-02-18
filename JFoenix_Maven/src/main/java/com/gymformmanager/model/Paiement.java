package com.gymformmanager.model;

import javax.persistence.*;
import java.sql.Date;

// Classe permettant de gérer les Paiement (Et la base de donnée - annotation Hibernate)
@Entity
@Table(name = "Paiement")
public class Paiement {



    @Id
    @Column(name="idPaiement", nullable = false, unique=true)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="paiementSeqGen")
    @SequenceGenerator(name = "paiementSeqGen", sequenceName = "PaiementSequence", allocationSize = 1, initialValue= 1)
    private int idPaiement;

    @Column(name = "idMembre_Sport")
    private int idMembre_Sport;

    @Column(name = "datePaiement")
    private Date datePaiement;

    @Column(name = "sommePaye")
    private int sommePaye;

    @Column(name = "typePaiement")
    private String typePaiement;

    @Column(name = "duree")
    private String duree;

    @Column(name= "plusRecent")
    private boolean plusRecent;

    public Paiement() { }

    public Paiement(int idMembre_Sport, Date datePaiement, int sommePaye,
                    String typePaiement, String duree) {
        this.idMembre_Sport = idMembre_Sport;
        this.datePaiement = datePaiement;
        this.sommePaye = sommePaye;
        this.typePaiement = typePaiement;
        this.duree = duree;
    }

    public int getIdMembre_Sport() { return idMembre_Sport; }

    public Date getDatePaiement() { return datePaiement; }

    public int getSommePaye() { return sommePaye; }

    public String getTypePaiement() { return typePaiement; }

    public String getDuree() { return duree; }

    public int getIdPaiement() { return idPaiement; }

    public boolean isPlusRecent() { return plusRecent;  }
}
