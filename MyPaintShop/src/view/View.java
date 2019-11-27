package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private FXMLLoader loader = new FXMLLoader(getClass().getResource("PrimaryStage.fxml"));
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
        Parent root = loader.load(getClass().getResource("PrimaryStage.fxml"));
        primaryStage.setTitle("My Paint Shop");
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene); 
        primaryStage.show();
		
	}

}
