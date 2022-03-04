/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entities.Avis;
import entities.Produit;
import utils.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Mr
 */
public class AvisCRUD {

    Connection cnxx;

    public AvisCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterAvis(Avis a) {

        String req = "INSERT INTO avis (referenceProduit,idClient,description,type) VALUES (?,?,?,?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, a.getReferenceProduit());
            pst.setInt(2, a.getIdClient());
            pst.setString(3, a.getDescription());
            pst.setString(4, a.getType());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerAvis(int idAvis) {

        String req = "delete from avis where idAvis = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idAvis);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerAvisParProduit(int referenceProduit) {

        String req = "delete from avis where referenceProduit=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, referenceProduit);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void modifierAvis(Avis a) {

        String req = "update avis set referenceProduit=? ,description=?, type=? where idAvis = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(4, a.getIdAvis());
            pst.setInt(1, a.getReferenceProduit());
            pst.setString(2, a.getDescription());
            pst.setString(3, a.getType());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public ObservableList<Avis> afficher(int idProduit) {

        List<Avis> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM avis where referenceProduit=" + idProduit;
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Avis a = new Avis();
                a.setIdAvis(rs.getInt(1));
                a.setReferenceProduit(rs.getInt(2));
                a.setIdClient(rs.getInt(3));
                a.setDescription(rs.getString(4));
                a.setType(rs.getString(5));
                myList.add(a);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        ObservableList<Avis> listAvis = FXCollections.observableArrayList();
        for (Avis a : myList) {
            listAvis.add(a);
        }
        return listAvis;
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

    public String recupererLibelle(int idProduit) {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT libelle FROM produit where (reference=" + idProduit + ")";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                return rs.getString((1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return "";
    }

    public int recupererNote(int idProduit) {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT note FROM produit where (reference=" + idProduit + ")";
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
}
