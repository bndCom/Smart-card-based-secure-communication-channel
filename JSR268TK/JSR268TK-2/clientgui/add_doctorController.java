package clientgui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

public void addDoctor(ActionEvent event) throws IOException{
		
		int gender = male.isSelected() ? 0 : 1;
		// getting the date
		String date = ""+dateOfBirth.getValue().getYear();
		date += "-";
		date += dateOfBirth.getValue().getMonthValue() < 10 ? "0"+dateOfBirth.getValue().getMonthValue() : dateOfBirth.getValue().getMonthValue();
		date += "-";
		date += dateOfBirth.getValue().getDayOfMonth() < 10 ? "0"+dateOfBirth.getValue().getDayOfMonth() : dateOfBirth.getValue().getDayOfMonth();
				
		System.out.println("--------------------");
		System.out.println(firstName.getText());
		System.out.println(lastName.getText());
		System.out.println(dateOfBirth.getValue().getYear()+"-"+dateOfBirth.getValue().getMonthValue()+"-"+dateOfBirth.getValue().getDayOfMonth());
		System.out.println(Long.parseLong(nationalID.getText()));
		System.out.println(gender);
		System.out.println(email.getText());
		System.out.println(phone.getText());
		System.out.println(adresse.getText());
		String status = "";
		if(active.isSelected()) status = "ACTIVE";
		if(inactive.isSelected()) status = "INACTIVE";
		if(disabled.isSelected()) status = "DISABLED";
		try {
			Main.admin.addNewDoctor(Main.canal,
					firstName.getText(),
					lastName.getText(),
					picture.getText(),
					Long.parseLong(nationalID.getText()),
					gender,
					email.getText(),
					phone.getText(),
					adresse.getText(),
					about.getText(),
					"1234",
					status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void cancelForm(ActionEvent event) throws IOException{
        Parent secondView = FXMLLoader.load(getClass().getResource("doctors_records.fxml"));
        Scene secondScene = new Scene(secondView);

        // Get the current stage (window) using the event's source
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        window.setScene(secondScene);
        window.show();
	}
}
