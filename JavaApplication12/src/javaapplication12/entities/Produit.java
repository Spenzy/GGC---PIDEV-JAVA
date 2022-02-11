/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12.entities;

/**
 *
 * @author Mr
 */
public class Produit {

    private int reference;
    private String nom;
    private String categorie;
    private String fiche_technique;
    private float prix;

    public Produit() {

    }

    public Produit(int reference, String nom, String categorie, String fiche_technique, float prix) {
        this.reference = reference;
        this.nom = nom;
        this.categorie = categorie;
        this.fiche_technique = fiche_technique;
        this.prix = prix;
    }

    public int getReference() {
        return reference;
    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getFiche_technique() {
        return fiche_technique;
    }

    public float getPrix() {
        return prix;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setFiche_technique(String fiche_technique) {
        this.fiche_technique = fiche_technique;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{" + "reference=" + reference + ", nom=" + nom + ", categorie=" + categorie + ", fiche_technique=" + fiche_technique + ", prix=" + prix + '}';
    }

}
