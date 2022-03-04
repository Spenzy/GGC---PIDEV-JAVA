/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Shop;

import GUI.Commande.PasserCommandeController;
import GUI.DashboardController;
import entities.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.ProduitCRUD;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class AffichageProduitClientController implements Initializable {

    int idClient;
    public Produit p1 = new Produit();
    public ProduitCRUD p = new ProduitCRUD();

    public AffichageProduitClientController() {
    }

    public AffichageProduitClientController(int idClient) {
        this.idClient = idClient;
    }

    @FXML
    private VBox Products;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //recupérer liste des Produits
        List<Produit> ListProduits = p.afficher();

        Products.getChildren().clear();
        if (ListProduits.isEmpty()) {
            Label emptyLabel = new Label("Aucun produit n'est disponible pour le moment");
            Products.getChildren().add(emptyLabel);
        } else {
            Button ButtonCommande = new Button("Passer Commande");
            Products.getChildren().add(ButtonCommande);
            ButtonCommande.setTranslateX(480);
            ButtonCommande.setPrefHeight(90);
            ButtonCommande.setStyle("-fx-text-fill: black;\n"
                    + "    -fx-font-family: \"Arial\";\n"
                    + "    -fx-font-weight: bold;\n"
                    + "    -fx-background-color: linear-gradient(#61a2b1, #A945B4 );\n"
                    + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 )");

            ButtonCommande.setOnAction(e -> {
                try {
                    //init loader root
                    FXMLLoader testLoad = new FXMLLoader(getClass().getResource("Commande/PasserCommande.fxml"));

                    //init Controller
                    PasserCommandeController controller = new PasserCommandeController(idClient);
                    testLoad.setController(controller);

                    Parent root = testLoad.load();
                    DashboardController.refreshParent(root);

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            });

            for (Produit p1 : ListProduits) {
                VBox vbox = AffichageProduit(p1);
                Products.getChildren().add(vbox);

            }

            Separator sep = new Separator();
            sep.setOrientation(Orientation.HORIZONTAL);
            Products.getChildren().add(sep);
        }
    }

    private VBox AffichageProduit(Produit p1) {
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER_LEFT);

        Label Libelle = new Label(p1.getLibelle());
        Libelle.setPrefWidth(500);
        Libelle.setStyle("-fx-padding:5px; -fx-font-size: 25px;\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-text-fill: #4B1651 ;\n"
                + "    -fx-effect: dropshadow( gaussian , rgba(3,0,255,0.5) , 0,0,0,1 );");

        Label Categorie = new Label("Categorie : " + p1.getCategorie());
        Categorie.setPrefWidth(1000);

        Categorie.setStyle("-fx-padding:5px; -fx-font-size: 15px;");

        Label Description = new Label("Description : " + p1.getDescription());
        Description.setPrefWidth(5000);

        Description.setStyle("-fx-padding:5px; -fx-font-size: 15px;");

        Label Prix = new Label("Prix : " + p1.getPrix() + "");
        Prix.setStyle("-fx-padding:5px; -fx-font-size: 20px;\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-text-fill: #333333;\n"
                + "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
        Prix.setTranslateX(500);

        Label Note = new Label("Note: " + p1.getNote() + "");
        Note.setStyle("-fx-padding:5px; -fx-font-size: 15px;");
        if (p1.getNote() == 0) {
            Note.setText("Note : Pas encore attribuée");
        }

        Button btnAvis = new Button("Consulter Avis");
        btnAvis.setStyle(" -fx-text-fill: black;\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-background-color: linear-gradient(#61a2b1, #A945B4 );\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        btnAvis.setOnAction(e -> {
            //nemch lel interface affichageAvis
            try {
                FXMLLoader AfficherAvis = new FXMLLoader(getClass().getResource("AfficherAvis.fxml"));
                AfficherAvisController AfficherAvisController = new AfficherAvisController(idClient, p1.getReference(), p1.getNote(), p1.getLibelle());
                AfficherAvis.setController(AfficherAvisController);
                Parent root = AfficherAvis.load();
                Products.getChildren().clear();
                Products.getChildren().add(root);

            } catch (IOException ex) {
                Logger.getLogger(DonnerAvisController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        btnAvis.setTranslateX(265);

        layout.setStyle("-fx-background-color: #F4FFFF; -fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 2px;");
        layout.setOnMouseEntered((e) -> {
            layout.setStyle("-fx-background-color: #C5FCFC; -fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 2px;");
        });
        layout.setOnMouseExited((e) -> {
            layout.setStyle("-fx-background-color: #F4FFFF; -fx-border-color : #777777;-fx-border-width : 1px;-fx-border-radius : 2px;");
        });

        layout.getChildren().addAll(Libelle, Categorie, Description, Note, Prix, btnAvis);
        layout.setLayoutY(20);

        return layout;
    }

}
