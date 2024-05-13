package clientgui;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DoctorDashBoardController {
	

	@FXML
	private BarChart<CategoryAxis, NumberAxis> patientsGenderDuringWeekBarChart ;
	private XYChart.Series series1 = new XYChart.Series();
	private XYChart.Series series2 = new XYChart.Series();
	@FXML
	private LineChart<?, ?> patientsDuringWeekLineChart ;
	@FXML
	private PieChart patientsGenderPieChart ;    
	@FXML
	private Button menuButton , dashBoardButton , patientsButton , historyButton ;
	@FXML
	private SVGPath menuIcon , dashBoardIcon , patientsIcon , historyIcon ;
	@FXML
	private AnchorPane anchorPane ;
	@FXML
	private Text patientsNumber ;
	@FXML
	private Text sessionsNumber ;
	@FXML
	private Text sessionsNumberToday ;
	@FXML
	private Text doctorName ;
	@FXML
	private ImageView doctorPicture ;
	
	private Timeline animation;
	
	@FXML
	private void initialize() {
		
		// assign doctorName and picture and add them
		doctorName.setText("Dr. Mohcen Mokhati");
		Image dynamicImage = getDynamicImageFromBackend(); // Method to get image from backend
        doctorPicture.setPreserveRatio(false); // Set preserveRatio to false
        doctorPicture.setImage(dynamicImage);
        // Calculate circle clip dimensions and position
        double radius = Math.min(doctorPicture.getFitWidth(), doctorPicture.getFitHeight()) / 2.0;
        double centerX = doctorPicture.getFitWidth() / 2.0;
        double centerY = doctorPicture.getFitHeight() / 2.0;

        // Create a Circle clip
        Circle clip = new Circle(centerX, centerY, radius);
        doctorPicture.setClip(clip);
        // get number of patients
        patientsNumber.setText("1200");
        // get number of sessions of the doctor
        sessionsNumber.setText("14678");
        // get number of sessions today
        sessionsNumberToday.setText("124");
        
        // do the barchart
		patientsGenderDuringWeekBarChart.setTitle("Men and women sessions 5 days");
       
 
        series1.setName("male");       
        series1.getData().add(new XYChart.Data("5-11-2024", 5));
        series1.getData().add(new XYChart.Data("6-11-2024", 15));
        series1.getData().add(new XYChart.Data("7-11-2024", 12));
        series1.getData().add(new XYChart.Data("8-11-2024", 0));
        series1.getData().add(new XYChart.Data("9-11-2024", 2));
         
        series2.setName("female");
        series2.getData().add(new XYChart.Data("5-11-2024", 8));
        series2.getData().add(new XYChart.Data("6-11-2024", 1));
        series2.getData().add(new XYChart.Data("7-11-2024", 43));
        series2.getData().add(new XYChart.Data("8-11-2024", 25));
        series2.getData().add(new XYChart.Data("9-11-2024", 35));

        patientsGenderDuringWeekBarChart.getData().addAll(series1, series2);
        
        
        // do the lineChart
        XYChart.Series series = new XYChart.Series();
        series.setName("Patients through the year");
        //populating the series with data
        series.getData().add(new XYChart.Data("5-11-2024", 13));
        series.getData().add(new XYChart.Data("6-11-2024", 16));
        series.getData().add(new XYChart.Data("7-11-2024", 55));
        series.getData().add(new XYChart.Data("8-11-2024", 25));
        series.getData().add(new XYChart.Data("9-11-2024", 37));
        patientsDuringWeekLineChart.setTitle("Patients number last 5 days");
        patientsDuringWeekLineChart.getData().add(series);
        // do the pieChart
        patientsGenderPieChart.setTitle("Gender disturbution in our system");
        //Preparing ObservbleList object         
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
           new PieChart.Data("Males", 550), 
           new PieChart.Data("Females", 650));
        patientsGenderPieChart.setData(pieChartData);

		
    }
	private Image getDynamicImageFromBackend() {
        // Simulate fetching image data from backend
        return new Image("file:C:/Users/ya727/Pictures/Me/IMG_20240510_173241_045.jpg");
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
		// Create a scale transition
        /*ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), anchorPane);
        scaleTransition.setFromX(1); // Initial scale X
        scaleTransition.setFromY(1); // Initial scale Y
		if (anchorPane.getPrefWidth() == 210) {
			anchorPane.setPrefWidth(60);
			return; 
		}
		anchorPane.setPrefWidth(210);
        anchorPane.toFront();
*/
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
		// load history fxml file
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
		historyButton.setText("   History");
		
	}
}
