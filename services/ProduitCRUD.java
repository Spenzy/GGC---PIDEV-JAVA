/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javaapplication12.entities.Produit;
import javaapplication12.utils.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Mr
 */
public class ProduitCRUD {

    Connection cnxx;

    public ProduitCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterProduit(Produit p) {

        String req = "INSERT INTO produit (reference,libelle,categorie,description,prix,note) VALUES (?,?,?,?,?,?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, p.getReference());
            pst.setString(2, p.getLibelle());
            pst.setString(3, p.getCategorie());
            pst.setString(4, p.getDescription());
            pst.setFloat(5, p.getPrix());
            pst.setInt(6, p.getNote());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerProduit(int reference) {

        String req = "delete from produit where reference=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, reference);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void modifierProduit(Produit p) {

        String req = "update produit set libelle=? , categorie=? ,description=?,prix=? where reference = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(5, p.getReference());
            pst.setString(1, p.getLibelle());
            pst.setString(2, p.getCategorie());
            pst.setString(3, p.getDescription());
            pst.setFloat(4, p.getPrix());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public ObservableList<Produit> afficher() {

        List<Produit> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM produit order by note desc";
            ResultSet rs;
            rs = st.executeQuery(req);
            myList.clear();
            while (rs.next()) {

                Produit p = new Produit();
                p.setReference(rs.getInt(1));
                p.setLibelle(rs.getString(2));
                p.setCategorie(rs.getString(3));
                p.setDescription(rs.getString(4));
                p.setPrix(rs.getFloat(5));
                p.setNote(rs.getInt(6));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }

        ObservableList<Produit> listProduit = FXCollections.observableArrayList();
        for (Produit pr : myList) {
            listProduit.add(pr);
        }
        return listProduit;
    }

    public boolean verifCategorieProduit(String categorie) {
        String req = "SELECT * FROM produit WHERE categorie = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setString(1, categorie);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void remisePrixCategorie(String categorie, float remise) {
        String req = "update produit set prix=prix/100*" + remise + " where categorie = '" + categorie + "'";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    //fonction qui calcule la note de chaque produit a partir des types d'avis et leur nombre
    public void CalculAffectationNote() {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT reference FROM produit";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                int nbrExcellent = calculExcellent(rs.getInt(1));
                int nbrMoyen = calculMoyen(rs.getInt(1));
                int nbrMediocre = calculMediocre(rs.getInt(1));
                int Note = nbrExcellent * 2 + nbrMoyen - nbrMediocre;
                affectationNote(rs.getInt(1), Note);

            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public int calculExcellent(int idProduit) {
        String req = "select produit.reference , count(avis.idAvis) from produit inner join avis on(produit.reference=avis.referenceProduit) where(avis.type like 'excellent' and avis.referenceProduit=?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idProduit);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(2);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public int calculMoyen(int idProduit) {
        String req = "select produit.reference , count(avis.idAvis) from produit inner join avis on(produit.reference=avis.referenceProduit) where(avis.type like 'moyen' and avis.referenceProduit=?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idProduit);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(2);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public int calculMediocre(int idProduit) {
        String req = "select produit.reference , count(avis.idAvis) from produit inner join avis on(produit.reference=avis.referenceProduit) where(avis.type like 'mediocre' and avis.referenceProduit=?)";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idProduit);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(2);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    //affectation de la note d'un produit : appelée dans la fonction calcul note
    public void affectationNote(int idProduit, int note) {
        String req = "update produit set note=? where reference = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, note);
            pst.setInt(2, idProduit);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public boolean VerifUniciteProduit(Produit p1) {
        String req = "SELECT * FROM produit WHERE reference = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, p1.getReference());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

    public boolean VerifCommandeProduit(Produit p1) {
        String req = "SELECT * FROM lignecommande WHERE idProduit = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, p1.getReference());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
