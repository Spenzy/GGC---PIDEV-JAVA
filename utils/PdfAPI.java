/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.utils;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.mail.MessagingException;
import sprint1.pidev.entities.Commande;
import sprint1.pidev.entities.LigneCommande;
import sprint1.pidev.gui.StatisticCommandeController;
import sprint1.pidev.services.CommandeCRUD;
import sprint1.pidev.services.LivraisonCRUD;

/**
 *
 * @author Mr
 */
public class PdfAPI {

    public static void createAndSendCommande(String mail, Commande p) {

        try {
            Rectangle pageSize = new Rectangle(350, 720);
            Document document = new Document(pageSize);
            String filepath = "commande" + p.getIdCommande()+".pdf";
            PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(filepath));

            document.open();
            Image image1 = Image.getInstance("src/sprint1/pidev/img/LogoGGC.png");
            double percent = 0.5;
            image1.scaleAbsolute(150, 130);
            document.add(image1);
            
            CommandeCRUD c = new CommandeCRUD();
            LivraisonCRUD l = new LivraisonCRUD();
            Paragraph chapterTitle = new Paragraph("Commande de " + p.getIdClient() + " à la date " + p.getDateCommande());
            Paragraph paragraph = new Paragraph("Commande: \n"
                    + "Id : " + p.getIdCommande()+ "\n"
                    + "Adresse : " + p.getAdresse()+ "\n"
                    + "Date : " + p.getDateCommande()+ "\n"
                    + "Etat : Pas encore livrée \n"
                    + "Prix Total : " + p.getPrix()+ "DT\n \n");
            
            Paragraph paragraphsignature = new Paragraph("Gamer Geeks Community APP");
           // Chapter chapter1 = new Chapter(chapterTitle, 1);
            //chapter1.setNumberDepth(0);

            document.add(chapterTitle);
            document.add(paragraph);

            List<LigneCommande> lignes=p.getLignes();
            for (LigneCommande ligne : lignes) {
                document.add(new Paragraph(
                        "\n* Produit: " + c.recupererLibelle(ligne.getIdProduit()) + "\n* Quantite: " + ligne.getQuantite() 
                        + "\n* Prix: " + ligne.getPrix() + "DT\n"));

            }
            document.add(paragraphsignature);

            document.close();
            MailAPI.sendMailWithFile(mail, "Commande GGC", new File(filepath));
            //auto open for testing
          //  File myFile = new File("/path/to/file.pdf");
           //Desktop.getDesktop().open(myFile);
        } catch (DocumentException | IOException | MessagingException e) {
            System.err.println(e.getMessage());
        }
    }

}
