/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Client;
import entities.Personne;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;


import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.Authentification;
import services.ClientCRUD;
import services.PersonneCRUD;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class InscriptionController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfpassword;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfphone;
    @FXML
    private Button btnSave;
PersonneCRUD pc = new PersonneCRUD();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO                   
    }

    @FXML
    private void addPersonne(ActionEvent event) {
        
        String nom = tfnom.getText();
        String prenom = tfprenom.getText();
        LocalDate myDate = tfdate.getValue();
        
        String email = tfemail.getText();
        int telephone = Integer.parseInt(tfphone.getText());
        String password = tfpassword.getText();
        Authentification aa = new Authentification();
        ClientCRUD cc = new ClientCRUD();
        Client c = new Client(nom, prenom, java.sql.Date.valueOf( myDate ), email, telephone, aa.hashagePWD(password));
        
        cc.ajouterClient(c);
    }
}
