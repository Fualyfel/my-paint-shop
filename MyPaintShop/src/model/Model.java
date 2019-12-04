package model;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import controller.Controller;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
/*
 * The Model handles most of the logic in the application, creating shapes and adding them to the
 * pane. selecting shapes, saving the image, and so on.
 */
public class Model {

	double startingX = 0.0;
	double startingY = 0.0;

	private Shape shape;
//	private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	private Shape selectedShape;
	private Stack<Shape> states = new Stack<Shape>();
	private Controller controller;
//	public Stack<State> DrawStates = new Stack<State>();

	public Shape createShape(String shapeName) {
		if (shapeName.equalsIgnoreCase("rectangle"))
			return new Rectangle();
		else if (shapeName.equalsIgnoreCase("ellipse"))
			return new Ellipse();
		else if (shapeName.equalsIgnoreCase("triangle"))
			return new Triangle();
		else if (shapeName.equalsIgnoreCase("line"))
			return new Line();
		else
			return null;
		
	}

	public void addShapeToPane(String shapeName, MouseEvent event, Pane pane) {
		startingX = event.getX();
		startingY = event.getY();
		System.out.println("Drag Detected: X : " + startingX + " Y: " + startingY);
		shape = createShape(shapeName);
		pane.getChildren().add(shape);
		states.push(shape);
		shape.addEventHandler(MouseEvent.DRAG_DETECTED, controller.new DragStarter());
		shape.setOnMouseDragOver(controller.new Drag());
		shape.setOnMousePressed(controller.new BrushDraw());
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
	
	/*
	 * selects the shape by marking it with a border
	 */
	public void selectShape(Shape shape) {
		
	}
	
	/*
	 * deletes the shape by removing it from the pane.
	 */
	public void deleteShape(Shape shape, Pane pane) {
		
	}
	
	/*
	 * saves the pane and canvas as an image
	 */
	public void save(Pane pane, Canvas canvas) {
		
	}
	
	/*
	 * loads the image into the pane
	 */
	public void load(Pane pane, Canvas canvas) {
		
	}
	
	public void undo() {
		try {
			controller.mainPane.getChildren().remove(states.pop());
		} catch (EmptyStackException e) {
			System.out.println("No history.");
		}
	}

	public Shape getShape() {
		return shape;
	}
	
	public Stack<Shape> getStack() {
		return states;
	}

	public Controller getController() {
		return controller;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
}
