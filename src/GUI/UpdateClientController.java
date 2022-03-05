/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.Authentification;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class UpdateClientController implements Initializable {

    @FXML
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
    private TextField tfpassword;
    @FXML
    private Button tfupdate;
    Authentification aa = new Authentification();
    Connection cnxx;

    /**
     * Initializes the controller class.
     */

    public UpdateClientController() {
        cnxx = MyConnection.getInstance().getCnx();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == tfupdate) {
            UpdateClient1();
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
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

    private void UpdateClient1() {

        String req = " UPDATE Personne SET nom ='" + tfNom.getText() + "', prenom ='" + tfPrenom.getText() + "', dateNaissance ='" + java.sql.Date.valueOf(tfdate.getValue()) + "' , email = '" + tfemail.getText() + "', telephone= '" + tftelephone.getText() + "', password ='" + aa.hashagePWD(tfpassword.getText()) + "' WHERE id_personne = '" + tfidClient.getText() + "'";
        executeQuery(req);

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
}
