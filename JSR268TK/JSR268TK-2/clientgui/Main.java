package clientgui;
import javax.smartcardio.CardChannel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jsr268gp.sampleclient.SessionAdmin;

/**
 *
 * @author Ali Mahdi
 */
public class Main extends Application {
	static CardChannel canal;
	public static SessionAdmin admin = new SessionAdmin(canal, "http://localhost:8080");
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("admin-panel.fxml"));
        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNIFIED);

        scene.setFill(Color.DARKGRAY);      
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

