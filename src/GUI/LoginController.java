/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import entities.Personne;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static jdk.nashorn.tools.Shell.SUCCESS;
import services.PersonneCRUD;
import static tray.animations.AnimationType.FADE;
import tray.notification.TrayNotification;
import utils.EmailUtils;
import utils.RandomGenerator;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class LoginController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private Button btnsignin;
    @FXML
    private Button btnsignup;
    @FXML
    private PasswordField pwdId;
    PersonneCRUD pc = new PersonneCRUD();

    @FXML
    private Label errorLogin;
    @FXML
    private Label ereurpassword;
    @FXML
    private AnchorPane root;
    @FXML
    private StackPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Login(ActionEvent event) throws IOException {
        DataValidation validator = new DataValidation();
        PersonneCRUD repo = new PersonneCRUD();
        if (email.getText().isEmpty()) {
            errorLogin.setText("champs vide");
        } else {
            errorLogin.setText("");
        }

        if (pwdId.getText().isEmpty()) {
            ereurpassword.setText("champs vide");
        } else {
            ereurpassword.setText("");
        }
        if (!pwdId.getText().isEmpty() && !email.getText().isEmpty()) {

            if (email.getText().equals("admin") && pwdId.getText().equals("admin")) {
                Parent blah = FXMLLoader.load(getClass().getResource("Dashbord.fxml"));
                Scene scene = new Scene(blah);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();
            } else if (repo.getUserBy(email.getText(), pwdId.getText())) {
                Parent blah = FXMLLoader.load(getClass().getResource("ListeClient.fxml"));
                Scene scene = new Scene(blah);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.show();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur login");
            alert.setHeaderText(null);
            alert.setContentText("Enter a Login!");
            alert.show();

        }
    }

    public void showDialog() {
        DataValidation validator = new DataValidation();
        PersonneCRUD pc = new PersonneCRUD();
        pane.setVisible(true);
        pane.toFront();
        JFXButton valider = new JFXButton("valider");
        valider.setMinHeight(35);
        valider.setStyle("-fx-background-radius: 5pt;-fx-background-color: #5cb85c;-fx-text-fill: white;");
        valider.setMinWidth(70);
        valider.setTranslateX(-20);
        valider.setTranslateY(-5);
        JFXButton fermer = new JFXButton("fermer");
        fermer.setMinHeight(35);
        fermer.setStyle("-fx-background-radius: 5pt;-fx-background-color: #d9534f;-fx-text-fill: white;");
        fermer.setMinWidth(70);
        fermer.setTranslateX(20);
        fermer.setTranslateY(-5);
        StackPane pane = new StackPane();
        pane.setAlignment(valider, Pos.BOTTOM_RIGHT);
        pane.setAlignment(fermer, Pos.BOTTOM_LEFT);
        pane.setMinSize(488, 211);
        Label header = new Label("Mot de passe oublié");
        pane.setAlignment(header, Pos.TOP_CENTER);
        header.setTranslateY(10);
        header.setTranslateX(10);
        Label lab = new Label("veiller entrer votre email");
        pane.setAlignment(lab, Pos.CENTER_LEFT);
        lab.setTranslateY(-40);
        JFXTextField email = new JFXTextField();
        pane.setAlignment(email, Pos.CENTER_LEFT);

        header.setTranslateX(20);
        header.setTranslateY(20);
        Label errorEmail = new Label();
        pane.setAlignment(errorEmail, Pos.CENTER_LEFT);
        errorEmail.setTranslateX(20);
        errorEmail.setTranslateY(40);
        pane.getChildren().setAll(header, email, lab, valider, fermer, errorEmail);

        JFXDialog dialog = new JFXDialog(this.pane, pane, JFXDialog.DialogTransition.CENTER);
        fermer.setOnAction(event -> {
            dialog.close();
            this.pane.toBack();

        });
        valider.setOnAction(event -> {

            if (email.getText().isEmpty()) {
                errorLogin.setText("champs vide");
            } else if (!validator.validEmail(email.getText())) {
                errorLogin.setText("email invalide");
            } else if (pc.getUserBy(email.getText()) == null) {
                errorLogin.setText("email inconnue");
            } else {
                try {
                    Personne u = pc.getUserBy(email.getText()).get();
                    String str = RandomGenerator.generateRandom(10);
                    //String pass = BCrypt.hashpw(str, BCrypt.gensalt());
                    // u.setPassword(pass);
                    pc.modifierPersonne(u);
                    EmailUtils.sendMail("wiem.bensalah1@gmail.com", str);
                    dialog.close();
                    this.pane.toBack();
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        dialog.show();
    }

    public String cToken(String passwordToHash) {

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    @FXML
    private void resetP(MouseEvent event) throws Exception {

        if (email.getText() == null || email.getText().trim().isEmpty()) {
            System.out.println("eeeeeeeee");
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please enter an email to send the reset link to!");
            a.show();
            //

        } else {
            //String aaa="amir";           
            String emailp = email.getText();
            String Token = cToken(emailp);
            PersonneCRUD c = new PersonneCRUD();
            // System.out.println("aaaaa");
            //System.out.println("1"+Token);
            System.out.println(c.ResetPass(emailp, Token));
            System.out.println("2fsdf");

            String body = " Your password has been update -> " + Token;
            System.out.println("345454");

//            sendMail(emailp, "test", body);
            System.out.println("bbbbbb");

            //TrayNotification tray = new TrayNotification("Password Reset", "A link has been sent to your Email",SUCCESS);
            //tray.setAnimationType(FADE);
            // tray.showAndWait();
        }

    }
    private static final String FROM = "gamergeekscommunity@gmail.com";
    private static final String PASSWORD = "#ggc2022";

    public static void sendMail(String mail, String subject, String message) throws AddressException, MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session mailSession = Session.getDefaultInstance(props, null);
        Message mailMessage = new MimeMessage(mailSession);

        mailMessage.setFrom(new InternetAddress(FROM));
        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
        mailMessage.setContent(message, "text/html; charset=utf-8");
        mailMessage.setSubject(subject);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", FROM, PASSWORD);
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
    }

    /*
    
           public static void sendMail(String recepient , String body) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "gamergeekscommunity@gmail.com";
        //Your gmail password
        String password = "azerty147852369";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient , body);

        //Send mail
        Transport.send(message);
    }
     */
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Password Change Request");
            message.setText(body);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
