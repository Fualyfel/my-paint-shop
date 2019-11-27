package model;

import controller.Controller;
import controller.Controller.Drag;
import controller.Controller.DragStarter;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Model {
	
	double startingX = 0.0;
	double startingY = 0.0;
	
	private Shape shape;
	
	public void addShapeToPane(String shapeName, MouseEvent event, Pane pane) {
			((Node) event.getSource()).startFullDrag();
			startingX = event.getX();
			startingY = event.getY();
			System.out.println("Drag Detected: X : " + startingX + " Y: " + startingY);
			shape = createShape(shapeName, startingX, startingY);
			pane.getChildren().add(shape);
//			shape.addEventHandler(MouseEvent.DRAG_DETECTED, new DragStarter());
//			shape.setOnMouseDragOver(new Drag());
	}
	
	public Shape createShape(String shapeName, double startingX, double startingY) {
		if (shapeName.equalsIgnoreCase("rectangle"))
			return new Rectangle(startingX, startingY);
		else if (shapeName.equalsIgnoreCase("ellipse"))
			return new Ellipse(startingX, startingY);
		else if (shapeName.equalsIgnoreCase("triangle"))
			return new Triangle(startingX, startingY);
		else
			return null;
		
	}
	
	public void drawShape(MouseEvent event, Shape shape) {
		((Drawable) shape).draw(event, startingX, startingY);
	}
}
