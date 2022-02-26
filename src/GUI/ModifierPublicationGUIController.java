/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PublicationCRUD;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class ModifierPublicationGUIController implements Initializable {

    @FXML
    private Button btnModifier;
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
    @FXML
    private Label errWindow;
    
    
    int idClient;//un id pour tester les fonctionalités de l'application
    int idPublication;// id pub de test
        
    PublicationCRUD pc = new PublicationCRUD();

    public ModifierPublicationGUIController() {
    }

    public ModifierPublicationGUIController(int idClient, int idPublication) {
        this.idClient = idClient;
        this.idPublication = idPublication;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Publication p = pc.afficherPublication(idPublication);
        //init des champs de modification
        tfTitre.setText(p.getTitre());
        taDesc.setText(p.getDesc());
        
        //Lors du click sur le bouton publier
        btnAnnuler.setOnAction((ActionEvent a) -> {
            Stage stage = (Stage) btnAnnuler.getScene().getWindow();
            stage.close();
        });

        //Lors du click sur le bouton publier
        btnModifier.setOnAction((ActionEvent a) -> {
            resetErrLabels();
            //test si l'un des champs est vide
            if (tfTitre.getText().isEmpty()) {
                errTitre.setText("Erreur, Champ vide!");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Modifier?");
                alert.setHeaderText("Etes vous sur de vos modifications?");
                alert.setContentText("Acceptez pour modifier votre publication");

                Optional<ButtonType> option = alert.showAndWait();
                //tous les conséquences de l'alerte de confirmation
                if (option.get() == null) {
                    this.errWindow.setText("Pas de selection!");
                } else if (option.get() == ButtonType.CANCEL) {
                    this.errWindow.setText("Vous avez annulé la selection!");
                } else if (option.get() == ButtonType.OK) {
                    p.setTitre(tfTitre.getText());
                    p.setDesc(taDesc.getText());
                    pc.modifierPublication(p);
                    //message de test
                    this.errWindow.setText("Poste ajouté avec succée!");
                    //on ferme l'interface d'ajout après un cas succéssive
                    Stage stage = (Stage) btnAnnuler.getScene().getWindow();
                    stage.close();
                } else {
                    this.errWindow.setText("-");
                }
            }
        });

    }
    
    public void resetErrLabels(){
        errTitre.setText("");
        errDesc.setText("");
    }
    
    public Scene refreshPublier(Button btn) {
        ForumHomeGUIController fhc = new ForumHomeGUIController();
        try {
            //init loader root
            System.out.println("wselt lenna");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierPublicationGUI.fxml"));

            //init Controller
            ModifierPublicationGUIController controller = new ModifierPublicationGUIController(idClient,idPublication);
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
            Logger.getLogger(ForumHomeGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fhc.refreshForum(idClient);
    }

}
