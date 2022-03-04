/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sprint1.pidev.entities.LigneCommande;
import sprint1.pidev.services.CommandeCRUD;
import sprint1.pidev.services.LigneCommandeCRUD;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class PanierController implements Initializable {

    @FXML
    private VBox panier;
    public int idCommande = 13;
    @FXML
    private GridPane productGridPane;
    private Label TotalPrix;

    public PanierController() {
    }

    public PanierController(int idCommande) {
        this.idCommande = idCommande;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //recupérer liste des lignes de la commande
        LigneCommandeCRUD LC = new LigneCommandeCRUD();
        List<LigneCommande> ListLignes = LC.afficher(idCommande);

        panier.getChildren().clear();
        if (ListLignes.isEmpty()) {
            Label emptyLabel = new Label("Aucun produit n'est ajouté au panier");
            panier.getChildren().add(emptyLabel);
        } else {
            Label PanierLabel = new Label("Voici votre panier");
            panier.getChildren().add(PanierLabel);

            float prixTotal = 0;
            for (LigneCommande lc : ListLignes) {
                HBox hbox = LigneCommande(lc);
                panier.getChildren().add(hbox);
                prixTotal += lc.getPrix();
            }

            Separator sep = new Separator();
            sep.setOrientation(Orientation.HORIZONTAL);
            panier.getChildren().add(sep);

            panier.getChildren().add(totalView(prixTotal));

        }
    }

    private HBox totalView(Float prix) {
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER);
        Label Total = new Label("Total : ");
        Total.setStyle("-fx-font-size:15pt");
        this.TotalPrix = new Label(prix + "");
        Total.setStyle("-fx-font-size:15pt");

        layout.getChildren().addAll(Total, TotalPrix);
        return layout;
    }

    private HBox LigneCommande(LigneCommande lc) {
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER_LEFT);

        LigneCommandeCRUD LC = new LigneCommandeCRUD();
        Label productName = new Label(LC.recupererLibelle(lc.getIdProduit()));
        productName.setPrefWidth(130);
        productName.setStyle("-fx-font-size:15pt; -fx-padding:5px");

        Label Quantite = new Label(lc.getQuantite() + "");
        Quantite.setStyle("-fx-padding:5px");

        Label Prix = new Label(lc.getPrix() + "");
        Prix.setStyle("-fx-padding:5px");

        Label LabelRouge = new Label("La quantité est egale à 0");
        LabelRouge.setVisible(false);
        LabelRouge.setTextFill(Color.web("#FF5555"));

        Button plusBtn = new Button("+");
        plusBtn.setStyle("-fx-padding:5px");
        plusBtn.setOnAction(e -> {
            LabelRouge.setVisible(false);
            lc.setQuantite(lc.getQuantite() + 1);
            LC.modifierLigneCommande(lc);
            CommandeCRUD c = new CommandeCRUD();
            c.calculPrixCommande(idCommande);
            LC.setLigne(lc);
            Quantite.setText(lc.getQuantite() + "");
            Prix.setText(lc.getPrix() + "");
            this.TotalPrix.setText(c.RecupererPrixCommande(lc.getIdCommande()) + "");
        });

        Button minusBtn = new Button("-");
        minusBtn.setStyle("-fx-padding:5px");
        minusBtn.setOnAction(e -> {
            if (Integer.parseInt(Quantite.getText()) > 0) {
                LabelRouge.setVisible(false);
                lc.setQuantite(lc.getQuantite() - 1);
                LC.modifierLigneCommande(lc);
                CommandeCRUD c = new CommandeCRUD();
                c.calculPrixCommande(idCommande);
                LC.setLigne(lc);
                Quantite.setText(lc.getQuantite() + "");
                Prix.setText(lc.getPrix() + "");
                this.TotalPrix.setText(c.RecupererPrixCommande(lc.getIdCommande()) + "");
            } else {
                LabelRouge.setVisible(true);
            }
        });

        layout.setSpacing(8);
        productName.setPrefWidth(250);
        plusBtn.setPrefWidth(45);
        minusBtn.setPrefWidth(45);
        Quantite.setPrefWidth(50);
        Prix.setPrefWidth(70);
        layout.getChildren().addAll(productName, plusBtn, Quantite, minusBtn, Prix, LabelRouge);

        return layout;

    }

}
