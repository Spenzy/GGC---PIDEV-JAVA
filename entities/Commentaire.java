package entities;

public class Commentaire {
    int id_commentaire, id_publication;
    String description;

    public Commentaire() {
    }

    public Commentaire(int id_commentaire, int id_publication, String description) {
        this.id_commentaire = id_commentaire;
        this.id_publication = id_publication;
        this.description = description;
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

    @Override
    public String toString() {
        return "Commentaire{" +
                "id_commentaire=" + id_commentaire +
                ", id_publication=" + id_publication +
                ", description='" + description + '\'' +
                '}';
    }
}
