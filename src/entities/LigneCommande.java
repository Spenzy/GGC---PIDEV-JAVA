/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Mr
 */
public class LigneCommande {
    
    private int idCommande,idLigne,idProduit,quantite;
    private float prix;

    public LigneCommande() {
    }

    public LigneCommande(int idCommande,int idProduit, int quantite, float prix) {
        this.idCommande = idCommande;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.prix = prix;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public int getIdLigne() {
        return idLigne;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public float getPrix() {
        return prix;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "LigneCommande{" + "idCommande=" + idCommande + ", idLigne=" + idLigne + ", idProduit=" + idProduit + ", quantite=" + quantite + ", prix=" + prix + '}';
    }
    
    
    
}
