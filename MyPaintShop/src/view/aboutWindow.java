package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class aboutWindow extends Stage {
	FlowPane root = new FlowPane();
	private final Label aboutInfo = new Label("A software project made by Team 11\n"
							+"Made with love by Fawaz Alesayi (@Fulayfel on github),\nAbdullah Alfaifi,\nWaleed Alfaifi,\nand Yazeed Aljohany");
	Scene scene;
	
	public aboutWindow() {
		root.setPadding(new Insets(15));
		root.getChildren().addAll(aboutInfo);
		scene = new Scene(root, 500, 200);
		this.setTitle("About");
		this.setScene(scene);
	}
}
