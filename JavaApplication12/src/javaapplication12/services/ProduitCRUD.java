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

        String req = "INSERT INTO produit (reference,nom,categorie,fiche_technique,prix) VALUES (?,?,?,?,?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, p.getReference());
            pst.setString(2, p.getNom());
            pst.setString(3, p.getCategorie());
            pst.setString(4, p.getFiche_technique());
            pst.setFloat(5, p.getPrix());
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

        String req = "update produit set nom=? , categorie=? ,fiche_technique=?,prix=? where reference = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(5, p.getReference());
            pst.setString(1, p.getNom());
            pst.setString(2, p.getCategorie());
            pst.setString(3, p.getFiche_technique());
            pst.setFloat(4, p.getPrix());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public List<Produit> afficher() {

        List<Produit> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM produit";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                Produit p = new Produit();
                p.setReference(rs.getInt(1));
                p.setNom(rs.getString(2));
                p.setCategorie(rs.getString(3));
                p.setFiche_technique(rs.getString(4));
                p.setPrix(rs.getFloat(5));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
    }

        public boolean verifCategorieProduit(String categorie){
        String req = "SELECT * FROM produit WHERE categorie = ?";
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setString(1,categorie);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
    
    public void remisePrixCategorie(String categorie, float remise) {
        String req = "update produit set prix=prix/100*" + remise + " where categorie = '" + categorie+"'";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.executeUpdate();
            System.out.println("mise à jour des prix dont la categorie est "+categorie+" avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
