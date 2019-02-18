package com.gymformmanager.controller;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gymformmanager.util.MessageAlerte;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class controllerSamy implements Initializable {

    private static final String LINKED_IN = "https://www.linkedin.com/in/samy-bencharef-94028213a/";
    private static final String GITHUB = "https://github.com/SamyBencharef";

    // permet de faire une notification sur le bureau de l'utilisateur
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MessageAlerte.showTrayMessage(String.format("Bonjour %s!", System.getProperty("user.name")), "Merci d'utiliser GYM FORM Manager ! ");
    }

    // Fonction permettant de charger une page web sur le pc de l'utilisateur
    private void loadWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    // MéthodeS permettant d'aller sur les différents liens contenue dnas la fenetre
    @FXML
    private void loadGitHub(ActionEvent event) {
        loadWebpage(GITHUB);
    }

    @FXML
    private void loadLinkedIN(ActionEvent event) {
        loadWebpage(LINKED_IN);
    }

    // Méthode permettant d'ouvrir l'interface email de l'utilisateur en pré-remplissant son destinataire
    @FXML
    private void loadMailInterface(ActionEvent event) {
        try {
            Desktop.getDesktop().mail(new URI("mailto:samybencharef@gmail.com"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}