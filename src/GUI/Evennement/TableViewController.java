/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Evennement;

import entities.Evenement;
import static GUI.DashboardAdminController.refreshParent;
import static GUI.DashboardController.refreshParent;
import GUI.DashboardModerateurController;
import GUI.Forum.ForumHomeGUIController;
import services.EvenementCrud;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import services.ParticipationCrud;
import utils.MailAPI;

/**
 * FXML Controller class
 *
 * @author Azer Lahmer
 */
public class TableViewController implements Initializable {

    @FXML
    private TableView<Evenement> evenementsTable;
    private TableColumn<Evenement, String> refCol;
   
     private TableColumn<Evenement, String> titreCol;
    private TableColumn<Evenement, String> dateDebutCol;
    private TableColumn<Evenement, String> dateFinCol;
    private TableColumn<Evenement, String> localisationCol;
    private TableColumn<Evenement, String> descriptionCol;
    private TableColumn<Evenement, String> nbrParticipantsCol;
   

    /**
     * Initializes the controller class.
     */
    EvenementCrud ec = new EvenementCrud();
    ParticipationCrud pc = new ParticipationCrud();
    @FXML
    private Button btnModif;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button tfListeParticipations;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initCols();
        loadEvenements();

        btnModif.setOnAction(a -> {
            Stage primaryStage = new Stage();

            try {
                Evenement e = evenementsTable.getSelectionModel().getSelectedItem();
                //Parent root = FXMLLoader.load(getClass().getResource("ModifierEvenement.fxml"));
                System.out.println(e);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifierEvenement.fxml"));

                Parent root = fxmlLoader.load();
                ModifierEvenementController controller = fxmlLoader.<ModifierEvenementController>getController();
                controller.setReference(e.getReference());
                controller.initialize(url, rb);
                Scene scene = new Scene(root);
                primaryStage.setTitle("Modifier cet Evenement");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    private void initCols() {
        refCol = new TableColumn<>("Reference");//nom de l'afichage
        refCol.setCellValueFactory(new PropertyValueFactory<>("reference"));

             titreCol = new TableColumn<>("Titre");//nom de l'afichage
        titreCol.setCellValueFactory(new PropertyValueFactory<>("Titre")); 
        
        dateDebutCol = new TableColumn<>("Date Debut");//nom de l'afichage
        dateDebutCol.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));

        dateFinCol = new TableColumn<>("Date Fin");//nom de l'afichage
        dateFinCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));

        localisationCol = new TableColumn<>("Localisation");//nom de l'afichage
        localisationCol.setCellValueFactory(new PropertyValueFactory<>("localisation"));

        descriptionCol = new TableColumn<>("Description");//nom de l'afichage
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        nbrParticipantsCol = new TableColumn<>("Nbres Participants");//nom de l'afichage
        nbrParticipantsCol.setCellValueFactory(new PropertyValueFactory<>("nbrParticipant"));

   

        evenementsTable.getColumns().addAll(refCol, titreCol, dateDebutCol, dateFinCol, localisationCol, descriptionCol, nbrParticipantsCol);
    }

    public void loadEvenements() {

        List<Evenement> evenements = ec.afficherEvenements();
        evenementsTable.getItems().clear();
        //afficher par objet dans une boucle   
        for (Evenement e : evenements) {
            evenementsTable.getItems().add(e);
        }

    }

    @FXML
    private void Ajouter(ActionEvent event) {

        Stage primaryStage = new Stage();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("AjouterEvenement.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setTitle("Ajouter Evenement");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void Supprimer(ActionEvent event) {

        Evenement e = evenementsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("vous voulez supprimer cet evenement !");
        //alert.show();
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == (ButtonType.OK)) {
            evenementsTable.getItems().remove(e);
            List<String> emails = pc.getParticipEmails(e.getReference());
            ec.supprimerEvenement(e.getReference());
            
            for(String mail : emails){
                try {
                    MailAPI.sendMail(mail, "Evennement Annulé", "L'évennement "+ e.getTitre() + " avec la référence "+ e.getReference() +" a été annulée");
                } catch (MessagingException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

    }

    @FXML
    private void ModifierTable(javafx.scene.input.MouseEvent event) {

    }

    @FXML
    private void Refresh(ActionEvent event) {

        loadEvenements();
    }

    @FXML
    private void listeParticipations(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ListeParticipation.fxml"));

            DashboardModerateurController.refreshParent(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
