package com.gymformmanager.model;

import javax.persistence.Query;


import java.util.List;

import static com.gymformmanager.model.PaiementDAO.deletePaiementByIdMembreSport;
import static com.gymformmanager.util.repeat.*;

public class MembreSportDAO {

    // Méthode permettant de récuperer le sport correspondant à un idSport
    public static int getIdMembreSportByIdSportAndIdMembre(int idSport, int idMembre){
        Query queryIdMembreSport = getNewSession().createQuery("select idMembre_Sport from MembreSport where idSport = :idSport and idMembre = :idMembre");
        queryIdMembreSport.setParameter("idSport", idSport);
        queryIdMembreSport.setParameter("idMembre", idMembre);
        int idMembreSportResult = (Integer)((org.hibernate.query.Query) queryIdMembreSport).uniqueResult();
        finishSession();
        return idMembreSportResult;
    }

    // Méthode peremettant de récuperer un membre sport à l'aide d'un idMembreSport
    public static MembreSport getMembreSportByIdMembreSport(int idMembre_Sport){
        Query queryIdMembreSport = getNewSession().createQuery("from MembreSport where idMembre_Sport = :idMembre_Sport");
        queryIdMembreSport.setParameter("idMembre_Sport", idMembre_Sport);
        MembreSport MembreSportResult = (MembreSport) ((org.hibernate.query.Query) queryIdMembreSport).uniqueResult();
        finishSession();
        return MembreSportResult;
    }

    // Méthode permettant de récuperer une liste de membresport à l'aide d'un idmembre
    public static List<MembreSport> getListMembreSportByIdMembre(int idMembre){
        Query queryIdMembreSport = getNewSession().createQuery("from MembreSport where idMembre = :idMembre");
        queryIdMembreSport.setParameter("idMembre", idMembre);
        List<MembreSport> MembreSportResult = ((org.hibernate.query.Query) queryIdMembreSport).list();
        finishSession();
        return MembreSportResult;
    }

    // Méthode permettant de supprimer un membre sport à l'aide d'un idsport
    public static void deleteMembreSportByIdSport(int idSport){
        Query queryIdMembreSport = getNewSession().createQuery("from MembreSport where idSport = :idSport");
        queryIdMembreSport.setParameter("idSport", idSport);
        List<MembreSport> MembreSportResults = ((org.hibernate.query.Query) queryIdMembreSport).list() ;
        for (MembreSport ms : MembreSportResults){
            deletePaiementByIdMembreSport(ms.getIdMembre_Sport()); // supprimer tous les paiements en rapport (foreign key)
        }

        Query query = getNewSession().createQuery("delete from MembreSport where idSport = :idSport");
        query.setParameter("idSport", idSport);
        query.executeUpdate();
        finishSession();
    }

    // Méthode permettant de supprimer un membre sport à l'aide d'un idMembre
    public static void deleteMembreSportByIdMembre(int idMembre){
        Query queryIdMembreSport = getNewSession().createQuery(" from MembreSport where idMembre = :idMembre");
        queryIdMembreSport.setParameter("idMembre", idMembre);
        List<MembreSport> MembreSportResults = ((org.hibernate.query.Query) queryIdMembreSport).list() ;
        for (MembreSport ms : MembreSportResults){
            deletePaiementByIdMembreSport(ms.getIdMembre_Sport()); // supprimer tous les paiements en rapport (foreign key)
        }

        Query query = getNewSession().createQuery("delete from MembreSport where idMembre = :idMembre");
        query.setParameter("idMembre", idMembre);
        query.executeUpdate();
        finishSession();
    }
}
