/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Shop;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import entities.Produit;
import services.ProduitCRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class StatistiqueProduitController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    private ObservableList<String> ProductsNames = FXCollections.observableArrayList();
    @FXML
    private NumberAxis yAxis;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // add observable list products
        ProduitCRUD p = new ProduitCRUD();
        ProductsNames.addAll(p.recupererLibelle());
        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(ProductsNames);
        xAxis.setAutoRanging(true);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        

        List<Produit> Products = p.afficherList();
        int nbrProducts=Products.size();
        int[] Note = new int[nbrProducts];
        int j = 0;
        for (Produit p1 : Products) {
            Note[j] = p1.getNote();j++;
        }
        for (int i = 0; i < Note.length; i++) {
            series.getData().add(new XYChart.Data<>(ProductsNames.get(i), Note[i]));
        }

        barChart.getData().add(series);
        barChart.setTitle("Statistique des notes pour chaque produit");
        xAxis.setLabel("Libelle");
        yAxis.setLabel("Note");
    }

}
