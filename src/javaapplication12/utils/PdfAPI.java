/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12.utils;

import javaapplication12.entities.Produit;

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
import javaapplication12.services.ProduitCRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.mail.MessagingException;

/**
 *
 * @author Mr
 */
public class PdfAPI {

    public static void createAndSendListProduit(String mail) {
        ProduitCRUD p = new ProduitCRUD();
        try {
            Rectangle pageSize = new Rectangle(350, 720);
            Document document = new Document(pageSize);
            String filepath = "produits.pdf";
            PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(filepath));

            document.open();
            Image image1 = Image.getInstance("src/javaapplication12/css/LogoGGC.png");
            double percent = 0.5;
            image1.scaleAbsolute(150, 130);
            document.add(image1);
            document.add(new Paragraph("Voici la liste de tous les produits du shop\n"));
           
            List<Produit> products = p.afficherList();
            for (Produit ligne : products) {
                document.add(new Paragraph(
                        "\n\n* Référence: " + ligne.getReference() +"\n* Libelle: " + ligne.getLibelle() + "\n* Categorie: " + ligne.getCategorie()+ "\n* Description: " + ligne.getDescription()+ "\n* Note: " + ligne.getNote()
                        + "\n* Prix: " + ligne.getPrix() + "DT\n\n"));

            }

            document.close();
            MailAPI.sendMailWithFile(mail, "Produits GGC", new File(filepath));
            //auto open for testing
            //  File myFile = new File("/path/to/file.pdf");
            //Desktop.getDesktop().open(myFile);
        } catch (DocumentException | IOException | MessagingException e) {
            System.err.println(e.getMessage());
        }
    }

}
