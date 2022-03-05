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
public class Produit {

    private int reference;
    private String libelle;
    private String categorie;
    private String description;
    private float prix;
    private int note;

    public Produit() {

    }

    public Produit(int reference, String libelle, String categorie, String description, float prix) {
        this.reference = reference;
        this.libelle = libelle;
        this.categorie = categorie;
        this.description = description;
        this.prix = prix;
        this.note=0;
    }

    public int getReference() {
        return reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getNote() {
        return note;
    }

    public float getPrix() {
        return prix;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{" + "reference=" + reference + ", libelle=" + libelle + ", categorie=" + categorie + ", description=" + description + ", prix=" + prix + ", note=" + note + '}';
    }

}
