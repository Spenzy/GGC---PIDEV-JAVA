/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Shop;

import GUI.DashboardController;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.Avis;
import entities.Produit;
import services.AvisCRUD;
import services.ProduitCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class DonnerAvisController implements Initializable {

    public int idClient;
    public String Libelle;
    public int idProduit;
    public int Note;
    public AvisCRUD a = new AvisCRUD();

    @FXML
    private ComboBox<String> cbType;
    @FXML
    private TextArea teDescription;
    @FXML
    private Button btnAjouterAvis;
    

    /**
     * Initializes the controller class.
     */
     public DonnerAvisController() {
    }

    public DonnerAvisController(int idClient,int idProduit, int Note, String Libelle) {
        this.idClient=idClient;
        this.Libelle = Libelle;
        this.idProduit = idProduit;
        this.Note = Note;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AvisCRUD a = new AvisCRUD();
        cbType.getItems().add("excellent");
        cbType.getItems().add("moyen");
        cbType.getItems().add("mediocre");
        btnAjouterAvis.setStyle("-fx-text-fill: black;\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-background-color: linear-gradient(#61a2b1, #A945B4 );\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        btnAjouterAvis.setOnAction((e) -> {
            AjouterAvisOnclick();
            Note = a.recupererNote(idProduit);
            refreshAfficherAvis(btnAjouterAvis);
        });
    }

    private void AjouterAvisOnclick() {

        String type = cbType.getValue();
        String description = teDescription.getText();
        if (type == null || teDescription.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Erreur d'ajout champs vides,veuillez remplir les 2 champs");
            alert.show();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ajout Avis");
            alert.setHeaderText("Voulez vous ajouter cet avis ?");
            //alert.setContentText("");

            Optional<ButtonType> option = alert.showAndWait();
            //confirmation
            if (option.get() == ButtonType.OK) {

                int reference = a.recupererReference(Libelle);
                a.ajouterAvis(new Avis(reference, idClient, description, type));
                ProduitCRUD p = new ProduitCRUD();
                p.CalculAffectationNote();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Information!");
                alert2.setHeaderText(null);
                alert2.setContentText("Avis ajouté avec succées ");
                alert2.show();

            } else {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Erreur!");
                alert3.setHeaderText(null);
                alert3.setContentText("Erreur d'ajout!");
                alert3.show();
            }
        }
       
       
        //refresh AffichageAvisProduit.fxml
    }

    public void refreshAfficherAvis(Button btn) {
        try {
            //init loader root
            FXMLLoader testLoad = new FXMLLoader(getClass().getResource("AfficherAvis.fxml"));

            //init Controller
            AfficherAvisController controller = new AfficherAvisController(idClient,idProduit, Note, Libelle);
            testLoad.setController(controller);

            Parent root = testLoad.load();

            //scene switch
            DashboardController.refreshParent(root);

        } catch (IOException ex) {
            Logger.getLogger(AfficherAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
