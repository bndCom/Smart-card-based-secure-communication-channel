package clientgui;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class adminPanelController implements Initializable {
    @FXML
    private TableView<PatientDto>  tableview;
    @FXML
    private TableColumn<PatientDto,String> ID; // Assuming Doctor has an ID property
    @FXML
    private TableColumn<PatientDto,String>  LastName;
    @FXML
    private TableColumn<PatientDto,String>  Name;
    @FXML
    private TableColumn<PatientDto,String>  tel;
    @FXML
    private TableColumn<PatientDto,String>  LastSession;
    @FXML
    private TableColumn<PatientDto,String>  Record;
    @FXML
    private TableColumn<PatientDto, Void>  tools; // Added for tools column
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
    
    private ObservableList<PatientDto> dataList = FXCollections.observableArrayList();
    @FXML
    private Label AdminName;

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
		try {
			Main.admin.addNewPatient("khalil", "bobo", "2021-05-08", (long)28, 34, "khgmail.com", "0779", "contante");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// getting all patients from the database
		List<Map<String, Object>> mapList = new LinkedList<Map<String, Object>>();
		try {
			mapList = Main.admin.getAllPatients();
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
}