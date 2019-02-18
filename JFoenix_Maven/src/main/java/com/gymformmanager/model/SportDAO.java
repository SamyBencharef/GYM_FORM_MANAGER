package com.gymformmanager.model;

import com.gymformmanager.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

import static com.gymformmanager.model.MembreSportDAO.deleteMembreSportByIdSport;
import static com.gymformmanager.util.repeat.finishSession;
import static com.gymformmanager.util.repeat.getNewSession;

public class SportDAO  {


    // Méthode permettant de récuperer la liste des idSports correspondant à un idMembre
    public static  List<Integer> getIdSportsByIdMembre(int idMembre){
        // Partie d'affichage des sports
        Query queryIdSport = getNewSession().createQuery("Select idSport from MembreSport where idMembre = :idMembre ");
        queryIdSport.setParameter("idMembre", idMembre);
        List<Integer> sportsResults = ((org.hibernate.query.Query) queryIdSport).list();
        finishSession();
        return sportsResults;
    }

    // Méthode permettant de récuperer le sport correspondant à un idSport
    public static Sport getSportByIdSport(int idSport){
        Query querySport = getNewSession().createQuery("from Sport where idSport = :idSport ");
        querySport.setParameter("idSport", idSport);
        Sport sportResult = (Sport)((org.hibernate.query.Query) querySport).uniqueResult();
        finishSession();
        return sportResult;
    }

    // Méthode permettant de récuperer un  sport correspondant à son intitulé (RISQUE SOLUTION A REVOIR)
    public static Sport getSportByNameSport(String nameSport) {
        Query query = getNewSession().createQuery("from Sport where intituleSport = :intituleSport ");
        query.setParameter("intituleSport", nameSport);
        Sport sportResult = (Sport) ((org.hibernate.query.Query) query).uniqueResult();
        finishSession();
        return sportResult;
    }

    // Méthode permettant de supprimer un sport (pour cela on doit supprimer toutes les autres dépendances avec ce sport
    public static void deleteSportById(int idSport){
        deleteMembreSportByIdSport(idSport);
        Query query = getNewSession().createQuery("delete from Sport where idSport = :idSport");
        query.setParameter("idSport", idSport);
        query.executeUpdate();
        finishSession();
    }

    // Méthode permettant de modifié tous les attributs d'un sport à l'aide de son id
    public static void updateSportByIdSport(int idSport, String intituleSport, String entraineur,
                                            Integer prixAnnee, Integer prixTrimestre, Integer prixMois){

        Query query = getNewSession().createQuery("update Sport set intituleSport = :intituleSport " +
                ", prixAnnee = :prixAnnee, prixTrimestre = :prixTrimestre, prixMois = :prixMois," +
                "entraineur = :entraineur where idSport = :idSport");
        query.setParameter("intituleSport", intituleSport);
        query.setParameter("prixAnnee", prixAnnee);
        query.setParameter("prixTrimestre", prixTrimestre);
        query.setParameter("prixMois", prixMois);
        query.setParameter("entraineur", entraineur);
        query.setParameter("idSport", idSport);
        query.executeUpdate();
        finishSession();
    }


    // Méthode permettant de récuperer tous les sports de la database
    public static List<Sport> getAllSports(){
        Query query = getNewSession().createQuery("from Sport");
        List<Sport> sports = ((org.hibernate.query.Query) query).list();
        finishSession();
        return sports;
    }
}
