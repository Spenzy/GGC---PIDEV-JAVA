/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Commande;
import entities.Commentaire;
import entities.LigneCommande;
import entities.Produit;
import entities.Publication;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import services.CommandeCRUD;
import services.LivraisonCRUD;
import services.PersonneCRUD;
import services.ProduitCRUD;
import services.VoteCRUD;

/**
 *
 * @author Spenz
 */
public class PdfAPI {

    public static void createAndSendForumPost(String mail, Publication p, List<Commentaire> commentaires) {

        try {
            Rectangle pageSize = new Rectangle(350, 720);
            Document document = new Document(pageSize);
            String filepath = "publication" + p.getId_publication()+".pdf";
            PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(filepath));

            document.open();
            Image image1 = Image.getInstance("src/GUI/ressources/ggc.png");
            image1.scaleAbsolute(150, 130);
            
            VoteCRUD vc = new VoteCRUD();
            PersonneCRUD pcrud = new PersonneCRUD();
            Paragraph chapterTitle = new Paragraph("Publication de " + pcrud.getUsername(p.getId_client()) + " à la date " + p.getDatePub());
            Paragraph paragraph = new Paragraph("Publication: \n"
                    + "Titre : " + p.getTitre() + "\n"
                    + "Description : " + p.getDesc() + "\n"
                    + "NbrVotes : " + vc.calculNbrVote(p.getId_publication()) + "\n \n");
            Paragraph paragraphsignature = new Paragraph("Gamer Geeks Community APP");
            Chapter chapter1 = new Chapter(chapterTitle, 1);
            chapter1.setNumberDepth(0);

            document.add(chapter1);
            document.add(paragraph);

            for (Commentaire c : commentaires) {
                document.add(new Paragraph(
                        "* " + pcrud.getUsername(c.getIdClient()) + " " + c.getDatePost() + ": \n"
                        + "Commentaire : " + p.getDesc() + "\n"));

            }
            document.add(paragraphsignature);

            document.close();
            File fileToSend = new File(filepath);
            MailAPI.sendMailWithFile(mail, "Poste Forum GGC", fileToSend);
            fileToSend.delete();
        } catch (DocumentException | IOException | MessagingException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("generation success");
    }
    
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
    
     public static void createAndSendCommande(String mail, Commande p) {

        try {
            Rectangle pageSize = new Rectangle(350, 720);
            Document document = new Document(pageSize);
            String filepath = "commande" + p.getIdCommande()+".pdf";
            PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(filepath));

            document.open();
            Image image1 = Image.getInstance("src/GUI/ressources/ggc.png");
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
