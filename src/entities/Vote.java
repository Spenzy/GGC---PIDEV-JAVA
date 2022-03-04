package entities;

public class Vote {
    int id_client,id_publication;
    String type;

    public Vote(){
    }

    public Vote(int id_client, int id_publication, String type) {
        this.id_client = id_client;
        this.id_publication = id_publication;
        this.type = type;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_publication() {
        return id_publication;
    }

    public void setId_publication(int id_publication) {
        this.id_publication = id_publication;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id_client=" + id_client +
                ", id_publication=" + id_publication +
                ", type='" + type + '\'' +
                '}';
    }
}
