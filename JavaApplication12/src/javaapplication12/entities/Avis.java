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
public class Avis {

    private int idAvis;
    private int referenceProduit;
    private String description;
    private String type;

    public Avis() {

    }

    public Avis(int referenceProduit, String description, String type) {
        this.referenceProduit = referenceProduit;
        this.description = description;
        if (type.equals("excellent") || type.equals("mediocre") || type.equals("moyen")) {
            this.type = type;
        } else {
            System.out.println("le type doit etre excellent , moyen ou mediocre");
        }
    }

    public int getIdAvis() {
        return idAvis;
    }

    public int getReferenceProduit() {
        return referenceProduit;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setReferenceProduit(int referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        if (type.equals("excellent") || type.equals("mediocre") || type.equals("moyen")) {
            this.type = type;
        } else {
            System.out.println("le type doit etre excellent , moyen ou mediocre");
        }
    }

    @Override
    public String toString() {
        return "Avis{" + "idAvis=" + idAvis + ", referenceProduit=" + referenceProduit + ", description=" + description + ", type=" + type + '}';
    }

}
