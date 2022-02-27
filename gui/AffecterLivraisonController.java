/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.gui;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sprint1.pidev.entities.Commande;
import sprint1.pidev.entities.Livraison;
import sprint1.pidev.services.CommandeCRUD;
import sprint1.pidev.services.LivraisonCRUD;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class AffecterLivraisonController implements Initializable {

    public CommandeCRUD c = new CommandeCRUD();
    public LivraisonCRUD l = new LivraisonCRUD();
    @FXML
    private TableView<Commande> tvCommande;
    @FXML
    private TableColumn<Commande, Integer> tcCCommande;
    @FXML
    private TableColumn<Commande, Integer> tcClient;
    @FXML
    private TableColumn<Commande, String> tcAdresse;
    @FXML
    private TableColumn<Commande, Float> tcPrix;
    @FXML
    private TableColumn<Commande, Boolean> tcLivree;
    @FXML
    private TableColumn<Commande, Date> tcCDate;
    @FXML
    private TableView<Livraison> tvLivraison;
    @FXML
    private TableColumn<Livraison, Integer> tcLCommande;
    @FXML
    private TableColumn<Livraison, Integer> tcLivreur;
    @FXML
    private TableColumn<Livraison, Date> tcLDate;
    @FXML
    private ComboBox<Integer> cbCommande;
    @FXML
    private ComboBox<Integer> cbLivreur;
    @FXML
    private DatePicker dpLivraison;
    @FXML
    private Button btnValider;
    @FXML
    private Label LabelDateVide;
    @FXML
    private Label LabelSysDate;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button Modification;
    @FXML
    private Button btnAnnulerModification;
    @FXML
    private Label title;
    @FXML
    private Button btnRetardLivraison;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialisations buttons
        Modification.setVisible(false);
        btnAnnulerModification.setVisible(false);
        //remplissage tableViewCommande
        tcCCommande.setCellValueFactory(new PropertyValueFactory<>("IdCommande"));
        tcClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        tcAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tcPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tcLivree.setCellValueFactory(new PropertyValueFactory<>("livree"));
        tcCDate.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        tvCommande.setItems(c.afficher());
        //remplissage table view livraison
        tcLCommande.setCellValueFactory(new PropertyValueFactory<>("IdCommande"));
        tcLivreur.setCellValueFactory(new PropertyValueFactory<>("idLivreur"));
        tcLDate.setCellValueFactory(new PropertyValueFactory<>("DateHeure"));
        tvLivraison.setItems(l.afficher());

        //remplissage comboBox Commande
        cbCommande.setItems(l.affecterCommande());
        cbCommande.getSelectionModel().selectFirst();
        //remplissage comboBox Livreur
        cbLivreur.setItems(l.affecterLivreur());
        cbLivreur.getSelectionModel().selectFirst();

        //init labels
        LabelDateVide.setVisible(false);
        LabelSysDate.setVisible(false);
    }

    @FXML
    private void ValiderOnclick(ActionEvent event) {
        //ajouter Livraison
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider cette livraison ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation 
        if (option.get() == ButtonType.OK) {

            if (dpLivraison.getValue() == null) {
                LabelDateVide.setVisible(true);
            } else {
                LabelDateVide.setVisible(false);

                int Commande = cbCommande.getSelectionModel().getSelectedItem();
                int Livreur = cbLivreur.getSelectionModel().getSelectedItem();
                LocalDate dateLivraison = dpLivraison.getValue();
                LocalDate sysdate = LocalDate.now();

                if (dateLivraison.isBefore(sysdate)) {
                    LabelSysDate.setVisible(true);
                } else {
                    LabelSysDate.setVisible(false);
                    Livraison l1 = new Livraison(Commande, Livreur, java.sql.Date.valueOf(dateLivraison));
                    l.ajouterLivraison(l1);
                    c.commandeLivree(Commande);

                    //refresh table views and ComboBox s
                    tvCommande.setItems(c.afficher());
                    tvLivraison.setItems(l.afficher());
                    cbCommande.setItems(l.affecterCommande());
                    cbCommande.getSelectionModel().selectFirst();
                    cbLivreur.setItems(l.affecterLivreur());
                    cbLivreur.getSelectionModel().selectFirst();
                    dpLivraison.setValue(null);
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation d'ajout!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("La livraison a été ajoutée avec succées");
                    alert2.show();
                }

            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("La livraison n'a pas été affectée");
            alert2.show();
        }

    }

    @FXML
    private void ModifierOnclick(ActionEvent event) {

        Livraison l1 = tvLivraison.getSelectionModel().getSelectedItem();
        if (l1 == null) {
            //veuillez selectionner une liiiiiiiiiiiiiiiigne
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur !");
            alert1.setHeaderText(null);
            alert1.setContentText("veuillez selectionner une ligne du tableau puis appuyez sur le bouton modifier");
            alert1.show();

        } else {
            title.setText("Modification Livraison");
            Modification.setVisible(true);
            btnAnnulerModification.setVisible(true);
            btnValider.setVisible(false);
            btnModifier.setVisible(false);
            btnSupprimer.setVisible(false);
            btnRetardLivraison.setVisible(false);

            cbCommande.getItems().clear();
            cbCommande.getItems().add(l1.getIdCommande());
            cbCommande.getSelectionModel().selectLast();
            cbCommande.setEditable(false);

            cbLivreur.setValue(l1.getIdLivreur());

            dpLivraison.setValue(l1.getDateHeure().toLocalDate());

        }

    }

    @FXML
    private void SupprimerOnclick(ActionEvent event) {

        Livraison l1 = tvLivraison.getSelectionModel().getSelectedItem();
        if (l1 == null) {
            //veuillez selectionner une liiiiiiiiiiiiiiiigne
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur !");
            alert1.setHeaderText(null);
            alert1.setContentText("veuillez selectionner une ligne du tableau puis appuyez sur le bouton supprimer");
            alert1.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation");
            alert.setHeaderText("Voulez vous vraiment supprimer cette livraison ?");
            //alert.setContentText("");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation 
            if (option.get() == ButtonType.OK) {
                l.supprimerLivraison(l1.getIdCommande());

                //refresh table views and ComboBox and buttons and labels
                tvCommande.setItems(c.afficher());
                tvLivraison.setItems(l.afficher());
                cbCommande.setItems(l.affecterCommande());
                cbCommande.getSelectionModel().selectFirst();
                cbLivreur.setItems(l.affecterLivreur());
                cbLivreur.getSelectionModel().selectFirst();
                dpLivraison.setValue(null);
                btnValider.setVisible(true);
                Modification.setVisible(false);
                btnAnnulerModification.setVisible(false);
                LabelDateVide.setVisible(false);
                LabelSysDate.setVisible(false);

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Confirmation !");
                alert1.setHeaderText(null);
                alert1.setContentText("La livraison a été supprimée avec sucées");
                alert1.show();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur!");
                alert2.setHeaderText(null);
                alert2.setContentText("La livraison n'a pas été supprimée");
                alert2.show();
            }
        }
    }

    @FXML
    private void ModifierLivraisonOnclick(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Validation");
        alert.setHeaderText("Voulez vous valider la modification de cette livraison ?");
        //alert.setContentText("");

        Optional<ButtonType> option = alert.showAndWait();
        //confirmation 
        if (option.get() == ButtonType.OK) {
            //Modifier Livraison
            if (dpLivraison.getValue() == null) {
                LabelDateVide.setVisible(true);
            } else {
                LabelDateVide.setVisible(false);

                int Commande = cbCommande.getSelectionModel().getSelectedItem();
                int Livreur = cbLivreur.getSelectionModel().getSelectedItem();
                LocalDate dateLivraison = dpLivraison.getValue();
                LocalDate sysdate = LocalDate.now();

                if (dateLivraison.isBefore(sysdate)) {
                    LabelSysDate.setVisible(true);
                } else {
                    LabelSysDate.setVisible(false);
                    Livraison l1 = new Livraison(Commande, Livreur, java.sql.Date.valueOf(dateLivraison));
                    l.modifierLivraison(l1);
                    c.commandeLivree(Commande);

                    //refresh table views and ComboBox and buttons and labels
                    tvCommande.setItems(c.afficher());
                    tvLivraison.setItems(l.afficher());
                    cbCommande.setItems(l.affecterCommande());
                    cbCommande.getSelectionModel().selectFirst();
                    cbLivreur.setItems(l.affecterLivreur());
                    cbLivreur.getSelectionModel().selectFirst();
                    dpLivraison.setValue(null);
                    btnValider.setVisible(true);
                    Modification.setVisible(false);
                    btnAnnulerModification.setVisible(false);
                    btnModifier.setVisible(true);
                    btnSupprimer.setVisible(true);
                    btnRetardLivraison.setVisible(true);

                    LabelDateVide.setVisible(false);
                    LabelSysDate.setVisible(false);
                    title.setText("Affectation Livraison Commande");

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Confirmation de modification!");
                    alert2.setHeaderText(null);
                    alert2.setContentText("La livraison a été Modifiée avec succées");
                    alert2.show();
                }

            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Erreur!");
            alert2.setHeaderText(null);
            alert2.setContentText("La livraison n'a pas été Modifiée");
            alert2.show();
        }

    }

    @FXML
    private void AnnulerModificationµOnclick(ActionEvent event) {
        title.setText("Affectation Livraison Commande");
        btnValider.setVisible(true);
        Modification.setVisible(false);
        btnAnnulerModification.setVisible(false);
        btnModifier.setVisible(true);
        btnSupprimer.setVisible(true);
        btnRetardLivraison.setVisible(true);
        //réinitialisation des comboboxes et dateField et Labels

        //remplissage comboBox Commande
        cbCommande.setItems(l.affecterCommande());
        cbCommande.getSelectionModel().selectFirst();
        //remplissage comboBox Livreur
        cbLivreur.setItems(l.affecterLivreur());
        cbLivreur.getSelectionModel().selectFirst();
        //vider DatePicker
        dpLivraison.setValue(null);
        //init labels
        LabelDateVide.setVisible(false);
        LabelSysDate.setVisible(false);

    }

    @FXML
    private void RetardLivraisonOnclick(ActionEvent event) {
        l.RemiseLivraison();
        tvCommande.setItems(c.afficher());
        tvLivraison.setItems(l.afficher());
        cbCommande.setItems(l.affecterCommande());
        cbCommande.getSelectionModel().selectFirst();
        cbLivreur.setItems(l.affecterLivreur());
        cbLivreur.getSelectionModel().selectFirst();
        dpLivraison.setValue(null);
        btnValider.setVisible(true);
        Modification.setVisible(false);
        btnAnnulerModification.setVisible(false);
        btnModifier.setVisible(true);
        btnSupprimer.setVisible(true);
        LabelDateVide.setVisible(false);
        LabelSysDate.setVisible(false);
        title.setText("Affectation Livraison Commande");
    }

}
