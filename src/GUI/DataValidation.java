/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.control.TextField;
import animatefx.animation.Shake;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

/**
 *
 * @author Dell
 */
public class DataValidation {

    Connection cnxx;

    public Boolean verifEmail(String Email) {
        boolean testEmail = false;
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT email FROM personne";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getString(1) == Email) {
                    testEmail = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return testEmail;

    }

    /*
    public boolean VerifEvenement(int reference) {
        boolean testEvenement = false;
        try {
            Statement st = cnxx.createStatement();
            String req = "SELECT reference FROM evenement";
            ResultSet rs;
            rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getInt(1) == reference) {
                    testEvenement = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //   return null;
        }
        return testEvenement;
    }*/

    public static boolean emailFormat(TextField inputTextField) {
        boolean isEmail = true;

        if (!inputTextField.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com")) {
            isEmail = false;
            inputTextField.setStyle("-fx-border-color: red");
            new Shake(inputTextField).play();

        } else {
            inputTextField.setStyle("-fx-border-color: transparent");

        }
        return isEmail;

    }

    public static boolean dataLength(TextField inputTextField, String requiredLength) {
        boolean isDataLength = true;
        String validationString = null;

        if (!inputTextField.getText().matches("\\b\\w" + "{" + requiredLength + "}")) {
            isDataLength = false;
            inputTextField.setStyle("-fx-border-color: red");
            new Shake(inputTextField).play();

        } else {
            inputTextField.setStyle("-fx-border-color: transparent");

        }
        return isDataLength;

    }

    public static boolean textAlphabet(TextField inputTextField) {
        boolean isAlphabet = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[a-z A-Z]+")) {
            isAlphabet = false;
            inputTextField.setStyle("-fx-border-color: red");
            new Shake(inputTextField).play();
        } else {
            inputTextField.setStyle("-fx-border-color: transparent");

        }
        return isAlphabet;

    }

    public static boolean textNumeric(TextField inputTextField) {
        boolean isNumeric = true;

        if (!inputTextField.getText().matches("[0-9]+")) {
            isNumeric = false;
            inputTextField.setStyle("-fx-border-color: red");
            new Shake(inputTextField).play();

        } else {
            inputTextField.setStyle("-fx-border-color: transparent");

        }

        return isNumeric;

    }

    public static boolean isNotEmpty(TextField inputTextField) {
        boolean isNull = true;

        //point out difference between null and isEmpty() *FIND OUT WHEN TO USE NULL
        if (inputTextField.getText().isEmpty()) {
            isNull = false;
            inputTextField.setStyle("-fx-border-color: red");
            new Shake(inputTextField).play();
        } else {
            inputTextField.setStyle("-fx-border-color: transparent");

        }

        return isNull;

    }

    public boolean validEmail(String email) {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null || email.isEmpty()) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public boolean validPassword(String password) {
        String passwordRegex = "^(\\w{6,18})$";

        Pattern pat = Pattern.compile(passwordRegex);
        if (password == null || password.isEmpty()) {
            return false;
        }
        return pat.matcher(password).matches();
    }

}
