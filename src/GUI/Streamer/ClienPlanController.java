/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Streamer;

import entities.Plan;
import entities.Streamer;
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
import services.PlanCRUD;
import services.StreamerCRUD;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author msi
 */
public class ClienPlanController implements Initializable {

    @FXML
    private TableView<Plan> tvFP;
    @FXML
    private TableColumn<Plan, String> tvFPIdS;
    @FXML
    private TableColumn<Plan, Date> tvFPDate;
    @FXML
    private TableColumn<Plan, Date> tvFPHeure;
    @FXML
    private TableColumn<Plan, String> tvFPDesc;
    @FXML
    private TableColumn<Plan, Integer> tvFPIdE;
    @FXML
    private Button recupererFP;
    @FXML
    private TableColumn<Plan, Float> tvFPDuree;
    @FXML
    private ImageView QRCode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        showPlans();
    }    

    @FXML
    private void recupererOnF(ActionEvent event) throws WriterException {
        
        
        
        Plan l1 = tvFP.getSelectionModel().getSelectedItem();
        if (l1 == null) {
            //veuillez selectionner une liiiiiiiiiiiiiiiigne
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur !");
            alert1.setHeaderText(null);
            alert1.setContentText("veuillez selectionner une ligne du tableau puis appuyez sur le bouton recuperer");
            alert1.show();

        } else {
        
        start(l1);
        
        
        }
        
    }
    
    public void showPlans() {

        PlanCRUD ssc = new PlanCRUD();
        Streamer ss = new Streamer();
        StreamerCRUD s= new StreamerCRUD();
        
        

        
        

        
        ObservableList<Plan> list = ssc.getPlanList();
        
        
        
        
        
        
        tvFPIdS.setCellValueFactory(new PropertyValueFactory<Plan, String>("idStreamer"));
        tvFPDate.setCellValueFactory(new PropertyValueFactory<Plan, Date>("date"));
        tvFPHeure.setCellValueFactory(new PropertyValueFactory<Plan, Date>("heure"));
        tvFPDuree.setCellValueFactory(new PropertyValueFactory<Plan, Float>("duree"));
        tvFPDesc.setCellValueFactory(new PropertyValueFactory<Plan, String>("description"));
        tvFPIdE.setCellValueFactory(new PropertyValueFactory<Plan, Integer>("idEvenement"));
        
        tvFP.setItems(list);

    }
    
    
    public void start(Plan p) {
        
        QRCodeWriter QRCodeWriter;
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String myWeb = p.getDescription();
        int width = 300;
        int height = 300;
        String fileType = "png";
        
        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }


System.out.println("Success...");
            
        } catch (WriterException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ImageView qrView = new ImageView();
        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                
        QRCode.setImage(qrView.getImage());
        
        //StackPane root = new StackPane();
        /*root.getChildren().add(qrView);
        
        Scene scene = new Scene(root, 350, 350);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }
    
    
    
   /* private static final String outputQr= "C:\";
    
    private static void generateQrCode(Plan p,int w,int h, String FilePath) throws WriterException
    {
        QRCodeWriter qc= new QRCodeWriter();
        BitMatrix bm = qc.encode(p.getDescription(), BarcodeFormat.QR_CODE, w, h);
        
        Path pobj =FileSystems.getDefault().getPath(FilePath);
        try {
            MatrixToImageWriter.writeToPath(bm, "PNG", pobj);
        } catch (IOException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    
    
    
    
    
    
    

   
    
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

