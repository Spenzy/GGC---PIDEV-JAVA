/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PersonneCRUD;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class LoginController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private Button btnsignin;
    @FXML
    private Button btnsignup;
    @FXML
    private PasswordField pwdId;
    PersonneCRUD pc = new PersonneCRUD();
    @FXML
    private Label errorLogin;
    @FXML
    private Label ereurpassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Login(ActionEvent event) throws IOException {
        DataValidation validator = new DataValidation();
        PersonneCRUD repo = new PersonneCRUD();
        if (email.getText().isEmpty()) {
            errorLogin.setText("champs vide");
        } else {
            errorLogin.setText("");
        }

        if (pwdId.getText().isEmpty()) {
            ereurpassword.setText("champs vide");
        } else {
            ereurpassword.setText("");
        }
        if (!pwdId.getText().isEmpty() && !email.getText().isEmpty()) {

            if (email.getText().equals("admin") && pwdId.getText().equals("admin")) {
                Parent blah = FXMLLoader.load(getClass().getResource("Dashbord.fxml"));
                Scene scene = new Scene(blah);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();
            } else if (repo.getUserBy(email.getText(), pwdId.getText())) {
                Parent blah = FXMLLoader.load(getClass().getResource("ListeClient.fxml"));
                Scene scene = new Scene(blah);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur login");
            alert.setHeaderText(null);
            alert.setContentText("Enter a Login!");
            alert.show();

        }
    }
}
