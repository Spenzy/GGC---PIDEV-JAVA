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
public class Avis {

    private int idAvis;
    private int referenceProduit;
    private int idClient;
    private String description;
    private String type;

    public Avis() {

    }

    public Avis(int referenceProduit,int idClient, String description, String type) {
        this.referenceProduit = referenceProduit;
        this.description = description;
        this.idClient=idClient;
        if (type.equals("excellent") || type.equals("mediocre") || type.equals("moyen")) {
            this.type = type;
        } else {
            System.out.println("le type doit etre excellent , moyen ou mediocre");
        }
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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
        return "Avis{" + "idAvis=" + idAvis + ", referenceProduit=" + referenceProduit + ", idClient=" + idClient + ", description=" + description + ", type=" + type + '}';
    }

    
}
