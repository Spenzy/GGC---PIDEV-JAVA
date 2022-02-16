/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sprint1.pidev.entities.Commande;
import sprint1.pidev.utils.MyConnection;

/**
 *
 * @author Marwa
 */
public class CommandeCRUD {

    Connection cnxx;

    public CommandeCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterCommande(Commande c) {

        String req = "INSERT INTO commande (idClient,idProduit,quantite,adresse,prix,livree) VALUES (?,?,?,?,?,?)";
        PreparedStatement pst;
        try {

            pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, c.getIdClient());
            pst.setInt(2, c.getIdProduit());
            pst.setInt(3, c.getQuantite());
            pst.setString(4, c.getAdresse());
            pst.setFloat(5, c.getPrix());
            pst.setBoolean(6, c.isLivree());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            long pk = rs.getLong(1);
            c.setIdCommande((int) pk);

            calculPrixCommande(c);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerCommande(int idCommande) {

        String req = "delete from commande where idCommande=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommande);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void modifierCommande(Commande c) {

        String req = "update commande set idClient=? , idProduit=? ,quantite=?,adresse=?,prix=?,livree=? where idCommande = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, c.getIdClient());
            pst.setInt(2, c.getIdProduit());
            pst.setInt(3, c.getQuantite());
            pst.setString(4, c.getAdresse());
            pst.setFloat(5, c.getPrix());
            pst.setBoolean(6, c.isLivree());
            pst.setInt(7, c.getIdCommande());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        calculPrixCommande(c);

    }

    public List<Commande> afficher() {

        List<Commande> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM commande";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Commande p = new Commande();
                p.setIdCommande(rs.getInt(1));
                p.setIdClient(rs.getInt(2));
                p.setIdProduit(rs.getInt(3));
                p.setQuantite(rs.getInt(4));
                p.setAdresse(rs.getString(5));
                p.setPrix(rs.getFloat(6));
                p.setLivree(rs.getBoolean(7));
                myList.add(p);
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
            String req = "SELECT idCommande FROM commande";
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

    public boolean VerifClient(int idClient) {
        boolean testClient = false;
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT idClient FROM client";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getInt(1) == idClient) {
                    testClient = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return testClient;
    }

    public boolean VerifProduit(int idProduit) {
        boolean testProduit = false;
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT reference FROM produit";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getInt(1) == idProduit) {
                    testProduit = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return testProduit;
    }

    //métier calcul du prix automatique à partir de la table commande(idProduit,quantite) et de la table produit(idProduit,prix)
    //si le total dépasse 100 dinars il ny a pas de frais de livraison
    public void calculPrixCommande(Commande c) {
        String req = "update commande set prix=(select prix from produit where reference=?)*quantite where idCommande = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setFloat(1, c.getIdProduit());
            pst.setInt(2, c.getIdCommande());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void commandeLivree(Commande c) {
        c.setLivree(true);
        modifierCommande(c);
        System.out.println("Commande " + c.getIdCommande() + " livrée");
    }
}
