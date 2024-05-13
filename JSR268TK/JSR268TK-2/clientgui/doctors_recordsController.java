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
import javafx.fxml.Initializable;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.sql.Date;

public class doctors_recordsController implements Initializable {
    @FXML
    private TableView<DoctorDto> tableview;
    @FXML
    private TableColumn<DoctorDto,String> ID;
    @FXML
    private TableColumn<DoctorDto,String>  LastName;
    @FXML
    private TableColumn<DoctorDto,String>  Name;
    @FXML
    private TableColumn<DoctorDto,String>  tel;
    @FXML
    private TableColumn<DoctorDto,String>  LastSession;
    @FXML
    private TableColumn<DoctorDto,String>  Record;
    @FXML
    private TableColumn<DoctorDto,Void>  tools;
    @FXML
    private TextField filterField;
    @FXML
    private Button addBtn;
    @FXML
    private Label AdminName;
    @FXML
    private Button menuButton;
    @FXML
    private Button dashBoardButton;
    @FXML
    private Button patientsButton;
    @FXML
    private Button doctorsButton;
    @FXML
    private AnchorPane anchorPane ;
    @FXML
    private SVGPath menuIcon , dashBoardIcon , doctorsIcon , patientsIcon ;
    private Timeline animation;

    private ObservableList<DoctorDto> dataList = FXCollections.observableArrayList();
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("doctorId"));
        LastName.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("lastName"));
        Name.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("firstName"));
        tel.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("phoneNumber"));
        LastSession.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("email"));
        Record.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("address"));
        tools.setCellValueFactory(new PropertyValueFactory<DoctorDto, Void>("tools"));

        // Custom cell factory for the tools column
        tools.setCellFactory(new Callback<TableColumn<DoctorDto, Void>, TableCell<DoctorDto, Void>>() {
            public TableCell<DoctorDto, Void> call(TableColumn<DoctorDto, Void> column) {
                return new TableCell<DoctorDto, Void>() {
                    final Button btn1 = new Button("show");
                    final Button btn2 = new Button("delete");
                    final Button btn3 = new Button("edit");

                    {
                        btn1.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                            public void handle(javafx.event.ActionEvent event) {
                                DoctorDto doctor = getTableView().getItems().get(getIndex());
                            }
                        });
                        btn2.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                            public void handle(javafx.event.ActionEvent event) {
                                DoctorDto doctor = getTableView().getItems().get(getIndex());
                            }
                        });
                        btn3.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
                            public void handle(javafx.event.ActionEvent event) {
                                DoctorDto doctor = getTableView().getItems().get(getIndex());
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
            mapList = Main.admin.getAllDoctors();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Iterator<Map<String, Object>> itr = mapList.iterator();
        while(itr.hasNext()){
            // constructing the object from the received json
            DoctorDto doctor = new DoctorDto();
            Map<String, Object> mp = itr.next();
            doctor.setDoctorId(Util.doubleToLong((Double)mp.get("doctorId")));
            doctor.setFirstName((String)mp.get("firstName"));
            doctor.setLastName((String)mp.get("lastName"));
            doctor.setGender(Util.doubleToInt((Double)mp.get("gender")));
            doctor.setEmail((String)mp.get("email"));
            doctor.setPhoneNumber((String)mp.get("phoneNumber"));
            doctor.setAddress((String)mp.get("address"));
            // adding the doctor to the table view
            dataList.add(doctor);
        }

        // Setting up filtering
        final FilteredList<DoctorDto> filteredData = new FilteredList<DoctorDto>(dataList);
        filterField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                filteredData.setPredicate(new Predicate<DoctorDto>() {
                    public boolean test(DoctorDto doctor) {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        String lowerCaseFilter = newValue.toLowerCase();
                        String doctorFirstName = doctor.getFirstName().toLowerCase();
                        String doctorLastName = doctor.getLastName().toLowerCase();

                        return doctorFirstName.contains(lowerCaseFilter) ||
                                doctorLastName.contains(lowerCaseFilter);
                    }
                });

                // Update row colors after filtering
                tableview.refresh();
            }
        });

        SortedList<DoctorDto> sortedData = new SortedList<DoctorDto>(filteredData);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());
        tableview.setItems(sortedData);
    }

    // the function to be executed when the button add is clicked
    public void addDoctor(ActionEvent event) throws IOException{

        Parent secondView = FXMLLoader.load(getClass().getResource("add_doctor.fxml"));
        Scene secondScene = new Scene(secondView);

        // Get the current stage (window) using the event's source
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // Set the new scene on the current stage
        window.setScene(secondScene);
        window.show();


    }
    @FXML
	private void handleMenuButtonAction() {
		
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
            Parent secondView = FXMLLoader.load(getClass().getResource("AdminDashBoardController.fxml"));
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
		doctorsButton.setStyle("-fx-background-color : #185FA1   ;  -fx-text-fill: white; ");
		doctorsIcon.setStyle("-fx-fill :  #ffffff; ;");
	}
	@FXML
	private void handlePatientsButtonAction(ActionEvent event) throws IOException {
		// load patients fxml file
		patientsButton.setStyle("-fx-background-color : #ffffff; -fx-text-fill:#185FA1; ");
		patientsIcon.setStyle("-fx-fill :  #185FA1 ;");
		dashBoardButton.setStyle("-fx-background-color : #185FA1    ; -fx-text-fill: white; ");
		dashBoardIcon.setStyle("-fx-fill :  #ffffff; ;");
		doctorsButton.setStyle("-fx-background-color : #185FA1   ;  -fx-text-fill: white; ");
		doctorsIcon.setStyle("-fx-fill :  #ffffff; ;");
		
		 if (event.getEventType().equals(ActionEvent.ACTION)) {
	            Parent secondView = FXMLLoader.load(getClass().getResource("admin-panel.fxml"));
	            Scene secondScene = new Scene(secondView);

	            // Get the current stage (window) using the event's source
	            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

	            // Set the new scene on the current stage
	            window.setScene(secondScene);
	            window.show();
		 }
		
	}
	@FXML
	private void handleDoctrsButtonAction(ActionEvent event) throws IOException {
		// load doctors fxml file
		 if (event.getEventType().equals(ActionEvent.ACTION)) {
	            Parent secondView = FXMLLoader.load(getClass().getResource("doctors_records.fxml"));
	            Scene secondScene = new Scene(secondView);

	            // Get the current stage (window) using the event's source
	            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

	            // Set the new scene on the current stage
	            window.setScene(secondScene);
	            window.show();
		 }
		doctorsButton.setStyle("-fx-background-color : #ffffff; -fx-text-fill:#185FA1; ");
		doctorsIcon.setStyle("-fx-fill :  #185FA1 ;");
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
    		doctorsButton.setPrefWidth(60);
    		doctorsButton.setTextAlignment(TextAlignment.LEFT);
    		doctorsButton.setText("");
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
		doctorsButton.setPrefWidth(210);
		doctorsButton.setTextAlignment(TextAlignment.LEFT);
		doctorsButton.setText("   Doctors");
		
	}
    }