/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1.pidev.entities;

/**
 *
 * @author Marwa
 */
public class Commande {
    private int idCommande,idClient,idProduit,quantite;
    private String adresse;
    private float prix;
    private boolean livree;

    public Commande() {
    }

    public Commande(int idClient, int idProduit, int quantite, String adresse) {
        this.idClient = idClient;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.adresse = adresse;
        this.prix = 0;
        this.livree = false;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public int getQuantite() {
        return quantite;
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

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
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

    @Override
    public String toString() {
        return "Commande{" + "idCommande=" + idCommande + ", idClient=" + idClient + ", idProduit=" + idProduit + ", quantite=" + quantite + ", adresse=" + adresse + ", prix=" + prix + ", livree=" + livree + '}';
    }
    
    
    
    
}
