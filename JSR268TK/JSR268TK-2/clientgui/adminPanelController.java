package application;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class adminPanelController implements Initializable {
    @FXML
    private TableView<Doctor>  tableview;
    @FXML
    private TableColumn<Doctor,String> ID; // Assuming Doctor has an ID property
    @FXML
    private TableColumn<Doctor,String>  LastName;
    @FXML
    private TableColumn<Doctor,String>  Name;
    @FXML
    private TableColumn<Doctor,String>  tel;
    @FXML
    private TableColumn<Doctor,String>  LastSession;
    @FXML
    private TableColumn<Doctor,String>  Record;
    @FXML
    private TableColumn<Doctor, Void>  tools; // Added for tools column
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
    
    
    
    private ObservableList<Doctor> dataList = FXCollections.observableArrayList();
	@FXML
	private Label AdminName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    	ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        LastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        LastSession.setCellValueFactory(new PropertyValueFactory<>("lastSession"));
        Record.setCellValueFactory(new PropertyValueFactory<>("record"));
        tools.setCellValueFactory(new PropertyValueFactory("tools"));


        // Custom cell factory for the tools column
         tools.setCellFactory(column -> {
            return new TableCell<Doctor, Void>() {
            	
                final Button btn1 = new Button("show");
                final Button btn2 = new Button("delete");
                final Button btn3 = new Button("edit");
                {
                    btn1.setOnAction(event -> {
                        Doctor doctor = getTableView().getItems().get(getIndex());
                    });
                    btn2.setOnAction(event -> {
                        Doctor doctor = getTableView().getItems().get(getIndex());
                    });
                    btn3.setOnAction(event -> {
                        Doctor doctor = getTableView().getItems().get(getIndex());
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
        });

        // Creating some sample Doctor objects and adding them to the dataList
         Doctor doctor1 = new Doctor("1", "Doe", "John", "123456789", "2024-05-08", "Record 1");
         Doctor doctor2 = new Doctor("2", "Smith", "Alice", "987654321", "2024-05-09", "Record 2");
         Doctor doctor3 = new Doctor("3", "Johnson", "Michael", "555555555", "2024-05-10", "Record 3");
         Doctor doctor4 = new Doctor("4", "Williams", "Emily", "111111111", "2024-05-11", "Record 4");
         Doctor doctor5 = new Doctor("5", "Brown", "David", "222222222", "2024-05-12", "Record 5");
         Doctor doctor6 = new Doctor("6", "Jones", "Jessica", "333333333", "2024-05-13", "Record 6");

        dataList.addAll(doctor1,doctor2,doctor3,doctor4,doctor5,doctor6); // Add other doctors similarly

        // Setting up filtering
        FilteredList<Doctor> filteredData = new FilteredList<>(dataList, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(doctor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String doctorFirstName = doctor.getName().toLowerCase();
                String doctorLastName = doctor.getLastName().toLowerCase();

                return doctorFirstName.contains(lowerCaseFilter) ||
                        doctorLastName.contains(lowerCaseFilter);
            });

            // Update row colors after filtering
            tableview.refresh();
        });

        SortedList<Doctor> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());
        tableview.setItems(sortedData);
    }
}
