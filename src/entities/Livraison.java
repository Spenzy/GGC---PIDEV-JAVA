/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author Marwa
 */
public class Livraison {
    private int idCommande;
    private int idLivreur;
    private Date DateHeure;

    public Livraison() {
    }

    public Livraison(int idCommande, int idLivreur, Date DateHeure) {
        this.idCommande = idCommande;
        this.idLivreur = idLivreur;
        this.DateHeure = DateHeure;
    }
    public int getIdCommande() {
        return idCommande;
    }

    public int getIdLivreur() {
        return idLivreur;
    }

    public Date getDateHeure() {
        return DateHeure;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setIdLivreur(int idLivreur) {
        this.idLivreur = idLivreur;
    }

    public void setDateHeure(Date DateHeure) {
        this.DateHeure = DateHeure;
    }

    @Override
    public String toString() {
        return "Livraison{" + "idCommande=" + idCommande + ", idLivreur=" + idLivreur + ", DateHeure=" + DateHeure + '}';
    }

    
    
    
    
    
    
}
