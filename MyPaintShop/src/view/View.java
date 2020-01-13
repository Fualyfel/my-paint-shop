package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 * The starting point of the application.
 * @author Fawaz
 *
 */
public class View extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PrimaryStage.fxml"));
        Parent root = loader.load(getClass().getResource("PrimaryStage.fxml"));
        primaryStage.setTitle("My Paint Shop");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene); 
        primaryStage.show();
		
	}

}
