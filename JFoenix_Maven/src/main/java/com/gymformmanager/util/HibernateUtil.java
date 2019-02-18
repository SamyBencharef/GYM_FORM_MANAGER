package com.gymformmanager.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

// Classe permettant de ce connecté à la base de donnée, fait paserell avec le fichier conf d'hibernate
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // On crée notre session factory sur notre fichier de conf hibernate
            return new Configuration().configure("hibernate/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Si ça ce ne connecte pas on affiche une erreur
            List<String> messageErreur = new ArrayList<>();
            messageErreur.add("Erreur lors de la \"connexion à la base de donnée\" ");
            MessageAlerte.errorPrint(false, messageErreur);
            System.err.println("Echec de la création de la session factory" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}