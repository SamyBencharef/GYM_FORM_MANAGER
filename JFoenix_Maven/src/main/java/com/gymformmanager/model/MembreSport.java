package com.gymformmanager.model;

import javax.persistence.*;

// Classe permettant de gérer les MembreSport (Et la base de donnée - annotation Hibernate)
@Entity
@Table(name = "MEMBRE_SPORT")
public class MembreSport{

    @Id
    @Column(name="idMembre_Sport", nullable = false, unique=true)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="membre_sportSeqGen")
    @SequenceGenerator(name = "membre_sportSeqGen", sequenceName = "membre_sportSequence", allocationSize = 1, initialValue= 1)
    private int idMembre_Sport;

    @Column(name="idSport")
    private int idSport;

    @Column(name = "idMembre")
    private int idMembre;

    public MembreSport() { }

    public MembreSport(int idSport, int idMembre) {
        this.idSport = idSport;
        this.idMembre = idMembre;
    }

    public int getIdSport() { return idSport; }

    public int getIdMembre() { return idMembre; }

    public int getIdMembre_Sport() { return idMembre_Sport; }
}
