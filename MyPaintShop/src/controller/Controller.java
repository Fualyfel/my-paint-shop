package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Drawable;
import model.Ellipse;
import model.Rectangle;
import model.Triangle;

public class Controller {

	@FXML
	Pane mainPane;
	@FXML
	Canvas canvas;
	@FXML
	ToggleButton circleToggle;
	@FXML
	ToggleButton rectangleToggle;
	@FXML
	ToggleButton triangleToggle;
	@FXML
	ToggleButton lineToggle;
	@FXML
	ColorPicker borderColor;
	@FXML
	ColorPicker fillColor;

	ToggleGroup shapeChooser = new ToggleGroup();

	Shape shape;

	double startingX = 0.0;
	double startingY = 0.0;
	double terminalX = 0.0;
	double terminalY = 0.0;

	/*
	 * initialize() is called whenever the application is launched. ( similar to
	 * start() )
	 */
	@FXML
	private void initialize() {
		bindCanvasToPane();
		canvas.addEventHandler(MouseEvent.DRAG_DETECTED, new DragStarter());
		canvas.setOnMouseDragOver(new Drag());
		clipChildren(mainPane);
		circleToggle.setToggleGroup(shapeChooser);
		triangleToggle.setToggleGroup(shapeChooser);
		rectangleToggle.setToggleGroup(shapeChooser);
		lineToggle.setToggleGroup(shapeChooser);
		borderColor.setValue(Color.BLACK);
	}

	/*
	 * starts the dragging process for shape drawing
	 */
	class DragStarter implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			((Node) event.getSource()).startFullDrag();
			if (shapeChooser.getSelectedToggle() != lineToggle) {
				startingX = event.getX();
				startingY = event.getY();
				System.out.println("Drag Detected: X : " + startingX + " Y: " + startingY);

				if (shapeChooser.getSelectedToggle() == circleToggle) {
					shape = new Ellipse(startingX, startingY);
				} else if (shapeChooser.getSelectedToggle() == rectangleToggle) {
					shape = new Rectangle(startingX, startingY);
				} else if (shapeChooser.getSelectedToggle() == triangleToggle) {
					shape = new Triangle(startingX, startingY);
				}
				shape.addEventHandler(MouseEvent.DRAG_DETECTED, new DragStarter());
				shape.setOnMouseDragOver(new Drag());
				mainPane.getChildren().add(shape);
			}
			else {
		        GraphicsContext context = canvas.getGraphicsContext2D();
		        context.setFill(Color.BLACK);
		        context.beginPath();
		        context.moveTo(event.getX(), event.getY());
		        context.stroke();
			}
		}
	}

	class Drag implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (shapeChooser.getSelectedToggle() != lineToggle) {
			((Drawable) shape).draw(event, startingX, startingY);
			}
			else {
	            final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
	            graphicsContext.lineTo(event.getX(), event.getY());
	            graphicsContext.setLineWidth(5);
	            graphicsContext.stroke();
	            
			}
		}

	}

	/*
	 * method that helps with resizing the canvas
	 */
	public void bindCanvasToPane() {
		canvas.widthProperty().bind(mainPane.widthProperty());
		canvas.heightProperty().bind(mainPane.heightProperty());
	}

	/*
	 * handles clipping (when the shape is out of the pane.)
	 */
	static void clipChildren(Region pane) {

		final javafx.scene.shape.Rectangle outputClip = new javafx.scene.shape.Rectangle();
		pane.setClip(outputClip);

		pane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
			outputClip.setWidth(newValue.getWidth());
			outputClip.setHeight(newValue.getHeight());
		});
	}

}
