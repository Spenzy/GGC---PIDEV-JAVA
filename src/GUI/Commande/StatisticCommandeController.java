/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Commande;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import entities.Commande;
import services.CommandeCRUD;

/**
 * FXML Controller class
 *
 * @author Mr
 */
public class StatisticCommandeController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(Locale.FRENCH).getMonths();
        System.out.println(months);
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
        xAxis.setAutoRanging(true);
        CommandeCRUD c = new CommandeCRUD();
        setCommandeData(c.afficherList());

    }

    public void setCommandeData(List<Commande> Commandes) {
        // Count the number of people having their birthday in a specific month.
        int[] monthCounter = new int[12];
        for (Commande p : Commandes) {
            if(p.getDateCommande().toLocalDate().getYear()==LocalDate.now().getYear()){
            int month = (p.getDateCommande().toLocalDate().getMonth().getValue()) - 1;
            monthCounter[month]++;
            }
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
        barChart.setTitle("Statistique des commandes par mois");
        
    }

}
