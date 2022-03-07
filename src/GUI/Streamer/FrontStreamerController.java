/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Streamer;

import static GUI.DashboardController.refreshParent;
import GUI.homePage;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.StreamerCRUD;
import services.PersonneCRUD;
import entities.Streamer;
import entities.Personne;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import entities.Streamer;
import java.sql.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import services.StreamerCRUD;
import entities.Streamer;
import entities.Personne;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import services.PersonneCRUD;
import utils.MailAPI;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class FrontStreamerController implements Initializable {

    @FXML
    private TableColumn<Streamer, String> tvfBNomS;
    @FXML
    private TableColumn<Streamer, String> tvfBPrenS;
    @FXML
    private TableColumn<Streamer, Date> tvfBDateS;
    @FXML
    private TableColumn<Streamer, String> tvfBEmailS;
    @FXML
    private TableColumn<Streamer, Integer> tvfBNumS;
    @FXML
    private TableColumn<Streamer, String> tvfBInfoS;
    @FXML
    private TableColumn<Streamer, String> tvfBLS;
    @FXML
    private TableView<Streamer> tvfBS;
    @FXML
    private Button btnPlans;
    @FXML
    private Button btnMail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        fshowStreamers();
        
        btnMail.setOnAction(a -> {
            PersonneCRUD pcrud = new PersonneCRUD();
            Streamer s1 = tvfBS.getSelectionModel().getSelectedItem();
            Personne p1 = pcrud.afficherPersonne(s1.getIdStreamer());
            System.out.println(p1);
            String body = "Le Streamer"+p1.getNom()+" "+p1.getPrenom()+": \n"
                    + " * Email streamer : "+ s1.getEmail() + "\n "
                    + " * Infos streamer : "+ s1.getInformations()+ "\n "
                    + " * Lien du streamer : " + s1.getLienStreaming();
            try {
                MailAPI.sendMail(pcrud.afficherPersonne(homePage.loggedInID).getEmail(),"Infos Streamer" ,body);
            } catch (MessagingException ex) {
                System.err.println(ex.getMessage());
            }
        });
        
        btnPlans.setOnAction(a -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("ClienPlan.fxml"));
                Scene scene = new Scene(root);
                Stage stg = new Stage();
                stg.setScene(scene);
                stg.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    @FXML
    private void BackHome(ActionEvent event) {
    }

    public void fshowStreamers() {

        StreamerCRUD ssc = new StreamerCRUD();

        ObservableList<Streamer> list = ssc.getStreamerList();
        //tvIdS.setCellValueFactory(new PropertyValueFactory<Streamer, Integer>("idStreamer"));
        tvfBNomS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("nom"));
        tvfBPrenS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("prenom"));
        tvfBDateS.setCellValueFactory(new PropertyValueFactory<Streamer, Date>("dateNaissance"));
        tvfBEmailS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("email"));
        //tvBPwS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("password"));
        tvfBNumS.setCellValueFactory(new PropertyValueFactory<Streamer, Integer>("telephone"));
        tvfBInfoS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("informations"));
        tvfBLS.setCellValueFactory(new PropertyValueFactory<Streamer, String>("lienStreaming"));

        tvfBS.setItems(list);
    }
}
