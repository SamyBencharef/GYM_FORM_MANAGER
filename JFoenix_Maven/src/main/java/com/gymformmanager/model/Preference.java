package com.gymformmanager.model;


import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Classe permettant de faire les interraction avec le fichier conf
public class Preference {

    // le chemin vers le fichier
    public static final String CONFIG_FILE = "src/main/resources/conf/config.txt";

    private String username;
    private String password;

    // initialisation par défault de ce fichier
    public Preference(){
        setUsername("admin");
        setPassword("admin");
    }

    // Permet d'intialiser par défault notre fichier conf (appelé quand conf n'existe pas)
    public static void initConfig(){
        Writer writer = null;
        try {
            Preference preference = new Preference();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } /*finally {
            try {
              //  writer.close();
            }catch (IOException ex){
                Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
            }

        }*/
    }

    // Méthode permettant de remplacer le fichier conf actuel par un nouveau
    public static void writePreferenceToFile(Preference preference){
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            }catch (IOException ex){
                Logger.getLogger(Preference.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Méthode permettant de récuperer le fichier conf actuel
    public static Preference getPreference() {
        Gson gson = new Gson();
        Preference preference = new Preference();
        try {
            preference = gson.fromJson(new FileReader(CONFIG_FILE), Preference.class);
        } catch (FileNotFoundException e) {
            initConfig();
            e.printStackTrace();
        }
        return preference;
    }


    // Méthodes get et set basique
    public String getUsername() { return username; }

    public void setUsername(String username) {
        this.username = DigestUtils.shaHex(username);
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = DigestUtils.shaHex(password);
    }
}
