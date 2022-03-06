package main;

import GUI.DataValidation;
import entities.Commentaire;
import entities.Publication;
import entities.Vote;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import services.CommentaireCRUD;
import services.PublicationCRUD;
import services.VoteCRUD;

import org.apache.log4j.BasicConfigurator;
import services.Authentification;
import utils.MailAPI;
import utils.PdfAPI;

public class MainClass {
    public static void main(String[] args) {
        
        Authentification aa = new Authentification();
        System.out.println(aa.hashagePWD("pwd"));
    
    }
    
}
