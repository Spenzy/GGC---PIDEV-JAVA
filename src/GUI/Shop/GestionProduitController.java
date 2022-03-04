/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Shop;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.Produit;
import services.AvisCRUD;
import services.ProduitCRUD;
import utils.MailAPI;
import utils.PdfAPI;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class GestionProduitController implements Initializable {

    ProduitCRUD p = new ProduitCRUD();
    @FXML
    private TableColumn<Produit, Integer> tcReference;
    @FXML
    private TableColumn<Produit, Integer> tcLibelle;
    @FXML
    private TableColumn<Produit, String> tcCategorie;
    @FXML
    private TableColumn<Produit, String> tcDescription;
    @FXML
    private TableColumn<Produit, Integer> tcPrix;
    @FXML
    private TableView<Produit> tvProduit;
    @FXML
    private TableColumn<Produit, Integer> tcNote;
    @FXML
    private Button btnModifierProduit;
    @FXML
    private Button btnSupprimerProduit;
    @FXML
    private Button btnRemise;
    @FXML
    private TextField tfCategorie;
    @FXML
    private TextField tfPourcentage;
    @FXML
    private TextField tfReference;
    @FXML
    private TextField tfLibelle;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextArea teDescription;
    @FXML
    private Button btnAjouterProduit;
    @FXML
    private Label Titre;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnAnnuler;
    @FXML
    private TextField tfCategorieRemise;
    @FXML
    private Button btnpdf;
    @FXML
    private Button btnStat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        tcReference.setCellValueFactory(new PropertyValueFactory<>("Reference"));
        tcLibelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        tcCategorie.setCellValueFactory(new PropertyValueFactory<>("Categorie"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        tcPrix.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        tcNote.setCellValueFactory(new PropertyValueFactory<>("Note"));

        // Set Sort type for userName column
        // Display row data
        tvProduit.setItems(p.afficher());

    }

    @FXML
    private void SupprimerProduitOnclick(ActionEvent event) {
        Produit p1 = tvProduit.getSelectionModel().getSelectedItem();
        if (p1 == null) {
            //veuillez selectionner une liiiiiiiiiiiiiiiigne
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur !");
            alert.setHeaderText(null);
            alert.setContentText("veuillez selectionner une ligne du tableau puis appuyez sur le bouton supprimer");
            alert.show();
        } else {

            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Suppression");
            alert1.setHeaderText("Voulez vous supprimer ce produit et tous les avis qui le concernent ?");
            //alert.setContentText("");

            Optional<ButtonType> option = alert1.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {

                if (!p.VerifCommandeProduit(p1)) {
                    AvisCRUD a = new AvisCRUD();
                    a.supprimerAvisParProduit(p1.getReference());
                    p.supprimerProduit(p1.getReference());
                    tvProduit.setItems(p.afficher());

                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setTitle("Information!");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Produit et avis supprimés avec succées");
                    alert3.show();
                } else {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Erreur!");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Ce produit est utilisé dans une ou plusieurs commandes vous ne pouvez pas le supprimer!");
                    alert3.show();

                }

            } else {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Erreur!");
                alert3.setHeaderText(null);
                alert3.setContentText("Erreur de suppression!");
                alert3.show();
            }

        }
    }

    @FXML
    private void RemiseOnclick(ActionEvent event) {

        if (tfCategorieRemise.getText().isEmpty() || tfPourcentage.getText().isEmpty()) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Erreur remise !");
            alert3.setHeaderText(null);
            alert3.setContentText("Un ou plusieurs champs sont vides!");
            alert3.show();
        } else {
            try {
                String categorie = tfCategorieRemise.getText();
                float pourcentage = Float.parseFloat(tfPourcentage.getText());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remise");
                alert.setHeaderText("Voulez vous affecter cette remise ?");
                //alert.setContentText("");

                Optional<ButtonType> option = alert.showAndWait();
                //confirmation 
                if (option.get() == ButtonType.OK) {

                    if (p.verifCategorieProduit(categorie)) {
                        p.remisePrixCategorie(categorie, pourcentage);
                        tvProduit.setItems(p.afficher());

                        
                        List<String> mails=p.recupererEmails();
                        System.out.println(mails);
                        for(String mail:mails){
                            try {
                                MailAPI.sendMail(mail,"Remise Sur Produits","Bonjour mr/mme,nous vous offrons une remise de "+pourcentage+"% sur tous les produits de catégorie "+categorie+"  .Visitez notre application GGC.");
                            } catch (MessagingException ex) {
                                Logger.getLogger(GestionProduitController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Information!");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Remise affectée avec succées et tous les clients ont été notifié par mail");
                        alert2.show();
                    } else {
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setTitle("Erreur!");
                        alert3.setHeaderText(null);
                        alert3.setContentText("Erreur de remise,La categorie saisie n'existe pas dans la liste des produits!");
                        alert3.show();
                    }

                } else {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Erreur!");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Erreur de remise!");
                    alert3.show();
                }

            } catch (NumberFormatException e) {
                //afficher alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur!");
                alert.setHeaderText(null);
                alert.setContentText("Erreur de remise, le champ pourcentage doit etre un nombre");
                alert.show();
            }
        }
        tvProduit.setItems(p.afficher());
        tfCategorieRemise.setText("");
        tfPourcentage.setText("");
    }

    @FXML
    private void AjouterProduitOnclick(ActionEvent event) {
        if (tfReference.getText().isEmpty() || tfLibelle.getText().isEmpty() || tfCategorie.getText().isEmpty() || tfPrix.getText().isEmpty() || teDescription.getText().isEmpty()) {
            //afficher alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Erreur d'ajout champs vides");
            alert.show();

        } else {

            try {

                int reference = Integer.parseInt(tfReference.getText());
                float prix = Float.parseFloat(tfPrix.getText());
                String libelle = tfLibelle.getText();
                String description = teDescription.getText();
                String categorie = tfCategorie.getText();
                Produit p1 = new Produit(reference, libelle, categorie, description, prix);

                if (p.VerifUniciteProduit(p1)) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Ajout");
                    alert.setHeaderText("Voulez vous ajouter ce produit?");
                    //alert.setContentText("");

                    Optional<ButtonType> option = alert.showAndWait();
                    //confirmation 
                    if (option.get() == ButtonType.OK) {
                        p.ajouterProduit(p1);
                        tfReference.setText("");
                        tfLibelle.setText("");
                        tfCategorie.setText("");
                        teDescription.setText("");
                        tfPrix.setText("");
                        //afficher information ajout
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Information!");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Ajout produit avec succées");
                        alert2.show();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Erreur!");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Ajout abandonné.");
                        alert2.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur Unicité!");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur d'ajout ce produit existe déja");
                    alert.show();
                }
            } catch (NumberFormatException e) {
                //afficher alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur!");
                alert.setHeaderText(null);
                alert.setContentText("Erreur d'ajout champs reference et prix doivent etre des nombres");
                alert.show();
            }

        }
        tvProduit.setItems(p.afficher());
    }

    @FXML
    private void ModifierProduitOnclick(ActionEvent event) {
        Produit p1 = tvProduit.getSelectionModel().getSelectedItem();
        if (p1 == null) {
            //veuillez selectionner une liiiiiiiiiiiiiiiigne
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur !");
            alert1.setHeaderText(null);
            alert1.setContentText("veuillez selectionner une ligne du tableau puis appuyez sur le bouton modifier");
            alert1.show();

        } else {
            Titre.setText("Modification Produit");
            btnModifier.setVisible(true);
            btnAnnuler.setVisible(true);
            btnAjouterProduit.setVisible(false);
            btnModifierProduit.setVisible(false);
            btnSupprimerProduit.setVisible(false);

            //remplir les champs avec les valeurs du produit
            tfReference.setText(p1.getReference() + "");
            tfReference.setEditable(false);
            tfLibelle.setText(p1.getLibelle());
            tfCategorie.setText(p1.getCategorie());
            teDescription.setText(p1.getDescription());
            tfPrix.setText(p1.getPrix() + "");

        }
    }

    @FXML
    private void ModifierOnclick(ActionEvent event) {
        if (tfReference.getText().isEmpty() || tfLibelle.getText().isEmpty() || tfCategorie.getText().isEmpty() || tfPrix.getText().isEmpty() || teDescription.getText().isEmpty()) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Erreur!");
            alert3.setHeaderText(null);
            alert3.setContentText("Un ou plusieurs champs sont vides!");
            alert3.show();

        } else {

            try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Modification");
                alert.setHeaderText("Voulez vous modifier ce produit?");
                //alert.setContentText("");

                Optional<ButtonType> option = alert.showAndWait();
                //confirmation 
                if (option.get() == ButtonType.OK) {

                    int reference = Integer.parseInt(tfReference.getText());
                    float prix = Float.parseFloat(tfPrix.getText());
                    String libelle = tfLibelle.getText();
                    String description = teDescription.getText();
                    String categorie = tfCategorie.getText();
                    Produit p1 = new Produit(reference, libelle, categorie, description, prix);

                    ProduitCRUD p = new ProduitCRUD();
                    p.modifierProduit(p1);

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Information!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Produit modifié avec succées");
                    alert2.show();
                } else {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Erreur!");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Erreur de modification!");
                    alert3.show();
                }
            } catch (NumberFormatException e) {
                //afficher alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur!");
                alert.setHeaderText(null);
                alert.setContentText("Erreur d'ajout champ prix doit etre un nombres");
                alert.show();
            }

        }
        tvProduit.setItems(p.afficher());
    }

    @FXML
    private void AnnulerOnclick(ActionEvent event) {
        btnModifier.setVisible(false);
        btnAnnuler.setVisible(false);
        btnModifierProduit.setVisible(true);
        btnSupprimerProduit.setVisible(true);
        btnAjouterProduit.setVisible(true);

        Titre.setText("Ajout Produit");

        tfReference.setText("");
        tfReference.setEditable(true);
        tfLibelle.setText("");
        tfCategorie.setText("");
        teDescription.setText("");
        tfPrix.setText("");
    }

    @FXML
    private void pdfOnclick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Modification");
                alert.setHeaderText("Voulez vous recevoir la liste des produits par mail?");
                //alert.setContentText("");

                Optional<ButtonType> option = alert.showAndWait();
                //confirmation 
                if (option.get() == ButtonType.OK) {
                    PdfAPI.createAndSendListProduit("gamergeekscommunity@gmail.com");
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Information!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Mail et pdf envoyés");
                    alert2.show();
                } else {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setTitle("Erreur!");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Erreur d'envoi!");
                    alert3.show();
                }
        
    }

    @FXML
    private void statOnclick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("StatistiqueProduit.fxml"));
            Stage primaryStage=new Stage();
            primaryStage.setTitle("Statistique des Notes de chaque produit");

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
