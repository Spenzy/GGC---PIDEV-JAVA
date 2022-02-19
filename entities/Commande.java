/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marwa
 */
public class Commande {

    private int idCommande, idClient;
    private String adresse;
    private float prix;
    private boolean livree;
    private Date DateCommande;
    private List<LigneCommande> Lignes=new ArrayList<>();
   
    public Commande() {
    }

    public Commande(int idClient, String adresse, Date DateCommande) {
        this.idClient = idClient;
        this.adresse = adresse;
        this.prix = 0;
        this.livree = false;
        this.DateCommande = DateCommande;
    }

    public void setLignes(List<LigneCommande> Lignes) {
        this.Lignes = Lignes;
    }

    public List<LigneCommande> getLignes() {
        return Lignes;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getAdresse() {
        return adresse;
    }

    public float getPrix() {
        return prix;
    }

    public boolean isLivree() {
        return livree;
    }

    public Date getDateCommande() {
        return DateCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setLivree(boolean livree) {
        this.livree = livree;
    }

    public void setDateCommande(Date DateCommande) {
        this.DateCommande = DateCommande;
    }

    @Override
    public String toString() {
        return "Commande{" + "idCommande=" + idCommande + ", idClient=" + idClient + ", adresse=" + adresse + ", prix=" + prix + ", livree=" + livree + ", DateCommande=" + DateCommande + ", Lignes=" + Lignes + '}';
    }

}
