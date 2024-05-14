package clientgui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import jsr268gp.sampleclient.APDUOps;
import jsr268gp.sampleclient.CardNotFound;
import jsr268gp.sampleclient.NotAuthenticatedError;
import jsr268gp.sampleclient.ServerError;
import jsr268gp.sampleclient.UnknownClientError;

public class add_doctorController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private Button cancel;
    @FXML
    private Button add;
    @FXML
    private RadioButton male;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton female;
    @FXML
    private TextField adresse;
    @FXML
    private TextField picture;
    @FXML
    private RadioButton active;
    @FXML
    private ToggleGroup status;
    @FXML
    private RadioButton inactive;
    @FXML
    private RadioButton disabled;
    @FXML
    private TextField about;
    @FXML
    private TextField nationalID;

    public void addDoctor(ActionEvent event) throws NumberFormatException, Exception {
        int gender = male.isSelected() ? 0 : 1;

        // Validate entries
        if (!validateEntries()) {
            return;
        }

        // Format the date
        String dateOfBirthStr = formatDate(dateOfBirth.getValue());

        // Get status
        String statusStr = getStatus();

        // connecting to the card
        TerminalFactory tf = TerminalFactory.getDefault();
        CardTerminal cad = tf.terminals().getTerminal("ACS ACR1281 1S Dual Reader PICC 0");
        if (cad == null) {
            Util.showAlert("Error", "Card reader not found");
            return;
        }

        Pair<CardChannel, Card> cadPair = APDUOps.connectAndSelect(cad);
        CardChannel canal = cadPair.getKey();
        Card c = cadPair.getValue();

        if (canal == null) {
            Util.showAlert("Error", "Card not inserted");
            return;
        }

        try {
            Main.admin.addNewDoctor(canal, firstName.getText(), lastName.getText(), picture.getText(),
                    Long.parseLong(nationalID.getText()), gender, email.getText(), phone.getText(),
                    adresse.getText(), about.getText(), "1234", statusStr);
            Parent secondView = FXMLLoader.load(getClass().getResource("doctors_records.fxml"));
            Scene secondScene = new Scene(secondView);

            // Get the current stage (window) using the event's source
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            window.setScene(secondScene);
            window.show();
        } catch (CardNotFound e) {
            Util.showAlert("Error", "Card not inserted");
        } catch (NotAuthenticatedError e) {
            Util.showAlert("Error", "Permission denied");
        } catch (ServerError e) {
            Util.showAlert("Error", "Server error");
        } catch(UnknownClientError e){
        	Util.showAlert("Error", "Unknown client error");
        }
    }

    private boolean validateEntries() {
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || phone.getText().isEmpty()
                || email.getText().isEmpty() || dateOfBirth.getValue() == null || nationalID.getText().isEmpty()
                || adresse.getText().isEmpty() || picture.getText().isEmpty() || about.getText().isEmpty()) {
            Util.showAlert("Error", "Please fill in all fields.");
            return false;
        }

        // Validate email format
        if (!isValidEmail(email.getText())) {
            Util.showAlert("Error", "Please enter a valid email address.");
            return false;
        }

        // Validate phone number format
        if (!isValidPhoneNumber(phone.getText())) {
            Util.showAlert("Error", "Please enter a valid phone number.");
            return false;
        }

        // Additional validation logic if needed...

        return true;
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Simple phone number validation using regex
        String phoneRegex = "^\\d{10}$"; // Assuming a 10-digit phone number format
        return phoneNumber.matches(phoneRegex);
    }


    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    private String getStatus() {
        if (active.isSelected()) {
            return "ACTIVE";
        } else if (inactive.isSelected()) {
            return "INACTIVE";
        } else if (disabled.isSelected()) {
            return "DISABLED";
        } else {
            return ""; // Return empty string if none selected
        }
    }


    @FXML
    public void cancelForm(ActionEvent event) throws IOException {
        Parent secondView = FXMLLoader.load(getClass().getResource("doctors_records.fxml"));
        Scene secondScene = new Scene(secondView);

        // Get the current stage (window) using the event's source
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        window.setScene(secondScene);
        window.show();
    }
}
