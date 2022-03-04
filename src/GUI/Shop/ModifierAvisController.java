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
import services.AvisCRUD;
import services.ProduitCRUD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class ModifierAvisController implements Initializable {

    int idClient;
    public Avis a1;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private TextArea teDescription;
    @FXML
    private Button btnModifierAvis;

    /**
     * Initializes the controller class.
     */
 public ModifierAvisController() {
    }

    public ModifierAvisController(int idClient, Avis a1) {
        this.idClient = idClient;
        this.a1 = a1;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AvisCRUD a = new AvisCRUD();
        cbType.getItems().add("excellent");
        cbType.getItems().add("moyen");
        cbType.getItems().add("mediocre");
        cbType.setValue(a1.getType());
        teDescription.setText(a1.getDescription());

        btnModifierAvis.setStyle("-fx-text-fill: black;\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-background-color: linear-gradient(#61a2b1, #A945B4 );\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        btnModifierAvis.setOnAction((e) -> {
            ModifierAvisOnclick();
            refreshAfficherAvis(btnModifierAvis);
        });
    }

    private void ModifierAvisOnclick() {

        if (teDescription.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur!");
            alert1.setHeaderText(null);
            alert1.setContentText("Le champ description est vide!");
            alert1.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modification");
            alert.setHeaderText("Voulez vous modifier votre avis?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                AvisCRUD a = new AvisCRUD();

                String type = cbType.getValue();
                String description = teDescription.getText();
                a1.setType(type);
                a1.setDescription(description);
                a.modifierAvis(a1);
                ProduitCRUD p = new ProduitCRUD();
                p.CalculAffectationNote();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Information!");
                alert2.setHeaderText(null);
                alert2.setContentText("Avis modifié avec succées ");
                alert2.show();
            } else {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Erreur!");
                alert3.setHeaderText(null);
                alert3.setContentText("Erreur de modification!");
                alert3.show();

            }

        }
    }

    public void refreshAfficherAvis(Button btn) {
        try {
            //init loader root
            FXMLLoader testLoad = new FXMLLoader(getClass().getResource("AfficherAvis.fxml"));

            //init Controller
            AvisCRUD a = new AvisCRUD();

            AfficherAvisController controller = new AfficherAvisController(idClient,a1.getReferenceProduit(), a.recupererNote(a1.getReferenceProduit()), a.recupererLibelle(a1.getReferenceProduit()));
            testLoad.setController(controller);

            Parent root = testLoad.load();

            //scene switch
            DashboardController.refreshParent(root);

        } catch (IOException ex) {
            Logger.getLogger(AfficherAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
