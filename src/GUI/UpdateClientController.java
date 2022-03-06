/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Personne;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
            UpdateClient2(76);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Validation");
            alert2.setHeaderText("Modification avec succés");

        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("Pas de modification");
            alert2.show();
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

        String req = " UPDATE Personne SET nom ='" + tfNom.getText() + "', prenom ='" + tfPrenom.getText() + "', dateNaissance ='" + java.sql.Date.valueOf(tfdate.getValue()) + "' , email = '" + tfemail.getText() + "', telephone= '" + tftelephone.getText() + "', password ='" + aa.hashagePWD(tfpassword.getText()) + "' WHERE id_personne = '" + idClient + "'";
        
        executeQuery(req);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succesful");
       alert.setHeaderText(null);
        alert.setContentText(" Modification avec succées!");
        alert.show();
    }
}
