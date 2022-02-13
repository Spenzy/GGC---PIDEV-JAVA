/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sprint1.pidev.entities.Commande;
import sprint1.pidev.entities.Livraison;
import sprint1.pidev.utils.MyConnection;

/**
 *
 * @author Marwa
 */
public class LivraisonCRUD {

    Connection cnxx;

    public LivraisonCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterLivraison(Livraison l) {

        String req = "INSERT INTO livraison (idLivreur,idCommande,DateHeure) VALUES (?,?,?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, l.getIdLivreur());
            pst.setInt(2, l.getIdCommande());
            pst.setDate(3, l.getDateHeure());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerLivraison(int idCommande) {

        String req = "delete from livraison where (idCommande=? )";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommande);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void modifierLivraison(Livraison l) {

        String req = "update livraison set idLivreur=? , DateHeure=? where idCommande = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, l.getIdLivreur());
            pst.setDate(2, l.getDateHeure());
            pst.setInt(3, l.getIdCommande());

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public List<Livraison> afficher() {

        List<Livraison> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM livraison";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Livraison l = new Livraison();
                l.setIdLivreur(rs.getInt(1));
                l.setIdCommande(rs.getInt(2));
                l.setDateHeure(rs.getDate(3));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
    }

    public boolean VerifCommande(int idCommande) {
        boolean testCommande = false;
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT idCommande FROM livraison";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getInt(1) == idCommande) {
                    testCommande = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return testCommande;
    }

    public boolean VerifLivreur(int idLivreur) {
        boolean testLivreur = false;
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT idLivreur FROM livreur";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getInt(1) == idLivreur) {
                    testLivreur = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return testLivreur;
    }

   //fonction remise si la livraison est en retard
    public void RemiseLivraison(){
        Date sysdate = null;
                
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT idCommande,DateHeure FROM livraison";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getDate(2) == sysdate) {
                    //remiseLivraison();
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
    }
    
    /*public void livraisonRetard(Livraison l, LocalDateTime dateAjout){
        final Runnable remiseLivraison = new Runnable() { //ScheduledExecutorService nécessite un objet runnable pour fonctionner ou on fait appel a notre methode archiver
            public void run() {
                remiseLivraison(l);
                System.out.println(l);//pour tester le chrono
            }
        };
        long delai = ChronoUnit.MILLIS.between(dateAjout, dateAjout.plusDays(1)); //ici on fait calculer le delai d'un jour depuis la date de livraison
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //ici on initialise un nouveau thread de ScheduledExecutorService
        scheduler.schedule(remiseLivraison , delai, TimeUnit.MILLISECONDS); //ici on programme le chrono du methode
    }
    
    public void remiseLivraison(Livraison l){
        
    }*/
}
