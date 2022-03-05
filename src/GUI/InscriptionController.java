/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import entities.Client;
import entities.Personne;
import java.net.URL;

import java.time.LocalDate;
import java.time.Period;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private PasswordField tfpassword;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TextField tfphone;
    PersonneCRUD pc = new PersonneCRUD();
    @FXML
    private Button btnSave;
    @FXML
    private Label ereurdate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO                   
    }
     public boolean isValidDateNaissance(){
         if(tfdate.getValue() == null){
        ereurdate.setText("champs vide");
        return false;
     }else if (Period.between(tfdate.getValue(), LocalDate.now()).getYears() < 18) {
         LocalDate myDate = tfdate.getValue();
        ereurdate.setText("date invalide");
        return false;
    }else{
         
         return true;
     }
     }

    @FXML
    private void addPersonne(ActionEvent event) {
        DataValidation validator = new DataValidation();
        String nom = tfnom.getText();
        String prenom = tfprenom.getText();
        LocalDate myDate = tfdate.getValue();

        String email = tfemail.getText();
        int telephone = Integer.parseInt(tfphone.getText());
        String password = tfpassword.getText();
        if(validator.isNotEmpty(tfemail) && validator.isNotEmpty(tfpassword) && validator.isNotEmpty(tfnom) && 
          validator.isNotEmpty(tfprenom)  &&  validator.isNotEmpty(tfphone) && validator.isNotEmpty(tfdate.getEditor())){

        
        if (validator.emailFormat(tfemail) && validator.textNumeric(tfphone) && validator.dataLength(tfphone, "8") 
            && validator.textAlphabet(tfnom) && validator.textAlphabet(tfprenom)&& Period.between(tfdate.getValue(), LocalDate.now()).getYears() > 18) {

            Authentification aa = new Authentification();
            ClientCRUD cc = new ClientCRUD();      
            Client c = new Client(nom, prenom, java.sql.Date.valueOf(myDate), email, telephone,aa.hashagePWD(password));

            cc.ajouterClient(c);
        }
    }
}
}
