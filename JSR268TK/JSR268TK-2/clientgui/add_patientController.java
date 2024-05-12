package clientgui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public void addPatient(ActionEvent event) throws IOException{
		
		int gender = male.isSelected() ? 1 : 2;
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
		
		try {
			Main.admin.addNewPatient(firstName.getText(),
					lastName.getText().toString(),
					//dateOfBirth.getValue().getYear()+"-"+dateOfBirth.getValue().getMonthValue()+"-"+dateOfBirth.getValue().getDayOfMonth(),
					date,
					Long.parseLong(nationalID.getText().toString()),
					gender,
					email.getText().toString(),
					phone.getText().toString(),
					adresse.getText().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void cancelForm(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("admin-panel.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        
        stage.initStyle(StageStyle.UNIFIED);
        scene.setFill(Color.DARKGRAY);      
        stage.setScene(scene);
        stage.show();
	}
}
