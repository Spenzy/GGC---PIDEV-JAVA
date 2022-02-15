package services;

import entities.Publication;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import utils.MyConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.constant.ConstantDescs.NULL;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class PublicationCRUD {
    Connection cnxx;

    public PublicationCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }



    public long ajouterPublication (Publication p) {
        long id = 0;
        if(verifPublication(p) && !verifQuotaPub(p.getId_client())) {
            String req = "INSERT INTO Publication (idPublication,object,description,nbrVote,archive,idClient ) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, p.getId_publication());
                pst.setString(2, p.getTitre());
                pst.setString(3, p.getDesc());
                pst.setInt(4, p.getNbrVote());
                pst.setBoolean(5, p.isArchive());
                pst.setInt(6, p.getId_client());
                pst.executeUpdate();
                //autoArchive(p, LocalDateTime.now());
                System.out.println("Publication ajoutée avec succés");
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }else if (verifQuotaPub(p.getId_client())){

        }else
        {
            System.out.println("Champ titre invalide");
        }
        return id;
    }

    public void modifierPublication(Publication p) {
        String req = "UPDATE publication SET object=?, description=?, nbrVote=?, archive = ? WHERE idPublication=? "; //id_client est fixe //archive aura son propre methode
        if(verifPublication(p)) {
            try {
                PreparedStatement pst = cnxx.prepareStatement(req);
                pst.setString(1, p.getTitre());
                pst.setString(2, p.getDesc());
                pst.setInt(3,p.getNbrVote());
                pst.setBoolean(4, p.isArchive());
                pst.setInt(5, p.getId_publication());
                pst.executeUpdate();
                //autoArchive(p, LocalDateTime.now());
                System.out.println("Publication modifiée avec succés");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }else
        {
            System.out.println("Champ titre invalide");
        }
    }

    public void supprimerPublication(int id_Publication) {
        String req = "delete from publication where idPublication = ? ";
        PreparedStatement pst;
        try {
            pst = cnxx.prepareStatement(req);
            pst.setInt(1, id_Publication);
            pst.executeUpdate();
            System.out.println("Publication supprimée avec succés");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Publication afficherPublication(int idP){
        Publication p = new Publication();
        try {
            String req = "SELECT * FROM publication WHERE idPublication = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idP);
            ResultSet rs = pst.executeQuery();
            rs.next();
            p.setId_publication(rs.getInt(1));
            p.setTitre(rs.getString(2));
            p.setDesc(rs.getString(3));
            p.setNbrVote(rs.getInt(4));
            p.setNbrVote(rs.getInt(5));
            p.setId_client(rs.getInt(6));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return p;
    }

    public ArrayList<Publication> afficherPublication() {
        ArrayList listePublications = new ArrayList();
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT * FROM publication";
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Publication p = new Publication();
                p.setId_publication(rs.getInt(1));
                p.setTitre(rs.getString(2));
                p.setDesc(rs.getString(3));
                p.setNbrVote(rs.getInt(4));
                p.setArchive(rs.getBoolean(5));
                p.setId_client(rs.getInt(6));
                listePublications.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listePublications;
    }

    public boolean verifPublication(Publication p){ //on teste si le champ titre est vide oubien nulle on retourne faux;
        return !p.getTitre().equals("") && !p.getTitre().equals(NULL);
    };

    public boolean verifQuotaPub(int idClient){ //on retourne TRUE si notre quota est attein
        String req = "SELECT count(*) FROM publication WHERE idClient = ? AND archive = 0"; // on teste uniquement les postes non archivé
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1,idClient);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                return rs.getInt(1)>10; //on a un maximum quota de 10 postes courants par client / user
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void archiver(Publication p){
        if(p.isArchive()){
            p.setArchive(true);
            modifierPublication(p);
        }else
            System.out.println("publication déja archivé");
    }

    public void autoArchive(Publication p, LocalDateTime dateAjout){
        final Runnable autoArch = new Runnable() { //ScheduledExecutorService nécessite un objet runnable pour fonctionner ou on fait appel a notre methode archiver
            public void run() {
                //archiver(p);
                p.setArchive(!p.isArchive());
                System.out.println(p);//pour tester le chrono
            }
        };                                                          //pour tester 5 secondes au lieu de 5 jrs
        long delai = ChronoUnit.MILLIS.between(dateAjout, dateAjout.plusSeconds(5)); //ici on fait calculer le delai de 5 jours depuis la date d'ajout
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //ici on initialise un nouveau thread de ScheduledExecutorService
        scheduler.schedule(autoArch, delai, TimeUnit.MILLISECONDS); //ici on programme le chrono du methode
    }

    /*
    public void autoArchive(Publication p){
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            JobDetail archJob = newJob(ArchJob.class)
                    .withIdentity("ArchiveJob"+p.getId_publication()+":"+p.getId_client(), "ArchJobGroup")
                    .build();

            Date dateArch = (Date) Date.from(LocalDateTime.now()
                                                    .plusSeconds(5)
                                                    .atZone(ZoneId.systemDefault())
                                                    .toInstant());

            Trigger archTrigger = newTrigger()
                                .withIdentity("ArchiveTrigger"+p.getId_publication()+":"+p.getId_client(), "ArchiveTriggerGroup")
                                .startAt(dateArch)
                                .build();

            scheduler.scheduleJob(archJob, archTrigger);
            System.out.println(archJob.getKey() + " will run at: "+ dateArch);

            scheduler.start();
        }catch (SchedulerException e){
            System.err.println(e.getMessage());
        }
    }

 */

}
/*
class ArchJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("testing job");
    }
}

 */