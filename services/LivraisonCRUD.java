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
                l.setIdCommande(rs.getInt(1));
                l.setIdLivreur(rs.getInt(2));
                l.setDateHeure(rs.getDate(3));
                myList.add(l);
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
    public void RemiseLivraison() {
        Date sysdate = new Date(System.currentTimeMillis());
        try {
            Statement st = cnxx.createStatement();
            // String req = "select idCommande,DateHeure from livraison where idCommande in (select idCommande from commande WHERE livree=0)";
            String req = "select livraison.idCommande,livraison.DateHeure from livraison inner join commande on (commande.idCommande=livraison.idCommande) where commande.livree=0 ";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getDate(2).before(sysdate)) {
                    System.out.println("livraison en retard, vous aurez une remise de 70% sur la commande " + rs.getInt(1));
                    remiseRetard(rs.getInt(1));
                    String email = recupererMailClient(rs.getInt(1));
                    //envoyerMailExcuse(email);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
    }

    public void remiseRetard(int idCommande) {
        String req = "update commande set prix=prix*30/100 where idCommande = ?";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, idCommande);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public String recupererMailClient(int idCommande) {
        try {
            Statement st = cnxx.createStatement();
            String req = "select personne.email from livraison inner join commande on(commande.idCommande=livraison.idCommande) inner join client on(client.idClient=commande.idClient) inner join personne on(personne.id_personne=client.idClient) where(commande.idCommande="+idCommande+");";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return "Erreur dans idCommande , cette commande n'est pas affectée à une livraison donc il est inutile de récuperer l'adresse mail du client";
    }

    public void envoyerMailExcuse(String email) {

    }
}
