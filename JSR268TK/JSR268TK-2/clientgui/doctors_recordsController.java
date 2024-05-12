package clientgui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;








import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class doctors_recordsController implements Initializable {
    @FXML
    private TableView<DoctorDto>  tableview;
    @FXML
    private TableColumn<DoctorDto,String> ID; // Assuming Doctor has an ID property
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
    private TableColumn<DoctorDto, Void>  tools; // Added for tools column
    @FXML
    private TextField filterField;
    @FXML
    private Button addBtn;
    @FXML
    private Label adminName;
    @FXML
    private Button Menu;
    @FXML
    private Button Doctors;
    @FXML
    private Button Dashboard;
    @FXML
    private Button History;
    
    private ObservableList<DoctorDto> dataList = FXCollections.observableArrayList();
    @FXML
    private Label AdminName;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<DoctorDto, String>("patientId"));
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
        // Creating some sample Doctor objects and adding them to the dataList
//        Doctor doctor1 = new Doctor("1", "Doe", "John", "123456789", "2024-05-08", "Record 1");
//        Doctor doctor2 = new Doctor("2", "Smith", "Alice", "987654321", "2024-05-09", "Record 2");
//        Doctor doctor3 = new Doctor("3", "Johnson", "Michael", "555555555", "2024-05-10", "Record 3");
//        Doctor doctor4 = new Doctor("4", "Williams", "Emily", "111111111", "2024-05-11", "Record 4");
//        Doctor doctor5 = new Doctor("5", "Brown", "David", "222222222", "2024-05-12", "Record 5");
//        Doctor doctor6 = new Doctor("6", "Jones", "Jessica", "333333333", "2024-05-13", "Record 6");
		Iterator<Map<String, Object>> itr = mapList.iterator();
		while(itr.hasNext()){
			// constructing the object from the received json
			DoctorDto doctor = new DoctorDto();
			Map<String, Object> mp = itr.next();
			doctor.setDoctorId(Util.doubleToLong((Double)mp.get("patientId")));
			doctor.setFirstName((String)mp.get("firstName"));
			doctor.setLastName((String)mp.get("lastName"));
			doctor.setNationalId(Util.doubleToLong((Double)mp.get("nationalId")));
			doctor.setGender(Util.doubleToInt((Double)mp.get("gender")));
			doctor.setEmail((String)mp.get("email"));
			doctor.setPhoneNumber((String)mp.get("phoneNumber"));
			doctor.setAddress((String)mp.get("address"));
			// adding the patient to the table view
			dataList.add(doctor);
		}

        // Setting up filtering
        final FilteredList<DoctorDto> filteredData = new FilteredList<DoctorDto>(dataList);
        filterField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                filteredData.setPredicate(new Predicate<DoctorDto>() {
                    public boolean test(DoctorDto patient) {
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

        SortedList<DoctorDto> sortedData = new SortedList<DoctorDto>(filteredData);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());
        tableview.setItems(sortedData);
    }
    
    // the function to be executed when the button add is clicked
    public void addDoctor(ActionEvent event) throws IOException{
    	
        Parent root = FXMLLoader.load(getClass().getResource("add_doctor.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage stage = new Stage();
        
        stage.initStyle(StageStyle.UNIFIED);
        scene.setFill(Color.DARKGRAY);      
        stage.setScene(scene);
        stage.show();
    	
    }
}

