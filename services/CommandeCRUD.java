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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

        String req = "INSERT INTO commande (idCommande,idClient,adresse,prix,livree,DateCommande) VALUES (?,?,?,?,?,?)";
        PreparedStatement pst;
        try {

            pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, c.getIdCommande());
            pst.setInt(2, c.getIdClient());
            pst.setString(3, c.getAdresse());
            pst.setFloat(4, c.getPrix());
            pst.setBoolean(5, c.isLivree());
            pst.setDate(6, c.getDateCommande());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            long pk = rs.getLong(1);
            c.setIdCommande((int) pk);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerCommande(int idCommande) {
        LigneCommandeCRUD LC = new LigneCommandeCRUD();
        LC.supprimerLignesCommande(idCommande);
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

        String req = "update commande set idClient=? , adresse=? , prix=? , livree=? , DateCommande=? where idCommande = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, c.getIdClient());
            pst.setString(2, c.getAdresse());
            pst.setFloat(3, c.getPrix());
            pst.setBoolean(4, c.isLivree());
            pst.setDate(5, c.getDateCommande());
            pst.setInt(6, c.getIdCommande());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public ObservableList<Commande> afficher() {

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
                p.setAdresse(rs.getString(3));
                p.setPrix(rs.getFloat(4));
                p.setLivree(rs.getBoolean(5));
                p.setDateCommande(rs.getDate(6));

                LigneCommandeCRUD LC = new LigneCommandeCRUD();
                p.setLignes(LC.afficher(rs.getInt(1)));

                myList.add(p);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        ObservableList<Commande> list = FXCollections.observableArrayList();
        for (Commande p : myList) {
            list.add(p);
        }

        return list;
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

    //métier calcul du prix automatique à partir de la table commande(idProduit,quantite) et de la table produit(idProduit,prix)
    //si le total dépasse 100 dinars il ny a pas de frais de livraison
    public void calculPrixCommande(int idCommande) {
        String req = "update commande set prix=(select sum(prix) from LigneCommande where idCommande=?) where idCommande = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommande);
            pst.setInt(2, idCommande);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void commandeLivree(int idCommande) {
        String req = "update commande set livree=1 where idCommande = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommande);

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void commandeNonLivree(int idCommande) {
        String req = "update commande set livree=0 where idCommande = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommande);

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public ObservableList<String> affecterProduit() {
        ObservableList<String> listProduit = FXCollections.observableArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT libelle FROM produit";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                listProduit.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return listProduit;
    }

    public int recupererReference(String libelle) {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT reference FROM produit where (libelle like '" + libelle + "')";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                return rs.getInt((1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return 0;
    }

    public float RecupererPrixCommande(int idCommande) {
        try {
            Statement st = cnxx.createStatement();
            String req = "select sum(prix) from LigneCommande where (idCommande=" + idCommande + ")";
            ResultSet rs;
            rs = st.executeQuery(req);
            if (rs.next()) {
                return rs.getFloat((1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return 0;
    }

    public List<Commande> afficherList() {
        ArrayList myList= new ArrayList();
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM commande";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Commande p = new Commande();
                p.setIdCommande(rs.getInt(1));
                p.setIdClient(rs.getInt(2));
                p.setAdresse(rs.getString(3));
                p.setPrix(rs.getFloat(4));
                p.setLivree(rs.getBoolean(5));
                p.setDateCommande(rs.getDate(6));

               

                myList.add(p);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
    }

    public String recupererLibelle(int idProduit) {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT libelle FROM produit where (reference=" + idProduit + ")";
            ResultSet rs;
            rs = st.executeQuery(req);
            if (rs.next()) {
                return rs.getString((1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return "";
    }    

}
