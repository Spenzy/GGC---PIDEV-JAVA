/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiPidev.GUI;

import Entities.Evenement;
import Entities.Participation;
import Services.EvenementCrud;
import Services.ParticipationCrud;
import Services.pdf;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;


/**
 * FXML Controller class
 *
 * @author Azer Lahmer
 */
public class AffichEventController implements Initializable {
@FXML
    private TableView<Evenement> evenementsTable;
    private TableColumn<Evenement,String > refCol;
    private TableColumn<Evenement,String> dateDebutCol;
    private TableColumn<Evenement,String> dateFinCol;
    private TableColumn<Evenement,String> localisationCol;
    private TableColumn<Evenement,String> descriptionCol;
    private TableColumn<Evenement,String> nbrParticipantsCol;
    private TableColumn<Evenement,String> imageCol;
    private TableColumn<Evenement,Button> participerCol;
    EvenementCrud ec = new EvenementCrud();
    private Button participe;
    int idClient = 2;
    int idEvent=2;
    ParticipationCrud pc = new ParticipationCrud();
    @FXML
    private Button pdfID;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         initCols();
        loadEvenements();
             
        
        // TODO
    } 
    private void handleButtonAction(ActionEvent event){
        if (event.getSource()==participe){
            Participation p = new Participation(idClient ,idEvent, 4);
            pc.ajouterParticipation(p);
            
        }
    }
     private void initCols() {
      refCol = new TableColumn<>("Reference");//nom de l'afichage
      refCol.setCellValueFactory(new PropertyValueFactory<>("reference"));
      
      dateDebutCol = new TableColumn<>("Date Debut");//nom de l'afichage
      dateDebutCol.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
      
      dateFinCol = new TableColumn<>("Date Fin");//nom de l'afichage
      dateFinCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
      
      localisationCol = new TableColumn<>("Localisation");//nom de l'afichage
      localisationCol.setCellValueFactory(new PropertyValueFactory<>("localisation"));
      
      descriptionCol = new TableColumn<>("Description");//nom de l'afichage
      descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
      
      nbrParticipantsCol = new TableColumn<>("Nbres Participants");//nom de l'afichage
      nbrParticipantsCol.setCellValueFactory(new PropertyValueFactory<>("nbrParticipant"));
      imageCol = new TableColumn<>("image");//nom de l'afichage
      
      imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));
      /*
       Callback<TableColumn<Evenement,String>,TableCell<Evenement,String>> cellFactory=(param)->{
final TableCell<Evenement,String> cell=new TableCell<Evenement,String>(){


}
}
   */   
      
      
      
      
      evenementsTable.getColumns().addAll(refCol, dateDebutCol, dateFinCol, localisationCol, descriptionCol, nbrParticipantsCol,imageCol);
    addButtonToTable();
     }
   
   
          public void loadEvenements() {
      
      List<Evenement> evenements = ec.afficherEvenements();
      evenementsTable.getItems().clear();
      //afficher par objet dans une boucle   
      for (Evenement e : evenements) {
       evenementsTable.getItems().add(e);   
      }
      
      
    }
          private void addButtonToTable() {
        TableColumn<Evenement, Void> participer = new TableColumn("Participation");

        Callback<TableColumn<Evenement, Void>, TableCell<Evenement, Void>> cellFactory = new Callback<TableColumn<Evenement, Void>, TableCell<Evenement, Void>>() {
            @Override
            public TableCell<Evenement, Void> call(final TableColumn<Evenement, Void> param) {
                final TableCell<Evenement, Void> cell = new TableCell<Evenement, Void>() {

                    private final Button btn = new Button("Participer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            Evenement data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                            Participation p = new Participation(idClient ,data.getReference(), 4);
                            pc.ajouterParticipation(p);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        participer.setCellFactory(cellFactory);

        evenementsTable.getColumns().add(participer);

    }

    @FXML
    private void pdf1(ActionEvent event)throws FileNotFoundException, SQLException, DocumentException {
       // int i=1;
        //for(i=1;i<10;i++){
        int max=100;
        int min=1;
        int b = (int)(Math.random()*(max-min+1)+min);
        pdf Pdf=new pdf();
        Pdf.add("event("+b+").pdf");
       // i=i+1;
        // }
        
    }
}