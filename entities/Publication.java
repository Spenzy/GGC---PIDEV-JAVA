package entities;

public class Publication {
    int id_publication,id_client,nbrVote;
    String titre,desc;
    boolean archive;

    public Publication() {
    }

    public Publication(int id_publication, int id_client, int nbrVote, String titre, String desc, boolean archive) {
        this.id_publication = id_publication;
        this.id_client = id_client;
        this.nbrVote = nbrVote;
        this.titre = titre;
        this.desc = desc;
        this.archive = archive;
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

    public int getNbrVote() {
        return nbrVote;
    }

    public void setNbrVote(int nbrVote) {
        this.nbrVote = nbrVote;
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

    @Override
    public String toString() {
        return "Publication{" +
                "id_publication=" + id_publication +
                ", id_client=" + id_client +
                ", nbrVote=" + nbrVote +
                ", titre='" + titre + '\'' +
                ", desc='" + desc + '\'' +
                ", archive=" + archive +
                '}';
    }
}
