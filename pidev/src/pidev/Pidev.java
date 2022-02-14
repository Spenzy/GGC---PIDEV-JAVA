/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import entities.Client;
import entities.Moderateur;
import entities.Personne;

import java.text.DateFormat;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.Authentification;
import services.PersonneCRUD;
import services.ClientCRUD;
import services.ModerateurCRUD;

import utils.MyConnection;

/**
 *
 * @author Dell
 */
public class Pidev {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyConnection mc = MyConnection.getInstance();
        PersonneCRUD ps = new PersonneCRUD();
        Authentification aa = new Authentification();
        ClientCRUD cc = new ClientCRUD();
        ModerateurCRUD moc = new ModerateurCRUD();
        Client c = new Client();
        Moderateur m = new Moderateur(); // TextField tfpwd = new tpwd; hashagePWD(tfpwd.getValue())
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            java.sql.Date date = new java.sql.Date(sdformat.parse("2020-01-25").getTime());
            Personne p = new Personne(16, "amir", "ben salah", date, "amir.bensalah@esprit.tn", 54405584, aa.hashagePWD("amirzzzz"));
            Personne p2 = new Personne(18, "oussema", "jebri", date, "oos.bensalah@esprit.tn", 6547855, "zieddfsdfsd");
            Personne p1 = new Personne(16, "nawres", "sarra", date, "azer.lahmer@esprit.tn", 987456321, "azerazeazeaze");
            Client c3 = new Client(16, "nawres", "sarra", date, "azer.lahmer@esprit.tn", 987456321, "azerazeazeaze");
            Client c1 = new Client(15, "farouk", "boosaid", date, "farook.boosaid@esprit.tn", 28608927, aa.hashagePWD("amirzzzz"));
            Client c2 = new Client(22, "nawres", "elair", date, "nawres.jebri@esprit.tn", 54405584, aa.hashagePWD("azerty"));
            Moderateur m1 = new Moderateur(50, "farouk", "boosaid", date, "farook.boosaid@esprit.tn", 28608927, aa.hashagePWD("amirzzzz"));

////////////////////////////////////////// Ajouter Personne //////////////////////////////////////////   
            //ps.ajouterPersonne(p);
            //  ps.ajouterPersonne(p);
            // ps.ajouterPersonne(p2);
            /////////////////////////////////// modifier Personne silon id personne /////////////////////////////
            /*
         p.setId_personne(36);
         p.setPrenom("azer");
         ps.modifierPersonne(p);
             */
            //////////////////////// Suprimer personne silon leur id ///////////////////
            
            
            // ps.supprimerPersonne(37);
            
            
            //////////////////////////////Afficher liste de personne //////////
            
            
            //System.out.println(ps.afficher());
            //System.out.println(ps.afficherParid(32));
            
            
///////////////////////////////////////////Ajouter Client//////////////////////////////////////////
            //cc.ajouterClient(c1);
            //cc.ajouterClient(c2);
            //cc.ajouterClient(c3);
            ////////////////////////Modifier Client silon leur id  ///////////////////////////
            // c = cc.afficherClientid(41);
            // c.setBan(2);
            // cc.modifierClient(c);
//////////////////////////// supprimer client silon leur id ///////////////////////////////////
//cc.supprimerClient(40);
            /////////////////////////////////afficher Client ////////////////////
            //System.out.println(cc.afficherCl());
            ////////////////////Ajouter Moderateur///////////////////////////
            // moc.ajouterModerateur(m);
            //moc.ajouterModerateur(m1);
            ////////////////////////////Modfier Moderateur////////////
            /*
        m = moc.afficherModerateur(32);
        m.setNom("zied");
        moc.modifierModerateur(m);
             */
            /////////////////////////Suprimer Moderateur/////////////////
            
           
        } catch (ParseException ex) {
            Logger.getLogger(Pidev.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
