package com.gymformmanager.model;

import javax.persistence.Query;
import java.util.List;

import static com.gymformmanager.util.repeat.*;

public class PaiementDAO {

    // Méthode permettant de récuperer tous les sports de la database
    public static List<Paiement> getAllPaiement(){
        Query query = getNewSession().createQuery("from Paiement");
        List<Paiement> paiements = ((org.hibernate.query.Query) query).list();
        finishSession();
        return paiements;
    }

    // Méthode permettant de modifier tous le boolean plus recent d'un paiement à l'aide de son id paiement
    public static void updatePaiementPlusRecent(int idPaiement, boolean plusRecent){
        org.hibernate.query.Query query = getNewSession().createQuery("update Paiement set plusRecent = :plusrecent" +
                " where idPaiement = :idPaiement");
        query.setParameter("plusrecent", plusRecent );
        query.setParameter("idPaiement", idPaiement);
        int result = query.executeUpdate();
        System.out.println(result);
        finishSession();
    }

    // Méthode permettant de supprimer un paiement à l'aide de l'id paiement
    public static void deletePaiementWithIdPaiement(int idPaiement){
        Query query = getNewSession().createQuery("delete from Paiement where idPaiement = :idPaiement ");
        query.setParameter("idPaiement", idPaiement);
        query.executeUpdate();
        finishSession();
    }

    // Permet de récuperer un paiement à l'aide de son id paiement
    public static Paiement  getPaiementWithIdPaiement(int idPaiement){
        Query query = getNewSession().createQuery("from Paiement where idPaiement = :idPaiement ");
        query.setParameter("idPaiement", idPaiement);
        Paiement paiement = (Paiement) ((org.hibernate.query.Query) query).uniqueResult();
        finishSession();
        return paiement;
    }

    // permet de supprimer une liste de paiement à l'aide d'un idmembresport
    public static void deletePaiementByIdMembreSport(int idMembreSport){
        Query query = getNewSession().createQuery("delete from Paiement where idMembre_Sport = :idMembreSport");
        query.setParameter("idMembreSport", idMembreSport);
        query.executeUpdate();
        finishSession();
    }
}
