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
import entities.Commande;
import entities.LigneCommande;
import utils.MyConnection;

/**
 *
 * @author Mr
 */
public class LigneCommandeCRUD {

    Connection cnxx;

    public LigneCommandeCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public void ajouterLigneCommande(LigneCommande lc) {

        String req = "INSERT INTO LigneCommande (idCommande,idProduit,quantite,prix) VALUES (?,?,?,?)";
        PreparedStatement pst;
        try {

            pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, lc.getIdCommande());
            pst.setInt(2, lc.getIdProduit());
            pst.setInt(3, lc.getQuantite());
            pst.setFloat(4, lc.getPrix());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            long pk = rs.getLong(1);
            lc.setIdLigne((int) pk);

            calculPrixLigne(lc);

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void supprimerLignesCommande(int idCommande) {
        String req = "delete from LigneCommande where idCommande=?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommande);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void modifierLigneCommande(LigneCommande lc) {
        String req = "update LigneCommande set idProduit=? , quantite=? , prix=? where (idCommande = ? and idLigne = ?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, lc.getIdProduit());
            pst.setInt(2, lc.getQuantite());
            pst.setFloat(3, lc.getPrix());
            pst.setInt(4, lc.getIdCommande());
            pst.setInt(5, lc.getIdLigne());
            pst.executeUpdate();

            calculPrixLigne(lc);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public List<LigneCommande> afficher(int idCommande) {

        List<LigneCommande> myList = new ArrayList();

        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM LigneCommande where( idCommande= " + idCommande + " )";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                LigneCommande lc = new LigneCommande();
                lc.setIdLigne(rs.getInt(1));
                lc.setIdCommande(rs.getInt(2));
                lc.setIdProduit(rs.getInt(3));
                lc.setQuantite(rs.getInt(4));
                lc.setPrix(rs.getFloat(5));
                myList.add(lc);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return myList;
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

    public void calculPrixLigne(LigneCommande lc1) {
        String req = "select produit.prix*lignecommande.quantite from lignecommande inner join produit on(produit.reference=lignecommande.idProduit) where( lignecommande.idLigne=" + lc1.getIdLigne() + ")";
        try {
            Statement st = cnxx.createStatement();
            ResultSet rs;
            rs = st.executeQuery(req);
            if (rs.next()) {
                float prixLigne = rs.getFloat(1);
                lc1.setPrix(prixLigne);
                modifierPrixLigneCommande(lc1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    private void modifierPrixLigneCommande(LigneCommande lc) {
        String req = "update LigneCommande set prix=? where (idLigne = ?)";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setFloat(1, lc.getPrix());
            pst.setInt(2, lc.getIdLigne());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public String recupererLibelle(int idProduit) {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT libelle FROM produit where (reference="+idProduit+")";
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

    public void setLigne(LigneCommande lc) {
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM LigneCommande where( idLigne= " + lc.getIdLigne() + " )";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {

                lc.setIdLigne(rs.getInt(1));
                lc.setIdCommande(rs.getInt(2));
                lc.setIdProduit(rs.getInt(3));
                lc.setQuantite(rs.getInt(4));
                lc.setPrix(rs.getFloat(5));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
    }

}
