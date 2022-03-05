/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import entities.Produit;
import entities.Publication;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import services.CommentaireCRUD;
import services.ProduitCRUD;
import services.PublicationCRUD;
import services.VoteCRUD;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class StatistiqueForumController implements Initializable {
    
    VoteCRUD vc = new VoteCRUD();
    CommentaireCRUD cc = new CommentaireCRUD();

    private ObservableList<String> publications = FXCollections.observableArrayList();
    @FXML
    private BarChart<String, Integer> barchartForum;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // add observable list products
        PublicationCRUD pc = new PublicationCRUD();
        List<Publication> pub = pc.afficherPublication();
        for (Publication p : pub)
        {
            publications.add(p.getTitre());
        }
        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(publications);
        xAxis.setAutoRanging(true);

        XYChart.Series<String, Integer> seriesVote = new XYChart.Series<>();
        XYChart.Series<String, Integer> seriesComm = new XYChart.Series<>();
        
        
        int nbrPub=pub.size();
        int[] Vote = new int[nbrPub];
        int[] Comm = new int[nbrPub];
        int j = 0;
        for (Publication p : pub) {
            Vote[j] = vc.calculNbrVote(p.getId_publication());
            Comm[j] = cc.calculNbrCommentaire(p.getId_publication());
            j++;
        }
        for (int i = 0; i < Vote.length; i++) {
            seriesVote.getData().add(new XYChart.Data<>(publications.get(i), Vote[i]));
            seriesComm.getData().add(new XYChart.Data<>(publications.get(i), Comm[i]));
        }

        barchartForum.getData().add(seriesVote);
        barchartForum.getData().add(seriesComm);
        barchartForum.setTitle("Statistiques ");
        xAxis.setLabel("Publications");
        yAxis.setLabel("Votes/Commentaires");
    }
    
}
