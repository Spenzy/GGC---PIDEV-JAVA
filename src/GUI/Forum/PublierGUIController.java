/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import entities.Publication;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PublicationCRUD;
import utils.BadWordFilter;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class PublierGUIController implements Initializable {

    @FXML
    private Button btnPublier;
    @FXML
    private TextField tfTitre;
    @FXML
    private TextArea taDesc;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Label errTitre;
    @FXML
    private Label errDesc;

    int idClient;

    public PublierGUIController() {
    }

    public PublierGUIController(int idClient) {
        this.idClient = idClient;
    }

    PublicationCRUD pc = new PublicationCRUD();
    @FXML
    private Label errWindow;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Lors du click sur le bouton publier
        btnAnnuler.setOnAction((ActionEvent a) -> {
            closeWindow(btnAnnuler);
        });

        //Lors du click sur le bouton publier
        btnPublier.setOnAction((ActionEvent a) -> {
            resetErrLabels();
            //test si l'un des champs est vide
            if (tfTitre.getText().isEmpty()) {
                errTitre.setText("Erreur, Champ vide!");
            } else if (pc.verifQuotaPub(idClient)) {
                errTitre.setText("Vous avez attein la limite de publications courantes!");
            }else if(BadWordFilter.filterText(tfTitre.getText()) || BadWordFilter.filterText(taDesc.getText())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Erreur");
                alert.setHeaderText("Publication refusé!");
                alert.setContentText("Votre commentaire contient des mots vulguéres!");
                alert.show();
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Publier?");
                alert.setHeaderText("Etes vous pret a publier?");
                alert.setContentText("Acceptez pour publier votre publication");

                Optional<ButtonType> option = alert.showAndWait();
                //tous les conséquences de l'alerte de confirmation
                if (option.get() == null) {
                    this.errWindow.setText("Pas de selection!");
                } else if (option.get() == ButtonType.CANCEL) {
                    this.errWindow.setText("Vous avez annulé la selection!");
                } else if (option.get() == ButtonType.OK) {
                    Publication p = new Publication(idClient, tfTitre.getText(), taDesc.getText(), false);
                    pc.ajouterPublication(p);
                    //message de test
                    this.errWindow.setText("Poste ajouté avec succée!");
                    //close window
                    closeWindow(btnPublier);
                } else {
                    this.errWindow.setText("-");
                }
            }
        });
    }

    public void resetErrLabels() {
        errTitre.setText("");
        errDesc.setText("");
    }

    public void closeWindow(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public Parent refreshPublier(Button btn) {
        ForumHomeGUIController fhc = new ForumHomeGUIController();
        try {
            //init loader root
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PublierGUI.fxml"));

            //init Controller
            PublierGUIController controller = new PublierGUIController(idClient);
            loader.setController(controller);

            Parent root = loader.load();

            //scene switch
            Scene affPubScene = new Scene(root);
//            ((Stage) btn.getScene().getWindow()).setScene(affPubScene);
            Stage stage = new Stage();
            stage.setScene(affPubScene);
            stage.showAndWait();

            fhc = new ForumHomeGUIController(idClient);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return fhc.refreshForum();
    }

}