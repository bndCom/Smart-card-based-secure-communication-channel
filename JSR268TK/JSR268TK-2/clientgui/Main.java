package clientgui;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jsr268gp.sampleclient.SessionAdmin;
import jsr268gp.sampleclient.SessionDoctor;

public class Main extends Application {
 
	public static CardChannel canal;
	public static CardTerminal cad = null;
	public static Card c = null;
	public static SessionAdmin admin = new SessionAdmin(canal, "http://localhost:8080");
	public static SessionDoctor doctor = new SessionDoctor(canal, "http://localhost:8080");
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	
        Parent root = FXMLLoader.load(getClass().getResource("homePage1.fxml"));
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false); 
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
