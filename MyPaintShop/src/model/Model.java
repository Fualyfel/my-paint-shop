package model;

import controller.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Model {

	double startingX = 0.0;
	double startingY = 0.0;

	private Shape shape;
	
	private Controller controller;

	public Shape createShape(String shapeName) {
		if (shapeName.equalsIgnoreCase("rectangle"))
			return new Rectangle();
		else if (shapeName.equalsIgnoreCase("ellipse"))
			return new Ellipse();
		else if (shapeName.equalsIgnoreCase("triangle"))
			return new Triangle();
		else
			return null;
	}

	public void addShapeToPane(String shapeName, MouseEvent event, Pane pane) {
		startingX = event.getX();
		startingY = event.getY();
		System.out.println("Drag Detected: X : " + startingX + " Y: " + startingY);
		shape = createShape(shapeName);
		pane.getChildren().add(shape);
		shape.addEventHandler(MouseEvent.DRAG_DETECTED, controller.new DragStarter());
		shape.setOnMouseDragOver(controller.new Drag());
	}

	public void drawShape(MouseEvent event, Shape shape) {
		((Drawable) shape).draw(event, startingX, startingY);
	}

	public void drawBrush(MouseEvent event, GraphicsContext context) {
		context.setStroke(Color.BLACK);
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			context.setLineWidth(3);
			context.beginPath();
			context.moveTo(event.getX(), event.getY());
			context.lineTo(event.getX(), event.getY());
			context.stroke();
		}
		else {
			context.lineTo(event.getX(), event.getY());
			context.setLineWidth(3);
			context.stroke();
		}
	}

	public Shape getShape() {
		return shape;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
