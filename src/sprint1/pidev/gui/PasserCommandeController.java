/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.gui;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;
import sprint1.pidev.entities.Commande;
import sprint1.pidev.entities.LigneCommande;
import sprint1.pidev.services.CommandeCRUD;
import sprint1.pidev.services.LigneCommandeCRUD;
import sprint1.pidev.utils.PdfAPI;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class PasserCommandeController implements Initializable {

    public int idClient = 111;
    public int idCommande = 0;
    public Commande c1 = new Commande(idClient, "");
    public CommandeCRUD c = new CommandeCRUD();
    @FXML
    private Button btnAjouter;
    @FXML
    private TextField tfAdresse;
    @FXML
    private Spinner<Integer> sQuantite;
    @FXML
    private Button btnAjouterLigne;
    @FXML
    private ComboBox<String> cbProduit;
    @FXML
    private Label labelQuantite;
    @FXML
    private Label labelAdresse;
    @FXML
    private Button btnAnnuler;
    @FXML
    private AnchorPane panePanier;
    String[] words = {"carthege","carthage Byrsa","Ariana","sidi Bou said","gammarth","marsa","kram","manzah1"
    ,"manzah2","manzah3","manzah4","manzah5"
    ,"manzah6","manzah7","manzah8","manzah9"
    ,"nasr","tunis","monastir","mahdia"
    ,"sousse","sfax","mednine" ," selyenna"
    ,"beja","benzart","gammarth","marssa"
    ,"tabarka","aindrahem","jandouba"
    };
    @FXML
    private Button pdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TextFields.bindAutoCompletion(tfAdresse,words);
        cbProduit.setItems(c.affecterProduit());
        cbProduit.getSelectionModel().selectFirst();

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1);
        sQuantite.setValueFactory(valueFactory);

        labelAdresse.setVisible(false);
        labelQuantite.setVisible(false);
    }

    @FXML
    private void ajouterCommandeOnclick(ActionEvent event) {

        if (this.idCommande == 0) {
            Alert alert1 = new Alert(AlertType.ERROR);
            alert1.setTitle("Erreur!");
            alert1.setHeaderText(null);
            alert1.setContentText("Aucune commande n'est saisie,Veuillez remplir tous les champs!");
            alert1.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation");
            alert.setHeaderText("Voulez vous valider cette commande ?");
            //alert.setContentText("");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                ModifierAdresse();
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Information!");
                alert1.setHeaderText(null);
                alert1.setContentText("Commande Validée avec Succées!");
                alert1.show();
                viderChampsRefresh();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("Votre commande n'a pas été validée");
                alert2.show();
            }
        }
    }

    @FXML
    private void AjouterLigneOnclick(ActionEvent event) {
        //création Commande
        String adresse = tfAdresse.getText();
        if (adresse.isEmpty()) {
            labelAdresse.setVisible(true);
        } else {
            labelAdresse.setVisible(false);
            c1.setAdresse(adresse);

            if (sQuantite.getValue() == 0) {
                labelQuantite.setVisible(true);
            } else {

                labelQuantite.setVisible(false);

                String libelle = cbProduit.getValue();
                int quantite = sQuantite.getValue();

                //ajouter commande
                c1.setLivree(false);
                c.ajouterCommande(c1);
                this.idCommande = c1.getIdCommande();
                //ajouter ligne
                int idProduit = c.recupererReference(libelle);
                LigneCommande lc1 = new LigneCommande(c1.getIdCommande(), idProduit, quantite, 0);
                LigneCommandeCRUD LC = new LigneCommandeCRUD();
                LC.ajouterLigneCommande(lc1);
                c.calculPrixCommande(c1.getIdCommande());

                //affihcerPanier
                AfficherPanier();

                //faragh les champs
                cbProduit.getSelectionModel().selectFirst();
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1);
                sQuantite.setValueFactory(valueFactory);
            }
        }
    }

    public void AfficherPanier() {
        try {
            FXMLLoader panierMain = new FXMLLoader(getClass().getResource("Panier.fxml"));
            PanierController panierController = new PanierController(idCommande);
            panierMain.setController(panierController);
            Parent root = panierMain.load();

            panePanier.getChildren().clear();
            panePanier.getChildren().add(root);

        } catch (IOException ex) {
            Logger.getLogger(PasserCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AnnulerOnclick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annulation");
        alert.setHeaderText("Voulez vous vraiment annuler cette commande ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation 
        if (option.get() == ButtonType.OK) {

            if (this.idCommande == 0) {
                Alert alert1 = new Alert(AlertType.ERROR);
                alert1.setTitle("Erreur!");
                alert1.setHeaderText(null);
                alert1.setContentText("Aucune commande n'est saisie");
                alert1.show();
            } else {
                c.supprimerCommande(idCommande);
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Information!");
                alert1.setHeaderText(null);
                alert1.setContentText("Commande Annulée.");
                alert1.show();

                viderChampsRefresh();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("Votre commande n'a pas été annullée");
            alert2.show();
        }
    }

    public void ModifierAdresse() {
        if (tfAdresse.getText().isEmpty()) {
            labelAdresse.setVisible(true);
        } else {
            c1.setAdresse(tfAdresse.getText());
            c1.setLivree(false);
            c.modifierCommande(c1);
            c.calculPrixCommande(idCommande);
            labelAdresse.setVisible(false);
        }
    }

    public void viderChampsRefresh() {
        tfAdresse.setText("");
        tfAdresse.setEditable(true);
        cbProduit.setItems(c.affecterProduit());
        cbProduit.getSelectionModel().selectFirst();

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1);
        sQuantite.setValueFactory(valueFactory);

        labelAdresse.setVisible(false);
        labelQuantite.setVisible(false);

        panePanier.getChildren().clear();

    }

    @FXML
    private void pdfOnclick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Génération PDF");
        alert.setHeaderText("Voulez vous vraiment envoyer cette commande par mail ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation 
        if (option.get() == ButtonType.OK) {
        LigneCommandeCRUD lc=new LigneCommandeCRUD();
        c.calculPrixCommande(c1.getIdCommande());
        c1.setPrix(c.RecupererPrixCommande(c1.getIdCommande()));
        c1.setLignes(lc.afficher(c1.getIdCommande()));
        String email=c.recupererMail(c1.getIdClient());
        System.out.println(email);
        PdfAPI.createAndSendCommande(email,c1);
    }else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("Pas d'exportation PDF ni envoi de mail");
            alert2.show();
        }
    }

}
