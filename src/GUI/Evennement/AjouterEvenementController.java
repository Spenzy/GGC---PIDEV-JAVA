/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Evennement;

/*import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Entities.Evenement;
import Services.EvenementCrud;
import java.awt.Image;*/
import entities.Evenement;
import services.EvenementCrud;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.controlsfx.control.textfield.TextFields;
import java.util.Date;

/**
 * FXML Controller class
 *
 * @author Azer Lahmer
 */
public class AjouterEvenementController implements Initializable {

    @FXML
    private TextField tfReference;
    private TextField tfHeureDebut;
    @FXML
    private TextField tfLocalisation;
    @FXML
    private TextField tfNbrParticipants;
    private TextField tfHeureFin;
    @FXML
    private TextField tfDescription;
    @FXML
    private Button btnAjouter;
    @FXML
    private DatePicker tfDateDebut;
    @FXML
    private DatePicker tfDateFin;

    /**
     * Initializes the controller class.
     */
    Evenement e = new Evenement();

    EvenementCrud ec = new EvenementCrud();
    String[] words = {"manzah1",
        "manzah2", "manzah3", "manzah4", "manzah5",
        "manzah6", "manzah7", "manzah8", "manzah9",
        "nasr", "tunis", "monastir", "mahdia",
        "sousse", "sfax", "mednine", " selyenna",
        "beja", "benzart", "gammarth", "marssa",
        "tabarka", "aindrahem", "jandouba"
    };
    @FXML
    private TextField tfTitre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //String[] possibleWords = {"hai","hello"};
        TextFields.bindAutoCompletion(tfLocalisation, words);

    }

    @FXML
    private void ajouterEvenement(ActionEvent event) {

        //// verif champ vide
        if (tfReference.getText().isEmpty() || tfLocalisation.getText().isEmpty() || tfDescription.getText().isEmpty() || tfNbrParticipants.getText().isEmpty() || tfTitre.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText(" Champ vide!");
            alert.show();
        } else /// Verifier nbrParticipants
        if (Integer.parseInt(tfNbrParticipants.getText()) < 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText(" nombre de participants doit etre superieur à 1");
            alert.show();

            ///Verifier reference
        } else if (ec.VerifEvenement(Integer.parseInt(tfReference.getText()))) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("La reference de cet evenement Existe ");
            alert.show();
        } else if (tfDateDebut.getValue().isBefore(LocalDate.now()) || tfDateFin.getValue().isBefore(tfDateDebut.getValue())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText("Date deb et fin erronée");
            alert.show();

        } else {

            //Récuperer les données saisies dans le formulaire 
            int ref = Integer.parseInt(tfReference.getText());
            String local = tfLocalisation.getText();
            String desc = tfDescription.getText();
            int nbrPart = Integer.parseInt(tfNbrParticipants.getText());
            String Titre = tfTitre.getText();
            //img = img.replace("//", "///");

            Evenement e = new Evenement(ref, java.sql.Date.valueOf(tfDateDebut.getValue()), java.sql.Date.valueOf(tfDateFin.getValue()), local, desc, nbrPart, Titre);
            EvenementCrud ec = new EvenementCrud();
            ec.ajouterEvenement(e);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succesful");
            alert.setHeaderText(null);
            alert.setContentText(" Evenement ajoutée avec succéez!");
            alert.show();
            ((Stage) btnAjouter.getScene().getWindow()).close();
        }

    }
    /*
    @FXML
    private void uploadPic(MouseEvent event) {

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filname = f.getAbsolutePath();
        tfImage.setText(filname);
        Image getAbsolutePath = null;
        ImageIcon icon = new ImageIcon(filname);
        Image image = icon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        tfload.setIcon(icon);

    }
     */

 /*
    @FXML
    private void uploadPic(MouseEvent event) {
       
          JFileChooser fileChooser = new JFileChooser();

            fileChooser.setCurrentDirectory(new File(".")); //sets current directory

            int response = fileChooser.showOpenDialog(null); //select file to open
            //int response = fileChooser.showSaveDialog(null); //select file to save

            if(response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                                String imagepath= file.getAbsolutePath();
                                tfImage.setText(imagepath);
                                ImageIcon icon = new ImageIcon(imagepath);
            Image image = icon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
            //tfload.setIcon(icon);
                        }
    
    }*/
 /*private void uploadPic(MouseEvent event) throws IOException {
      //creating the image object
      InputStream stream = new FileInputStream("D:\\images\\elephant.jpg");
      Image image = new Image(stream);
      //Creating the image view
      ImageView imageView = new ImageView();
      //Setting image to the image view
      imageView.setImage(image);
      //Setting the image view parameters
      imageView.setX(10);
      imageView.setY(10);
      imageView.setFitWidth(575);
      imageView.setPreserveRatio(true);
      //Setting the Scene object
      Group root = new Group(imageView);
      Scene scene = new Scene(root, 595, 370);
      stage.setTitle("Displaying Image");
      stage.setScene(scene);
      stage.show();
   }*/
}
