package entities;

import java.sql.Date;

public class Commentaire {

    int id_commentaire, id_publication, idClient;
    String description;
    Date datePost;

    public Commentaire() {
    }

    public Commentaire(int id_publication, int idClient, String description) {
        this.id_publication = id_publication;
        this.idClient = idClient;
        this.description = description;
        Date sysdate = new Date(System.currentTimeMillis());
        this.datePost = sysdate;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getId_commentaire() {
        return id_commentaire;
    }

    public void setId_commentaire(int id_commentaire) {
        this.id_commentaire = id_commentaire;
    }

    public int getId_publication() {
        return id_publication;
    }

    public void setId_publication(int id_publication) {
        this.id_publication = id_publication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    @Override
    public String toString() {
        return "Commentaire{" 
                + "id_commentaire=" + id_commentaire 
                + ", id_publication=" + id_publication 
                + ", idClient=" + idClient 
                + ", description=" + description 
                + ", datePost=" + datePost + '}';
    }

    
}
