package clientgui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jsr268gp.sampleclient.CardNotFound;
import jsr268gp.sampleclient.NotAuthenticatedError;
import jsr268gp.sampleclient.ServerError;

public class add_patientController {
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
	private TextField nationalID;
	@FXML
	private TextField picture;

public void addPatient(ActionEvent event) throws NumberFormatException, Exception{
       if (!validateEntries()) {
          return;
       }
		int gender = male.isSelected() ? 1 : 2;
		// getting the date
		String date = "";
        // Get the selected date from the DatePicker
        LocalDate selectedDate = dateOfBirth.getValue();
        if (selectedDate != null) {
            // Format the date as "yyyy-MM-dd"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = selectedDate.format(formatter);
        }
		try {
			Main.admin.addNewPatient(firstName.getText(),
					lastName.getText().toString(),
					date,
					Long.parseLong(nationalID.getText().toString()),
					gender,
					email.getText().toString(),
					phone.getText().toString(),
					adresse.getText().toString());
		}  catch (CardNotFound e){
			Util.showAlert("Error", "Card not inserted");
		} catch (NotAuthenticatedError e) {
			Util.showAlert("Error", "Permission denied");
		}catch (ServerError e){
			Util.showAlert("Error", "Server error");
		}
        Parent secondView = FXMLLoader.load(getClass().getResource("admin-panel.fxml"));
        Scene secondScene = new Scene(secondView);

        // Get the current stage (window) using the event's source
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        window.setScene(secondScene);
        window.show();
	}
	
    @FXML
	public void cancelForm(ActionEvent event) throws IOException{
        Parent secondView = FXMLLoader.load(getClass().getResource("admin-panel.fxml"));
        Scene secondScene = new Scene(secondView);

        // Get the current stage (window) using the event's source
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        window.setScene(secondScene);
        window.show();
	}
    private boolean validateEntries() {
        // Validation logic for entries
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || phone.getText().isEmpty() ||
                email.getText().isEmpty() || dateOfBirth.getValue() == null || nationalID.getText().isEmpty() ||
                adresse.getText().isEmpty()) {
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

}
