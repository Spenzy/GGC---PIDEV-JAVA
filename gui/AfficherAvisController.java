/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapplication12.entities.Avis;
import javaapplication12.services.AvisCRUD;
import javaapplication12.services.ProduitCRUD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class AfficherAvisController implements Initializable {

    public int idClient = 111;
    public int idProduit, Note;
    public String Libelle;
    public ProduitCRUD p = new ProduitCRUD();
    public AvisCRUD a = new AvisCRUD();
    public Avis a1 = new Avis();
    Button btnAjouter;
    @FXML
    private VBox Avis;
    @FXML
    private Button btnRetour;
    @FXML
    private AnchorPane arrierePlan;

    /**
     * Initializes the controller class.
     */
    public AfficherAvisController() {
    }

    public AfficherAvisController(int idProduit, int Note, String Libelle) {
        this.idProduit = idProduit;
        this.Note = Note;
        this.Libelle = Libelle;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //detecter la fermeture de la fentere et ouvrir affichage produits
        arrierePlan.setStyle("-fx-background-color: white");
        btnRetour.setOnAction(e -> {
            Stage stage = (Stage) btnRetour.getScene().getWindow();
            stage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AffichageProduitClient.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Interface Client Produits et Avis");

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(AfficherAvisController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        /* stage.setOnCloseRequest(e -> {
            System.out.println("tsaaaker w nhelou ejdid");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("AffichageProduitClient.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(AfficherAvisController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
         */

        List<Avis> ListAvis = a.afficher(idProduit);
        Avis.getChildren().clear();
        Avis.setStyle("-fx-background-color: white");
        if (ListAvis.isEmpty()) {
            Label emptyLabel = new Label("Aucun avis n'a été donné");
            Avis.getChildren().add(emptyLabel);
        } else {
            Label Label = new Label("Voici les avis du produit " + Libelle);
            Label.setStyle("-fx-padding:5px; -fx-font-size: 15px;");
            Avis.getChildren().add(Label);

            Label LabelLibelle = new Label(Libelle);
            LabelLibelle.setPrefWidth(500);
            LabelLibelle.setStyle("-fx-padding:5px; -fx-font-size: 25px;\n"
                    + "    -fx-font-weight: bold;\n"
                    + "    -fx-text-fill: #4B1651 ;\n"
                    + "    -fx-effect: dropshadow( gaussian , rgba(3,0,255,0.5) , 0,0,0,1 );");
            Avis.getChildren().add(LabelLibelle);
            Label LabelNote = new Label("Noté : " + Note);
            LabelNote.setPrefWidth(500);
            LabelNote.setStyle("-fx-padding:5px; -fx-font-size: 15px;\n"
                    + "    -fx-font-weight: bold;\n"
                    + "    -fx-text-fill: #4B1651 ;\n"
                    + "    -fx-effect: dropshadow( gaussian , rgba(3,0,255,0.5) , 0,0,0,1 );");
            Avis.getChildren().add(LabelNote);

            for (Avis a1 : ListAvis) {
                VBox vbox = AffichageAvis(a1);
                Avis.getChildren().add(vbox);
            }
        }
        Separator sep = new Separator();
        sep.setOrientation(Orientation.HORIZONTAL);
        Avis.getChildren().add(sep);

        btnAjouter = new Button("Donner Avis");
        btnAjouter.setStyle(" -fx-text-fill: black;\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-background-color: linear-gradient(#61a2b1, #A945B4 );\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        btnAjouter.setOnAction(e -> {
            ajouterAvis();
        });
        btnAjouter.setTranslateX(270);
        Avis.getChildren().add(btnAjouter);
    }

    private VBox AffichageAvis(Avis a1) {
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER_LEFT);

        Label Client = new Label("Client : " + a1.getIdClient());
        // Categorie.setPrefWidth(1000);
        Client.setStyle("-fx-padding:5px; -fx-font-size: 15px;");

        Label Type = new Label("Type : " + a1.getType());
        Type.setStyle("-fx-padding:5px; -fx-font-size: 15px;");

        Label Description = new Label("Description : " + a1.getDescription());
        Description.setPrefWidth(5000);
        Description.setStyle("-fx-padding:5px; -fx-font-size: 15px;");

        Button btnAvis = new Button("Donner Avis");
        btnAvis.setStyle(" -fx-text-fill: black;\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-background-color: linear-gradient(#61a2b1, #A945B4 );\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");

        layout.setStyle("-fx-background-color: #F4FFFF; -fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 2px;");

        Button modifier = new Button("Modifier");
        modifier.setStyle(" -fx-text-fill: black;\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-background-color: linear-gradient(#DDFAFC, #A1E0E6 );\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        modifier.setOnAction(e -> {
            modifierAvis(a1);
        });
        modifier.setTranslateX(557);

        Button supprimer = new Button("Supprimer");
        supprimer.setStyle(" -fx-text-fill: black;\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-background-color: linear-gradient(#DDFAFC, #A1E0E6);\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        supprimer.setOnAction(e -> {
            a.supprimerAvis(a1.getIdAvis());
            p.CalculAffectationNote();
            Note = a.recupererNote(a1.getReferenceProduit());
            refreshAffichageAvis(supprimer);
        });
        supprimer.setTranslateX(550);
        supprimer.setScaleX(0.9);

        if (idClient == a1.getIdClient()) {
            layout.getChildren().addAll(Client, Type, Description, modifier, supprimer);
        } else {
            layout.getChildren().addAll(Client, Type, Description);
        }
        layout.setLayoutY(20);
        layout.setSpacing(5);
        return layout;
    }

    public void refreshAffichageAvis(Button btn) {
        try {
            //init loader root
            FXMLLoader testLoad = new FXMLLoader(getClass().getResource("AfficherAvis.fxml"));

            //init Controller
            AfficherAvisController controller = new AfficherAvisController(idProduit, Note, Libelle);
            testLoad.setController(controller);

            Parent root = testLoad.load();

            //scene switch
            Scene affPubScene = new Scene(root);
            ((Stage) btn.getScene().getWindow()).setScene(affPubScene);

        } catch (IOException ex) {
            Logger.getLogger(AffichageProduitClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ajouterAvis() {
        try {
            btnAjouter.setVisible(false);
            FXMLLoader AvisMain = new FXMLLoader(getClass().getResource("DonnerAvis.fxml"));
            DonnerAvisController avisController = new DonnerAvisController(idProduit, Note, Libelle);
            AvisMain.setController(avisController);
            Parent root = AvisMain.load();

            Avis.getChildren().add(root);

        } catch (IOException ex) {
            Logger.getLogger(DonnerAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modifierAvis(Avis a1) {
        try {
            btnAjouter.setVisible(false);
            FXMLLoader AvisMain = new FXMLLoader(getClass().getResource("ModifierAvis.fxml"));
            ModifierAvisController avisController = new ModifierAvisController(a1);
            AvisMain.setController(avisController);
            Parent root = AvisMain.load();

            Avis.getChildren().add(root);

        } catch (IOException ex) {
            Logger.getLogger(ModifierAvisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
