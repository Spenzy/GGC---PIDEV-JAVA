/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.DashboardController.refreshParent;
import entities.Personne;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.Authentification;
import services.PersonneCRUD;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class UpdateClientController implements Initializable {

    private TextField tfidClient;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tftelephone;
    @FXML
    private TextField tfemail;
    @FXML
    private DatePicker tfdate;
    @FXML
    private PasswordField tfpassword;
    @FXML
    private Button tfupdate;
    Authentification aa = new Authentification();
    Personne p = new Personne();
    PersonneCRUD pc = new PersonneCRUD();
    Connection cnxx;
    int idClient = homePage.loggedInID;

    /**
     * Initializes the controller class.
     */

    public UpdateClientController() {
        cnxx = MyConnection.getInstance().getCnx();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        p = pc.afficherPersonne(idClient);
        tfNom.setText(p.getNom());
        tfPrenom.setText(p.getPrenom());
        tfdate.setValue(p.getDateNaissance().toLocalDate());
        tfemail.setText(p.getEmail());
        tftelephone.setText(p.getTelephone() + "");
        tfpassword.setText(p.getPassword());

        // TODO
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == tfupdate) {
            UpdateClient2(idClient);
           

        } 
    }

    private void executeQuery(String req) {
        cnxx = MyConnection.getInstance().getCnx();
        Statement st;
        try {
            st = cnxx.createStatement();
            st.executeUpdate(req);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void UpdateClient2(int idClient) {
        DataValidation validator = new DataValidation();
        String pwd = p.getPassword();
        if (!tfpassword.getText().equals(pwd))
        {
            pwd = aa.hashagePWD(tfpassword.getText());
        }

        if (validator.isNotEmpty(tfemail) && validator.isNotEmpty(tfpassword) && validator.isNotEmpty(tfNom)
                && validator.isNotEmpty(tfPrenom) && validator.isNotEmpty(tftelephone) && validator.isNotEmpty(tfdate.getEditor()) && validator.emailFormat(tfemail) && validator.textNumeric(tftelephone) && validator.dataLength(tftelephone, "8")
                && validator.textAlphabet(tfNom) && validator.textAlphabet(tfPrenom) && Period.between(tfdate.getValue(), LocalDate.now()).getYears() > 18) {
           String req = " UPDATE Personne SET nom ='" + tfNom.getText() + "', prenom ='" + tfPrenom.getText() + "', dateNaissance ='" + java.sql.Date.valueOf(tfdate.getValue()) + "' , email = '" + tfemail.getText() + "', telephone= '" + tftelephone.getText() + "', password ='" + pwd + "' WHERE id_personne = '" + idClient + "'";

            executeQuery(req);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succesful");
            alert.setHeaderText(null);
            alert.setContentText(" Modification avec succées!");
            alert.showAndWait();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("UpdateClient.fxml"));
                DashboardController.refreshParent(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            
            
        } else {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText(" Champs incorrect!");
            alert.show();
        }

    }
}
