package com.gymformmanager.model;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

import static com.gymformmanager.model.MembreSportDAO.deleteMembreSportByIdMembre;
import static com.gymformmanager.util.repeat.finishSession;
import static com.gymformmanager.util.repeat.getNewSession;

public class MembreDAO {


    // Méthode permettant de récuperer tous les membres de la database
    public static List<Membre> getAllMembres(){
        Query query = getNewSession().createQuery("from Membre");
        List<Membre> membres = ((org.hibernate.query.Query) query).list();
        finishSession();
        return membres;
    }

    // Méthode permettant d'obtenir un membre à l'aide de tous ces autres attributs (Assez barbare à ameliorer .. )
    public static Membre getMembreWithAllAttributs(String nomMembre, String prenomMembre,
                                                   String emailMembre, String numPortableMembre, Date dtnMembre){
        Query query = getNewSession().createQuery("from Membre where nomMembre = :nomMembre and prenomMembre = :prenomMembre " +
                "and emailMembre = :emailMembre and  numPortableMembre = :numPortableMembre and dtnMembre = :dtnMembre");
        query.setParameter("nomMembre", nomMembre);
        query.setParameter("prenomMembre", prenomMembre);
        query.setParameter("emailMembre", emailMembre);
        query.setParameter("numPortableMembre", numPortableMembre);
        query.setParameter("dtnMembre", dtnMembre);
        Membre resultMembre = (Membre)((org.hibernate.query.Query) query).uniqueResult();
        finishSession();
        return resultMembre;
    }

    // Méthode permettant de récuperer le membre correspondant à un idMembre
    public static Membre getMembreByIdMembre(int idMembre){
        Query queryMembre = getNewSession().createQuery("from Membre where idMembre = :idMembre ");
        queryMembre.setParameter("idMembre", idMembre);
        Membre membreResult = (Membre)((org.hibernate.query.Query) queryMembre).uniqueResult();
        finishSession();
        return membreResult;
    }



    // Méthode permettant de modifié tous les attributs d'un sport à l'aide de son id
    public static void updateMembreByIdMembre(int idMembre, String nomMembre, String prenomMembre,
                                              String emailMembre, String numPortableMembre, java.sql.Date dtnMembre,
                                            Float poidMembre, Float tailleMembre, String genre){

        Query query = getNewSession().createQuery("update Membre set nomMembre = :nomMembre " +
                ", prenomMembre = :prenomMembre, emailMembre = :emailMembre, numPortableMembre = :numPortableMembre," +
                "dtnMembre = :dtnMembre , poidMembre = :poidMembre, " +
                "tailleMembre = :tailleMembre , genre = :genre where idMembre = :idMembre");
        query.setParameter("nomMembre", nomMembre);
        query.setParameter("prenomMembre", prenomMembre);
        query.setParameter("emailMembre", emailMembre);
        query.setParameter("numPortableMembre", numPortableMembre);
        query.setParameter("dtnMembre", dtnMembre);
        query.setParameter("poidMembre", poidMembre);
        query.setParameter("tailleMembre", tailleMembre);
        query.setParameter("genre", genre);
        query.setParameter("idMembre", idMembre);
        query.executeUpdate();
        finishSession();
    }


    // Méthode permettant de supprimer un membre correspondant à son idMembre
    public static void deleteMembreWithIdMembre(int idMembre){
        deleteMembreSportByIdMembre(idMembre); // supprimer tous les MembreSport en rapport (foreign key)
        Query query = getNewSession().createQuery("delete from Membre where idMembre = :idMembre");
        query.setParameter("idMembre", idMembre);
        query.executeUpdate();
        finishSession();

    }
}
