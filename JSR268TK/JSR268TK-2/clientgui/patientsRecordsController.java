package clientgui;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class patientsRecordsController {
	@FXML
	private TableView tableview;
	@FXML
	private TableColumn ID;
	@FXML
	private TableColumn LastName;
	@FXML
	private TableColumn Name;
	@FXML
	private TableColumn tel;
	@FXML
	private TableColumn LastSession;
	@FXML
	private TableColumn Record;
	@FXML
	private TableColumn tools;
	@FXML
	private TextField filterField;
	@FXML
	private Button addBtn;
	@FXML
	private Label AdminName;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Button menuButton;
	@FXML
	private SVGPath menuIcon;
	@FXML
	private Button dashBoardButton,historyButton;
	@FXML
	private SVGPath dashBoardIcon;
	@FXML
	private Button patientsButton;
	@FXML
	private SVGPath patientsIcon;
	@FXML
	private SVGPath historyIcon;

	private Timeline animation;

	private ObservableList<PatientDto> dataList = FXCollections.observableArrayList();
	public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<PatientDto, String>("patientId"));
        LastName.setCellValueFactory(new PropertyValueFactory<PatientDto, String>("lastName"));
        Name.setCellValueFactory(new PropertyValueFactory<PatientDto, String>("firstName"));
        tel.setCellValueFactory(new PropertyValueFactory<PatientDto, String>("phoneNumber"));
        LastSession.setCellValueFactory(new PropertyValueFactory<PatientDto, String>("email"));
        Record.setCellValueFactory(new PropertyValueFactory<PatientDto, String>("address"));
        tools.setCellValueFactory(new PropertyValueFactory<PatientDto, Void>("tools"));

        // Custom cell factory for the tools column
        tools.setCellFactory(new Callback<TableColumn<PatientDto, Void>, TableCell<PatientDto, Void>>() {
            public TableCell<PatientDto, Void> call(TableColumn<PatientDto, Void> column) {
                return new TableCell<PatientDto, Void>() {
                    final Button btn1 = new Button("show");
                    final Button btn2 = new Button("delete");
                    final Button btn3 = new Button("edit");
                    
                    {
                        btn1.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                            public void handle(javafx.event.ActionEvent event) {
                            	PatientDto doctor = getTableView().getItems().get(getIndex());
                            }
                        });
                        btn2.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                            public void handle(javafx.event.ActionEvent event) {
                            	PatientDto doctor = getTableView().getItems().get(getIndex());
                            }
                        });
                        btn3.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                            public void handle(javafx.event.ActionEvent event) {
                            	PatientDto doctor = getTableView().getItems().get(getIndex());
                            }
                        });
                    }
                    
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn1.getStyleClass().add("btn-tool");
                            btn2.getStyleClass().add("btn-tool");
                            btn3.getStyleClass().add("btn-tool");
                            HBox buttonsContainer = new HBox(btn1, btn2, btn3);
                            buttonsContainer.setSpacing(10);
                            setGraphic(buttonsContainer);
                        }
                    }
                };
            }
        });
		
		// getting all patients from the database
		List<Map<String, Object>> mapList = new LinkedList<Map<String, Object>>();
		try {
			mapList = Main.admin.getAllPatients();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<Map<String, Object>> itr = mapList.iterator();
		while(itr.hasNext()){
			// constructing the object from the received json
			PatientDto patient = new PatientDto();
			Map<String, Object> mp = itr.next();
			patient.setPatientId(Util.doubleToLong((Double)mp.get("patientId")));
			patient.setFirstName((String)mp.get("firstName"));
			patient.setLastName((String)mp.get("lastName"));
			patient.setDateOfBirth((String)mp.get("dateOfBirth"));
			patient.setNationalId(Util.doubleToLong((Double)mp.get("nationalId")));
			patient.setGender(Util.doubleToInt((Double)mp.get("gender")));
			patient.setEmail((String)mp.get("email"));
			patient.setPhoneNumber((String)mp.get("phoneNumber"));
			patient.setAddress((String)mp.get("address"));
			// adding the patient to the table view
			dataList.add(patient);
		}

        // Setting up filtering
        final FilteredList<PatientDto> filteredData = new FilteredList<PatientDto>(dataList);
        filterField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                filteredData.setPredicate(new Predicate<PatientDto>() {
                    public boolean test(PatientDto patient) {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseFilter = newValue.toLowerCase();
                        String doctorFirstName = patient.getFirstName().toLowerCase();
                        String doctorLastName = patient.getLastName().toLowerCase();

                        return doctorFirstName.contains(lowerCaseFilter) ||
                                doctorLastName.contains(lowerCaseFilter);
                    }
                });

                // Update row colors after filtering
                tableview.refresh();
            }
        });

        SortedList<PatientDto> sortedData = new SortedList<PatientDto>(filteredData);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());
        tableview.setItems(sortedData);
    }
    
    // the function to be executed when the button add is clicked
    public void addPatient(ActionEvent event) throws IOException{
    	
        Parent secondView = FXMLLoader.load(getClass().getResource("add_patient_doctor.fxml"));
        Scene secondScene = new Scene(secondView);

        // Get the current stage (window) using the event's source
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        window.setScene(secondScene);
        window.show();
    	
    }
 // Event Listener on Button[#menuButton].onAction
    @FXML
	private void handleMenuButtonAction(ActionEvent event) {
		
		animation = new Timeline(
                new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        double targetWidth = 60;
                    	if (anchorPane.getPrefWidth() == 210) {
                    		anchorPane.setPrefWidth(targetWidth);  
                    		updateButtons(targetWidth);
                    		return; 
                		}
                    	targetWidth = 210;
                		anchorPane.setPrefWidth(targetWidth);
                		anchorPane.toFront();
                		updateButtons(targetWidth);

                    }
                }),
                new KeyFrame(Duration.millis(200))
        );
		animation.playFromStart();
	}
	@FXML
	private void handledashBoardButtonAction(ActionEvent event) {
		// load dashboard fxml file
		try {
            // Load the first side
            Parent secondView = FXMLLoader.load(getClass().getResource("doctor-page-dashboard.fxml"));
            Scene secondScene = new Scene(secondView);

            // Get the current stage (window) using the event's source
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            window.setScene(secondScene);
            window.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
		dashBoardButton.setStyle("-fx-background-color : #ffffff; -fx-text-fill:#185FA1; ");
		dashBoardIcon.setStyle("-fx-fill :  #185FA1 ;");
		patientsButton.setStyle("-fx-background-color : #185FA1   ;  -fx-text-fill: white; ");
		patientsIcon.setStyle("-fx-fill :  #ffffff; ;");
		historyButton.setStyle("-fx-background-color : #185FA1   ;  -fx-text-fill: white; ");
		historyIcon.setStyle("-fx-fill :  #ffffff; ;");
	}
	@FXML
	private void handlePatientsButtonAction(ActionEvent event) throws IOException {
		// load patients fxml file
		patientsButton.setStyle("-fx-background-color : #ffffff; -fx-text-fill:#185FA1; ");
		patientsIcon.setStyle("-fx-fill :  #185FA1 ;");
		dashBoardButton.setStyle("-fx-background-color : #185FA1    ; -fx-text-fill: white; ");
		dashBoardIcon.setStyle("-fx-fill :  #ffffff; ;");
		historyButton.setStyle("-fx-background-color : #185FA1   ;  -fx-text-fill: white; ");
		historyIcon.setStyle("-fx-fill :  #ffffff; ;");
		
		 if (event.getEventType().equals(ActionEvent.ACTION)) {
	            Parent secondView = FXMLLoader.load(getClass().getResource("patients-records.fxml"));
	            Scene secondScene = new Scene(secondView);

	            // Get the current stage (window) using the event's source
	            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

	            // Set the new scene on the current stage
	            window.setScene(secondScene);
	            window.show();
		 }
		
	}
	@FXML
	private void handleHistoryButtonAction(ActionEvent event) {
		historyButton.setStyle("-fx-background-color : #ffffff; -fx-text-fill:#185FA1; ");
		historyIcon.setStyle("-fx-fill :  #185FA1 ;");
		dashBoardButton.setStyle("-fx-background-color : #185FA1  ;   -fx-text-fill: white; ");
		dashBoardIcon.setStyle("-fx-fill :  #ffffff; ;");
		patientsButton.setStyle("-fx-background-color : #185FA1  ;   -fx-text-fill: white; ");
		patientsIcon.setStyle("-fx-fill :  #ffffff; ;");
	}
	
	
	
	
private void updateButtons (double width) {
		
		if (width != 210){
			menuButton.setPrefWidth(60);
    		menuButton.setTextAlignment(TextAlignment.LEFT);
    		menuButton.setText("");
    		dashBoardButton.setPrefWidth(60);
    		dashBoardButton.setTextAlignment(TextAlignment.LEFT);
    		dashBoardButton.setText("");
    		patientsButton.setPrefWidth(60);
    		patientsButton.setTextAlignment(TextAlignment.LEFT);
    		patientsButton.setText("");
    		historyButton.setPrefWidth(60);
    		historyButton.setTextAlignment(TextAlignment.LEFT);
    		historyButton.setText("");
    		return ;
		}
		menuButton.setPrefWidth(210);
		menuButton.setTextAlignment(TextAlignment.LEFT);
		menuButton.setText("   Menu");
		dashBoardButton.setPrefWidth(210);
		dashBoardButton.setTextAlignment(TextAlignment.LEFT);
		dashBoardButton.setText("   Statistics");
		patientsButton.setPrefWidth(210);
		patientsButton.setTextAlignment(TextAlignment.LEFT);
		patientsButton.setText("   Patients");
		historyButton.setPrefWidth(210);
		historyButton.setTextAlignment(TextAlignment.LEFT);
		historyButton.setText("   Doctors");
		
	}
}