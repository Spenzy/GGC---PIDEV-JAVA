package entities;

import java.sql.Date;

public class Publication {

    int id_publication, id_client;
    String titre, desc;
    boolean archive;
    Date datePub;

    public Publication() {
    }

    public Publication(int id_client, String titre, String desc, boolean archive) {
        this.id_client = id_client;
        this.titre = titre;
        this.desc = desc;
        this.archive = archive;
        Date sysdate = new Date(System.currentTimeMillis());
        this.datePub = sysdate;
    }

    public int getId_publication() {
        return id_publication;
    }

    public void setId_publication(int id_publication) {
        this.id_publication = id_publication;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public Date getDatePub() {
        return datePub;
    }

    public void setDatePub(Date datePub) {
        this.datePub = datePub;
    }

    @Override
    public String toString() {
        return "Publication{"
                + "id_publication=" + id_publication
                + ", id_client=" + id_client
                + ", titre=" + titre
                + ", desc=" + desc
                + ", archive=" + archive
                + ", datePub=" + datePub + '}';
    }

}
