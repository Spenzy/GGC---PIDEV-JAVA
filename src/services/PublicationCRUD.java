package services;

import entities.Publication;
import utils.MyConnection;

import java.sql.*;
import static java.sql.JDBCType.NULL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.quartz.Job;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;
import utils.MailAPI;

public class PublicationCRUD {

    Connection cnxx;
    PersonneCRUD pcrud = new PersonneCRUD();

    public PublicationCRUD() {
        cnxx = MyConnection.getInstance().getCnx();
    }

    public long ajouterPublication(Publication p) {
        long id = 0;
        if (verifPublication(p) && !verifQuotaPub(p.getId_client())) {
            String req = "INSERT INTO Publication (object,description,archive,idClient,date) VALUES (?,?,?,?,?)";
            try {
                PreparedStatement pst = cnxx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, p.getTitre());
                pst.setString(2, p.getDesc());
                pst.setBoolean(3, p.isArchive());
                pst.setInt(4, p.getId_client());
                pst.setDate(5, p.getDatePub());
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
        } else if (verifQuotaPub(p.getId_client())) {
            System.out.println("Vous avez attein votre quota de publication");
        } else {
            System.out.println("Champ titre invalide");
        }
        return id;
    }

    public void modifierPublication(Publication p) {
        String req = "UPDATE publication SET object=?, description=?, archive = ? WHERE idPublication=? "; //id_client est fixe //archive aura son propre methode
        if (verifPublication(p)) {
            try {
                PreparedStatement pst = cnxx.prepareStatement(req);
                pst.setString(1, p.getTitre());
                pst.setString(2, p.getDesc());
                pst.setBoolean(3, p.isArchive());
                pst.setInt(4, p.getId_publication());
                pst.executeUpdate();
                //autoArchive(p, LocalDateTime.now());
                System.out.println("Publication modifiée avec succés");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        } else {
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

    public Publication afficherPublication(int idP) {
        Publication p = new Publication();
        try {
            String req = "SELECT * FROM publication WHERE idPublication = ?";
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idP);
            ResultSet rs = pst.executeQuery();
            rs.next();
            p.setId_publication(rs.getInt(1));
            p.setTitre(rs.getString(2));
            p.setDesc(rs.getString(3));
            p.setArchive(rs.getBoolean(4));
            p.setId_client(rs.getInt(5));
            p.setDatePub(rs.getDate(6));
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
                p.setArchive(rs.getBoolean(4));
                p.setId_client(rs.getInt(5));
                p.setDatePub(rs.getDate(6));
                listePublications.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listePublications;
    }

    public boolean verifPublication(Publication p) { //on teste si le champ titre est vide oubien nulle on retourne faux;
        return !p.getTitre().equals("") && !p.getTitre().equals(NULL);
    }

    public boolean verifQuotaPub(int idClient) { //on retourne TRUE si notre quota est attein
        String req = "SELECT count(*) FROM publication WHERE idClient = ? AND archive = 0"; // on teste uniquement les postes non archivé
        try {
            PreparedStatement pst = cnxx.prepareStatement(req);
            pst.setInt(1, idClient);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 10; //on a un maximum quota de 10 postes courants par client / user
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void archiver(Publication p) {
        if (!p.isArchive()) {
            p.setArchive(true);
            System.out.println("publication archivé");

        } else {
            p.setArchive(false);
            System.out.println("publication déarchivé");
        }
        modifierPublication(p);
        try {
            MailAPI.sendMail(pcrud.afficherPersonne(
                    p.getId_client()).getEmail()
                    , "Archivage Publication"
                    , "Votre Publication " + p.getTitre() + "à été archivé");
        } catch (MessagingException ex) {
            System.err.println(ex.getMessage());
        }
    }

//    public void autoArchive(Publication p, LocalDateTime dateAjout){
//        
//        //ScheduledExecutorService nécessite un objet runnable pour fonctionner ou on fait appel a notre methode archiver
//        final Runnable autoArch = new Runnable() { 
//            public void run() {
//                //archiver(p);
//                p.setArchive(!p.isArchive());
//                System.out.println(p);//pour tester le chrono
//            }
//        };                                                          //pour tester 5 secondes au lieu de 5 jrs
//        long delai = ChronoUnit.MILLIS.between(dateAjout, dateAjout.plusSeconds(5)); //ici on fait calculer le delai de 5 jours depuis la date d'ajout
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //ici on initialise un nouveau thread de ScheduledExecutorService
//        scheduler.schedule(autoArch, delai, TimeUnit.MILLISECONDS); //ici on programme le chrono du methode
//    } 

    public void autoArchive(Publication p) {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            JobDetail archJob = newJob(ArchJob.class)
                    .withIdentity("ArchiveJob" + p.getId_publication() + ":" + p.getId_client(), "ArchJobGroup")
                    .build();

            java.util.Date dateArch = Date.from(LocalDateTime.now().plusSeconds(10).atZone(ZoneId.of("UTC")).toInstant());

            Trigger archTrigger = newTrigger()
                    .withIdentity("ArchiveTrigger" + p.getId_publication() + ":" + p.getId_client(), "ArchiveTriggerGroup")
                    .startAt(dateArch)
                    .build();

            scheduler.scheduleJob(archJob, archTrigger);
            System.out.println(archJob.getKey() + " will run at: " + dateArch);

            scheduler.start();

        } catch (SchedulerException e) {
            System.err.println(e.getMessage());
        }
    }
    
   

}

class ArchJob implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("Knock! Knock!");
    }
}
